import React from 'react';
import ClayIcon from '@clayui/icon';
import ClayTable from '@clayui/table';
import ClayButton from '@clayui/button';
import {Dialog, UserAvatar} from 'com.churchmutual.commons.web';
import {RoleSelect} from './RoleSelect';

export default class extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      primaryUser: {
        fullName: '',
        email: '',
        role: '',
        status: ''
      },
      userList: [],
      removeUser: {
        removeUserModalVisible: false,
        removingUser: {}
      }
    }
  }

  updateUserList(businessGroupId) {
    let groupId = businessGroupId ? businessGroupId : this.props.groupId;

    if (groupId != 0) {
      this.getPrimaryUser(groupId);
      this.getRelatedUsersList(groupId);
    }
  }

  getPrimaryUser(groupId) {
    fetch(`/o/self-provisioning/primary/${this.getUserId()}/group/${groupId}?p_auth=${Liferay.authToken}`)
      .then(res => res.json())
      .then(data => this.updatePrimaryUser(data))
      .catch(() => this.props.displayErrorMessage('error.unable-to-retrieve-primary-account-user'));
  }

  getRelatedUsersList(groupId) {
    fetch(`/o/self-provisioning/${this.getUserId()}/group/${groupId}?p_auth=${Liferay.authToken}`)
      .then(res => res.json())
      .then(data => this.setState({ userList: data }))
      .catch(() => this.props.displayErrorMessage('error.unable-to-retrieve-list-of-account-users'));
  }

  updatePrimaryUser(user) {
    this.setState({primaryUser: user});

    this.props.setIsAdminOrOwner(this.isAdminOrOwner(user));
  }

  isAdminOrOwner(user) {
    let primaryUserRole = user.role.toLowerCase();

    return primaryUserRole === 'admin' || primaryUserRole === 'owner';
  }

  setNewRole(user, newRole, isPrimaryUser) {
    if (newRole === 'owner') {
      this.updateCurrentOwnerToAdmin();
    }

    let updatedUser = {...user};
    updatedUser.role = newRole;

    if (isPrimaryUser) {
      this.setState({primaryUser: updatedUser});
    } else {
      this.setState((previousState) => {
        let userList = [...previousState.userList];
        let index = userList.indexOf(user);

        userList[index] = updatedUser;

        return {userList: userList};
      });
    }

    this.props.addUpdatedUserRole(updatedUser);
  }

  updateCurrentOwnerToAdmin() {
    let ownerUser = this.getOwnerUser();

    this.setNewRole(ownerUser, 'Admin', this.isPrimaryUser(ownerUser));
  }

  isPrimaryUser(user) {
    return this.state.primaryUser.email === user.email;
  }

  getOwnerUser() {
    if (this.state.primaryUser.role === 'owner') {
      return this.state.primaryUser;
    } else {
      return this.state.userList.find((u) => u.role === 'owner');
    }
  }

  roleSelect(user) {
    if (!this.props.isEditingUsers || user.removed) {
      return this.getLocalization(user.role);
    }

    return (
      <RoleSelect
        value={this.getLocalization(user.role)}
        user={user}
        primaryUser={this.state.primaryUser}
        handleFieldChange={(user, fieldValue, isPrimaryUser) =>
          this.setNewRole(user, fieldValue, isPrimaryUser)}
      />
    );
  }

  removeUserButton(user) {
    if (this.props.isEditingUsers && this.isAdminOrOwner(this.state.primaryUser) && !this.isPrimaryUser(user)) {
      return (
        <ClayTable.Cell>
          {!user.removed && (
            <ClayButton monospaced="true" displayType="unstyled" small="true" className="text-danger"
                onClick={() => this.showRemoveUserConfirmationDialog(user)}>
              <ClayIcon symbol={"trash"} spritemap={this.getSpritemap()}/>
            </ClayButton>
          )}
        </ClayTable.Cell>
      );
    }
    else if (this.props.isEditingUsers) {
      return (<ClayTable.Cell />);
    }
  }

  showRemoveUserConfirmationDialog(user) {
    this.setState({
      removeUser: {
        removeUserModalVisible: true,
        removingUser: user
      }
    });
  }

  confirmRemoveUser() {
    let user = this.state.removeUser.removingUser;

    this.props.addMemberToBeRemoved(user);

    this.indicateRemovedUser(user);

    this.closeRemoveUserConfirmationDialog();
  }

  indicateRemovedUser(user) {
    this.setState((previousState) => {
      let newUserList = previousState.userList.map(u => {
        if (u.email === user.email) {
          return {
            ...u,
            removed: true,
            status: 'removed'
          }
        }
        return u;
      });
      return {
        userList: newUserList
      }
    });
  }

  closeRemoveUserConfirmationDialog() {
    this.setState({
      removeUser: {
        removeUserModalVisible: false,
        removingUser: {}
      }
    });
  }

  setRemoveUserConfirmationDialogVisible(visible) {
    if (!visible) {
      this.closeRemoveUserConfirmationDialog();
    } else {
      this.setState({removeUserModalVisible:visible})
    }
  }

  getSpritemap() {
    return Liferay.ThemeDisplay.getPathThemeImages() + '/lexicon/icons.svg';
  }

  getLocalization(key) {
    return key && key != '' ? Liferay.Language.get(key) : key;
  }

  getUserId() {
    return Liferay.ThemeDisplay.getUserId();
  }

  getCompanyId() {
    return Liferay.ThemeDisplay.getCompanyId();
  }

  render() {
    return (
      <React.Fragment>
        <ClayTable>
          <ClayTable.Head>
            <ClayTable.Row>
              <ClayTable.Cell expanded headingCell>{Liferay.Language.get('name')}</ClayTable.Cell>
              <ClayTable.Cell expanded headingCell>{Liferay.Language.get('email')}</ClayTable.Cell>
              <ClayTable.Cell expanded headingCell>{Liferay.Language.get('role')}</ClayTable.Cell>
              <ClayTable.Cell headingCell>{Liferay.Language.get('status')}</ClayTable.Cell>
              {this.props.isEditingUsers && this.isAdminOrOwner(this.state.primaryUser) ? <ClayTable.Cell/> : null}
            </ClayTable.Row>
          </ClayTable.Head>

          <ClayTable.Body>
            <ClayTable.Row className={this.state.primaryUser.changed ? "unsaved-changes" : ""}>
              <ClayTable.Cell className="h4">
                <div className="flex-container align-items-center">
                  <UserAvatar
                    image={this.state.primaryUser.portraitImageUrl}
                    firstName={this.state.primaryUser.firstName}
                    lastName={this.state.primaryUser.lastName}
                    className="mr-3"
                  />
                  <div>
                    {this.state.primaryUser.fullName}
                    <small className="font-weight-normal">{" (" + Liferay.Language.get('me').toLowerCase() + ")"}</small>
                  </div>
                </div>
              </ClayTable.Cell>
              <ClayTable.Cell>{this.state.primaryUser.email}</ClayTable.Cell>
              <ClayTable.Cell>{this.roleSelect(this.state.primaryUser)}</ClayTable.Cell>
              <ClayTable.Cell>{this.getLocalization(this.state.primaryUser.status)}</ClayTable.Cell>
              {this.removeUserButton(this.state.primaryUser)}
            </ClayTable.Row>

            {this.state.userList.map((user, index) => (
              <ClayTable.Row key={index} className={(user.changed || user.removed) ? "unsaved-changes" : ""}>
                <ClayTable.Cell className="h4">
                  <div className="flex-container align-items-center">
                    <UserAvatar
                      index={index}
                      image={user.portraitImageUrl}
                      firstName={user.firstName}
                      lastName={user.lastName}
                      className="mr-3"
                    />
                    <div>{user.fullName}</div>
                  </div>
                </ClayTable.Cell>
                <ClayTable.Cell>{user.email}</ClayTable.Cell>
                <ClayTable.Cell>{this.roleSelect(user)}</ClayTable.Cell>
                <ClayTable.Cell>{Liferay.Language.get(user.status)}</ClayTable.Cell>
                {this.removeUserButton(user)}
              </ClayTable.Row>
            ))}

          </ClayTable.Body>
        </ClayTable>

        <Dialog
          title={Liferay.Language.get('remove-user')}
          buttonConfirmText={Liferay.Language.get('continue')}
          onClickConfirm={() => this.confirmRemoveUser()}
          visible={this.state.removeUser.removeUserModalVisible}
          setVisible={(show) => this.setRemoveUserConfirmationDialogVisible(show)}
          status="warning"
        >
          <div className="lead-text" dangerouslySetInnerHTML={{
              __html: Liferay.Util.sub(
                  Liferay.Language.get('are-you-sure-you-want-to-remove-x-from-this-account'),
                  this.state.removeUser.removingUser.fullName
              )
          }}/>
        </Dialog>
      </React.Fragment>
    );
  }
}