/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.churchmutual.core.service.impl;

import com.churchmutual.core.model.CMICAccountEntry;
import com.churchmutual.core.model.CMICAccountEntryDisplay;
import com.churchmutual.core.model.CMICOrganization;
import com.churchmutual.core.service.CMICOrganizationLocalService;
import com.churchmutual.core.service.base.CMICAccountEntryLocalServiceBaseImpl;
import com.churchmutual.rest.AccountWebService;
import com.churchmutual.rest.ProducerWebService;
import com.churchmutual.rest.TransactionWebService;
import com.churchmutual.rest.model.CMICAccountDTO;

import com.churchmutual.rest.model.CMICProducerDTO;
import com.churchmutual.rest.model.CMICTransactionAccountSummaryDTO;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.business.AccountEntryBusinessService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Validator;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * The implementation of the cmic account entry local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICAccountEntryLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICAccountEntryLocalServiceBaseImpl
 */
@Component(property = "model.class.name=com.churchmutual.core.model.CMICAccountEntry", service = AopService.class)
public class CMICAccountEntryLocalServiceImpl extends CMICAccountEntryLocalServiceBaseImpl {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never reference this class directly. Use <code>com.churchmutual.core.service.CMICAccountEntryLocalService</code> via injection or a <code>org.osgi.util.tracker.ServiceTracker</code> or use <code>com.churchmutual.core.service.CMICAccountEntryLocalServiceUtil</code>.
	 */
	@Override
	public CMICAccountEntry addAccountEntry(long userId, String accountNumber, String companyNumber)
		throws PortalException {

		CMICAccountDTO cmicAccountDTO = _accountWebService.getAccounts(accountNumber);

		String name = cmicAccountDTO.getAccountName();

		// 5 digit producerCode = 2 digit agentNumber + 3 digit divisionNumber

		String producerCode = cmicAccountDTO.getProducerCode();

		String agentNumber = producerCode.substring(0,2);

		String divisionNumber = producerCode.substring(2,5);

		List<CMICProducerDTO> cmicProducerDTOs = _producerWebService.getProducers(
			agentNumber, divisionNumber, StringPool.BLANK, null);

		if (cmicProducerDTOs.isEmpty()) {
			throw new PortalException(String.format("Producer with producerCode %s could not be found", producerCode));
		}

		CMICProducerDTO cmicProducerDTO = cmicProducerDTOs.get(0);

		List<CMICTransactionAccountSummaryDTO> cmicTransactionAccountSummaryDTOs =
			_transactionWebService.getTransactionAccountSummaryByAccounts(new String[]{accountNumber});

		CMICOrganization cmicOrganization = cmicOrganizationPersistence.fetchByProducerId(cmicProducerDTO.getId());

		if (cmicOrganization == null) {
			cmicOrganization = _cmicOrganizationLocalService.addCMICOrganization(userId, cmicProducerDTO.getId());
		}

		CMICAccountEntry cmicAccountEntry = cmicAccountEntryPersistence.fetchByAN_CN(accountNumber, companyNumber);

		if (cmicAccountEntry != null) {
			return cmicAccountEntry;
		}

		AccountEntry accountEntry = _accountEntryBusinessService.createAccountEntry(
			userId, name, userId, cmicOrganization.getOrganizationId());

		long cmicAccountEntryId = counterLocalService.increment(CMICAccountEntry.class.getName());

		cmicAccountEntry = createCMICAccountEntry(cmicAccountEntryId);

		cmicAccountEntry.setAccountEntryId(accountEntry.getAccountEntryId());
		cmicAccountEntry.setAccountNumber(accountNumber);
		cmicAccountEntry.setCompanyNumber(companyNumber);

		if (!cmicTransactionAccountSummaryDTOs.isEmpty()) {
			CMICTransactionAccountSummaryDTO cmicTransactionAccountSummaryDTO = cmicTransactionAccountSummaryDTOs.get(0);

			cmicAccountEntry.setNumExpiredPolicies(cmicTransactionAccountSummaryDTO.getNumExpiredPolicies());
			cmicAccountEntry.setNumFuturePolicies(cmicTransactionAccountSummaryDTO.getNumFuturePolicies());
			cmicAccountEntry.setNumInForcePolicies(cmicTransactionAccountSummaryDTO.getNumInForcePolicies());
			cmicAccountEntry.setTotalBilledPremium(cmicTransactionAccountSummaryDTO.getTotalBilledPremium().toString());
		}

		return cmicAccountEntryPersistence.update(cmicAccountEntry);
	}

	@Override
	public CMICAccountEntry fetchAccountEntry(String accountNumber, String companyNumber) {
		return cmicAccountEntryPersistence.fetchByAN_CN(accountNumber, companyNumber);
	}

	@Override
	public List<CMICAccountEntry> getCMICAccountEntriesByUserIdOrderedByName(long userId, int start, int end) {
		return cmicAccountEntryFinder.findByUserIdOrderedByName(userId, start, end);
	}

	@Override
	public List<CMICAccountEntryDisplay> getCMICAccountEntryDisplays(List<String> cmicAccountEntryIds) {
		List<CMICAccountEntryDisplay> cmicAccountEntryDisplays = new ArrayList<>();

		for (String cmicAccountEntryId : cmicAccountEntryIds) {
			CMICAccountEntry cmicAccountEntry = cmicAccountEntryPersistence.fetchByPrimaryKey(GetterUtil.getLong(cmicAccountEntryId));

			if (Validator.isNotNull(cmicAccountEntry)) {
				cmicAccountEntryDisplays.add(new CMICAccountEntryDisplay(cmicAccountEntry));
			}
		}

		return cmicAccountEntryDisplays;
	}

	@Override
	public List<CMICAccountEntry> getCMICAccountEntriesByUserId(long userId) {
		List<AccountEntryUserRel> accountEntryUserRels =
			_accountEntryUserRelLocalService.getAccountEntryUserRelsByAccountUserId(userId);

		return accountEntryUserRels.stream().map(
			accountEntryUserRel -> cmicAccountEntryPersistence.fetchByAccountEntryId(accountEntryUserRel.getAccountEntryId())
		).filter(
			Objects::nonNull
		).collect(
			Collectors.toList()
		);
	}

	@Reference
	protected AccountEntryBusinessService _accountEntryBusinessService;

	@Reference
	protected AccountEntryLocalService _accountEntryLocalService;

	@Reference
	protected AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	protected AccountWebService _accountWebService;

	@Reference
	protected CMICOrganizationLocalService _cmicOrganizationLocalService;

	@Reference
	protected ProducerWebService _producerWebService;

	@Reference
	protected TransactionWebService _transactionWebService;

}