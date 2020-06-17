package com.churchmutual.core.service.impl;

import com.churchmutual.commons.enums.BusinessPortalType;
import com.churchmutual.commons.util.CollectionsUtil;
import com.churchmutual.core.constants.CMICUserConstants;
import com.churchmutual.core.model.CMICOrganization;
import com.churchmutual.core.service.CMICOrganizationLocalService;
import com.churchmutual.core.service.base.CMICUserLocalServiceBaseImpl;
import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.model.CMICUserDTO;
import com.churchmutual.self.provisioning.api.SelfProvisioningBusinessService;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.PropertyFactoryUtil;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.LocaleUtil;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.util.Validator;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Kayleen Lim
 */
@Component(property = "model.class.name=com.churchmutual.core.model.CMICUser", service = AopService.class)
public class CMICUserLocalServiceImpl extends CMICUserLocalServiceBaseImpl {

	@Override
	public User addUser(String cmicUUID, String registrationCode) throws PortalException {
		CMICUserDTO cmicUserDTO = _portalUserWebService.validateUser(registrationCode);

		return _addUser(cmicUUID, cmicUserDTO);
	}

	@Override
	public BusinessPortalType getBusinessPortalType(long userId) throws PortalException {
		User user = _userLocalService.getUser(userId);

		Group brokerPortalGroup = _groupLocalService.getFriendlyURLGroup(
			user.getCompanyId(), BusinessPortalType.BROKER.getFriendlyURL());

		if (_groupLocalService.hasUserGroup(userId, brokerPortalGroup.getGroupId())) {

			return BusinessPortalType.BROKER;
		}

		throw new PortalException("Error: portal type was undefined for user " + userId);
	}

	@Override
	public BusinessPortalType getBusinessPortalType(String registrationCode) throws PortalException {
		CMICUserDTO cmicUserDTO = _portalUserWebService.validateUserRegistration(registrationCode);

		return _getBusinessPortalType(cmicUserDTO);
	}

	@Override
	public List<User> getCMICOrganizationUsers(long cmicOrganizationId) throws PortalException {
		CMICOrganization cmicOrganization = _cmicOrganizationLocalService.getCMICOrganization(cmicOrganizationId);

		List<CMICUserDTO> cmicUserDTOList = _portalUserWebService.getCMICOrganizationUsers(cmicOrganization.getProducerId());

		//TODO CMIC-273

		long organizationId = cmicOrganization.getOrganizationId();

		return _userLocalService.getOrganizationUsers(organizationId);
	}

	@Override
	public User getUser(String cmicUUID) {
		DynamicQuery dynamicQuery = _userLocalService.dynamicQuery();

		dynamicQuery.add(PropertyFactoryUtil.forName("externalReferenceCode").like(cmicUUID));

		return CollectionsUtil.getFirst(_userLocalService.dynamicQuery(dynamicQuery));
	}

	@Override
	public void inviteUsersToCMICOrganization(String[] emailAddresses, long cmicOrganizationId) throws PortalException {
		for (String emailAddress: emailAddresses) {
			CMICOrganization cmicOrganization = _cmicOrganizationLocalService.fetchCMICOrganization(cmicOrganizationId);

			if (Validator.isNotNull(cmicOrganization)) {
				_portalUserWebService.inviteUserToCMICOrganization(emailAddress.trim(), cmicOrganization.getProducerId());
			}
		}
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

	@Override
	public void removeUserFromCMICOrganization(long userId, long cmicOrganizationId) throws PortalException {
		User user = _userLocalService.getUser(userId);

		String cmicUUID = user.getExternalReferenceCode();

		CMICOrganization cmicOrganization = _cmicOrganizationLocalService.getCMICOrganization(cmicOrganizationId);

		_portalUserWebService.removeUserFromCMICOrganization(cmicUUID, cmicOrganization.getProducerId());
	}

	@Override
	public void validateUserRegistration(String registrationCode) throws PortalException {
		BusinessPortalType businessPortalType = getBusinessPortalType(registrationCode);

		if (BusinessPortalType.INSURED.equals(businessPortalType)) {
			throw new PortalException("User must have the Broker portal type");
		}
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

		throw new PortalException("Error: portal type was undefined for user with role " + userRole);
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
	private CMICOrganizationLocalService _cmicOrganizationLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private Portal _portal;

	@Reference
	private PortalUserWebService _portalUserWebService;

	@Reference
	private SelfProvisioningBusinessService _selfProvisioningBusinessService;

	@Reference
	private UserLocalService _userLocalService;

}