package com.churchmutual.core.model;

public class CMICAccountEntryDisplay {

	public long getCmicAccountEntryId() {
		return _cmicAccountEntryId;
	}

	public void setCmicAccountEntryId(long cmicAccountEntryId) {
		_cmicAccountEntryId = cmicAccountEntryId;
	}

	public long getAccountEntryId() {
		return _accountEntryId;
	}

	public void setAccountEntryId(long accountEntryId) {
		_accountEntryId = accountEntryId;
	}

	public String getAccountNumber() {
		return _accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public int getNumExpiredPolicies() {
		return _numExpiredPolicies;
	}

	public void setNumExpiredPolicies(int numExpiredPolicies) {
		_numExpiredPolicies = numExpiredPolicies;
	}

	public int getNumFuturePolicies() {
		return _numFuturePolicies;
	}

	public void setNumFuturePolicies(int numFuturePolicies) {
		_numFuturePolicies = numFuturePolicies;
	}

	public int getNumInForcePolicies() {
		return _numInForcePolicies;
	}

	public void setNumInForcePolicies(int numInForcePolicies) {
		_numInForcePolicies = numInForcePolicies;
	}

	public long getProducerId() {
		return _producerId;
	}

	public void setProducerId(long producerId) {
		_producerId = producerId;
	}

	public String getProducerName() {
		return _producerName;
	}

	public void setProducerName(String producerName) {
		_producerName = producerName;
	}

	public String getTotalBilledPremium() {
		return _totalBilledPremium;
	}

	public void setTotalBilledPremium(String totalBilledPremium) {
		_totalBilledPremium = totalBilledPremium;
	}

	private long _cmicAccountEntryId;
	private long _accountEntryId;
	private String _accountNumber;
	private String _name;
	private int _numExpiredPolicies;
	private int _numFuturePolicies;
	private int _numInForcePolicies;
	private String _producerName;
	private long _producerId;
	private String _totalBilledPremium;

}
