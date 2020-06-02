import React from 'react';
import ClayButton from '@clayui/button';
import {Input} from "com.churchmutual.commons.web";

export default class extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      emails: "",
      showErrors: false
    };
    this.emailsInputRef = React.createRef();
  }

  clearMemberEmails() {
    this.setState({
      emails: "",
      showInviteMembers: false
    });
    this.emailsInputRef.current.value = '';
    this.setShowErrors(false);
    this.props.onClickCancel();
  }

  getGroupId() {
    return Liferay.ThemeDisplay.getScopeGroupId();
  }

  getUserId() {
    return Liferay.ThemeDisplay.getUserId();
  }

  setShowErrors(value) {
    this.setState({
      showErrors: value
    })
  }

  updateAccountUserEntries() {
    this.props.updateUserList();
  }

  updateInvitations(value) {
    this.setState({
      emails: value
    })
  }

  submit() {
    let data = JSON.stringify({
      emails: this.state.emails,
      groupId: this.getGroupId(),
      userId: this.getUserId()
    });

    let headers = new Headers();
    headers.set("Content-Type", "application/json");

    fetch(`/o/self-provisioning/invite-members/`, {
      method: 'post',
      headers: headers,
      body: data
    }).then((response) =>  {
      if (!response.ok) {
        throw response;
      }
      this.setShowErrors(false);
      this.updateAccountUserEntries();
      this.props.displaySuccessMessage("your-request-completed-successfully")
      this.clearMemberEmails();
    }).catch((e) => {
        e.text().then(message => {
          if (message == "") {
            message = "your-request-failed-to-complete";
          }
          this.props.displayErrorMessage(message)
        });
      }
    );
  }

  validateAndSubmit() {
    let emails = this.state.emails.split(",");

    for (const email of emails) {
      if (email.indexOf('@') < 0 || email.indexOf('.') < 0) {
        this.setShowErrors(true);
        return;
      }

      // check if multiple emails exist without a separator, denoted by special character @

      const re = new RegExp("@.*@");

      if (re.test(email)) {
        this.setShowErrors(true);
        return;
      }
    }

    this.submit();
  }

  render () {
    return this.props.visible && (
      <div>
        <div>
          <label className="invite-members-text">{Liferay.Language.get("invite-members")}</label>
        </div>
        <div className="invite-members-container">
          <div className="invite-members-form">
            <Input
              defaultValue={this.state.emails}
              label={Liferay.Language.get("email-s")}
              fieldName="invitationEmailsInput"
              handleFieldChange={(fieldName, fieldValue) => this.updateInvitations(fieldValue)}
              showErrors={this.state.showErrors}
              errorMsg={Liferay.Language.get('please-enter-a-valid-email-address')}
              inputRef={this.emailsInputRef}
            />
          </div>
          <ClayButton.Group className="invite-members-buttons flex-nowrap" spaced>
            <ClayButton displayType="secondary" onClick={() => this.clearMemberEmails()}>
              {Liferay.Language.get("cancel")}
            </ClayButton>
            <ClayButton displayType="primary" onClick={() => this.validateAndSubmit()}>
              {Liferay.Language.get("invite")}
            </ClayButton>
          </ClayButton.Group>
        </div>
        <label className="separate-emails-by-commas">{Liferay.Language.get("separate-emails-by-commas")}</label>
      </div>
    );
  }
}