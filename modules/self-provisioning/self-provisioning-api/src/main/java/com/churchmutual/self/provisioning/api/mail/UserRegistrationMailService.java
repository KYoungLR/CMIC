package com.churchmutual.self.provisioning.api.mail;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;

/**
 * @author Luiz Marins
 */
public interface UserRegistrationMailService {

    public void sendMail(long groupId, long inviterUserId, User invitedUser) throws PortalException;
}
