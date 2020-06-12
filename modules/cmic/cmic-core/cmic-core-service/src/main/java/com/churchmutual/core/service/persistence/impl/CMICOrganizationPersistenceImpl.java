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

package com.churchmutual.core.service.persistence.impl;

import com.churchmutual.core.exception.NoSuchCMICOrganizationException;
import com.churchmutual.core.model.CMICOrganization;
import com.churchmutual.core.model.impl.CMICOrganizationImpl;
import com.churchmutual.core.model.impl.CMICOrganizationModelImpl;
import com.churchmutual.core.service.persistence.CMICOrganizationPersistence;
import com.churchmutual.core.service.persistence.impl.constants.cmicPersistenceConstants;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the cmic organization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Kayleen Lim
 * @generated
 */
@Component(service = CMICOrganizationPersistence.class)
public class CMICOrganizationPersistenceImpl
	extends BasePersistenceImpl<CMICOrganization>
	implements CMICOrganizationPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CMICOrganizationUtil</code> to access the cmic organization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CMICOrganizationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathFetchByOrganizationId;
	private FinderPath _finderPathCountByOrganizationId;

	/**
	 * Returns the cmic organization where organizationId = &#63; or throws a <code>NoSuchCMICOrganizationException</code> if it could not be found.
	 *
	 * @param organizationId the organization ID
	 * @return the matching cmic organization
	 * @throws NoSuchCMICOrganizationException if a matching cmic organization could not be found
	 */
	@Override
	public CMICOrganization findByOrganizationId(long organizationId)
		throws NoSuchCMICOrganizationException {

		CMICOrganization cmicOrganization = fetchByOrganizationId(
			organizationId);

		if (cmicOrganization == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("organizationId=");
			msg.append(organizationId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCMICOrganizationException(msg.toString());
		}

		return cmicOrganization;
	}

	/**
	 * Returns the cmic organization where organizationId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param organizationId the organization ID
	 * @return the matching cmic organization, or <code>null</code> if a matching cmic organization could not be found
	 */
	@Override
	public CMICOrganization fetchByOrganizationId(long organizationId) {
		return fetchByOrganizationId(organizationId, true);
	}

	/**
	 * Returns the cmic organization where organizationId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param organizationId the organization ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cmic organization, or <code>null</code> if a matching cmic organization could not be found
	 */
	@Override
	public CMICOrganization fetchByOrganizationId(
		long organizationId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {organizationId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByOrganizationId, finderArgs, this);
		}

		if (result instanceof CMICOrganization) {
			CMICOrganization cmicOrganization = (CMICOrganization)result;

			if ((organizationId != cmicOrganization.getOrganizationId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_CMICORGANIZATION_WHERE);

			query.append(_FINDER_COLUMN_ORGANIZATIONID_ORGANIZATIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(organizationId);

				List<CMICOrganization> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByOrganizationId, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {organizationId};
							}

							_log.warn(
								"CMICOrganizationPersistenceImpl.fetchByOrganizationId(long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CMICOrganization cmicOrganization = list.get(0);

					result = cmicOrganization;

					cacheResult(cmicOrganization);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByOrganizationId, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CMICOrganization)result;
		}
	}

	/**
	 * Removes the cmic organization where organizationId = &#63; from the database.
	 *
	 * @param organizationId the organization ID
	 * @return the cmic organization that was removed
	 */
	@Override
	public CMICOrganization removeByOrganizationId(long organizationId)
		throws NoSuchCMICOrganizationException {

		CMICOrganization cmicOrganization = findByOrganizationId(
			organizationId);

		return remove(cmicOrganization);
	}

	/**
	 * Returns the number of cmic organizations where organizationId = &#63;.
	 *
	 * @param organizationId the organization ID
	 * @return the number of matching cmic organizations
	 */
	@Override
	public int countByOrganizationId(long organizationId) {
		FinderPath finderPath = _finderPathCountByOrganizationId;

		Object[] finderArgs = new Object[] {organizationId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CMICORGANIZATION_WHERE);

			query.append(_FINDER_COLUMN_ORGANIZATIONID_ORGANIZATIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(organizationId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_ORGANIZATIONID_ORGANIZATIONID_2 =
		"cmicOrganization.organizationId = ?";

	private FinderPath _finderPathFetchByProducerId;
	private FinderPath _finderPathCountByProducerId;

	/**
	 * Returns the cmic organization where producerId = &#63; or throws a <code>NoSuchCMICOrganizationException</code> if it could not be found.
	 *
	 * @param producerId the producer ID
	 * @return the matching cmic organization
	 * @throws NoSuchCMICOrganizationException if a matching cmic organization could not be found
	 */
	@Override
	public CMICOrganization findByProducerId(long producerId)
		throws NoSuchCMICOrganizationException {

		CMICOrganization cmicOrganization = fetchByProducerId(producerId);

		if (cmicOrganization == null) {
			StringBundler msg = new StringBundler(4);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("producerId=");
			msg.append(producerId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchCMICOrganizationException(msg.toString());
		}

		return cmicOrganization;
	}

	/**
	 * Returns the cmic organization where producerId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param producerId the producer ID
	 * @return the matching cmic organization, or <code>null</code> if a matching cmic organization could not be found
	 */
	@Override
	public CMICOrganization fetchByProducerId(long producerId) {
		return fetchByProducerId(producerId, true);
	}

	/**
	 * Returns the cmic organization where producerId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param producerId the producer ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cmic organization, or <code>null</code> if a matching cmic organization could not be found
	 */
	@Override
	public CMICOrganization fetchByProducerId(
		long producerId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {producerId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByProducerId, finderArgs, this);
		}

		if (result instanceof CMICOrganization) {
			CMICOrganization cmicOrganization = (CMICOrganization)result;

			if ((producerId != cmicOrganization.getProducerId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_SELECT_CMICORGANIZATION_WHERE);

			query.append(_FINDER_COLUMN_PRODUCERID_PRODUCERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(producerId);

				List<CMICOrganization> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByProducerId, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {producerId};
							}

							_log.warn(
								"CMICOrganizationPersistenceImpl.fetchByProducerId(long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CMICOrganization cmicOrganization = list.get(0);

					result = cmicOrganization;

					cacheResult(cmicOrganization);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByProducerId, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CMICOrganization)result;
		}
	}

	/**
	 * Removes the cmic organization where producerId = &#63; from the database.
	 *
	 * @param producerId the producer ID
	 * @return the cmic organization that was removed
	 */
	@Override
	public CMICOrganization removeByProducerId(long producerId)
		throws NoSuchCMICOrganizationException {

		CMICOrganization cmicOrganization = findByProducerId(producerId);

		return remove(cmicOrganization);
	}

	/**
	 * Returns the number of cmic organizations where producerId = &#63;.
	 *
	 * @param producerId the producer ID
	 * @return the number of matching cmic organizations
	 */
	@Override
	public int countByProducerId(long producerId) {
		FinderPath finderPath = _finderPathCountByProducerId;

		Object[] finderArgs = new Object[] {producerId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CMICORGANIZATION_WHERE);

			query.append(_FINDER_COLUMN_PRODUCERID_PRODUCERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(producerId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_PRODUCERID_PRODUCERID_2 =
		"cmicOrganization.producerId = ?";

	public CMICOrganizationPersistenceImpl() {
		setModelClass(CMICOrganization.class);

		setModelImplClass(CMICOrganizationImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("active", "active_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the cmic organization in the entity cache if it is enabled.
	 *
	 * @param cmicOrganization the cmic organization
	 */
	@Override
	public void cacheResult(CMICOrganization cmicOrganization) {
		entityCache.putResult(
			entityCacheEnabled, CMICOrganizationImpl.class,
			cmicOrganization.getPrimaryKey(), cmicOrganization);

		finderCache.putResult(
			_finderPathFetchByOrganizationId,
			new Object[] {cmicOrganization.getOrganizationId()},
			cmicOrganization);

		finderCache.putResult(
			_finderPathFetchByProducerId,
			new Object[] {cmicOrganization.getProducerId()}, cmicOrganization);

		cmicOrganization.resetOriginalValues();
	}

	/**
	 * Caches the cmic organizations in the entity cache if it is enabled.
	 *
	 * @param cmicOrganizations the cmic organizations
	 */
	@Override
	public void cacheResult(List<CMICOrganization> cmicOrganizations) {
		for (CMICOrganization cmicOrganization : cmicOrganizations) {
			if (entityCache.getResult(
					entityCacheEnabled, CMICOrganizationImpl.class,
					cmicOrganization.getPrimaryKey()) == null) {

				cacheResult(cmicOrganization);
			}
			else {
				cmicOrganization.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all cmic organizations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CMICOrganizationImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the cmic organization.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CMICOrganization cmicOrganization) {
		entityCache.removeResult(
			entityCacheEnabled, CMICOrganizationImpl.class,
			cmicOrganization.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(CMICOrganizationModelImpl)cmicOrganization, true);
	}

	@Override
	public void clearCache(List<CMICOrganization> cmicOrganizations) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CMICOrganization cmicOrganization : cmicOrganizations) {
			entityCache.removeResult(
				entityCacheEnabled, CMICOrganizationImpl.class,
				cmicOrganization.getPrimaryKey());

			clearUniqueFindersCache(
				(CMICOrganizationModelImpl)cmicOrganization, true);
		}
	}

	protected void cacheUniqueFindersCache(
		CMICOrganizationModelImpl cmicOrganizationModelImpl) {

		Object[] args = new Object[] {
			cmicOrganizationModelImpl.getOrganizationId()
		};

		finderCache.putResult(
			_finderPathCountByOrganizationId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByOrganizationId, args, cmicOrganizationModelImpl,
			false);

		args = new Object[] {cmicOrganizationModelImpl.getProducerId()};

		finderCache.putResult(
			_finderPathCountByProducerId, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByProducerId, args, cmicOrganizationModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		CMICOrganizationModelImpl cmicOrganizationModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				cmicOrganizationModelImpl.getOrganizationId()
			};

			finderCache.removeResult(_finderPathCountByOrganizationId, args);
			finderCache.removeResult(_finderPathFetchByOrganizationId, args);
		}

		if ((cmicOrganizationModelImpl.getColumnBitmask() &
			 _finderPathFetchByOrganizationId.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				cmicOrganizationModelImpl.getOriginalOrganizationId()
			};

			finderCache.removeResult(_finderPathCountByOrganizationId, args);
			finderCache.removeResult(_finderPathFetchByOrganizationId, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				cmicOrganizationModelImpl.getProducerId()
			};

			finderCache.removeResult(_finderPathCountByProducerId, args);
			finderCache.removeResult(_finderPathFetchByProducerId, args);
		}

		if ((cmicOrganizationModelImpl.getColumnBitmask() &
			 _finderPathFetchByProducerId.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				cmicOrganizationModelImpl.getOriginalProducerId()
			};

			finderCache.removeResult(_finderPathCountByProducerId, args);
			finderCache.removeResult(_finderPathFetchByProducerId, args);
		}
	}

	/**
	 * Creates a new cmic organization with the primary key. Does not add the cmic organization to the database.
	 *
	 * @param cmicOrganizationId the primary key for the new cmic organization
	 * @return the new cmic organization
	 */
	@Override
	public CMICOrganization create(long cmicOrganizationId) {
		CMICOrganization cmicOrganization = new CMICOrganizationImpl();

		cmicOrganization.setNew(true);
		cmicOrganization.setPrimaryKey(cmicOrganizationId);

		return cmicOrganization;
	}

	/**
	 * Removes the cmic organization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param cmicOrganizationId the primary key of the cmic organization
	 * @return the cmic organization that was removed
	 * @throws NoSuchCMICOrganizationException if a cmic organization with the primary key could not be found
	 */
	@Override
	public CMICOrganization remove(long cmicOrganizationId)
		throws NoSuchCMICOrganizationException {

		return remove((Serializable)cmicOrganizationId);
	}

	/**
	 * Removes the cmic organization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cmic organization
	 * @return the cmic organization that was removed
	 * @throws NoSuchCMICOrganizationException if a cmic organization with the primary key could not be found
	 */
	@Override
	public CMICOrganization remove(Serializable primaryKey)
		throws NoSuchCMICOrganizationException {

		Session session = null;

		try {
			session = openSession();

			CMICOrganization cmicOrganization = (CMICOrganization)session.get(
				CMICOrganizationImpl.class, primaryKey);

			if (cmicOrganization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCMICOrganizationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(cmicOrganization);
		}
		catch (NoSuchCMICOrganizationException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected CMICOrganization removeImpl(CMICOrganization cmicOrganization) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(cmicOrganization)) {
				cmicOrganization = (CMICOrganization)session.get(
					CMICOrganizationImpl.class,
					cmicOrganization.getPrimaryKeyObj());
			}

			if (cmicOrganization != null) {
				session.delete(cmicOrganization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (cmicOrganization != null) {
			clearCache(cmicOrganization);
		}

		return cmicOrganization;
	}

	@Override
	public CMICOrganization updateImpl(CMICOrganization cmicOrganization) {
		boolean isNew = cmicOrganization.isNew();

		if (!(cmicOrganization instanceof CMICOrganizationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(cmicOrganization.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					cmicOrganization);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in cmicOrganization proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CMICOrganization implementation " +
					cmicOrganization.getClass());
		}

		CMICOrganizationModelImpl cmicOrganizationModelImpl =
			(CMICOrganizationModelImpl)cmicOrganization;

		Session session = null;

		try {
			session = openSession();

			if (cmicOrganization.isNew()) {
				session.save(cmicOrganization);

				cmicOrganization.setNew(false);
			}
			else {
				cmicOrganization = (CMICOrganization)session.merge(
					cmicOrganization);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}

		entityCache.putResult(
			entityCacheEnabled, CMICOrganizationImpl.class,
			cmicOrganization.getPrimaryKey(), cmicOrganization, false);

		clearUniqueFindersCache(cmicOrganizationModelImpl, false);
		cacheUniqueFindersCache(cmicOrganizationModelImpl);

		cmicOrganization.resetOriginalValues();

		return cmicOrganization;
	}

	/**
	 * Returns the cmic organization with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cmic organization
	 * @return the cmic organization
	 * @throws NoSuchCMICOrganizationException if a cmic organization with the primary key could not be found
	 */
	@Override
	public CMICOrganization findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCMICOrganizationException {

		CMICOrganization cmicOrganization = fetchByPrimaryKey(primaryKey);

		if (cmicOrganization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCMICOrganizationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return cmicOrganization;
	}

	/**
	 * Returns the cmic organization with the primary key or throws a <code>NoSuchCMICOrganizationException</code> if it could not be found.
	 *
	 * @param cmicOrganizationId the primary key of the cmic organization
	 * @return the cmic organization
	 * @throws NoSuchCMICOrganizationException if a cmic organization with the primary key could not be found
	 */
	@Override
	public CMICOrganization findByPrimaryKey(long cmicOrganizationId)
		throws NoSuchCMICOrganizationException {

		return findByPrimaryKey((Serializable)cmicOrganizationId);
	}

	/**
	 * Returns the cmic organization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param cmicOrganizationId the primary key of the cmic organization
	 * @return the cmic organization, or <code>null</code> if a cmic organization with the primary key could not be found
	 */
	@Override
	public CMICOrganization fetchByPrimaryKey(long cmicOrganizationId) {
		return fetchByPrimaryKey((Serializable)cmicOrganizationId);
	}

	/**
	 * Returns all the cmic organizations.
	 *
	 * @return the cmic organizations
	 */
	@Override
	public List<CMICOrganization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
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
	@Override
	public List<CMICOrganization> findAll(int start, int end) {
		return findAll(start, end, null);
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
	@Override
	public List<CMICOrganization> findAll(
		int start, int end,
		OrderByComparator<CMICOrganization> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
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
	@Override
	public List<CMICOrganization> findAll(
		int start, int end,
		OrderByComparator<CMICOrganization> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CMICOrganization> list = null;

		if (useFinderCache) {
			list = (List<CMICOrganization>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CMICORGANIZATION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CMICORGANIZATION;

				if (pagination) {
					sql = sql.concat(CMICOrganizationModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CMICOrganization>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CMICOrganization>)QueryUtil.list(
						q, getDialect(), start, end);
				}

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the cmic organizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CMICOrganization cmicOrganization : findAll()) {
			remove(cmicOrganization);
		}
	}

	/**
	 * Returns the number of cmic organizations.
	 *
	 * @return the number of cmic organizations
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_CMICORGANIZATION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "cmicOrganizationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CMICORGANIZATION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CMICOrganizationModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cmic organization persistence.
	 */
	@Activate
	public void activate() {
		CMICOrganizationModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		CMICOrganizationModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CMICOrganizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CMICOrganizationImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathFetchByOrganizationId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CMICOrganizationImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByOrganizationId",
			new String[] {Long.class.getName()},
			CMICOrganizationModelImpl.ORGANIZATIONID_COLUMN_BITMASK);

		_finderPathCountByOrganizationId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByOrganizationId",
			new String[] {Long.class.getName()});

		_finderPathFetchByProducerId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, CMICOrganizationImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByProducerId",
			new String[] {Long.class.getName()},
			CMICOrganizationModelImpl.PRODUCERID_COLUMN_BITMASK);

		_finderPathCountByProducerId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByProducerId",
			new String[] {Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CMICOrganizationImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = cmicPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.churchmutual.core.model.CMICOrganization"),
			true);
	}

	@Override
	@Reference(
		target = cmicPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = cmicPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CMICORGANIZATION =
		"SELECT cmicOrganization FROM CMICOrganization cmicOrganization";

	private static final String _SQL_SELECT_CMICORGANIZATION_WHERE =
		"SELECT cmicOrganization FROM CMICOrganization cmicOrganization WHERE ";

	private static final String _SQL_COUNT_CMICORGANIZATION =
		"SELECT COUNT(cmicOrganization) FROM CMICOrganization cmicOrganization";

	private static final String _SQL_COUNT_CMICORGANIZATION_WHERE =
		"SELECT COUNT(cmicOrganization) FROM CMICOrganization cmicOrganization WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "cmicOrganization.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CMICOrganization exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CMICOrganization exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CMICOrganizationPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"active"});

	static {
		try {
			Class.forName(cmicPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}