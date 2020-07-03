import React, {useState} from 'react';
import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';

const AccountSearch = ({globalFilter, setGlobalFilter}) => {
  const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';
  const [value, setValue] = useState(globalFilter);

  return (
    <ClayInput.Group>
      <ClayInput.GroupItem shrink className="align-items-center text-secondary">
        <ClayTooltipProvider delay="100">
          <ClayIcon
            data-tooltip-align="bottom-left"
            symbol="info-circle-open"
            spritemap={spritemap}
            title={Liferay.Language.get('search-by-account-name-policy-producer-code')}
          />
        </ClayTooltipProvider>
      </ClayInput.GroupItem>
      <ClayInput.GroupItem>
        <ClayInput
          aria-label="Search"
          className="input-group-inset input-group-inset-after"
          type="text"
          onChange={e => {
            setValue(e.target.value);
            setGlobalFilter(e.target.value || undefined);
          }}
          placeholder={Liferay.Language.get('search')}
        />
        <ClayInput.GroupInsetItem after tag="span">
          <ClayIcon
            className="icon-monospaced text-secondary"
            symbol="search"
            spritemap={spritemap}
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
};

export default AccountSearch;