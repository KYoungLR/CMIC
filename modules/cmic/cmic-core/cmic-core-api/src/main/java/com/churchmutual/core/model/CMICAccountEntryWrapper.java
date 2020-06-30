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

package com.churchmutual.core.model;

import com.liferay.portal.kernel.model.ModelWrapper;
import com.liferay.portal.kernel.model.wrapper.BaseModelWrapper;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * This class is a wrapper for {@link CMICAccountEntry}.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICAccountEntry
 * @generated
 */
public class CMICAccountEntryWrapper
	extends BaseModelWrapper<CMICAccountEntry>
	implements CMICAccountEntry, ModelWrapper<CMICAccountEntry> {

	public CMICAccountEntryWrapper(CMICAccountEntry cmicAccountEntry) {
		super(cmicAccountEntry);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("cmicAccountEntryId", getCmicAccountEntryId());
		attributes.put("accountEntryId", getAccountEntryId());
		attributes.put("accountNumber", getAccountNumber());
		attributes.put("companyNumber", getCompanyNumber());
		attributes.put("numExpiredPolicies", getNumExpiredPolicies());
		attributes.put("numFuturePolicies", getNumFuturePolicies());
		attributes.put("numInForcePolicies", getNumInForcePolicies());
		attributes.put("totalBilledPremium", getTotalBilledPremium());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long cmicAccountEntryId = (Long)attributes.get("cmicAccountEntryId");

		if (cmicAccountEntryId != null) {
			setCmicAccountEntryId(cmicAccountEntryId);
		}

		Long accountEntryId = (Long)attributes.get("accountEntryId");

		if (accountEntryId != null) {
			setAccountEntryId(accountEntryId);
		}

		String accountNumber = (String)attributes.get("accountNumber");

		if (accountNumber != null) {
			setAccountNumber(accountNumber);
		}

		String companyNumber = (String)attributes.get("companyNumber");

		if (companyNumber != null) {
			setCompanyNumber(companyNumber);
		}

		Integer numExpiredPolicies = (Integer)attributes.get(
			"numExpiredPolicies");

		if (numExpiredPolicies != null) {
			setNumExpiredPolicies(numExpiredPolicies);
		}

		Integer numFuturePolicies = (Integer)attributes.get(
			"numFuturePolicies");

		if (numFuturePolicies != null) {
			setNumFuturePolicies(numFuturePolicies);
		}

		Integer numInForcePolicies = (Integer)attributes.get(
			"numInForcePolicies");

		if (numInForcePolicies != null) {
			setNumInForcePolicies(numInForcePolicies);
		}

		String totalBilledPremium = (String)attributes.get(
			"totalBilledPremium");

		if (totalBilledPremium != null) {
			setTotalBilledPremium(totalBilledPremium);
		}
	}

	/**
	 * Returns the account entry ID of this cmic account entry.
	 *
	 * @return the account entry ID of this cmic account entry
	 */
	@Override
	public long getAccountEntryId() {
		return model.getAccountEntryId();
	}

	/**
	 * Returns the account number of this cmic account entry.
	 *
	 * @return the account number of this cmic account entry
	 */
	@Override
	public String getAccountNumber() {
		return model.getAccountNumber();
	}

	/**
	 * Returns the cmic account entry ID of this cmic account entry.
	 *
	 * @return the cmic account entry ID of this cmic account entry
	 */
	@Override
	public long getCmicAccountEntryId() {
		return model.getCmicAccountEntryId();
	}

	/**
	 * Returns the company number of this cmic account entry.
	 *
	 * @return the company number of this cmic account entry
	 */
	@Override
	public String getCompanyNumber() {
		return model.getCompanyNumber();
	}

	/**
	 * Returns the num expired policies of this cmic account entry.
	 *
	 * @return the num expired policies of this cmic account entry
	 */
	@Override
	public int getNumExpiredPolicies() {
		return model.getNumExpiredPolicies();
	}

	/**
	 * Returns the num future policies of this cmic account entry.
	 *
	 * @return the num future policies of this cmic account entry
	 */
	@Override
	public int getNumFuturePolicies() {
		return model.getNumFuturePolicies();
	}

	/**
	 * Returns the num in force policies of this cmic account entry.
	 *
	 * @return the num in force policies of this cmic account entry
	 */
	@Override
	public int getNumInForcePolicies() {
		return model.getNumInForcePolicies();
	}

	/**
	 * Returns the primary key of this cmic account entry.
	 *
	 * @return the primary key of this cmic account entry
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the total billed premium of this cmic account entry.
	 *
	 * @return the total billed premium of this cmic account entry
	 */
	@Override
	public String getTotalBilledPremium() {
		return model.getTotalBilledPremium();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cmic account entry model instance should use the <code>CMICAccountEntry</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets the account entry ID of this cmic account entry.
	 *
	 * @param accountEntryId the account entry ID of this cmic account entry
	 */
	@Override
	public void setAccountEntryId(long accountEntryId) {
		model.setAccountEntryId(accountEntryId);
	}

	/**
	 * Sets the account number of this cmic account entry.
	 *
	 * @param accountNumber the account number of this cmic account entry
	 */
	@Override
	public void setAccountNumber(String accountNumber) {
		model.setAccountNumber(accountNumber);
	}

	/**
	 * Sets the cmic account entry ID of this cmic account entry.
	 *
	 * @param cmicAccountEntryId the cmic account entry ID of this cmic account entry
	 */
	@Override
	public void setCmicAccountEntryId(long cmicAccountEntryId) {
		model.setCmicAccountEntryId(cmicAccountEntryId);
	}

	/**
	 * Sets the company number of this cmic account entry.
	 *
	 * @param companyNumber the company number of this cmic account entry
	 */
	@Override
	public void setCompanyNumber(String companyNumber) {
		model.setCompanyNumber(companyNumber);
	}

	/**
	 * Sets the num expired policies of this cmic account entry.
	 *
	 * @param numExpiredPolicies the num expired policies of this cmic account entry
	 */
	@Override
	public void setNumExpiredPolicies(int numExpiredPolicies) {
		model.setNumExpiredPolicies(numExpiredPolicies);
	}

	/**
	 * Sets the num future policies of this cmic account entry.
	 *
	 * @param numFuturePolicies the num future policies of this cmic account entry
	 */
	@Override
	public void setNumFuturePolicies(int numFuturePolicies) {
		model.setNumFuturePolicies(numFuturePolicies);
	}

	/**
	 * Sets the num in force policies of this cmic account entry.
	 *
	 * @param numInForcePolicies the num in force policies of this cmic account entry
	 */
	@Override
	public void setNumInForcePolicies(int numInForcePolicies) {
		model.setNumInForcePolicies(numInForcePolicies);
	}

	/**
	 * Sets the primary key of this cmic account entry.
	 *
	 * @param primaryKey the primary key of this cmic account entry
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the total billed premium of this cmic account entry.
	 *
	 * @param totalBilledPremium the total billed premium of this cmic account entry
	 */
	@Override
	public void setTotalBilledPremium(String totalBilledPremium) {
		model.setTotalBilledPremium(totalBilledPremium);
	}

	@Override
	protected CMICAccountEntryWrapper wrap(CMICAccountEntry cmicAccountEntry) {
		return new CMICAccountEntryWrapper(cmicAccountEntry);
	}

}