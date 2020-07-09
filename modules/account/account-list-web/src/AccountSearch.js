import React, {useState} from 'react';
import {ClayButtonWithIcon} from '@clayui/button';
import {ClayInput} from '@clayui/form';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';

export const AccountSearch = ({activePage, queryString, setPageNumber, setQueryString}) => {
  const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';

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
          onChange={e => {
            setQueryString(e.target.value);
            if (activePage != 1) {
              setPageNumber(1);
            }
          }}
          placeholder={Liferay.Language.get('search')}
          type="text"
          value={queryString}
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

export const AccoutSearchFilter = (member, queryString) => {
  queryString = queryString.trim().toLowerCase();

  return (
    member.accountName.toLowerCase().includes(queryString) ||
    member.accountNumber.includes(queryString) ||
    member.producerCode.includes(queryString)
  );
};
