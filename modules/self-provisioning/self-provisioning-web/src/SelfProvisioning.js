import React from 'react';
import ClayButton from "@clayui/button";
import InviteMembers from "./InviteMembers";
import UserList from "./UserList";
import {Toast} from "commons-web";
import {ChangesFeedback, ChangesTrackerContext} from 'commons-web';

class SelfProvisioning extends React.Component {

  static contextType = ChangesTrackerContext;

  userListRef;

  constructor(props) {
    super(props);

    this.state = {
      isAdminOrOwner: false,
      isEditingUsers: false,
      inviteMembersVisible: false,
      membersToBeRemoved: [],
      roleTypes: [],
      toast:  {
        displayType: "",
        message: "",
        title: "",
      },
      updatedUserRoles: []
    };
  }

  componentDidMount() {
    this.getRoleTypes();
  }

  updateAccountMembers() {
      let data = JSON.stringify({
          "updateUsersRoles": this.state.updatedUserRoles,
          "removeUsers": this.state.membersToBeRemoved
      });

      let headers = new Headers();
      headers.set("Content-Type", "application/json");

      fetch(`/o/self-provisioning/update-account-members/${this.getCompanyId()}/${this.getUserId()}/${this.getGroupId()}`, {
          method: 'post',
          headers: headers,
          body: data
      }).then((response) => {
          if(!response.ok) {
              throw response;
          }

          this.userListRef.updateUserList();

          this.context.clearChanges();

          this.setState({
            isEditingUsers: false,
            updatedUserRoles: [],
            membersToBeRemoved: [],
          });

          this.displaySuccessMessage("your-request-completed-successfully")
      }).catch(
          () => this.displayErrorMessage("your-request-failed-to-complete")
      );
  }

  inviteMembers() {
    return this.state.inviteMembersVisible
      && !this.state.isEditingUsers
      && (
        <div>
            <hr className="policy-details-divider"/>
            <InviteMembers
                displayErrorMessage={(msg) => this.displayErrorMessage(msg)}
                displaySuccessMessage={(msg) => this.displaySuccessMessage(msg)}
                updateUserList={() => this.userListRef.updateUserList()}
                visible={this.state.inviteMembersVisible}
                onClickCancel={() => this.setState({inviteMembersVisible: false})}
            />
        </div>
    );
  }

  cancelSaveButtons() {
    return this.state.isEditingUsers && (
        <ClayButton.Group
          className="invite-members-buttons flex-nowrap account-user-control-buttons" spaced>
            <ChangesFeedback />
            <ClayButton displayType="secondary"
              onClick={() => this.cancelUpdateAccountMembers()}>
                {Liferay.Language.get("cancel")}
            </ClayButton>
            <ClayButton displayType="primary" onClick={() => this.updateAccountMembers()}>
                {Liferay.Language.get("save")}
            </ClayButton>
        </ClayButton.Group>
    );
  }

  editButton() {
    return !this.state.isEditingUsers && this.state.isAdminOrOwner && (
      <button className="btn btn-outline-primary account-user-control-buttons"
              onClick={() => this.setState({
                isEditingUsers: true,
                inviteMembersVisible: false
              })}>
        {Liferay.Language.get("edit")}
      </button>
    );
  }

  cancelUpdateAccountMembers() {
    this.context.clearChanges();
    this.userListRef.updateUserList();

    this.setState({
      isEditingUsers: false,
      updatedUserRoles: [],
      membersToBeRemoved: []
    });
  }

  addUserButton() {
    return !this.state.isEditingUsers && this.state.isAdminOrOwner && (
        <div className="self-provisioning-add-button">
            <button className="btn btn-outline-primary add-button"
              disabled={this.state.inviteMembersVisible} onClick={() => this.setState({inviteMembersVisible: true})}>
                {Liferay.Language.get("add +")}
            </button>
        </div>
    );
  }

  addUpdatedUserRole(user) {
    this.context.onChange();
    this.setState((previousState) => {
      let updatedUserRoles = [...previousState.updatedUserRoles]

      updatedUserRoles = updatedUserRoles.filter((e) => e.email !== user.email);

      user.changed = true;

      return {updatedUserRoles: [...updatedUserRoles, user]};
    });
  }

  addMemberToBeRemoved(user) {
    this.context.onChange();
    this.setState((previousState) => {
      return {membersToBeRemoved: [...previousState.membersToBeRemoved, user]}
    });
  }

  displayErrorMessage(msg) {
    this.setState({
      toast: {
        displayType: "danger",
        message: msg,
        title: Liferay.Language.get("error-colon")
      }
    });
  }

  displaySuccessMessage(msg) {
    this.setState({
      toast: {
        displayType: "success",
        message: msg,
        title: Liferay.Language.get("success-colon")
      }
    });
  }

  onToastClosed() {
    this.setState({
      toast: {
        displayType: "",
        message: "",
        title: ""
      }
    });
  }


  getRoleTypes() {
    fetch(`/o/self-provisioning/roleTypes/${this.getUserId()}/group/${this.getGroupId()}`)
      .then(res => res.json())
      .then(data => this.setState({ roleTypes: data }))
      .catch(() => this.props.displayErrorMessage("error.unable-to-retrieve-list-of-account-roles"));
  }

  getRoleLabel(value) {
    let roleTypes = this.state.roleTypes;

    let index = roleTypes.find(role => role.value == value);

    if (index) {
      return roleTypes[index].label;
    }
  }

  getRoleValue(label) {
    let roleTypes = this.state.roleTypes;

    let index = roleTypes.find(role => role.label == label);

    if (index) {
      return roleTypes[index].value;
    }
  }

  getCompanyId() {
    return Liferay.ThemeDisplay.getCompanyId();
  }

  getGroupId() {
    return Liferay.ThemeDisplay.getScopeGroupId();
  }

  getUserId() {
    return Liferay.ThemeDisplay.getUserId();
  }

  render() {
    return (
        <div>
          <div className="vertical">
            <h1 className="self-provisioning-portlet-title">
              {Liferay.Language.get('account-users')}

              {this.editButton()}
              {this.cancelSaveButtons()}
            </h1>

            <div>
              <UserList
                addMemberToBeRemoved={(user) => this.addMemberToBeRemoved(user)}
                addUpdatedUserRole={(user) => this.addUpdatedUserRole(user)}
                displayErrorMessage={(msg) => this.displayErrorMessage(msg)}
                displaySuccessMessage={(msg) => this.displaySuccessMessage(msg)}
                isEditingUsers={this.state.isEditingUsers}
                ref={(userListRef) => this.userListRef = userListRef}
                setIsAdminOrOwner={(isAdminOrOwner) => this.setState({isAdminOrOwner: isAdminOrOwner})}
              />
            </div>

            {this.addUserButton()}

            {this.inviteMembers()}

            <Toast
              displayType={this.state.toast.displayType}
              message={this.state.toast.message}
              onClose={() => this.onToastClosed()}
              title={this.state.toast.title} />
          </div>
        </div>
    );
  }
};
SelfProvisioning.contextType = ChangesTrackerContext;

export default SelfProvisioning;