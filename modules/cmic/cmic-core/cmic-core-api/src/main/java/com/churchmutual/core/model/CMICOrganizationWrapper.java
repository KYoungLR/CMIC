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
 * This class is a wrapper for {@link CMICOrganization}.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICOrganization
 * @generated
 */
public class CMICOrganizationWrapper
	extends BaseModelWrapper<CMICOrganization>
	implements CMICOrganization, ModelWrapper<CMICOrganization> {

	public CMICOrganizationWrapper(CMICOrganization cmicOrganization) {
		super(cmicOrganization);
	}

	@Override
	public Map<String, Object> getModelAttributes() {
		Map<String, Object> attributes = new HashMap<String, Object>();

		attributes.put("cmicOrganizationId", getCmicOrganizationId());
		attributes.put("organizationId", getOrganizationId());
		attributes.put("agent", getAgent());
		attributes.put("division", getDivision());
		attributes.put("producerId", getProducerId());
		attributes.put("producerType", getProducerType());
		attributes.put("active", isActive());

		return attributes;
	}

	@Override
	public void setModelAttributes(Map<String, Object> attributes) {
		Long cmicOrganizationId = (Long)attributes.get("cmicOrganizationId");

		if (cmicOrganizationId != null) {
			setCmicOrganizationId(cmicOrganizationId);
		}

		Long organizationId = (Long)attributes.get("organizationId");

		if (organizationId != null) {
			setOrganizationId(organizationId);
		}

		String agent = (String)attributes.get("agent");

		if (agent != null) {
			setAgent(agent);
		}

		String division = (String)attributes.get("division");

		if (division != null) {
			setDivision(division);
		}

		Long producerId = (Long)attributes.get("producerId");

		if (producerId != null) {
			setProducerId(producerId);
		}

		Integer producerType = (Integer)attributes.get("producerType");

		if (producerType != null) {
			setProducerType(producerType);
		}

		Boolean active = (Boolean)attributes.get("active");

		if (active != null) {
			setActive(active);
		}
	}

	/**
	 * Returns the active of this cmic organization.
	 *
	 * @return the active of this cmic organization
	 */
	@Override
	public boolean getActive() {
		return model.getActive();
	}

	/**
	 * Returns the agent of this cmic organization.
	 *
	 * @return the agent of this cmic organization
	 */
	@Override
	public String getAgent() {
		return model.getAgent();
	}

	/**
	 * Returns the cmic organization ID of this cmic organization.
	 *
	 * @return the cmic organization ID of this cmic organization
	 */
	@Override
	public long getCmicOrganizationId() {
		return model.getCmicOrganizationId();
	}

	/**
	 * Returns the division of this cmic organization.
	 *
	 * @return the division of this cmic organization
	 */
	@Override
	public String getDivision() {
		return model.getDivision();
	}

	/**
	 * Returns the organization ID of this cmic organization.
	 *
	 * @return the organization ID of this cmic organization
	 */
	@Override
	public long getOrganizationId() {
		return model.getOrganizationId();
	}

	/**
	 * Returns the primary key of this cmic organization.
	 *
	 * @return the primary key of this cmic organization
	 */
	@Override
	public long getPrimaryKey() {
		return model.getPrimaryKey();
	}

	/**
	 * Returns the producer ID of this cmic organization.
	 *
	 * @return the producer ID of this cmic organization
	 */
	@Override
	public long getProducerId() {
		return model.getProducerId();
	}

	/**
	 * Returns the producer type of this cmic organization.
	 *
	 * @return the producer type of this cmic organization
	 */
	@Override
	public int getProducerType() {
		return model.getProducerType();
	}

	/**
	 * Returns <code>true</code> if this cmic organization is active.
	 *
	 * @return <code>true</code> if this cmic organization is active; <code>false</code> otherwise
	 */
	@Override
	public boolean isActive() {
		return model.isActive();
	}

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. All methods that expect a cmic organization model instance should use the <code>CMICOrganization</code> interface instead.
	 */
	@Override
	public void persist() {
		model.persist();
	}

	/**
	 * Sets whether this cmic organization is active.
	 *
	 * @param active the active of this cmic organization
	 */
	@Override
	public void setActive(boolean active) {
		model.setActive(active);
	}

	/**
	 * Sets the agent of this cmic organization.
	 *
	 * @param agent the agent of this cmic organization
	 */
	@Override
	public void setAgent(String agent) {
		model.setAgent(agent);
	}

	/**
	 * Sets the cmic organization ID of this cmic organization.
	 *
	 * @param cmicOrganizationId the cmic organization ID of this cmic organization
	 */
	@Override
	public void setCmicOrganizationId(long cmicOrganizationId) {
		model.setCmicOrganizationId(cmicOrganizationId);
	}

	/**
	 * Sets the division of this cmic organization.
	 *
	 * @param division the division of this cmic organization
	 */
	@Override
	public void setDivision(String division) {
		model.setDivision(division);
	}

	/**
	 * Sets the organization ID of this cmic organization.
	 *
	 * @param organizationId the organization ID of this cmic organization
	 */
	@Override
	public void setOrganizationId(long organizationId) {
		model.setOrganizationId(organizationId);
	}

	/**
	 * Sets the primary key of this cmic organization.
	 *
	 * @param primaryKey the primary key of this cmic organization
	 */
	@Override
	public void setPrimaryKey(long primaryKey) {
		model.setPrimaryKey(primaryKey);
	}

	/**
	 * Sets the producer ID of this cmic organization.
	 *
	 * @param producerId the producer ID of this cmic organization
	 */
	@Override
	public void setProducerId(long producerId) {
		model.setProducerId(producerId);
	}

	/**
	 * Sets the producer type of this cmic organization.
	 *
	 * @param producerType the producer type of this cmic organization
	 */
	@Override
	public void setProducerType(int producerType) {
		model.setProducerType(producerType);
	}

	@Override
	protected CMICOrganizationWrapper wrap(CMICOrganization cmicOrganization) {
		return new CMICOrganizationWrapper(cmicOrganization);
	}

}