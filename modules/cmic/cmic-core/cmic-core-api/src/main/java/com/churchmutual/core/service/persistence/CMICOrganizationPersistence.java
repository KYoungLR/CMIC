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

package com.churchmutual.core.service.persistence;

import com.churchmutual.core.exception.NoSuchCMICOrganizationException;
import com.churchmutual.core.model.CMICOrganization;

import com.liferay.portal.kernel.service.persistence.BasePersistence;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence interface for the cmic organization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICOrganizationUtil
 * @generated
 */
@ProviderType
public interface CMICOrganizationPersistence
	extends BasePersistence<CMICOrganization> {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this interface directly. Always use {@link CMICOrganizationUtil} to access the cmic organization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this interface.
	 */

	/**
	 * Returns the cmic organization where organizationId = &#63; or throws a <code>NoSuchCMICOrganizationException</code> if it could not be found.
	 *
	 * @param organizationId the organization ID
	 * @return the matching cmic organization
	 * @throws NoSuchCMICOrganizationException if a matching cmic organization could not be found
	 */
	public CMICOrganization findByOrganizationId(long organizationId)
		throws NoSuchCMICOrganizationException;

	/**
	 * Returns the cmic organization where organizationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param organizationId the organization ID
	 * @return the matching cmic organization, or <code>null</code> if a matching cmic organization could not be found
	 */
	public CMICOrganization fetchByOrganizationId(long organizationId);

	/**
	 * Returns the cmic organization where organizationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param organizationId the organization ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cmic organization, or <code>null</code> if a matching cmic organization could not be found
	 */
	public CMICOrganization fetchByOrganizationId(
		long organizationId, boolean useFinderCache);

	/**
	 * Removes the cmic organization where organizationId = &#63; from the database.
	 *
	 * @param organizationId the organization ID
	 * @return the cmic organization that was removed
	 */
	public CMICOrganization removeByOrganizationId(long organizationId)
		throws NoSuchCMICOrganizationException;

	/**
	 * Returns the number of cmic organizations where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @return the number of matching cmic organizations
	 */
	public int countByOrganizationId(long organizationId);

	/**
	 * Returns the cmic organization where producerId = &#63; or throws a <code>NoSuchCMICOrganizationException</code> if it could not be found.
	 *
	 * @param producerId the producer ID
	 * @return the matching cmic organization
	 * @throws NoSuchCMICOrganizationException if a matching cmic organization could not be found
	 */
	public CMICOrganization findByProducerId(long producerId)
		throws NoSuchCMICOrganizationException;

	/**
	 * Returns the cmic organization where producerId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param producerId the producer ID
	 * @return the matching cmic organization, or <code>null</code> if a matching cmic organization could not be found
	 */
	public CMICOrganization fetchByProducerId(long producerId);

	/**
	 * Returns the cmic organization where producerId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param producerId the producer ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cmic organization, or <code>null</code> if a matching cmic organization could not be found
	 */
	public CMICOrganization fetchByProducerId(
		long producerId, boolean useFinderCache);

	/**
	 * Removes the cmic organization where producerId = &#63; from the database.
	 *
	 * @param producerId the producer ID
	 * @return the cmic organization that was removed
	 */
	public CMICOrganization removeByProducerId(long producerId)
		throws NoSuchCMICOrganizationException;

	/**
	 * Returns the number of cmic organizations where producerId = &#63;.
	 *
	 * @param producerId the producer ID
	 * @return the number of matching cmic organizations
	 */
	public int countByProducerId(long producerId);

	/**
	 * Caches the cmic organization in the entity cache if it is enabled.
	 *
	 * @param cmicOrganization the cmic organization
	 */
	public void cacheResult(CMICOrganization cmicOrganization);

	/**
	 * Caches the cmic organizations in the entity cache if it is enabled.
	 *
	 * @param cmicOrganizations the cmic organizations
	 */
	public void cacheResult(java.util.List<CMICOrganization> cmicOrganizations);

	/**
	 * Creates a new cmic organization with the primary key. Does not add the cmic organization to the database.
	 *
	 * @param cmicOrganizationId the primary key for the new cmic organization
	 * @return the new cmic organization
	 */
	public CMICOrganization create(long cmicOrganizationId);

	/**
	 * Removes the cmic organization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cmicOrganizationId the primary key of the cmic organization
	 * @return the cmic organization that was removed
	 * @throws NoSuchCMICOrganizationException if a cmic organization with the primary key could not be found
	 */
	public CMICOrganization remove(long cmicOrganizationId)
		throws NoSuchCMICOrganizationException;

	public CMICOrganization updateImpl(CMICOrganization cmicOrganization);

	/**
	 * Returns the cmic organization with the primary key or throws a <code>NoSuchCMICOrganizationException</code> if it could not be found.
	 *
	 * @param cmicOrganizationId the primary key of the cmic organization
	 * @return the cmic organization
	 * @throws NoSuchCMICOrganizationException if a cmic organization with the primary key could not be found
	 */
	public CMICOrganization findByPrimaryKey(long cmicOrganizationId)
		throws NoSuchCMICOrganizationException;

	/**
	 * Returns the cmic organization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cmicOrganizationId the primary key of the cmic organization
	 * @return the cmic organization, or <code>null</code> if a cmic organization with the primary key could not be found
	 */
	public CMICOrganization fetchByPrimaryKey(long cmicOrganizationId);

	/**
	 * Returns all the cmic organizations.
	 *
	 * @return the cmic organizations
	 */
	public java.util.List<CMICOrganization> findAll();

	/**
	 * Returns a range of all the cmic organizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICOrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cmic organizations
	 * @param end the upper bound of the range of cmic organizations (not inclusive)
	 * @return the range of cmic organizations
	 */
	public java.util.List<CMICOrganization> findAll(int start, int end);

	/**
	 * Returns an ordered range of all the cmic organizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICOrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cmic organizations
	 * @param end the upper bound of the range of cmic organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cmic organizations
	 */
	public java.util.List<CMICOrganization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CMICOrganization>
			orderByComparator);

	/**
	 * Returns an ordered range of all the cmic organizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>CMICOrganizationModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of cmic organizations
	 * @param end the upper bound of the range of cmic organizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cmic organizations
	 */
	public java.util.List<CMICOrganization> findAll(
		int start, int end,
		com.liferay.portal.kernel.util.OrderByComparator<CMICOrganization>
			orderByComparator,
		boolean useFinderCache);

	/**
	 * Removes all the cmic organizations from the database.
	 */
	public void removeAll();

	/**
	 * Returns the number of cmic organizations.
	 *
	 * @return the number of cmic organizations
	 */
	public int countAll();

}