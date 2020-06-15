import React from 'react';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {UserCard} from 'com.churchmutual.commons.web';

const ContactList = (props) => {
  if (props.isLoading) {
    return (<ClayLoadingIndicator />);
  }
  else {
    return (
      <React.Fragment>
        <ClayLayout.Row>
          {props.contactsList.map((contact, index) => (
            <ClayLayout.Col md={6} lg={4} key={index}>
              <UserCard
                image={contact.image}
                name={contact.name}
                title={contact.title}
                email={contact.email}
                phone={contact.phone}
              />
            </ClayLayout.Col>
          ))}
        </ClayLayout.Row>
      </React.Fragment>
    );
  }
};

export default ContactList;