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

import com.churchmutual.core.model.CMICOrganization;

import com.liferay.petra.lang.HashUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.CacheModel;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * The cache model class for representing CMICOrganization in entity cache.
 *
 * @author Kayleen Lim
 * @generated
 */
public class CMICOrganizationCacheModel
	implements CacheModel<CMICOrganization>, Externalizable {

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof CMICOrganizationCacheModel)) {
			return false;
		}

		CMICOrganizationCacheModel cmicOrganizationCacheModel =
			(CMICOrganizationCacheModel)obj;

		if (cmicOrganizationId ==
				cmicOrganizationCacheModel.cmicOrganizationId) {

			return true;
		}

		return false;
	}

	@Override
	public int hashCode() {
		return HashUtil.hash(0, cmicOrganizationId);
	}

	@Override
	public String toString() {
		StringBundler sb = new StringBundler(15);

		sb.append("{cmicOrganizationId=");
		sb.append(cmicOrganizationId);
		sb.append(", organizationId=");
		sb.append(organizationId);
		sb.append(", agent=");
		sb.append(agent);
		sb.append(", division=");
		sb.append(division);
		sb.append(", producerId=");
		sb.append(producerId);
		sb.append(", producerType=");
		sb.append(producerType);
		sb.append(", active=");
		sb.append(active);
		sb.append("}");

		return sb.toString();
	}

	@Override
	public CMICOrganization toEntityModel() {
		CMICOrganizationImpl cmicOrganizationImpl = new CMICOrganizationImpl();

		cmicOrganizationImpl.setCmicOrganizationId(cmicOrganizationId);
		cmicOrganizationImpl.setOrganizationId(organizationId);

		if (agent == null) {
			cmicOrganizationImpl.setAgent("");
		}
		else {
			cmicOrganizationImpl.setAgent(agent);
		}

		if (division == null) {
			cmicOrganizationImpl.setDivision("");
		}
		else {
			cmicOrganizationImpl.setDivision(division);
		}

		cmicOrganizationImpl.setProducerId(producerId);
		cmicOrganizationImpl.setProducerType(producerType);
		cmicOrganizationImpl.setActive(active);

		cmicOrganizationImpl.resetOriginalValues();

		return cmicOrganizationImpl;
	}

	@Override
	public void readExternal(ObjectInput objectInput) throws IOException {
		cmicOrganizationId = objectInput.readLong();

		organizationId = objectInput.readLong();
		agent = objectInput.readUTF();
		division = objectInput.readUTF();

		producerId = objectInput.readLong();

		producerType = objectInput.readInt();

		active = objectInput.readBoolean();
	}

	@Override
	public void writeExternal(ObjectOutput objectOutput) throws IOException {
		objectOutput.writeLong(cmicOrganizationId);

		objectOutput.writeLong(organizationId);

		if (agent == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(agent);
		}

		if (division == null) {
			objectOutput.writeUTF("");
		}
		else {
			objectOutput.writeUTF(division);
		}

		objectOutput.writeLong(producerId);

		objectOutput.writeInt(producerType);

		objectOutput.writeBoolean(active);
	}

	public long cmicOrganizationId;
	public long organizationId;
	public String agent;
	public String division;
	public long producerId;
	public int producerType;
	public boolean active;

}