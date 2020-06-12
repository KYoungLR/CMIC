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

import com.churchmutual.core.model.CMICOrganization;

import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.util.OrderByComparator;

import java.io.Serializable;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * The persistence utility for the cmic organization service. This utility wraps <code>com.churchmutual.core.service.persistence.impl.CMICOrganizationPersistenceImpl</code> and provides direct access to the database for CRUD operations. This utility should only be used by the service layer, as it must operate within a transaction. Never access this utility in a JSP, controller, model, or other front-end class.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICOrganizationPersistence
 * @generated
 */
public class CMICOrganizationUtil {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify this class directly. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache()
	 */
	public static void clearCache() {
		getPersistence().clearCache();
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#clearCache(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static void clearCache(CMICOrganization cmicOrganization) {
		getPersistence().clearCache(cmicOrganization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#countWithDynamicQuery(DynamicQuery)
	 */
	public static long countWithDynamicQuery(DynamicQuery dynamicQuery) {
		return getPersistence().countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#fetchByPrimaryKeys(Set)
	 */
	public static Map<Serializable, CMICOrganization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return getPersistence().fetchByPrimaryKeys(primaryKeys);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery)
	 */
	public static List<CMICOrganization> findWithDynamicQuery(
		DynamicQuery dynamicQuery) {

		return getPersistence().findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int)
	 */
	public static List<CMICOrganization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end) {

		return getPersistence().findWithDynamicQuery(dynamicQuery, start, end);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#findWithDynamicQuery(DynamicQuery, int, int, OrderByComparator)
	 */
	public static List<CMICOrganization> findWithDynamicQuery(
		DynamicQuery dynamicQuery, int start, int end,
		OrderByComparator<CMICOrganization> orderByComparator) {

		return getPersistence().findWithDynamicQuery(
			dynamicQuery, start, end, orderByComparator);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel)
	 */
	public static CMICOrganization update(CMICOrganization cmicOrganization) {
		return getPersistence().update(cmicOrganization);
	}

	/**
	 * @see com.liferay.portal.kernel.service.persistence.BasePersistence#update(com.liferay.portal.kernel.model.BaseModel, ServiceContext)
	 */
	public static CMICOrganization update(
		CMICOrganization cmicOrganization, ServiceContext serviceContext) {

		return getPersistence().update(cmicOrganization, serviceContext);
	}

	/**
	 * Returns the cmic organization where organizationId = &#63; or throws a <code>NoSuchCMICOrganizationException</code> if it could not be found.
	 *
	 * @param organizationId the organization ID
	 * @return the matching cmic organization
	 * @throws NoSuchCMICOrganizationException if a matching cmic organization could not be found
	 */
	public static CMICOrganization findByOrganizationId(long organizationId)
		throws com.churchmutual.core.exception.NoSuchCMICOrganizationException {

		return getPersistence().findByOrganizationId(organizationId);
	}

	/**
	 * Returns the cmic organization where organizationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param organizationId the organization ID
	 * @return the matching cmic organization, or <code>null</code> if a matching cmic organization could not be found
	 */
	public static CMICOrganization fetchByOrganizationId(long organizationId) {
		return getPersistence().fetchByOrganizationId(organizationId);
	}

	/**
	 * Returns the cmic organization where organizationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param organizationId the organization ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cmic organization, or <code>null</code> if a matching cmic organization could not be found
	 */
	public static CMICOrganization fetchByOrganizationId(
		long organizationId, boolean useFinderCache) {

		return getPersistence().fetchByOrganizationId(
			organizationId, useFinderCache);
	}

	/**
	 * Removes the cmic organization where organizationId = &#63; from the database.
	 *
	 * @param organizationId the organization ID
	 * @return the cmic organization that was removed
	 */
	public static CMICOrganization removeByOrganizationId(long organizationId)
		throws com.churchmutual.core.exception.NoSuchCMICOrganizationException {

		return getPersistence().removeByOrganizationId(organizationId);
	}

	/**
	 * Returns the number of cmic organizations where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @return the number of matching cmic organizations
	 */
	public static int countByOrganizationId(long organizationId) {
		return getPersistence().countByOrganizationId(organizationId);
	}

	/**
	 * Returns the cmic organization where producerId = &#63; or throws a <code>NoSuchCMICOrganizationException</code> if it could not be found.
	 *
	 * @param producerId the producer ID
	 * @return the matching cmic organization
	 * @throws NoSuchCMICOrganizationException if a matching cmic organization could not be found
	 */
	public static CMICOrganization findByProducerId(long producerId)
		throws com.churchmutual.core.exception.NoSuchCMICOrganizationException {

		return getPersistence().findByProducerId(producerId);
	}

	/**
	 * Returns the cmic organization where producerId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param producerId the producer ID
	 * @return the matching cmic organization, or <code>null</code> if a matching cmic organization could not be found
	 */
	public static CMICOrganization fetchByProducerId(long producerId) {
		return getPersistence().fetchByProducerId(producerId);
	}

	/**
	 * Returns the cmic organization where producerId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param producerId the producer ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cmic organization, or <code>null</code> if a matching cmic organization could not be found
	 */
	public static CMICOrganization fetchByProducerId(
		long producerId, boolean useFinderCache) {

		return getPersistence().fetchByProducerId(producerId, useFinderCache);
	}

	/**
	 * Removes the cmic organization where producerId = &#63; from the database.
	 *
	 * @param producerId the producer ID
	 * @return the cmic organization that was removed
	 */
	public static CMICOrganization removeByProducerId(long producerId)
		throws com.churchmutual.core.exception.NoSuchCMICOrganizationException {

		return getPersistence().removeByProducerId(producerId);
	}

	/**
	 * Returns the number of cmic organizations where producerId = &#63;.
	 *
	 * @param producerId the producer ID
	 * @return the number of matching cmic organizations
	 */
	public static int countByProducerId(long producerId) {
		return getPersistence().countByProducerId(producerId);
	}

	/**
	 * Caches the cmic organization in the entity cache if it is enabled.
	 *
	 * @param cmicOrganization the cmic organization
	 */
	public static void cacheResult(CMICOrganization cmicOrganization) {
		getPersistence().cacheResult(cmicOrganization);
	}

	/**
	 * Caches the cmic organizations in the entity cache if it is enabled.
	 *
	 * @param cmicOrganizations the cmic organizations
	 */
	public static void cacheResult(List<CMICOrganization> cmicOrganizations) {
		getPersistence().cacheResult(cmicOrganizations);
	}

	/**
	 * Creates a new cmic organization with the primary key. Does not add the cmic organization to the database.
	 *
	 * @param cmicOrganizationId the primary key for the new cmic organization
	 * @return the new cmic organization
	 */
	public static CMICOrganization create(long cmicOrganizationId) {
		return getPersistence().create(cmicOrganizationId);
	}

	/**
	 * Removes the cmic organization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cmicOrganizationId the primary key of the cmic organization
	 * @return the cmic organization that was removed
	 * @throws NoSuchCMICOrganizationException if a cmic organization with the primary key could not be found
	 */
	public static CMICOrganization remove(long cmicOrganizationId)
		throws com.churchmutual.core.exception.NoSuchCMICOrganizationException {

		return getPersistence().remove(cmicOrganizationId);
	}

	public static CMICOrganization updateImpl(
		CMICOrganization cmicOrganization) {

		return getPersistence().updateImpl(cmicOrganization);
	}

	/**
	 * Returns the cmic organization with the primary key or throws a <code>NoSuchCMICOrganizationException</code> if it could not be found.
	 *
	 * @param cmicOrganizationId the primary key of the cmic organization
	 * @return the cmic organization
	 * @throws NoSuchCMICOrganizationException if a cmic organization with the primary key could not be found
	 */
	public static CMICOrganization findByPrimaryKey(long cmicOrganizationId)
		throws com.churchmutual.core.exception.NoSuchCMICOrganizationException {

		return getPersistence().findByPrimaryKey(cmicOrganizationId);
	}

	/**
	 * Returns the cmic organization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cmicOrganizationId the primary key of the cmic organization
	 * @return the cmic organization, or <code>null</code> if a cmic organization with the primary key could not be found
	 */
	public static CMICOrganization fetchByPrimaryKey(long cmicOrganizationId) {
		return getPersistence().fetchByPrimaryKey(cmicOrganizationId);
	}

	/**
	 * Returns all the cmic organizations.
	 *
	 * @return the cmic organizations
	 */
	public static List<CMICOrganization> findAll() {
		return getPersistence().findAll();
	}

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
	public static List<CMICOrganization> findAll(int start, int end) {
		return getPersistence().findAll(start, end);
	}

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
	public static List<CMICOrganization> findAll(
		int start, int end,
		OrderByComparator<CMICOrganization> orderByComparator) {

		return getPersistence().findAll(start, end, orderByComparator);
	}

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
	public static List<CMICOrganization> findAll(
		int start, int end,
		OrderByComparator<CMICOrganization> orderByComparator,
		boolean useFinderCache) {

		return getPersistence().findAll(
			start, end, orderByComparator, useFinderCache);
	}

	/**
	 * Removes all the cmic organizations from the database.
	 */
	public static void removeAll() {
		getPersistence().removeAll();
	}

	/**
	 * Returns the number of cmic organizations.
	 *
	 * @return the number of cmic organizations
	 */
	public static int countAll() {
		return getPersistence().countAll();
	}

	public static CMICOrganizationPersistence getPersistence() {
		return _serviceTracker.getService();
	}

	private static ServiceTracker
		<CMICOrganizationPersistence, CMICOrganizationPersistence>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			CMICOrganizationPersistence.class);

		ServiceTracker<CMICOrganizationPersistence, CMICOrganizationPersistence>
			serviceTracker =
				new ServiceTracker
					<CMICOrganizationPersistence, CMICOrganizationPersistence>(
						bundle.getBundleContext(),
						CMICOrganizationPersistence.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}