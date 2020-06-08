package com.churchmutual.rest.model;

import java.math.BigDecimal;

/**
 * @author Kayleen Lim
 */
public class CMICTransactionAccountSummaryDTO extends CMICObjectDTO {

	public String getAccountNumber() {
		return _accountNumber;
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

	public BigDecimal getTotalBilledPremium() {
		return _totalBilledPremium;
	}

	public void set_totalBilledPremium(BigDecimal totalBilledPremium) {
		_totalBilledPremium = totalBilledPremium;
	}

	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public void setNumExpiredPolicies(int numExpiredPolicies) {
		_numExpiredPolicies = numExpiredPolicies;
	}

	public void setNumFuturePolicies(int numFuturePolicies) {
		_numFuturePolicies = numFuturePolicies;
	}

	public void setNumInForcePolicies(int numInForcePolicies) {
		_numInForcePolicies = numInForcePolicies;
	}

	private String _accountNumber;
	private int _numExpiredPolicies;
	private int _numFuturePolicies;
	private int _numInForcePolicies;
	private BigDecimal _totalBilledPremium;

}