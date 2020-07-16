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

import com.churchmutual.commons.util.CollectionsUtil;
import com.churchmutual.core.model.CMICAccountEntry;
import com.churchmutual.core.model.CMICAccountEntryDisplay;
import com.churchmutual.core.model.CMICOrganization;
import com.churchmutual.core.service.CMICAccountEntryLocalService;
import com.churchmutual.core.service.CMICOrganizationLocalService;
import com.churchmutual.core.service.CMICOrganizationLocalServiceUtil;
import com.churchmutual.core.service.base.CMICAccountEntryLocalServiceBaseImpl;
import com.churchmutual.core.service.http.CMICCommissionDocumentServiceSoap;
import com.churchmutual.rest.AccountWebService;
import com.churchmutual.rest.ProducerWebService;
import com.churchmutual.rest.TransactionWebService;
import com.churchmutual.rest.model.CMICAccountDTO;

import com.churchmutual.rest.model.CMICProducerDTO;
import com.churchmutual.rest.model.CMICTransactionAccountSummaryDTO;
import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.model.AccountEntryUserRel;
import com.liferay.account.service.AccountEntryLocalService;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.account.service.AccountEntryOrganizationRelLocalService;
import com.liferay.account.service.AccountEntryOrganizationRelLocalServiceUtil;
import com.liferay.account.service.AccountEntryUserRelLocalService;
import com.liferay.account.service.business.AccountEntryBusinessService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalService;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.PortalUtil;
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
	public CMICAccountEntry addCMICAccountEntry(long userId, String accountNumber, String companyNumber, String accountName, long producerId, String producerCode)
		throws PortalException {

		CMICOrganization cmicOrganization = cmicOrganizationPersistence.fetchByProducerId(producerId);

		if (cmicOrganization == null) {
			cmicOrganization = _cmicOrganizationLocalService.addCMICOrganization(userId, producerId);
		}

		CMICAccountEntry cmicAccountEntry = cmicAccountEntryPersistence.fetchByAN_CN(accountNumber, companyNumber);

		if (cmicAccountEntry != null) {
			return cmicAccountEntry;
		}

		AccountEntry accountEntry = _accountEntryBusinessService.createAccountEntry(
			userId, accountName, userId, cmicOrganization.getOrganizationId());

		if (!_accountEntryOrganizationRelLocalService.hasAccountEntryOrganizationRel(accountEntry.getAccountEntryId(), cmicOrganization.getOrganizationId())) {
			_accountEntryOrganizationRelLocalService.addAccountEntryOrganizationRel(accountEntry.getAccountEntryId(), cmicOrganization.getOrganizationId());
		}

		long cmicAccountEntryId = counterLocalService.increment(CMICAccountEntry.class.getName());

		cmicAccountEntry = createCMICAccountEntry(cmicAccountEntryId);

		cmicAccountEntry.setAccountEntryId(accountEntry.getAccountEntryId());
		cmicAccountEntry.setAccountNumber(accountNumber);
		cmicAccountEntry.setCompanyNumber(companyNumber);

		List<CMICTransactionAccountSummaryDTO> cmicTransactionAccountSummaryDTOs =
			_transactionWebService.getTransactionAccountSummaryByAccounts(new String[]{accountNumber});

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
	public String getAccountEntryName(CMICAccountEntry cmicAccountEntry) {
		AccountEntry accountEntry = _accountEntryLocalService.fetchAccountEntry(
			cmicAccountEntry.getAccountEntryId());

		if (accountEntry == null) {
			_log.error(
				String.format("Account Entry with id %d could not be found", cmicAccountEntry.getAccountEntryId()));

			return StringPool.BLANK;
		}

		return accountEntry.getName();
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
	public List<CMICAccountEntryDisplay> getCMICAccountEntryDisplays(long userId) {
		List<CMICAccountEntry> cmicAccountEntries = getCMICAccountEntriesByUserId(userId);

		List<CMICAccountEntryDisplay> cmicAccountEntryDisplays = new ArrayList<>();

		for (CMICAccountEntry cmicAccountEntry : cmicAccountEntries) {
			cmicAccountEntryDisplays.add(new CMICAccountEntryDisplay(cmicAccountEntry));
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

	@Override
	public String getOrganizationName(CMICAccountEntry cmicAccountEntry) {
		List<AccountEntryOrganizationRel> accountEntryOrganizationRels =
			_accountEntryOrganizationRelLocalService.getAccountEntryOrganizationRels(
				cmicAccountEntry.getAccountEntryId());

		AccountEntryOrganizationRel accountEntryOrganizationRel = CollectionsUtil.getFirst(
				accountEntryOrganizationRels);

		if (accountEntryOrganizationRel == null) {
			_log.error(String.format("Account Entry %d is not related to an organization", cmicAccountEntry.getAccountEntryId()));

			return StringPool.BLANK;
		}

		Organization organization = OrganizationLocalServiceUtil.fetchOrganization(
				accountEntryOrganizationRel.getOrganizationId());

		if (organization == null) {
			_log.error(
				String.format(
					"Organization with id %d could not be found", accountEntryOrganizationRel.getOrganizationId()));

			return StringPool.BLANK;
		}

		 return organization.getName();
	}

	@Override
	public String getProducerCode(CMICAccountEntry cmicAccountEntry) {
		long companyId = PortalUtil.getDefaultCompanyId();

		Organization organization = _organizationLocalService.fetchOrganization(companyId, getOrganizationName(cmicAccountEntry));

		if (organization == null) {
			return StringPool.BLANK;
		}

		CMICOrganization cmicOrganization = _cmicOrganizationLocalService.fetchCMICOrganizationByOrganizationId(
			organization.getOrganizationId());

		if (cmicOrganization == null) {
			_log.error(
				String.format(
					"CMICOrganization with organization id %d could not be found", organization.getOrganizationId()));

			return StringPool.BLANK;
		}

		String divisionNumber = cmicOrganization.getDivisionNumber();
		String agentNumber = cmicOrganization.getAgentNumber();

		return divisionNumber + agentNumber;
	}

	@Reference
	protected AccountEntryBusinessService _accountEntryBusinessService;

	@Reference
	protected AccountEntryLocalService _accountEntryLocalService;

	@Reference
	protected AccountEntryOrganizationRelLocalService _accountEntryOrganizationRelLocalService;

	@Reference
	protected AccountEntryUserRelLocalService _accountEntryUserRelLocalService;

	@Reference
	protected AccountWebService _accountWebService;

	@Reference
	protected CMICOrganizationLocalService _cmicOrganizationLocalService;

	@Reference
	protected OrganizationLocalService _organizationLocalService;

	@Reference
	protected ProducerWebService _producerWebService;

	@Reference
	protected TransactionWebService _transactionWebService;

	private static Log _log = LogFactoryUtil.getLog(CMICAccountEntryLocalServiceImpl.class);
}