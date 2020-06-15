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
            <ClayLayout.Col md={6} lg={12} key={index}>
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
        <a href="contact" className="link-action">{Liferay.Language.get('see-all-contacts')}</a>
      </React.Fragment>
    );
  }
};

export default ContactList;