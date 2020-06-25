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

package com.churchmutual.core.service.impl;

import com.churchmutual.core.model.CMICOrganization;
import com.churchmutual.core.service.CMICOrganizationLocalService;
import com.churchmutual.core.service.base.CMICCommissionDocumentLocalServiceBaseImpl;
import com.churchmutual.rest.CommissionDocumentWebService;
import com.churchmutual.rest.PortalUserWebService;
import com.churchmutual.rest.model.CMICCommissionDocumentDTO;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.aop.AopService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.UserLocalService;

import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * The implementation of the cmic commission document local service.
 *
 * <p>
 * All custom service methods should be put in this class. Whenever methods are added, rerun ServiceBuilder to copy their definitions into the <code>com.churchmutual.core.service.CMICCommissionDocumentLocalService</code> interface.
 *
 * <p>
 * This is a local service. Methods of this service will not have security checks based on the propagated JAAS credentials because this service can only be accessed from within the same VM.
 * </p>
 *
 * @author Kayleen Lim
 * @see CMICCommissionDocumentLocalServiceBaseImpl
 */
@Component(property = "model.class.name=com.churchmutual.core.model.CMICCommissionDocument", service = AopService.class)
public class CMICCommissionDocumentLocalServiceImpl extends CMICCommissionDocumentLocalServiceBaseImpl {

	@Override
	public JSONArray getCommissionDocuments(long userId) throws PortalException {
		JSONArray response = _jsonFactory.createJSONArray();

		List<CMICCommissionDocumentDTO> cmicCommissionDocumentDTOs = new ArrayList<>();

		List<CMICOrganization> userOrganizations = _cmicOrganizationLocalService.getCMICUserOrganizations(userId);

		for (CMICOrganization cmicOrganization : userOrganizations) {
			List<CMICCommissionDocumentDTO> commissionDocumentDTOs = _commissionDocumentWebService.searchDocuments(
				cmicOrganization.getAgentNumber(), cmicOrganization.getDivisionNumber(), StringPool.BLANK,
				StringPool.BLANK, StringPool.BLANK);

			cmicCommissionDocumentDTOs.addAll(commissionDocumentDTOs);
		}

		// TODO remove this mock service call

		cmicCommissionDocumentDTOs = _commissionDocumentWebService.searchDocuments(
			StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK, StringPool.BLANK);

		for (CMICCommissionDocumentDTO cmicCommissionDocumentDTO : cmicCommissionDocumentDTOs) {
			JSONObject jsonObject = _jsonFactory.createJSONObject();

			String statementDate = _getFormattedStatementDate(cmicCommissionDocumentDTO.getStatementDate());
			String documentType = "Commission Remittance Statement";

			jsonObject.put("documentId", cmicCommissionDocumentDTO.getId());
			jsonObject.put("label", statementDate + " - " + documentType);
			jsonObject.put("url", "/");

			response.put(jsonObject);
		}

		return response;
	}

	private String _getFormattedStatementDate(String date) {
		SimpleDateFormat format1 = new SimpleDateFormat(_FORMAT_YYYY_MM_DD);
		SimpleDateFormat format2 = new SimpleDateFormat(_FORMAT_MM_DD_YYYY);

		try {
			Date statementDate = format1.parse(date);

			return format2.format(statementDate);
		}
		catch (Exception e) {
			_log.warn(e);

			return date;
		}
	}

	private static final String _FORMAT_MM_DD_YYYY = "MM/dd/yyyy";

	private static final String _FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	private static final Log _log = LogFactoryUtil.getLog(CMICCommissionDocumentLocalServiceImpl.class);

	@Reference
	private CMICOrganizationLocalService _cmicOrganizationLocalService;

	@Reference
	private CommissionDocumentWebService _commissionDocumentWebService;

	@Reference
	private JSONFactory _jsonFactory;

	@Reference
	private PortalUserWebService _portalUserWebService;

	@Reference
	private UserLocalService _userLocalService;

}