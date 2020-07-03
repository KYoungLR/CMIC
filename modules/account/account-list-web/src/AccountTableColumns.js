import React from 'react';
import ClayIcon from '@clayui/icon';
import {ClayTooltipProvider} from '@clayui/tooltip';
import NumberFormat from 'react-number-format';

const spritemap = themeDisplay.getPathThemeImages() + '/clay/icons.svg';

const tableColumns = [
  {
    Header: Liferay.Language.get('name'),
    accessor: 'accountName',
    Cell: ({row}) => {
      return (
        <React.Fragment>
          <h5 className="font-weight-bold mb-0">{row.original.accountName}</h5>
          <small className="text-muted">#{row.original.accountNumber}</small>
        </React.Fragment>
      );
    },
    headerProps: {
      expanded: true,
    },
  },
  {
    Header: Liferay.Language.get('producer-entity'),
    accessor: row => `${row.producerEntity} ${row.producerEntityCode}`,
    Cell: ({row}) => {
      return (
        <React.Fragment>
          <h5 className="font-weight-normal mb-0">{row.original.producerEntity}</h5>
          <small className="text-muted">{row.original.producerEntityCode}</small>
        </React.Fragment>
      );
    },
    headerProps: {
      expanded: true,
    },
  },
  {
    Header: Liferay.Language.get('in-force-policies'),
    accessor: 'inForcePolicies',
    disableGlobalFilter: true,
    headerProps: {
      align: 'center',
      className: 'table-cell-ws-nowrap',
    },
    cellProps: {
      align: 'center',
    },
  },
  {
    Header: Liferay.Language.get('future-policies'),
    accessor: 'futurePolicies',
    disableGlobalFilter: true,
    headerProps: {
      align: 'center',
      className: 'table-cell-ws-nowrap',
    },
    cellProps: {
      align: 'center',
    },
  },
  {
    Header: Liferay.Language.get('expired-policies'),
    accessor: 'expiredPolicies',
    disableGlobalFilter: true,
    headerProps: {
      align: 'center',
      className: 'table-cell-ws-nowrap',
    },
    cellProps: {
      align: 'center',
    },
  },
  {
    Header: () => {
      return (
        <React.Fragment>
          <ClayTooltipProvider delay="100">
            <ClayIcon
              className="text-secondary mr-2"
              data-tooltip-align="bottom-right"
              symbol="info-circle-open"
              spritemap={spritemap}
              title={Liferay.Language.get('this-value-is-an-estimate')}
            />
          </ClayTooltipProvider>
          {Liferay.Language.get('policy-premium')}
        </React.Fragment>
      );
    },
    accessor: 'policyPremium',
    disableGlobalFilter: true,
    Cell: ({value}) => {
      return (<NumberFormat value={value} displayType={'text'} thousandSeparator={true} prefix={'$'} />);
    },
    headerProps: {
      align: 'right',
      className: 'table-cell-ws-nowrap',
    },
    cellProps: {
      align: 'right',
      className: 'h3 font-weight-bold',
    }
  },
];

export default tableColumns;