import React from 'react';
import ClayLoadingIndicator from '@clayui/loading-indicator';

const ProducerOrgList = (props) => {
  if (props.isLoading) {
    return (<ClayLoadingIndicator />);
  }
  else {
    return (
      <React.Fragment>
        {props.producerOrgList.map((producer, index) => (
          <div className="well well-lg" key={index}>
            <div>
              <h2 className="well-title">{producer.producerName}</h2>
              <div className="small font-weight-bold text-muted text-uppercase">{Liferay.Language.get('producer-code')} {producer.producerCode}</div>
            </div>
            <div>
              {producer.producerAddress &&
                <React.Fragment>
                  <div>{producer.producerAddress.streetNumber} {producer.producerAddress.streetName}</div>
                  <div>{producer.producerAddress.city}, {producer.producerAddress.state} {producer.producerAddress.zipCode}</div>
                </React.Fragment>
              }
              {producer.producerNumber &&
                <div><a href={`tel:${producer.producerNumber}`}>{producer.producerNumber}</a></div>
              }
            </div>
          </div>
        ))}

        {props.producerOrgList.length > 3 &&
          <a href="javascript:;">{Liferay.Language.get('show-all')}</a>
        }
      </React.Fragment>
    );
  }
};

export default ProducerOrgList;