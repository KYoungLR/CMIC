import React from 'react';
import ReactDOM from 'react-dom';
import ClayCard from '@clayui/card';
import {Toast, CardPageHeader} from 'com.churchmutual.commons.web';
import AccountInfo from './AccountInfo';
import PolicyList from './PolicyList';
import PolicySearch from './PolicySearch';

class Policies extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      account: {},
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
    this.getAccount();
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

  getAccount() {
    /*fetch(`/o/account/accounts/${this.userId}`)
      .then(res => res.json())
      .then(data => {
        let accounts = data;
        this.setState({accountsList: accounts, isLoading: false});
      })
      .catch(() => this.displayErrorMessage('error.unable-to-retrieve-list-of-accounts'))*/

    this.setState({
      account: {
        accountName: 'First Baptist Green Bay',
        accountNumber: '1AJNS1981',
        producerEntity: 'XYZ Insurance',
        producerEntityCode: '35-001',
        policyList: [
          { policyName: 'Commercial Auto', policyNumber: '1AJNS1981', startDate: '1/1/20', endDate: '1/1/21', transactions: 7, amountBilled: 54428.21 },
          { policyName: 'General Liability', policyNumber: '9811ANS1J', startDate: '1/1/20', endDate: '1/1/21', transactions: 1, amountBilled: 27594.21 },
          { policyName: 'International Travel', policyNumber: '8BCU91982', startDate: '1/1/20', endDate: '1/1/21', transactions: 14, amountBilled: 16750.21 },
          { policyName: 'Multi-Peril', policyNumber: '44SD1NZC34', startDate: '1/1/19', endDate: '1/1/20', transactions: 35, amountBilled: 2460.21 }
        ]
      },
      isLoading: false
    });
  }

  render() {
    return (
      <div className="policy-list-portlet">
        <CardPageHeader>
            <AccountInfo
              account={this.state.account}
              isLoading={this.state.isLoading}
            />
        </CardPageHeader>
        <ClayCard>
          <div className="card-header flex-container flex-container-stacked-xs justify-content-between align-items-md-center">
            <ClayCard.Description displayType="title">{Liferay.Language.get('policies')}</ClayCard.Description>
            <div className="mt-4 mt-md-0">
              <PolicySearch
                isLoading={this.state.isLoading}
              />
            </div>
          </div>
          <ClayCard.Body>
            <PolicyList
              policyList={this.state.account.policyList}
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


export default Policies;
