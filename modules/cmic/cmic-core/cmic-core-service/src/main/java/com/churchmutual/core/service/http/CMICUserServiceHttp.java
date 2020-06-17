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

package com.churchmutual.core.service.http;

import com.churchmutual.core.service.CMICUserServiceUtil;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.HttpPrincipal;
import com.liferay.portal.kernel.service.http.TunnelUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;

/**
 * Provides the HTTP utility for the
 * <code>CMICUserServiceUtil</code> service
 * utility. The
 * static methods of this class calls the same methods of the service utility.
 * However, the signatures are different because it requires an additional
 * <code>HttpPrincipal</code> parameter.
 *
 * <p>
 * The benefits of using the HTTP utility is that it is fast and allows for
 * tunneling without the cost of serializing to text. The drawback is that it
 * only works with Java.
 * </p>
 *
 * <p>
 * Set the property <b>tunnel.servlet.hosts.allowed</b> in portal.properties to
 * configure security.
 * </p>
 *
 * <p>
 * The HTTP utility is only generated for remote services.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICUserServiceSoap
 * @generated
 */
public class CMICUserServiceHttp {

	public static com.liferay.portal.kernel.model.User addUser(
			HttpPrincipal httpPrincipal, String cmicUUID,
			String registrationCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "addUser", _addUserParameterTypes0);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cmicUUID, registrationCode);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.churchmutual.commons.enums.BusinessPortalType
			getBusinessPortalType(
				HttpPrincipal httpPrincipal, String registrationCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "getBusinessPortalType",
				_getBusinessPortalTypeParameterTypes1);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, registrationCode);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.churchmutual.commons.enums.BusinessPortalType)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.churchmutual.commons.enums.BusinessPortalType
			getBusinessPortalType(HttpPrincipal httpPrincipal, long userId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "getBusinessPortalType",
				_getBusinessPortalTypeParameterTypes2);

			MethodHandler methodHandler = new MethodHandler(methodKey, userId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.churchmutual.commons.enums.BusinessPortalType)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static java.util.List<com.liferay.portal.kernel.model.User>
			getCMICOrganizationUsers(
				HttpPrincipal httpPrincipal, long cmicOrganizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "getCMICOrganizationUsers",
				_getCMICOrganizationUsersParameterTypes3);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cmicOrganizationId);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (java.util.List<com.liferay.portal.kernel.model.User>)
				returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static com.liferay.portal.kernel.model.User getUser(
		HttpPrincipal httpPrincipal, String cmicUUID) {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "getUser", _getUserParameterTypes4);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cmicUUID);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return (com.liferay.portal.kernel.model.User)returnObj;
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void inviteUserToCMICOrganization(
			HttpPrincipal httpPrincipal, String[] emailAddresses,
			long cmicOrganizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "inviteUserToCMICOrganization",
				_inviteUserToCMICOrganizationParameterTypes5);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, emailAddresses, cmicOrganizationId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean isUserRegistered(
		HttpPrincipal httpPrincipal, String cmicUUID) {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "isUserRegistered",
				_isUserRegisteredParameterTypes6);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, cmicUUID);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static boolean isUserValid(
		HttpPrincipal httpPrincipal, String businessZipCode,
		String divisionAgentNumber, String registrationCode, String cmicUUID) {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "isUserValid",
				_isUserValidParameterTypes7);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, businessZipCode, divisionAgentNumber,
				registrationCode, cmicUUID);

			Object returnObj = null;

			try {
				returnObj = TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}

			return ((Boolean)returnObj).booleanValue();
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void removeUserFromCMICOrganization(
			HttpPrincipal httpPrincipal, long userId, long cmicOrganizationId)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "removeUserFromCMICOrganization",
				_removeUserFromCMICOrganizationParameterTypes8);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, userId, cmicOrganizationId);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	public static void validateUserRegistration(
			HttpPrincipal httpPrincipal, String registrationCode)
		throws com.liferay.portal.kernel.exception.PortalException {

		try {
			MethodKey methodKey = new MethodKey(
				CMICUserServiceUtil.class, "validateUserRegistration",
				_validateUserRegistrationParameterTypes9);

			MethodHandler methodHandler = new MethodHandler(
				methodKey, registrationCode);

			try {
				TunnelUtil.invoke(httpPrincipal, methodHandler);
			}
			catch (Exception e) {
				if (e instanceof
						com.liferay.portal.kernel.exception.PortalException) {

					throw (com.liferay.portal.kernel.exception.PortalException)
						e;
				}

				throw new com.liferay.portal.kernel.exception.SystemException(
					e);
			}
		}
		catch (com.liferay.portal.kernel.exception.SystemException se) {
			_log.error(se, se);

			throw se;
		}
	}

	private static Log _log = LogFactoryUtil.getLog(CMICUserServiceHttp.class);

	private static final Class<?>[] _addUserParameterTypes0 = new Class[] {
		String.class, String.class
	};
	private static final Class<?>[] _getBusinessPortalTypeParameterTypes1 =
		new Class[] {String.class};
	private static final Class<?>[] _getBusinessPortalTypeParameterTypes2 =
		new Class[] {long.class};
	private static final Class<?>[] _getCMICOrganizationUsersParameterTypes3 =
		new Class[] {long.class};
	private static final Class<?>[] _getUserParameterTypes4 = new Class[] {
		String.class
	};
	private static final Class<?>[]
		_inviteUserToCMICOrganizationParameterTypes5 = new Class[] {
			String[].class, long.class
		};
	private static final Class<?>[] _isUserRegisteredParameterTypes6 =
		new Class[] {String.class};
	private static final Class<?>[] _isUserValidParameterTypes7 = new Class[] {
		String.class, String.class, String.class, String.class
	};
	private static final Class<?>[]
		_removeUserFromCMICOrganizationParameterTypes8 = new Class[] {
			long.class, long.class
		};
	private static final Class<?>[] _validateUserRegistrationParameterTypes9 =
		new Class[] {String.class};

}