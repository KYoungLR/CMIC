package com.churchmutual.personal.menu.portlet.command;

import com.churchmutual.core.service.CMICUserLocalService;
import com.churchmutual.personal.menu.constants.PersonalMenuPortletKeys;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCRenderCommand;
import com.liferay.portal.kernel.theme.ThemeDisplay;
import com.liferay.portal.kernel.util.HtmlUtil;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.kernel.util.WebKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

/**
 * @author Matthew Chan
 */
@Component(
	immediate = true,
	property = {
		"javax.portlet.name=" + PersonalMenuPortletKeys.PERSONAL_MENU_PORTLET,
		"mvc.command.name=/"
	},
	service = MVCRenderCommand.class
)
public class PersonalMenuMVCRenderCommand implements MVCRenderCommand {

	@Override
	public String render(RenderRequest renderRequest, RenderResponse renderResponse) {
		renderRequest.setAttribute("profilePageRedirect", _getProfilePageRedirect(renderRequest));
		renderRequest.setAttribute("signInRedirect", _getSignInRedirect(renderRequest));

		return "/view.jsp";
	}

	private String _getProfilePageRedirect(RenderRequest renderRequest) {

		ThemeDisplay themeDisplay = (ThemeDisplay)renderRequest.getAttribute(WebKeys.THEME_DISPLAY);

		if (themeDisplay.isImpersonated()) {
			return PROFILE_PAGE_REDIRECT + "?doAsUserId=" + HtmlUtil.escapeURL(themeDisplay.getDoAsUserId());
		}

		return PROFILE_PAGE_REDIRECT;
	}

	private String _getSignInRedirect(RenderRequest renderRequest) {
		String portalURL = PortalUtil.getPortalURL(renderRequest);

		return portalURL + "/c/portal/login";
	}

	private static final String PROFILE_PAGE_REDIRECT = "/group/broker/profile";

	@Reference
	private CMICUserLocalService _cmicUserLocalService;

}