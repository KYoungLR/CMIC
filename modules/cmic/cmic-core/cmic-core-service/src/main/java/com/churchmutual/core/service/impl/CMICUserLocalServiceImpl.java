package com.churchmutual.core.service.impl;

import com.churchmutual.commons.enums.BusinessPortalType;
import com.churchmutual.core.constants.CMICUserConstants;
import com.churchmutual.core.service.CMICUserLocalService;
import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.model.CMICUserDTO;
import com.churchmutual.self.provisioning.api.SelfProvisioningBusinessService;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(immediate = true, service = AopService.class)
public class CMICUserLocalServiceImpl implements AopService, CMICUserLocalService {

	@Override
	public User addUser(String cmicUUID, String registrationCode) throws PortalException {
		CMICUserDTO cmicUserDTO = _portalUserWebService.validateUser(registrationCode);

		return _addUser(cmicUUID, cmicUserDTO);
	}

	@Override
	public BusinessPortalType getBusinessPortalType(String registrationCode) throws PortalException {
		CMICUserDTO cmicUserDTO = _portalUserWebService.validateUserRegistration(registrationCode);

		return _getBusinessPortalType(cmicUserDTO);
	}

	@Override
	public boolean isUserRegistered(String cmicUUID) {
		return _portalUserWebService.isUserRegistered(cmicUUID);
	}

	@Override
	public boolean isUserValid(
		String businessZipCode, String divisionAgentNumber, String registrationCode, String cmicUUID) {

		return _portalUserWebService.isUserValid(businessZipCode, divisionAgentNumber, registrationCode, cmicUUID);
	}

	@Indexable(type = IndexableType.REINDEX)
	private User _addUser(String cmicUUID, CMICUserDTO cmicUserDTO) throws PortalException {
		_validateCMICUserDTO(cmicUserDTO);

		long companyId = _portal.getDefaultCompanyId();

		Company company = _companyLocalService.getCompany(companyId);

		User user = _userLocalService.fetchUserByUuidAndCompanyId(cmicUUID, companyId);

		User defaultUser = company.getDefaultUser();

		if (Validator.isNull(user)) {
			user = _userLocalService.addUser(
				defaultUser.getUserId(), companyId, CMICUserConstants.AUTO_PASSWORD, StringPool.BLANK, StringPool.BLANK,
				CMICUserConstants.AUTO_SCREEN_NAME, StringPool.BLANK, CMICUserConstants.EMAIL, 0, StringPool.BLANK,
				LocaleUtil.getDefault(), CMICUserConstants.FIRST_NAME, StringPool.BLANK, CMICUserConstants.LAST_NAME, 0,
				0, CMICUserConstants.IS_MALE, CMICUserConstants.BIRTHDAY_MONTH, CMICUserConstants.BIRTHDAY_DAY,
				CMICUserConstants.BIRTHDAY_YEAR, StringPool.BLANK, null, null, null, null,
				CMICUserConstants.SEND_EMAIL_NOTIFICATIONS, null);

			user.setExternalReferenceCode(cmicUUID);

			_userLocalService.updateUser(user);
		}

		return user;
	}

	private BusinessPortalType _getBusinessPortalType(CMICUserDTO cmicUserDTO) throws PortalException {
		_validateCMICUserDTO(cmicUserDTO);

		String userRole = cmicUserDTO.getUserRole();

		String[] splitStrings = userRole.split(StringPool.SPACE);

		if ((splitStrings != null) && (splitStrings.length > 1)) {
			switch (splitStrings[0]) {
				case "producer":
					return BusinessPortalType.BROKER;
				case "insured":
					return BusinessPortalType.INSURED;
				default:
					throw new PortalException("Error: the portal type was undefined for user with role " + userRole);
			}
		}

		throw new PortalException("Error: portal type was undefined user with role " + userRole);
	}

	private void _promoteFirstRegisteredUser(long userId, long entityId, boolean isProducerOrganization)
		throws PortalException {

		_selfProvisioningBusinessService.promoteFirstActiveUser(userId, entityId, isProducerOrganization);
	}

	private void _validateCMICUserDTO(CMICUserDTO cmicUserDTO) throws PortalException {
		if (Validator.isNull(cmicUserDTO)) {
			throw new PortalException("Error: received invalid cmicUser information");
		}
	}

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortalUserWebService _portalUserWebService;

	@Reference
	private SelfProvisioningBusinessService _selfProvisioningBusinessService;

	@Reference
	private UserLocalService _userLocalService;

}