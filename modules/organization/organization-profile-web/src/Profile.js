import React from 'react';
import ClayCard from '@clayui/card';
import {Toast} from 'com.churchmutual.commons.web';
import ProducerOrgList from './ProducerOrgList';

class Profile extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      producerOrgList: [],
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
    this.getProducerOrgList();
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

  getProducerOrgList() {
    /*fetch(`/o/account/accounts/${this.userId}`)
      .then(res => res.json())
      .then(data => {
        let accounts = data;
        this.setState({producerOrgList: accounts, isLoading: false});
      })
      .catch(() => this.displayErrorMessage('error.unable-to-retrieve-list-of-accounts'))*/

    this.setState({
      producerOrgList: [
        {
        	producerName: 'Brown & Brown of SC',
        	producerCode: '35-000',
        	producerAddress: {
        		streetNumber: '123',
        		streetName: 'Main Street',
        		city: 'Green Bay',
        		state: 'WI',
        		zipCode: '54229'
        	},
        	producerNumber: '123-456-7890'
        },
        {
        	producerName: 'XYZ Insurance',
        	producerCode: '35-001',
        	producerAddress: {
        		streetNumber: '4434',
        		streetName: 'Cheese Dr',
        		city: 'Green Bay',
        		state: 'WI',
        		zipCode: '54220'
        	},
        	producerNumber: '987-456-3241'
        },
        {
        	producerName: 'Wisconsin Mutual',
        	producerCode: '35-002',
        	producerAddress: {
        		streetNumber: '4434',
        		streetName: 'Cheese Dr',
        		city: 'Green Bay',
        		state: 'WI',
        		zipCode: '54220'
        	},
        	producerNumber: '987-456-3241'
        },
        {
        	producerName: 'Brown & Brown of SC',
        	producerCode: '35-000',
        	producerAddress: {
        		streetNumber: '123',
        		streetName: 'Main Street',
        		city: 'Green Bay',
        		state: 'WI',
        		zipCode: '54229'
        	},
        	producerNumber: '123-456-7890'
        },
        {
        	producerName: 'XYZ Insurance',
        	producerCode: '35-001',
        	producerAddress: {
        		streetNumber: '4434',
        		streetName: 'Cheese Dr',
        		city: 'Green Bay',
        		state: 'WI',
        		zipCode: '54220'
        	},
        	producerNumber: '987-456-3241'
        },
        {
        	producerName: 'Wisconsin Mutual',
        	producerCode: '35-002',
        	producerAddress: {
        		streetNumber: '4434',
        		streetName: 'Cheese Dr',
        		city: 'Green Bay',
        		state: 'WI',
        		zipCode: '54220'
        	},
        	producerNumber: '987-456-3241'
        }
      ],
      isLoading: false
    });
  }

  render() {
    return (
      <div className="organization-profile-portlet">
        <ClayCard>
          <div className="card-header">
            <ClayCard.Description displayType="title">{Liferay.Language.get('producer-organizations')}</ClayCard.Description>
          </div>
          <ClayCard.Body>
            <ProducerOrgList
              producerOrgList={this.state.producerOrgList}
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


export default Profile;
