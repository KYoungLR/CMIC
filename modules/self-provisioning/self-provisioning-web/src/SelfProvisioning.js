import React from 'react';
import ClayCard from '@clayui/card';
import ClayButton from '@clayui/button';
import {ClaySelect} from '@clayui/form';
import {Toast, ChangesFeedback, ChangesTrackerContext} from 'com.churchmutual.commons.web';
import UserList from './UserList';
import InviteMembers from './InviteMembers';

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
        displayType: '',
        message: '',
        title: '',
      },
      updatedUserRoles: []
    };

    this.companyId = Liferay.ThemeDisplay.getCompanyId();
    this.userId = Liferay.ThemeDisplay.getUserId();
  }

  componentDidMount() {
    this.getBusinessesList();
    this.getRoleTypes();
  }

  updateAccountMembers() {
      let data = JSON.stringify({
          'updateUsersRoles': this.state.updatedUserRoles,
          'removeUsers': this.state.membersToBeRemoved
      });

      let headers = new Headers();
      headers.set('Content-Type', 'application/json');

      fetch(`/o/self-provisioning/update-account-members/${this.companyId}/${this.userId}/${this.state.groupId}`, {
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

          this.displaySuccessMessage('your-request-completed-successfully')
      }).catch(
          () => this.displayErrorMessage('your-request-failed-to-complete')
      );
  }

  inviteMembers() {
    return this.state.inviteMembersVisible
      && !this.state.isEditingUsers
      && (
        <InviteMembers
            displayErrorMessage={(msg) => this.displayErrorMessage(msg)}
            displaySuccessMessage={(msg) => this.displaySuccessMessage(msg)}
            groupId={this.state.groupId}
            currentBusinessName={this.getCurrentBusinessName()}
            updateUserList={() => this.userListRef.updateUserList()}
            visible={this.state.inviteMembersVisible}
            onClickCancel={() => this.setState({inviteMembersVisible: false})}
        />
    );
  }

  cancelSaveButtons() {
    return this.state.isEditingUsers && (
        <ClayButton.Group spaced>
            <ChangesFeedback />
            <ClayButton displayType="outline-secondary"
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
    return !this.state.isEditingUsers
    && this.state.isAdminOrOwner
    && (
      <ClayButton displayType="link" small className="link-action"
          onClick={() => this.setState({
            isEditingUsers: true,
            inviteMembersVisible: false
          })}>
        {Liferay.Language.get('edit-users')}
      </ClayButton>
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
    return !this.state.isEditingUsers
      && this.state.isAdminOrOwner
      && !this.state.inviteMembersVisible
      && (
        <ClayButton displayType="link" small className="link-action"
          onClick={() => this.setState({
            inviteMembersVisible: true
          })}>
          {Liferay.Language.get('Add users +')}
        </ClayButton>
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
    return (
      <div className="autofit-row align-items-center mb-2 mb-lg-0">
        <div className="autofit-col px-2">{Liferay.Language.get('viewing')}</div>
        <div className="autofit-col">
          {(() => {
            if (this.state.isEditingUsers) {
              return this.getCurrentBusinessName();
            }
            else {
              return (
                <ClaySelect className="form-control-sm"
                  onChange={(e) => this.updateBusinessUsers(e.target.value)} value={this.state.groupId}>
                    {this.state.businessesList.map(business => (
                      <ClaySelect.Option
                        key={business.groupId}
                        label={business.name}
                        value={business.groupId}
                      />
                    ))}
                </ClaySelect>
              );
            }
          })()}
        </div>
      </div>
    );
  }

  displayErrorMessage(msg) {
    this.setState({
      toast: {
        displayType: 'danger',
        message: msg,
        title: Liferay.Language.get('error-colon')
      }
    });
  }

  displaySuccessMessage(msg) {
    this.setState({
      toast: {
        displayType: 'success',
        message: msg,
        title: Liferay.Language.get('success-colon')
      }
    });
  }

  onToastClosed() {
    this.setState({
      toast: {
        displayType: '',
        message: '',
        title: ''
      }
    });
  }

  getBusinessesList() {
    fetch(`/o/self-provisioning/businesses/${this.userId}`)
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
      .catch(() => this.displayErrorMessage('error.unable-to-retrieve-list-of-user-businesses'))
  }

  getRoleTypes() {
    fetch(`/o/self-provisioning/roleTypes/${this.userId}/group/${this.state.groupId}`)
      .then(res => res.json())
      .then(data => this.setState({ roleTypes: data }))
      .catch(() => this.displayErrorMessage('error.unable-to-retrieve-list-of-account-roles'));
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

  getCurrentBusinessName() {
    let business = this.state.businessesList.find((b) => b.groupId == this.state.groupId);
    return business.name;
  }

  updateBusinessUsers(groupId) {
    this.setState({
      groupId: groupId,
      isAdminOrOwner: false,
      isEditingUsers: false,
      inviteMembersVisible: false,
      membersToBeRemoved: [],
      toast:  {
        displayType: '',
        message: '',
        title: '',
      },
      updatedUserRoles: []
    });

    this.userListRef.updateUserList(groupId);
  }

  render() {
    return (
      <div className="self-provisioning-portlet">
        <ClayCard>
          <div className="card-header">
            <ClayCard.Description displayType="title">
              {Liferay.Language.get('users')}
            </ClayCard.Description>
          </div>
          <ClayCard.Body>
            <div key={0} className="row no-gutters align-items-center mb-3">
              <div className="col py-2">
                {this.businessSelect()}
              </div>
              <div className="col-md-auto">
                {this.editButton()}
                {this.cancelSaveButtons()}
              </div>
            </div>

            <UserList
              key={1}
              addMemberToBeRemoved={(user) => this.addMemberToBeRemoved(user)}
              addUpdatedUserRole={(user) => this.addUpdatedUserRole(user)}
              displayErrorMessage={(msg) => this.displayErrorMessage(msg)}
              displaySuccessMessage={(msg) => this.displaySuccessMessage(msg)}
              isEditingUsers={this.state.isEditingUsers}
              groupId={this.state.groupId}
              ref={(userListRef) => this.userListRef = userListRef}
              setIsAdminOrOwner={(isAdminOrOwner) => this.setState({isAdminOrOwner: isAdminOrOwner})}
            />
          </ClayCard.Body>
          <div className="card-footer">
            {this.addUserButton()}
            {this.inviteMembers()}
          </div>
        </ClayCard>

        <Toast
          displayType={this.state.toast.displayType}
          message={this.state.toast.message}
          onClose={() => this.onToastClosed()}
          title={this.state.toast.title} />
      </div>
    );
  }
};

SelfProvisioning.contextType = ChangesTrackerContext;

export default SelfProvisioning;
