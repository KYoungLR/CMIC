<%--
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
--%>

<%@ include file="init.jsp" %>

<c:choose>
	<c:when test="${themeDisplay.isSignedIn()}">
		<liferay-util:buffer var="userAvatar">
			<span class="user-avatar-link">
				<span class="sticker sticker-lg">
					<span class="inline-item">
						<liferay-ui:user-portrait
							cssClass="sticker-lg"
							user="<%= user %>"
						/>
					</span>

					<c:if test="${themeDisplay.isImpersonated()}">
						<span class="sticker sticker-bottom-right sticker-circle sticker-outside sticker-sm sticker-user-icon">
							<aui:icon image="user" markupView="lexicon" />
						</span>
					</c:if>
				</span>
			</span>
		</liferay-util:buffer>

		<clay:link
			href="${profilePageRedirect}"
			label="<%= userAvatar %>"
			contributorKey="PersonalMenuKey"
		/>
	</c:when>
	<c:otherwise>

		<%
		Map<String, Object> anchorData = new HashMap<String, Object>();
		anchorData.put("redirect", String.valueOf(PortalUtil.isLoginRedirectRequired(request)));
		%>

		<span class="sign-in text-default" role="presentation">
			<a href="${signInRedirect}" class="sign-in text-default d-flex align-items-center" data-redirect="false">
				<clay:icon symbol="user" />
				<span class="taglib-icon-label"><liferay-ui:message key="sign-in" /></span>
			</a>
		</span>
	</c:otherwise>
</c:choose>