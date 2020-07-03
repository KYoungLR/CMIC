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
      accountsList: [
        { accountName: 'First Baptist Green Bay', accountNumber: '1AJNS1981', producerEntity: 'XYZ Insurance', producerEntityCode: '35-001', inForcePolicies: 24, futurePolicies: 22, expiredPolicies: 17, policyPremium: 54428.21 },
        { accountName: 'Merrill School District', accountNumber: '1AJNS1981', producerEntity: 'Brown & Brown', producerEntityCode: '35-002', inForcePolicies: 24, futurePolicies: 20, expiredPolicies: 9, policyPremium: 27594.21 },
        { accountName: 'Lucy’s Daycare', accountNumber: '1AJNS1981', producerEntity: 'XYZ Insurance', producerEntityCode: '35-003', inForcePolicies: 14, futurePolicies: 17, expiredPolicies: 3, policyPremium: 16750.21 },
        { accountName: 'St. John’s Cathedral', accountNumber: '1AJNS1981', producerEntity: 'Brown & Brown', producerEntityCode: '35-004', inForcePolicies: 17, futurePolicies: 1, expiredPolicies: 7, policyPremium: 83300.21 },
        { accountName: 'Trinity United Methodist', accountNumber: '1AJNS1981', producerEntity: 'XYZ Insurance', producerEntityCode: '35-005', inForcePolicies: 32, futurePolicies: 8, expiredPolicies: 20, policyPremium: 2460.21 },
        { accountName: 'First Baptist Green Bay', accountNumber: '1AJNS1981', producerEntity: 'Brown & Brown', producerEntityCode: '35-006', inForcePolicies: 24, futurePolicies: 22, expiredPolicies: 17, policyPremium: 54428.21 },
        { accountName: 'Merrill School District', accountNumber: '1AJNS1981', producerEntity: 'XYZ Insurance', producerEntityCode: '35-001', inForcePolicies: 24, futurePolicies: 20, expiredPolicies: 9, policyPremium: 27594.21 },
        { accountName: 'Lucy’s Daycare', accountNumber: '1AJNS1981', producerEntity: 'Brown & Brown', producerEntityCode: '35-007', inForcePolicies: 14, futurePolicies: 17, expiredPolicies: 3, policyPremium: 16750.21 },
        { accountName: 'St. John’s Cathedral', accountNumber: '1AJNS1981', producerEntity: 'XYZ Insurance', producerEntityCode: '35-008', inForcePolicies: 17, futurePolicies: 1, expiredPolicies: 7, policyPremium: 83300.21 },
        { accountName: 'Trinity United Methodist', accountNumber: '1AJNS1981', producerEntity: 'Brown & Brown', producerEntityCode: '35-009', inForcePolicies: 32, futurePolicies: 8, expiredPolicies: 20, policyPremium: 2460.21 },
        { accountName: 'First Baptist Green Bay', accountNumber: '1AJNS1981', producerEntity: 'XYZ Insurance', producerEntityCode: '35-001', inForcePolicies: 24, futurePolicies: 22, expiredPolicies: 17, policyPremium: 54428.21 },
        { accountName: 'Merrill School District', accountNumber: '1AJNS1981', producerEntity: 'Brown & Brown', producerEntityCode: '35-002', inForcePolicies: 24, futurePolicies: 20, expiredPolicies: 9, policyPremium: 27594.21 },
        { accountName: 'Lucy’s Daycare', accountNumber: '1AJNS1981', producerEntity: 'XYZ Insurance', producerEntityCode: '35-003', inForcePolicies: 14, futurePolicies: 17, expiredPolicies: 3, policyPremium: 16750.21 },
        { accountName: 'St. John’s Cathedral', accountNumber: '1AJNS1981', producerEntity: 'Brown & Brown', producerEntityCode: '35-004', inForcePolicies: 17, futurePolicies: 1, expiredPolicies: 7, policyPremium: 83300.21 },
        { accountName: 'Trinity United Methodist', accountNumber: '1AJNS1981', producerEntity: 'XYZ Insurance', producerEntityCode: '35-005', inForcePolicies: 32, futurePolicies: 8, expiredPolicies: 20, policyPremium: 2460.21 }
      ],
      isLoading: false
    });
  }

  render() {
    return (
      <div className="account-list-portlet">
        <ClayCard>
          <AccountList
            accountsList={this.state.accountsList}
            isLoading={this.state.isLoading}
          />
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
