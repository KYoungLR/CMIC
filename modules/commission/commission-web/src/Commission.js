import React from 'react';
import ClayCard from '@clayui/card';
import {Toast} from 'com.churchmutual.commons.web';
import Download from './Download';

class Commission extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      statementList: [],
      isLoading: true,
      toast:  {
        displayType: '',
        message: '',
        title: '',
      }
    };
  }

  componentDidMount() {
    this.getStatementList();
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

  getStatementList() {
    /*fetch(`/o/commission/${this.userId}`)
      .then(res => res.json())
      .then(data => {
        let accounts = data;
        this.setState({accountsList: accounts, isLoading: false});
      })
      .catch(() => this.displayErrorMessage('error.unable-to-retrieve-list-of-accounts'))*/

    this.setState({
      statementList: [
        { label: '04/01/2020 - Commission Remittence Statement', url: '/' },
        { label: '03/01/2020 - Commission Remittence Statement', url: '/' },
        { label: '02/01/2020 - Commission Remittence Statement', url: '/' },
        { label: '01/01/2020 - Commission Remittence Statement', url: '/' },
        { label: '31/12/2019 - Commission Remittence Statement', url: '/' },
        { label: '30/12/2019 - Commission Remittence Statement', url: '/' },
        { label: '29/12/2019 - Commission Remittence Statement', url: '/' },
        { label: '28/12/2019 - Commission Remittence Statement', url: '/' }
      ],
      isLoading: false
    });
  }

  render() {
    return (
      <div className="commission-portlet">
        <ClayCard>
          <div className="card-header">
            <ClayCard.Description displayType="title">{Liferay.Language.get('commissions')}</ClayCard.Description>
          </div>
          <ClayCard.Body>
            {(this.state.statementList.length > 0) &&
              <Download
                isLoading={this.state.isLoading}
                statementList={this.state.statementList}
              />
            }
            {(this.state.statementList.length == 0) &&
              <div>{Liferay.Language.get('no-recent-commission-statements-are-available')}</div>
            }
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


export default Commission;
