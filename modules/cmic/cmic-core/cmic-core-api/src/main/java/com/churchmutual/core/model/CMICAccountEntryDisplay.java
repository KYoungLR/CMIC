package com.churchmutual.core.model;

import com.liferay.account.model.AccountEntry;
import com.liferay.account.service.AccountEntryLocalServiceUtil;

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

		AccountEntry accountEntry = AccountEntryLocalServiceUtil.fetchAccountEntry(cmicAccountEntry.getAccountEntryId());

		_name = accountEntry.getName();
	}

	public long getAccountEntryId() {
		return _accountEntryId;
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

	public String getName() {
		return _name;
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

	public void setName(String name) {
		_name = name;
	}

	public void setProducerCode(String producerCode) {
		_producerCode = producerCode;
	}

	public void setProducerName(String producerName) {
		_producerName = producerName;
	}

	private long _accountEntryId;
	private String _accountNumber;
	private long _cmicAccountEntryId;
	private String _companyNumber;
	private String _name;
	private int _numExpiredPolicies;
	private int _numFuturePolicies;
	private int _numInForcePolicies;
	private String _producerCode;
	private String _producerName;
	private String _totalBilledPremium;

}