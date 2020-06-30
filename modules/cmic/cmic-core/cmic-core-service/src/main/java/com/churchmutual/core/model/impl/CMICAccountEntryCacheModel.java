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

package com.churchmutual.core.model.impl;

import com.churchmutual.core.model.CMICAccountEntry;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CMICAccountEntry in entity cache.
 *
 * @author Kayleen Lim
 * @generated
 */
public class CMICAccountEntryCacheModel
	implements CacheModel<CMICAccountEntry>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CMICAccountEntryCacheModel)) {
			return false;
		}

		CMICAccountEntryCacheModel cmicAccountEntryCacheModel =
			(CMICAccountEntryCacheModel)obj;

		if (cmicAccountEntryId ==
				cmicAccountEntryCacheModel.cmicAccountEntryId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, cmicAccountEntryId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(17);

		sb.append("{cmicAccountEntryId=");
		sb.append(cmicAccountEntryId);
		sb.append(", accountEntryId=");
		sb.append(accountEntryId);
		sb.append(", accountNumber=");
		sb.append(accountNumber);
		sb.append(", companyNumber=");
		sb.append(companyNumber);
		sb.append(", numExpiredPolicies=");
		sb.append(numExpiredPolicies);
		sb.append(", numFuturePolicies=");
		sb.append(numFuturePolicies);
		sb.append(", numInForcePolicies=");
		sb.append(numInForcePolicies);
		sb.append(", totalBilledPremium=");
		sb.append(totalBilledPremium);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CMICAccountEntry toEntityModel() {
		CMICAccountEntryImpl cmicAccountEntryImpl = new CMICAccountEntryImpl();

		cmicAccountEntryImpl.setCmicAccountEntryId(cmicAccountEntryId);
		cmicAccountEntryImpl.setAccountEntryId(accountEntryId);

		if (accountNumber == null) {
			cmicAccountEntryImpl.setAccountNumber("");
		}
		else {
			cmicAccountEntryImpl.setAccountNumber(accountNumber);
		}

		if (companyNumber == null) {
			cmicAccountEntryImpl.setCompanyNumber("");
		}
		else {
			cmicAccountEntryImpl.setCompanyNumber(companyNumber);
		}

		cmicAccountEntryImpl.setNumExpiredPolicies(numExpiredPolicies);
		cmicAccountEntryImpl.setNumFuturePolicies(numFuturePolicies);
		cmicAccountEntryImpl.setNumInForcePolicies(numInForcePolicies);

		if (totalBilledPremium == null) {
			cmicAccountEntryImpl.setTotalBilledPremium("");
		}
		else {
			cmicAccountEntryImpl.setTotalBilledPremium(totalBilledPremium);
		}

		cmicAccountEntryImpl.resetOriginalValues();

		return cmicAccountEntryImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		cmicAccountEntryId = objectInput.readLong();

		accountEntryId = objectInput.readLong();
		accountNumber = objectInput.readUTF();
		companyNumber = objectInput.readUTF();

		numExpiredPolicies = objectInput.readInt();

		numFuturePolicies = objectInput.readInt();

		numInForcePolicies = objectInput.readInt();
		totalBilledPremium = objectInput.readUTF();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(cmicAccountEntryId);

		objectOutput.writeLong(accountEntryId);

		if (accountNumber == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(accountNumber);
		}

		if (companyNumber == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(companyNumber);
		}

		objectOutput.writeInt(numExpiredPolicies);

		objectOutput.writeInt(numFuturePolicies);

		objectOutput.writeInt(numInForcePolicies);

		if (totalBilledPremium == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(totalBilledPremium);
		}
	}

	public long cmicAccountEntryId;
	public long accountEntryId;
	public String accountNumber;
	public String companyNumber;
	public int numExpiredPolicies;
	public int numFuturePolicies;
	public int numInForcePolicies;
	public String totalBilledPremium;

}