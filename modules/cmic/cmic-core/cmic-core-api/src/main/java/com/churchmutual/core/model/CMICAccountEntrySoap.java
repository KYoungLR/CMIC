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

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used by SOAP remote services, specifically {@link com.churchmutual.core.service.http.CMICAccountEntryServiceSoap}.
 *
 * @author Kayleen Lim
 * @generated
 */
public class CMICAccountEntrySoap implements Serializable {

	public static CMICAccountEntrySoap toSoapModel(CMICAccountEntry model) {
		CMICAccountEntrySoap soapModel = new CMICAccountEntrySoap();

		soapModel.setCmicAccountEntryId(model.getCmicAccountEntryId());
		soapModel.setAccountEntryId(model.getAccountEntryId());
		soapModel.setAccountNumber(model.getAccountNumber());
		soapModel.setNumExpiredPolicies(model.getNumExpiredPolicies());
		soapModel.setNumFuturePolicies(model.getNumFuturePolicies());
		soapModel.setNumInForcePolicies(model.getNumInForcePolicies());
		soapModel.setTotalBilledPremium(model.getTotalBilledPremium());

		return soapModel;
	}

	public static CMICAccountEntrySoap[] toSoapModels(
		CMICAccountEntry[] models) {

		CMICAccountEntrySoap[] soapModels =
			new CMICAccountEntrySoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CMICAccountEntrySoap[][] toSoapModels(
		CMICAccountEntry[][] models) {

		CMICAccountEntrySoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CMICAccountEntrySoap[models.length][models[0].length];
		}
		else {
			soapModels = new CMICAccountEntrySoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CMICAccountEntrySoap[] toSoapModels(
		List<CMICAccountEntry> models) {

		List<CMICAccountEntrySoap> soapModels =
			new ArrayList<CMICAccountEntrySoap>(models.size());

		for (CMICAccountEntry model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CMICAccountEntrySoap[soapModels.size()]);
	}

	public CMICAccountEntrySoap() {
	}

	public long getPrimaryKey() {
		return _cmicAccountEntryId;
	}

	public void setPrimaryKey(long pk) {
		setCmicAccountEntryId(pk);
	}

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

	public String getTotalBilledPremium() {
		return _totalBilledPremium;
	}

	public void setTotalBilledPremium(String totalBilledPremium) {
		_totalBilledPremium = totalBilledPremium;
	}

	private long _cmicAccountEntryId;
	private long _accountEntryId;
	private String _accountNumber;
	private int _numExpiredPolicies;
	private int _numFuturePolicies;
	private int _numInForcePolicies;
	private String _totalBilledPremium;

}