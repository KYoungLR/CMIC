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
 * This class is used by SOAP remote services, specifically {@link com.churchmutual.core.service.http.CMICOrganizationServiceSoap}.
 *
 * @author Kayleen Lim
 * @generated
 */
public class CMICOrganizationSoap implements Serializable {

	public static CMICOrganizationSoap toSoapModel(CMICOrganization model) {
		CMICOrganizationSoap soapModel = new CMICOrganizationSoap();

		soapModel.setCmicOrganizationId(model.getCmicOrganizationId());
		soapModel.setOrganizationId(model.getOrganizationId());
		soapModel.setAgentNumber(model.getAgentNumber());
		soapModel.setDivisionNumber(model.getDivisionNumber());
		soapModel.setProducerId(model.getProducerId());
		soapModel.setProducerType(model.getProducerType());
		soapModel.setActive(model.isActive());

		return soapModel;
	}

	public static CMICOrganizationSoap[] toSoapModels(
		CMICOrganization[] models) {

		CMICOrganizationSoap[] soapModels =
			new CMICOrganizationSoap[models.length];

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModel(models[i]);
		}

		return soapModels;
	}

	public static CMICOrganizationSoap[][] toSoapModels(
		CMICOrganization[][] models) {

		CMICOrganizationSoap[][] soapModels = null;

		if (models.length > 0) {
			soapModels =
				new CMICOrganizationSoap[models.length][models[0].length];
		}
		else {
			soapModels = new CMICOrganizationSoap[0][0];
		}

		for (int i = 0; i < models.length; i++) {
			soapModels[i] = toSoapModels(models[i]);
		}

		return soapModels;
	}

	public static CMICOrganizationSoap[] toSoapModels(
		List<CMICOrganization> models) {

		List<CMICOrganizationSoap> soapModels =
			new ArrayList<CMICOrganizationSoap>(models.size());

		for (CMICOrganization model : models) {
			soapModels.add(toSoapModel(model));
		}

		return soapModels.toArray(new CMICOrganizationSoap[soapModels.size()]);
	}

	public CMICOrganizationSoap() {
	}

	public long getPrimaryKey() {
		return _cmicOrganizationId;
	}

	public void setPrimaryKey(long pk) {
		setCmicOrganizationId(pk);
	}

	public long getCmicOrganizationId() {
		return _cmicOrganizationId;
	}

	public void setCmicOrganizationId(long cmicOrganizationId) {
		_cmicOrganizationId = cmicOrganizationId;
	}

	public long getOrganizationId() {
		return _organizationId;
	}

	public void setOrganizationId(long organizationId) {
		_organizationId = organizationId;
	}

	public String getAgentNumber() {
		return _agentNumber;
	}

	public void setAgentNumber(String agentNumber) {
		_agentNumber = agentNumber;
	}

	public String getDivisionNumber() {
		return _divisionNumber;
	}

	public void setDivisionNumber(String divisionNumber) {
		_divisionNumber = divisionNumber;
	}

	public long getProducerId() {
		return _producerId;
	}

	public void setProducerId(long producerId) {
		_producerId = producerId;
	}

	public int getProducerType() {
		return _producerType;
	}

	public void setProducerType(int producerType) {
		_producerType = producerType;
	}

	public boolean getActive() {
		return _active;
	}

	public boolean isActive() {
		return _active;
	}

	public void setActive(boolean active) {
		_active = active;
	}

	private long _cmicOrganizationId;
	private long _organizationId;
	private String _agentNumber;
	private String _divisionNumber;
	private long _producerId;
	private int _producerType;
	private boolean _active;

}