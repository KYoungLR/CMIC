import React from 'react';
import ClayLoadingIndicator from '@clayui/loading-indicator';

const ProducerOrgList = (props) => {
  if (props.isLoading) {
    return (<ClayLoadingIndicator />);
  }
  else {
    return (
      <React.Fragment>
        {props.producerOrgList.slice(0, 3).map((producer, index) => (
          <div className="well well-lg" key={index}>
            <div>
              <h2 className="well-title">{producer.name}</h2>
              <div className="small font-weight-bold text-muted text-uppercase">{Liferay.Language.get('producer-code')}: {producer.producerId}</div>
            </div>
            <div>
              <React.Fragment>
                <div>{producer.addressLine1}</div>
                <div>{producer.addressLine2}</div>
                <div>{producer.city}, {producer.state} {producer.postalCode}</div>
              </React.Fragment>
              {producer.phoneNumber &&
                <div><a href={`tel:${producer.phoneNumber}`}>{producer.phoneNumber}</a></div>
              }
            </div>
          </div>
        ))}

        {props.producerOrgList.length > 3 &&
          <div className="bg-fade">
            <a className="link-action" href="javascript:;">{Liferay.Language.get('show-all')}</a>
          </div>
        }
      </React.Fragment>
    );
  }
};

export default ProducerOrgList;