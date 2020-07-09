import React from 'react';
import ClayCard from '@clayui/card';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {Toast} from 'com.churchmutual.commons.web';
import AccountList from './AccountList';

/* TODO - when service is ready
Remove generateData reference
Delete GenerateData.js file
Remove "chance" from dependencies in package.json
*/
import generateData from './GenerateData';

class Accounts extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      accountsList: [],
      isLoading: true,
      filterBy: '',
      groupId: 0,
      searchTerm: '',
      toast:  {
        displayType: '',
        message: '',
        title: '',
      }
    };
  }

  componentDidMount() {
    this.getAccountsList();
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

  onToastClosed() {
    this.setState({
      toast: {
        displayType: '',
        message: '',
        title: ''
      }
    });
  }

  getAccountsList() {
    /*fetch(`/o/account/accounts/${this.userId}`)
      .then(res => res.json())
      .then(data => {
        let accounts = data;
        this.setState({accountsList: accounts, isLoading: false});
      })
      .catch(() => this.displayErrorMessage('error.unable-to-retrieve-list-of-accounts'))*/

    this.setState({
      accountsList: generateData(1000),
      isLoading: false
    });
  }

  render() {
    return (
      <div className="account-list-portlet">
        <ClayCard>
          {this.state.isLoading ? (
            <ClayLoadingIndicator />
          ) : (
            <AccountList accountsList={this.state.accountsList} />
          )}
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


export default Accounts;
