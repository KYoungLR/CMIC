package com.churchmutual.self.provisioning.service.mail;

import com.churchmutual.commons.constants.LayoutURLKeyConstants;
import com.churchmutual.commons.enums.BusinessPortalType;
import com.churchmutual.commons.util.PortalHostUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Luiz Marins
 */
@Component(immediate = true, service = CreateAccountURLBuilder.class)
public class CreateAccountURLBuilder {

    public String build(long groupId, User invitedUser) throws PortalException {
        Group group = _groupLocalService.getGroup(groupId);

        StringBundler url = new StringBundler(5);

        url.append(PortalHostUtil.getHost(groupId));
        url.append(PropsUtil.get(PropsKeys.LAYOUT_FRIENDLY_URL_PUBLIC_SERVLET_MAPPING));
        url.append(group.getFriendlyURL());
        url.append(_getUserRegistrationFriendlyUrl(group));
        url.append(_getUserUuidParameter(invitedUser));

        return url.toString();
    }

    private String _getUserRegistrationFriendlyUrl(Group group) {
        String friendlyURL = group.getFriendlyURL();

        return BusinessPortalType.BROKER.getFriendlyURL().equals(friendlyURL)
            ? LayoutURLKeyConstants.LAYOUT_FURL_BROKER_USER_REGISTRATION
            : LayoutURLKeyConstants.LAYOUT_FURL_INSURED_USER_REGISTRATION;
    }

    private String _getUserUuidParameter(User invitedUser) {
        return "?uuid=" + invitedUser.getUuid();
    }

    @Reference
    private GroupLocalService _groupLocalService;
}
