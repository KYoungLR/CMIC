package com.churchmutual.core.model;

import com.churchmutual.commons.util.CollectionsUtil;
import com.churchmutual.core.service.CMICOrganizationLocalServiceUtil;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.model.AccountEntryOrganizationRel;
import com.liferay.account.service.AccountEntryLocalServiceUtil;
import com.liferay.account.service.AccountEntryOrganizationRelLocalServiceUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.Organization;
import com.liferay.portal.kernel.service.OrganizationLocalServiceUtil;

import java.util.List;

public class CMICAccountEntryDisplay {

	public CMICAccountEntryDisplay(CMICAccountEntry cmicAccountEntry) {
		_accountEntryId = cmicAccountEntry.getAccountEntryId();
		_accountNumber = cmicAccountEntry.getAccountNumber();
		_cmicAccountEntryId = cmicAccountEntry.getCmicAccountEntryId();
		_companyNumber = cmicAccountEntry.getCompanyNumber();
		_numExpiredPolicies = cmicAccountEntry.getNumExpiredPolicies();
		_numFuturePolicies = cmicAccountEntry.getNumFuturePolicies();
		_numInForcePolicies = cmicAccountEntry.getNumInForcePolicies();
		_totalBilledPremium = cmicAccountEntry.getTotalBilledPremium();

		_populateFields(cmicAccountEntry);
	}

	public long getAccountEntryId() {
		return _accountEntryId;
	}

	public String getAccountName() {
		return _accountName;
	}

	public String getAccountNumber() {
		return _accountNumber;
	}

	public long getCmicAccountEntryId() {
		return _cmicAccountEntryId;
	}

	public String getCompanyNumber() {
		return _companyNumber;
	}

	public int getNumExpiredPolicies() {
		return _numExpiredPolicies;
	}

	public int getNumFuturePolicies() {
		return _numFuturePolicies;
	}

	public int getNumInForcePolicies() {
		return _numInForcePolicies;
	}

	public String getProducerCode() {
		return _producerCode;
	}

	public String getProducerName() {
		return _producerName;
	}

	public String getTotalBilledPremium() {
		return _totalBilledPremium;
	}

	private void _populateFields(CMICAccountEntry cmicAccountEntry) {
		AccountEntry accountEntry = AccountEntryLocalServiceUtil.fetchAccountEntry(
			cmicAccountEntry.getAccountEntryId());

		if (accountEntry == null) {
			_log.warn(
				String.format("Account Entry with id %d could not be found", cmicAccountEntry.getAccountEntryId()));

			return;
		}

		_accountName = accountEntry.getName();

		List<AccountEntryOrganizationRel> accountEntryOrganizationRels =
			AccountEntryOrganizationRelLocalServiceUtil.getAccountEntryOrganizationRels(
				accountEntry.getAccountEntryId());

		AccountEntryOrganizationRel accountEntryOrganizationRel = CollectionsUtil.getFirst(
			accountEntryOrganizationRels);

		if (accountEntryOrganizationRel == null) {
			_log.warn(
				String.format(
					"Account Entry %d is not related to an organization", cmicAccountEntry.getAccountEntryId()));

			return;
		}

		Organization organization = OrganizationLocalServiceUtil.fetchOrganization(
			accountEntryOrganizationRel.getOrganizationId());

		_producerName = organization.getName();

		if (organization == null) {
			_log.warn(
				String.format(
					"Organization with id %d could not be found", accountEntryOrganizationRel.getOrganizationId()));

			return;
		}

		CMICOrganization cmicOrganization = CMICOrganizationLocalServiceUtil.fetchCMICOrganizationByOrganizationId(
			organization.getOrganizationId());

		if (cmicOrganization == null) {
			_log.warn(
				String.format(
					"CMICOrganization with organization id %d could not be found", organization.getOrganizationId()));
		}

		String divisionNumber = cmicOrganization.getDivisionNumber();
		String agentNumber = cmicOrganization.getAgentNumber();

		_producerCode = divisionNumber + agentNumber;
	}

	private static final Log _log = LogFactoryUtil.getLog(CMICAccountEntryDisplay.class);

	private long _accountEntryId;
	private String _accountName;
	private String _accountNumber;
	private long _cmicAccountEntryId;
	private String _companyNumber;
	private int _numExpiredPolicies;
	private int _numFuturePolicies;
	private int _numInForcePolicies;
	private String _producerCode;
	private String _producerName;
	private String _totalBilledPremium;

}