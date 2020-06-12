import React from 'react';
import ClayCard from '@clayui/card';
import {Toast} from 'com.churchmutual.commons.web';
import AccountList from './AccountList';

class Accounts extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      accountsList: [],
      isLoading: true,
      groupId: 0,
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
      accountsList: [
        { accountName: 'First Baptist Green Bay', accountNumber: '1AJNS1981', inForcePolicies: 24, amountBilled: 54428.21 },
        { accountName: 'Merrill School District', accountNumber: '1AJNS1981', inForcePolicies: 14, amountBilled: 27594.21 },
        { accountName: 'Lucy’s Daycare', accountNumber: '1AJNS1981', inForcePolicies: 17, amountBilled: 16750.21 },
        { accountName: 'St. John’s Cathedral', accountNumber: '1AJNS1981', inForcePolicies: 32, amountBilled: 83300.21 },
        { accountName: 'Trinity United Methodist', accountNumber: '1AJNS1981', inForcePolicies: 23, amountBilled: 2460.21 }
      ],
      isLoading: false
    });
  }

  render() {
    return (
      <div className="account-producer-dashboard-portlet">
        <ClayCard>
          <div className="card-header">
            <ClayCard.Description displayType="title">{Liferay.Language.get('accounts')}</ClayCard.Description>
          </div>
          <ClayCard.Body>
            <AccountList
              accountsList={this.state.accountsList}
              isLoading={this.state.isLoading}
            />
          </ClayCard.Body>
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
