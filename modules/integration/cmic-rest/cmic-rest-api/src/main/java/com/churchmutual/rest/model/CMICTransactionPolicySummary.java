package com.churchmutual.rest.model;

import java.math.BigDecimal;

import java.util.Date;

/**
 * @author Kayleen Lim
 */
public class CMICTransactionPolicySummary extends CMICObject {

	public String getAccountNumber() {
		return _accountNumber;
	}

	public Date getCancelDate() {
		return _cancelDate;
	}

	public int getCurrentSequenceNumber() {
		return _currentSequenceNumber;
	}

	public Date getPolicyEffectiveDate() {
		return _policyEffectiveDate;
	}

	public Date getPolicyExpirationDate() {
		return _policyExpirationDate;
	}

	public String getPolicyNumber() {
		return _policyNumber;
	}

	public String getPolicyType() {
		return _policyType;
	}

	public BigDecimal getTotalBilledPremium() {
		return _totalBilledPremium;
	}

	public boolean isExpiredPolicy() {
		return _expiredPolicy;
	}

	public boolean isFuturePolicy() {
		return _futurePolicy;
	}

	public boolean isInForcePolicy() {
		return _inForcePolicy;
	}

	public void setAccountNumber(String accountNumber) {
		_accountNumber = accountNumber;
	}

	public void setCancelDate(Date cancelDate) {
		_cancelDate = cancelDate;
	}

	public void setCurrentSequenceNumber(int currentSequenceNumber) {
		_currentSequenceNumber = currentSequenceNumber;
	}

	public void setExpiredPolicy(boolean expiredPolicy) {
		_expiredPolicy = expiredPolicy;
	}

	public void setFuturePolicy(boolean futurePolicy) {
		_futurePolicy = futurePolicy;
	}

	public void setInForcePolicy(boolean inForcePolicy) {
		_inForcePolicy = inForcePolicy;
	}

	public void setPolicyEffectiveDate(Date policyEffectiveDate) {
		_policyEffectiveDate = policyEffectiveDate;
	}

	public void setPolicyExpirationDate(Date policyExpirationDate) {
		_policyExpirationDate = policyExpirationDate;
	}

	public void setPolicyNumber(String policyNumber) {
		_policyNumber = policyNumber;
	}

	public void setPolicyType(String policyType) {
		_policyType = policyType;
	}

	public void setTotalBilledPremium(BigDecimal totalBilledPremium) {
		_totalBilledPremium = totalBilledPremium;
	}

	private String _accountNumber;
	private Date _cancelDate;
	private int _currentSequenceNumber;
	private boolean _expiredPolicy;
	private boolean _futurePolicy;
	private boolean _inForcePolicy;
	private Date _policyEffectiveDate;
	private Date _policyExpirationDate;
	private String _policyNumber;
	private String _policyType;
	private BigDecimal _totalBilledPremium;

}