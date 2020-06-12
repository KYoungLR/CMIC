import React from 'react';
import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';

const AccountSearch = (props) => {
  const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';

  if (!props.isLoading) {
    return (
      <ClayInput.Group>
        <ClayInput.GroupItem>
          <ClayInput
            aria-label="Search"
            className="input-group-inset input-group-inset-after"
            type="text"
            placeholder={Liferay.Language.get('search-by-account-name-#-or-producer-code')}
          />
          <ClayInput.GroupInsetItem after tag="span">
            <ClayButtonWithIcon
              displayType="unstyled"
              spritemap={spritemap}
              symbol="search"
              className="text-secondary"
            />
          </ClayInput.GroupInsetItem>
        </ClayInput.GroupItem>
        <ClayInput.GroupItem shrink>
          <ClayButtonWithIcon
              displayType="unstyled"
              spritemap={spritemap}
              symbol="filter"
              className="btn-lg text-secondary"
            />
        </ClayInput.GroupItem>
      </ClayInput.Group>
    );
  }
  else {
    return '';
  }
};

export default AccountSearch;