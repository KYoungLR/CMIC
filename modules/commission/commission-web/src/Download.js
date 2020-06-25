import React, {useState} from 'react';
import ClayButton from '@clayui/button';
import ClayForm, {ClaySelect} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import ClayLayout from '@clayui/layout';
import ClayLoadingIndicator from '@clayui/loading-indicator';

const Download = (props) => {
  if (props.isLoading) {
    return (<ClayLoadingIndicator />);
  }
  else {
    const [activePath, setActivePath] = useState(false);

    const [errorMessage, showErrorMessage] = useState(false);

    const downloadStatement = (e) => {
      console.log(activePath);
      showErrorMessage(false);

      let id = activePath;

      let callback = (data) => {
        console.log(data)
      }

      let errCallback = () => {
        showErrorMessage(true)
      }

      Liferay.Service(
        '/cmic.cmiccommissiondocument/download-document',
        {
          id: id
        },
        callback,
        errCallback
      );
    };

    return (
      <div>
        <ClayLayout.Row>
          <ClayLayout.Col md={7} lg={6}>
            <ClayForm.Group>
              <ClaySelect
                aria-label="Select Statement"
                id="statementSelect"
                onChange={(e) => setActivePath(e.target.value)}
                defaultValue={0}
              >
                <ClaySelect.Option
                  disabled
                  value={0}
                  label={Liferay.Language.get('date-range')}
                />
                {props.statementList.map((statement, index) => (
                  <ClaySelect.Option
                    key={index}
                    label={statement.label}
                    value={statement.id}
                  />
                ))}
              </ClaySelect>
            </ClayForm.Group>
          </ClayLayout.Col>
          <ClayLayout.Col md={5} lg={6}>
            <ClayButton displayType="primary" className="btn-lg" onClick={(e) => downloadStatement(e)}>
              {Liferay.Language.get('download-statement')}
              <ClayIcon
                spritemap={Liferay.ThemeDisplay.getPathThemeImages() + '/cmic/icons.svg'}
                symbol="download"
              />
            </ClayButton>
          </ClayLayout.Col>
        </ClayLayout.Row>
        <ClayLayout.Row>
          {errorMessage &&
            <ClayForm.FeedbackGroup className="has-error">
              <ClayForm.FeedbackItem>
                {Liferay.Language.get('error.there-was-an-issue-downloading-the-pdf-please-try-again-later')}
              </ClayForm.FeedbackItem>
            </ClayForm.FeedbackGroup>
          }
        </ClayLayout.Row>
      </div>
    );
  }
};

export default Download;