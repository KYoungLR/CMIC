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

    return (
      <ClayLayout.Row>
        <ClayLayout.Col md={6}>
          <ClayForm.Group>
            <ClaySelect
              aria-label="Select Statement"
              id="statementSelect"
              onChange={(e) => setActivePath(e.target.value)}
            >
              {!activePath && <ClaySelect.Option />}

              {props.statementList.map((statement, index) => (
                <ClaySelect.Option
                  key={index}
                  label={statement.label}
                  value={statement.url}
                />
              ))}
            </ClaySelect>
          </ClayForm.Group>
        </ClayLayout.Col>
        <ClayLayout.Col md={6}>
          <ClayButton displayType="primary" className="btn-lg">
          {Liferay.Language.get('download-statement')}
          <ClayIcon
            spritemap={Liferay.ThemeDisplay.getPathThemeImages() + '/cmic/icons.svg'}
            symbol="download"
          />
        </ClayButton>
        </ClayLayout.Col>
      </ClayLayout.Row>
    );
  }
};

export default Download;