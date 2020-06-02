package com.churchmutual.self.provisioning.service.mail;

import com.churchmutual.self.provisioning.api.BusinessUserService;
import com.churchmutual.self.provisioning.api.mail.UserRegistrationMailService;
import com.liferay.journal.model.JournalArticle;
import com.liferay.journal.service.JournalArticleLocalService;
import com.liferay.mail.kernel.model.MailMessage;
import com.liferay.mail.kernel.service.MailService;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.model.User;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.PrefsPropsUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

/**
 * @author Luiz Marins
 */
@Component(immediate = true, service = UserRegistrationMailService.class)
public class UserRegistrationMailServiceImpl implements UserRegistrationMailService {

    @Override
    public void sendMail(long groupId, long inviterUserId, User invitedUser) throws PortalException {
        final String emailFrom = PrefsPropsUtil.getString(PropsKeys.MAIL_SESSION_MAIL_SMTP_USER);

        final String body = _buildEmailBody(groupId, inviterUserId, invitedUser);

        _sendMail(emailFrom, invitedUser.getEmailAddress(), body);
    }

    public void _sendMail(String emailFrom, String emailTo, String body) throws PortalException {
        try {
            InternetAddress fromAddr = new InternetAddress(emailFrom);

            InternetAddress toAddr = new InternetAddress(emailTo);

            MailMessage mailMessage = new MailMessage(fromAddr, toAddr, _EMAIL_SUBJECT, body, _HTML_FORMAT);

            _mailService.sendEmail(mailMessage);
        } catch (AddressException ae) {
            throw new PortalException(ae);
        }
    }

    private String _buildEmailBody(
            long groupId, long inviterUserId, User invitedUser)
        throws PortalException {

        String body = _getEmailBodyTemplate(groupId);

//        body = body.replace(
//            "$[CMIC_LOGO]",
//            _businessUserService.getInsuranceCompanyLogoURL(invitedUser.getCompanyId()));
        body = body.replace("$[REFERRAL_NAME]", _getReferralName(inviterUserId));
        body = body.replace("$[DASHBOARD_URL]", _createAccountURLBuilder.build(groupId, invitedUser));
        body = body.replace("$[CONTACT_PRODUCER_URL]", "javascript:;");
        body = body.replace("<![CDATA[", StringPool.BLANK);
        body = body.replace("]]>", StringPool.BLANK);

        return body;
    }

    private String _getReferralName(long inviterUserId) throws PortalException {
        User inviter = _userLocalService.getUserById(inviterUserId);

        return inviter.getFirstName() + StringPool.SPACE + inviter.getLastName();
    }

    private String _getEmailBodyTemplate(long groupId) throws PortalException {
        JournalArticle article = _journalArticleLocalService.getArticleByUrlTitle(
                groupId, _TEMPLATE_ARTICLE_URL_TITLE);

        return article.getContent();
    }


    @Reference
    private CreateAccountURLBuilder _createAccountURLBuilder;

    @Reference
    private BusinessUserService _businessUserService;

    @Reference
    private JournalArticleLocalService _journalArticleLocalService;

    @Reference
    private MailService _mailService;

    @Reference
    private UserLocalService _userLocalService;

    static final String _TEMPLATE_ARTICLE_URL_TITLE = "account-invitation-email-template";

    static final String _EMAIL_SUBJECT = "Welcome to Church Mutual";

    private static final boolean _HTML_FORMAT = true;
}