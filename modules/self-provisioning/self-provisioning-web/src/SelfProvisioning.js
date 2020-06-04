import React from 'react';
import UserList from "./UserList";
import InviteMembers from "./InviteMembers";
import ClayButton from "@clayui/button";
import {Toast} from "com.churchmutual.commons.web";
import {ChangesFeedback, ChangesTrackerContext} from 'com.churchmutual.commons.web';
import {ClaySelect} from "@clayui/form";

class SelfProvisioning extends React.Component {

  static contextType = ChangesTrackerContext;

  userListRef;

  constructor(props) {
    super(props);

    this.state = {
      businessesList: [],
      groupId: 0,
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
    this.getBusinessesList();
    this.getRoleTypes();
  }

  updateAccountMembers() {
      let data = JSON.stringify({
          "updateUsersRoles": this.state.updatedUserRoles,
          "removeUsers": this.state.membersToBeRemoved
      });

      let headers = new Headers();
      headers.set("Content-Type", "application/json");

      fetch(`/o/self-provisioning/update-account-members/${this.getCompanyId()}/${this.getUserId()}/${this.state.groupId}`, {
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
            <hr className="policy-details-divider mb-3"/>
            <InviteMembers
                displayErrorMessage={(msg) => this.displayErrorMessage(msg)}
                displaySuccessMessage={(msg) => this.displaySuccessMessage(msg)}
                groupId={this.state.groupId}
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

  businessSelect() {
    if (this.state.isEditingUsers) {
      let business = this.state.businessesList.find((b) => b.groupId == this.state.groupId)
      return (
        <div className="businessSelector">
          <div className="viewing">{Liferay.Language.get('viewing')}</div>
          <div>
            {business.name}
          </div>
        </div>
      );
    }

    return (
      <div className="businessSelector">
        <div className="viewing">{Liferay.Language.get('viewing')}</div>
        <ClaySelect onChange={(e) => this.updateBusinessUsers(e.target.value)} value={this.state.groupId}>
          {this.state.businessesList.map(business => (
            <ClaySelect.Option
              key={business.groupId}
              label={business.name}
              value={business.groupId}
            />
          ))}
        </ClaySelect>
      </div>
    );
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

  getBusinessesList() {
    fetch(`/o/self-provisioning/businesses/${this.getUserId()}`)
      .then(res => res.json())
      .then(data => {
        let businesses = data;
        this.setState({businessesList: businesses});

        if (businesses != null && businesses.length != 0) {
          let groupId = businesses[0].groupId;
          this.setState({groupId: groupId});
          this.userListRef.updateUserList(groupId);
        }
      })
      .catch(() => this.props.displayErrorMessage("error.unable-to-retrieve-list-of-user-businesses"))
  }

  getRoleTypes() {
    fetch(`/o/self-provisioning/roleTypes/${this.getUserId()}/group/${this.state.groupId}`)
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

  getUserId() {
    return Liferay.ThemeDisplay.getUserId();
  }

  updateBusinessUsers(groupId) {
    this.setState({
      groupId: groupId,
      isAdminOrOwner: false,
      isEditingUsers: false,
      inviteMembersVisible: false,
      membersToBeRemoved: [],
      toast:  {
        displayType: "",
        message: "",
        title: "",
      },
      updatedUserRoles: []
    });

    this.userListRef.updateUserList(groupId);
  }

  render() {
    return (
        <div>
          <div className="vertical">
            <h1 className="self-provisioning-portlet-title">
              {Liferay.Language.get('users')}

              {this.editButton()}
              {this.cancelSaveButtons()}
            </h1>

            {this.businessSelect()}

            <div>
              <UserList
                addMemberToBeRemoved={(user) => this.addMemberToBeRemoved(user)}
                addUpdatedUserRole={(user) => this.addUpdatedUserRole(user)}
                displayErrorMessage={(msg) => this.displayErrorMessage(msg)}
                displaySuccessMessage={(msg) => this.displaySuccessMessage(msg)}
                isEditingUsers={this.state.isEditingUsers}
                groupId={this.state.groupId}
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