import React from 'react';
import ClayCard from '@clayui/card';
import {Toast} from 'com.churchmutual.commons.web';
import ContactList from './ContactList';

class Contacts extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      contactsList: [],
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
    this.getContactsList();
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

  getContactsList() {
    /*fetch(`/o/contact/contacts/${this.userId}`)
      .then(res => res.json())
      .then(data => {
        let accounts = data;
        this.setState({contactsList: accounts, isLoading: false});
      })
      .catch(() => this.displayErrorMessage('error.unable-to-retrieve-list-of-contacts'))*/

    this.setState({
      contactsList: [
        { name: 'Karlie Schowalter', title: 'Div Agents: 35-000, 35-001, 35-858', email: 'karlie.schowalter@churchmutal.com', phone: '715-555-5555', image: 'https://clayui.com/images/long_user_image.png' },
        { name: 'Doris O’Connell', title: 'Div Agents: 49-064', email: 'doris.oconnell@churchmutal.com', image: 'https://clayui.com/images/thumbnail_coffee.jpg' },
        { name: 'Elouise Hintz', title: 'Div Agents: 49-334', phone: '715-555-5555', image: 'https://clayui.com/images/thumbnail_dock.jpg' },
      ],
      isLoading: false
    });
  }

  render() {
    return (
      <div className="contact-producer-dashboard-portlet">
        <div className="container-view">
          <h1>{Liferay.Language.get('contacts')}</h1>
        </div>
        
        <ClayCard>
          <div className="card-header">
            <ClayCard.Description displayType="title">{Liferay.Language.get('territory-managers')}</ClayCard.Description>
          </div>
          <ClayCard.Body>
            <ContactList
              contactsList={this.state.contactsList}
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


export default Contacts;
