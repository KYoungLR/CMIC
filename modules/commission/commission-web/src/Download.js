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
    const [activeId, setActiveId] = useState(false);

    const [errorMessage, showErrorMessage] = useState(false);

    const downloadStatement = (e) => {
      console.log(activeId);
      showErrorMessage(false);

      let callback = (data) => {
        console.log(data);
        const bytes = new Uint8Array(data.bytes);
        const blob = new Blob([bytes], { type: data.mimeType });
        const name = data.name ? data.name : data.url.substr(data.url.lastIndexOf('/') + 1).split('?')[0];
        const link = document.createElement('a');

        link.href = window.URL.createObjectURL(blob);
        link.download = name;
        link.target = '_blank';

        document.body.appendChild(link);
        link.click();
        document.body.removeChild(link);
      }

      let errCallback = () => {
        showErrorMessage(true);
      }

      Liferay.Service(
        '/cmic.cmiccommissiondocument/download-document',
        {
          id: activeId
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
                onChange={(e) => {
                  setActiveId(e.target.value);
                  showErrorMessage(false);
                }}
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
                    label={statement.statementDate + " - " + statement.documentType}
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
          {errorMessage &&
            <ClayLayout.Col size={12}>
              <ClayForm.FeedbackGroup className="has-error mt-4 mt-md-0">
                <ClayForm.FeedbackItem>
                  {Liferay.Language.get('error.there-was-an-issue-downloading-the-pdf-please-try-again-later')}
                </ClayForm.FeedbackItem>
              </ClayForm.FeedbackGroup>
            </ClayLayout.Col>
          }
        </ClayLayout.Row>
      </div>
    );
  }
};

export default Download;