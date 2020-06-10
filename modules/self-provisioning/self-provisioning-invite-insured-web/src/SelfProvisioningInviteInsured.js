import React from 'react';
import {Dialog, Input} from "com.churchmutual.commons.web";

class SelfProvisioningInviteInsured extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      account: {
        name: ''
      },
      dialogModal: {
        visible: ''
      },
      emails: '',
      formErrors: {
        emails: null
      },
      invitationsSent: false
    };
  }

  getAccount() {
    //TODO CMIC-250 call web service to get account
  }

  componentDidMount() {
    this.getAccount();
  }

  sendInvitations(e) {

    //TODO CMIC-250 call web service to send invites
    //TODO CMIC-251 handle errors

    this.setState({
      dialogModal: {
        visible: true
      },
      invitationsSent: true
    });
  }

  showInviteDialog(show) {
    this.setState({
      dialogModal: {
        visible: show
      }
    });

    if (!show) {
      this.setState({
        invitationsSent: false
      });
    }
  }

  render() {
    return(
      <div className="self-provisioning-invite-insured-portlet elevation-2 p-4 sheet">
        <h2>
          {Liferay.Language.get('invite-insured-users')}
        </h2>
        <div>
          {Liferay.Language.get('give-insureds-access-to-this-account-by-inviting-them-with-an-email')}
        </div>
        <h4 className="pt-3">
          <a className="invite-link text-primary text-uppercase" onClick={() => this.showInviteDialog(true)}>
            {Liferay.Language.get('invite')}
          </a>
        </h4>

        <Dialog
          hideCancel={this.state.invitationsSent}
          title={Liferay.Language.get('invite-insureds-to') + this.state.account.name}
          confirmButtonText = {this.state.invitationsSent ? Liferay.Language.get('done') : Liferay.Language.get('invite')}
          onClickConfirm = {this.state.invitationsSent ? (e) => this.showInviteDialog(false) : (e) => this.sendInvitations(e)}
          visible = {this.state.dialogModal.visible}
          setVisible = {(show) => this.showInviteDialog(show)}
        >

          {/*TODO CMIC-251 implement validation on emails for error handling*/}

          {this.state.invitationsSent ?
            <div className="text-success text-center">
              {Liferay.Language.get('invitations-sent')}
            </div> :
            <Input
              fieldName="emails"
              label={Liferay.Language.get('emails')}
              labelHint={Liferay.Language.get('separate-emails-by-commas')}
              placeholder={Liferay.Language.get('emails')}
              showErrors={this.state.formErrors.emails}
              errorMsg={Liferay.Language.get('error.emails')}
            />
          }
        </Dialog>
      </div>
    );
  }
};

export default SelfProvisioningInviteInsured;