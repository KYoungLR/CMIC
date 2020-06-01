<%@ include file="/init.jsp" %>

<label for="response">
	<liferay-ui:message key="response" />
</label>

<pre class="response-pre-container" id="response">
	${response}
</pre>

<liferay-ui:panel-container markupView="lexicon">
	<c:forEach items="${harnessDescriptors}" var="harnessDescriptor">
		<c:set var="title">
			<c:choose>
				<c:when test="${'DELETE' eq harnessDescriptor.method}">
					<button class="btn btn-danger" type="button">
						DELETE
					</button>
				</c:when>
				<c:when test="${'GET' eq harnessDescriptor.method}">
					<button class="btn btn-primary" type="button">
						GET
					</button>
				</c:when>
				<c:when test="${'POST' eq harnessDescriptor.method}">
					<button class="btn btn-success" type="button">
						POST
					</button>
				</c:when>
				<c:when test="${'PUT' eq harnessDescriptor.method}">
					<button class="btn btn-warning" type="button">
						PUT
					</button>
				</c:when>
			</c:choose>

			<liferay-ui:message key="${harnessDescriptor.endpoint}" />
		</c:set>

		<liferay-ui:panel
			collapsible="<%= true %>"
			markupView="lexicon"
			title="${title}"
		>
			<div>
				<strong><liferay-ui:message key="method-description" />:</strong> ${harnessDescriptor.description}
			</div>

			<c:if test="${harnessDescriptor.pathParameters.size() > 0}">
				<div>
					<strong><liferay-ui:message key="path-parameters" />:</strong>
				</div>

				<div>
					<c:set value="${harnessDescriptor.pathParameters}" var="parameters" />
					<%@ include file="/api/parameters.jspf" %>
				</div>
			</c:if>

			<c:if test="${harnessDescriptor.queryParameters.size() > 0}">
				<div>
					<strong><liferay-ui:message key="query-parameters" />:</strong>
				</div>

				<div>
					<c:set value="${harnessDescriptor.queryParameters}" var="parameters" />
					<%@ include file="/api/parameters.jspf" %>
				</div>
			</c:if>

			<c:if test="${harnessDescriptor.bodyParameters.size() > 0}">
				<div>
					<strong><liferay-ui:message key="body-parameters" />:</strong>
				</div>

				<div>
					<c:set value="${harnessDescriptor.bodyParameters}" var="parameters" />
					<%@ include file="/api/parameters.jspf" %>
				</div>
			</c:if>

			<aui:input name="screenNavigationCategoryKey" type="hidden" value="${screenNavigationCategoryKey}" />
			<aui:input name="screenNavigationEntryKey" type="hidden" value="${screenNavigationEntryKey}" />

			<portlet:resourceURL id="/invoke" var="invokeURL">
				<portlet:param name="screenNavigationCategoryKey" value="${screenNavigationCategoryKey}" />
				<portlet:param name="screenNavigationEntryKey" value="${screenNavigationEntryKey}" />
				<portlet:param name="endpoint" value="${harnessDescriptor.endpoint}" />
			</portlet:resourceURL>

			<aui:button-row>
				<aui:button cssClass="btn-info js-invoke" data-url="${invokeURL}" value="invoke" />
			</aui:button-row>
		</liferay-ui:panel>
	</c:forEach>
</liferay-ui:panel-container>