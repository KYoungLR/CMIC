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

			<div>
				<strong><liferay-ui:message key="parameters" />:</strong>
			</div>

			<div>
				<c:forEach items="${harnessDescriptor.parameters}" var="parameter">
					<c:choose>
						<c:when test="${fn:contains(parameter.type, '[]')}">
							<div class="array-field">
								<div class="d-none row template-row">
									<div class="col-9">
										<aui:input
											label=""
											name="${parameter.name}"
											required="${parameter.required}"
											type="text"
										/>
									</div>

									<div class="col-3">
										<button class="btn btn-default remove-item" type="button"><liferay-ui:message key="remove" /></button>
									</div>
								</div>

								<c:forEach items="${parameter.sampleValue}" var="sampleValue" varStatus="loop">
									<c:set value="${parameter.name}: ${parameter.type} / ${parameter.description}" var="label" />

									<c:if test="${loop.index > 0}">
										<c:set value="" var="label" />
									</c:if>

									<div class="row">
										<div class="col-9">
											<aui:input
												label="${label}"
												name="${parameter.name}"
												required="${parameter.required}"
												type="text"
												value="${sampleValue}"
											/>
										</div>

										<div class="col-3">
											<c:if test="${loop.index > 0}">
												<button class="btn btn-default remove-item" type="button"><liferay-ui:message key="remove" /></button>
											</c:if>
										</div>
									</div>
								</c:forEach>

								<button class="add-item btn btn-default" type="button"><liferay-ui:message key="add-item" /></button>
							</div>
						</c:when>
						<c:when test="${fn:contains(parameter.type, 'String')}">
							<aui:input
								label="${parameter.name}: ${parameter.type} / ${parameter.description}"
								name="${parameter.name}"
								required="${parameter.required}"
								type="text"
								value="${parameter.sampleValue}"
							/>
						</c:when>
						<c:when test="${fn:contains(parameter.type, 'Boolean')}">
							<aui:input
								checked="${parameter.sampleValue}"
								label="${parameter.name}: ${parameter.type} / ${parameter.description}"
								name="${parameter.name}"
								required="${parameter.required}"
								type="checkbox"
							/>
						</c:when>
						<c:when test="${fn:contains(parameter.type, 'Long') or fn:contains(parameter.type, 'Integer')}">
							<aui:input
								label="${parameter.name}: ${parameter.type} / ${parameter.description}"
								name="${parameter.name}"
								required="${parameter.required}"
								type="text"
								value="${parameter.sampleValue}"
							>
								<aui:validator name="number" />
							</aui:input>
						</c:when>
					</c:choose>
				</c:forEach>
			</div>

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