<%@ include file="/init.jsp" %>

<%
String resolvedModuleName = GetterUtil.getString(request.getAttribute("resolvedModuleName"));
%>

<portlet:actionURL name="<%= TestHarnessMVCActionCommand.MVC_COMMAND_NAME %>" var="invokeURL" />

<aui:form action="<%= invokeURL %>" cssClass="api-container" method="post" name="fm">
	<liferay-frontend:screen-navigation
		key="<%= TestHarnessConstants.SCREEN_NAVIGATION_KEY %>"
		portletURL="<%= currentURLObj %>"
	/>
</aui:form>

<aui:script require="<%= resolvedModuleName %>">
	var TestHarnessObj = TestHarness.default;
	var testHarness = new TestHarnessObj('.api-container');
</aui:script>