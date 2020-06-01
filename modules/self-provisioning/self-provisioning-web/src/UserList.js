import React from 'react';
import ClayButton from "@clayui/button";
import ClayIcon from '@clayui/icon';
import ClayTable from "@clayui/table";
import {RoleSelect} from "./RoleSelect";
import {Dialog} from 'commons-web';

export default class extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      primaryUser: {
        fullName: "",
        email: "",
        role: "",
        status: ""
      },
      userList: [],
      removeUser: {
        removeUserModalVisible: false,
        removingUser: {}
      }
    }
  }

  componentDidMount() {
    this.updateUserList();
  }

  updateUserList() {
    this.getPrimaryUser();
    this.getRelatedUsersList();
  }

  getPrimaryUser() {
    fetch(`/o/self-provisioning/primary/${this.getUserId()}/group/${this.getGroupId()}`)
      .then(res => res.json())
      .then(data => this.updatePrimaryUser(data))
      .catch(() => this.props.displayErrorMessage("error.unable-to-retrieve-primary-account-user"));
  }

  getRelatedUsersList() {
    fetch(`/o/self-provisioning/${this.getUserId()}/group/${this.getGroupId()}`)
      .then(res => res.json())
      .then(data => this.setState({ userList: data }))
      .catch(() => this.props.displayErrorMessage("error.unable-to-retrieve-list-of-account-users"));
  }

  updatePrimaryUser(user) {
    this.setState({primaryUser: user});

    let primaryUserRole = user.role.toLowerCase();

    this.props.setIsAdminOrOwner(primaryUserRole === "admin" || primaryUserRole === "owner")
  }

  setNewRole(user, newRole, isPrimaryUser) {
    if (newRole === "owner") {
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

    this.setNewRole(ownerUser, "Admin", this.isPrimaryUser(ownerUser));
  }

  isPrimaryUser(user) {
    return this.state.primaryUser.email === user.email;
  }

  getOwnerUser() {
    if (this.state.primaryUser.role === "owner") {
      return this.state.primaryUser;
    } else {
      return this.state.userList.find((u) => u.role === "owner");
    }
  }

  roleSelect(user) {
    if (!this.props.isEditingUsers || user.removed) {
      return (
        <div>
          {Liferay.Language.get(user.role)}
        </div>
      );
    }

    return (
      <div>
        <RoleSelect
          value={Liferay.Language.get(user.role)}
          user={user}
          primaryUser={this.state.primaryUser}
          handleFieldChange={(user, fieldValue, isPrimaryUser) =>
            this.setNewRole(user, fieldValue, isPrimaryUser)}
        />
      </div>
    );
  }

  removeUserButton(user) {
    return this.props.isEditingUsers
      && this.state.primaryUser.role == "owner"
      && !this.isPrimaryUser(user)
      && (
        <ClayTable.Cell>
          <div>
            {!user.removed && (
              <ClayButton monospaced="true" displayType="unstyled" small="true"
                onClick={() => this.showRemoveUserConfirmationDialog(user)}>
                  <ClayIcon symbol={"trash"} spritemap={this.getSpritemap()}/>
              </ClayButton>
            )}
          </div>
        </ClayTable.Cell>
      );
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
    return Liferay.ThemeDisplay.getPathThemeImages() + "/lexicon/icons.svg";
  }

  getGroupId() {
    return Liferay.ThemeDisplay.getScopeGroupId();
  }

  getUserId() {
    return Liferay.ThemeDisplay.getUserId();
  }

  getCompanyId() {
    return Liferay.ThemeDisplay.getCompanyId();
  }

  render() {
    return (
      <div>
        <ClayTable>
          <ClayTable.Head>
            <ClayTable.Row>
              <ClayTable.Cell headingCell>
                {Liferay.Language.get('name')}
              </ClayTable.Cell>
              <ClayTable.Cell headingCell>
                {Liferay.Language.get('email')}
              </ClayTable.Cell>
              <ClayTable.Cell headingCell>
                {Liferay.Language.get('role')}
                <ClayIcon
                  spritemap={this.getSpritemap()}
                  symbol="caret-bottom"
                />
              </ClayTable.Cell>
              <ClayTable.Cell headingCell>
                {Liferay.Language.get('status')}
              </ClayTable.Cell>
            </ClayTable.Row>
          </ClayTable.Head>

          <ClayTable.Body>
            <ClayTable.Row className={this.state.primaryUser.changed ? "changed" : ""}>
              <ClayTable.Cell>
                <span className="font-weight-bold">
                  {this.state.primaryUser.fullName}
                </span>
                <span className="primary-user-me">
                  {" (" + Liferay.Language.get('me').toLowerCase() + ")"}
                </span>
              </ClayTable.Cell>
              <ClayTable.Cell>
                {this.state.primaryUser.email}
              </ClayTable.Cell>
              <ClayTable.Cell>
                {this.roleSelect(this.state.primaryUser)}
              </ClayTable.Cell>
              <ClayTable.Cell>
                {Liferay.Language.get(this.state.primaryUser.status)}
              </ClayTable.Cell>
              {this.removeUserButton(this.state.primaryUser)}
            </ClayTable.Row>

            {this.state.userList.map((user, index) => (
              <ClayTable.Row key={index} className={[user.changed ? "changed" : "", user.removed ? "removed" : ""].join(' ')}>
                <ClayTable.Cell>
                  {user.fullName}
                </ClayTable.Cell>
                <ClayTable.Cell>
                  {user.email}
                </ClayTable.Cell>
                <ClayTable.Cell>
                  {this.roleSelect(user)}
                </ClayTable.Cell>
                <ClayTable.Cell>
                  {Liferay.Language.get(user.status)}
                </ClayTable.Cell>
                {this.removeUserButton(user)}
              </ClayTable.Row>
            ))}

          </ClayTable.Body>
        </ClayTable>

        <Dialog
          title={Liferay.Language.get("remove-user")}
          buttonConfirmText={Liferay.Language.get("continue")}
          onClickConfirm={() => this.confirmRemoveUser()}
          visible={this.state.removeUser.removeUserModalVisible}
          setVisible={(show) => this.setRemoveUserConfirmationDialogVisible(show)}
          status="warning"
        >
          <div dangerouslySetInnerHTML={{
              __html: Liferay.Util.sub(
                  Liferay.Language.get("are-you-sure-you-want-to-remove-x-from-this-account"),
                  this.state.removeUser.removingUser.fullName
              )
          }}/>
        </Dialog>
      </div>
    );
  }
}