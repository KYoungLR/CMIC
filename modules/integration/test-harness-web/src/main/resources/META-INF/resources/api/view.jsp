<%@ include file="/init.jsp" %>

<label for="response">
	<liferay-ui:message key="response" />
</label>

<pre class="response-pre-container" id="response">
	${response}
</pre>

<c:forEach items="${harnessDescriptors}" var="harnessDescriptor">
	<h3>
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
	</h3>

	<div>
		<strong><liferay-ui:message key="method-description" />:</strong> ${harnessDescriptor.description}
	</div>

	<div>
		<strong><liferay-ui:message key="parameters" />:</strong>
	</div>

	<ul>
		<c:forEach items="${harnessDescriptor.parameters}" var="parameter">
			<li><strong>${parameter.name}:</strong> ${parameter.type} / ${parameter.description} / ${parameter.required ? 'required' : 'not-required'} </li>
		</c:forEach>
	</ul>

	<aui:input name="screenNavigationCategoryKey" type="hidden" value="${screenNavigationCategoryKey}" />
	<aui:input name="screenNavigationEntryKey" type="hidden" value="${screenNavigationEntryKey}" />

	<portlet:resourceURL id="/invoke" var="invokeURL">
		<portlet:param name="screenNavigationCategoryKey" value="${screenNavigationCategoryKey}" />
		<portlet:param name="screenNavigationEntryKey" value="${screenNavigationEntryKey}" />
		<portlet:param name="endpoint" value="${harnessDescriptor.endpoint}" />
	</portlet:resourceURL>

	<aui:button-row>
		<aui:button cssClass="js-invoke" data-url="${invokeURL}" value="invoke" />
	</aui:button-row>
</c:forEach>