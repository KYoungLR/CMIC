import React from 'react';
import ClayCard from '@clayui/card';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {Toast, CardPageHeader} from 'com.churchmutual.commons.web';
import AccountInfo from './AccountInfo';
import TransactionList from './TransactionList';

class Transactions extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      transaction: {},
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
    /*fetch(`/o/transaction/transactions/${this.userId}`)
      .then(res => res.json())
      .then(data => {
        let transactions = data;
        this.setState({account: transactions, isLoading: false});
      })
      .catch(() => this.displayErrorMessage('error.unable-to-retrieve-list-of-transactions'))*/

    this.setState({
      transaction: {
        accountName: 'First Baptist Green Bay',
        accountNumber: '1AJNS1981',
        producerEntity: 'XYZ Insurance',
        producerEntityCode: '35-001',
        policyName: 'Commercial Auto',
        transactionList: [
         { transactionDate: '5/15/2020', transactionType: 'Issue' },
         { transactionDate: '4/23/2020', transactionType: 'Endorsement' },
         { transactionDate: '4/15/2020', transactionType: 'Cancellation' },
         { transactionDate: '4/1/2020', transactionType: 'Reinstate' },
         { transactionDate: '2/13/2020', transactionType: 'Audit' },
         { transactionDate: '2/13/2020', transactionType: 'Issue' },
         { transactionDate: '1/30/2020', transactionType: 'Endorsement' },
         { transactionDate: '1/11/2020', transactionType: 'Cancellation' },
         { transactionDate: '1/4/2020', transactionType: 'Reinstate' }
        ]
      },
      isLoading: false
    });
  }

  render() {
    if (this.state.isLoading) {
      return (<ClayLoadingIndicator />);
    }
    else {
      return (
        <div className="transaction-list-portlet">
          <CardPageHeader>
              <AccountInfo transaction={this.state.transaction} />
          </CardPageHeader>
          <ClayCard>
            <div className="card-header">
              <ClayCard.Description displayType="title">{this.state.transaction.policyName} - {Liferay.Language.get('transactions')}</ClayCard.Description>
            </div>
            <ClayCard.Body>
              <TransactionList transactionList={this.state.transaction.transactionList} />
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
  }
};


export default Transactions;
