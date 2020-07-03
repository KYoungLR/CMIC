import React, {useState, useMemo} from 'react';
import ClayButton from '@clayui/button';
import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';
import {useTable, useSortBy, useGlobalFilter} from 'react-table';
import AccountSearch from './AccountSearch';
import tableColumns from './AccountTableColumns';

const navigate = (e, id) => {
  // TODO - pass account number

  Liferay.Util.navigate('account-details');
}

const AccountList = (props) => {

  if (props.isLoading) {
    return (<ClayLoadingIndicator />);
  }
  else {
    const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/cmic/icons.svg';
    const columns = useMemo(() => tableColumns, []);
    const data = useMemo(() => props.accountsList, []);

    const {
      getTableProps,
      getTableBodyProps,
      headerGroups,
      rows,
      state,
      prepareRow,
      setGlobalFilter,
    } = useTable({
        columns,
        data,
      },
      useGlobalFilter,
      useSortBy,
    );

    return (
      <React.Fragment>
        <div className="card-header flex-container flex-container-stacked-xs justify-content-between align-items-md-center">
          <ClayCard.Description displayType="title">{Liferay.Language.get('accounts')} ({rows.length})</ClayCard.Description>
          <div className="mt-4 mt-md-0">
            <AccountSearch
              globalFilter={state.globalFilter}
              setGlobalFilter={setGlobalFilter}
            />
          </div>
        </div>
        <ClayCard.Body>
          {(() => {
            if (!rows.length) {
              return (
                <div className="p-4">No results</div>
              );
            }
            else {
              return (
                <ClayTable className="table-sticky" {...getTableProps()}>
                  <ClayTable.Head>
                    {headerGroups.map(headerGroup => (
                      <ClayTable.Row {...headerGroup.getHeaderGroupProps()}>
                        {headerGroup.headers.map(column => (
                          <ClayTable.Cell headingCell {...column.getHeaderProps({
                            ...column.headerProps
                          })}>
                            <span className="toggle-sort" {...column.getSortByToggleProps()}>
                              {column.render('Header')}

                              {column.isSorted
                                ? column.isSortedDesc
                                  ? <ClayIcon symbol={"sort-descending"} spritemap={spritemap} className="text-primary" />
                                  : <ClayIcon symbol={"sort-ascending"} spritemap={spritemap} className="text-primary" />
                                : <ClayIcon symbol={"sort"} spritemap={spritemap} className="text-muted" />
                              }
                            </span>
                          </ClayTable.Cell>
                        ))}
                      </ClayTable.Row>
                    ))}
                  </ClayTable.Head>

                  <ClayTable.Body>
                    {rows.map((row, i) => {
                      prepareRow(row)
                      return (
                        <ClayTable.Row
                          className="cursor-pointer"
                          onClick={(e) => navigate(e, row.original.accountNumber)}
                          {...row.getRowProps()}>

                          {row.cells.map(cell => {
                            return (
                              <ClayTable.Cell {...cell.getCellProps({
                                ...cell.column.cellProps
                              })}>
                                {cell.render('Cell')}
                              </ClayTable.Cell>
                            )
                          })}
                        </ClayTable.Row>
                      )
                    })}
                  </ClayTable.Body>
                </ClayTable>
              )
            }
          })()}
        </ClayCard.Body>
      </React.Fragment>
      /*<React.Fragment>
        <ClayTable className="table-sticky">
          <ClayTable.Head>
            <ClayTable.Row>
              <ClayTable.Cell expanded headingCell>
                {Liferay.Language.get('name')}
                <a href="javascript:;" className="text-muted">
                  <ClayIcon symbol={"sort"} spritemap={spritemap} />
                </a>
              </ClayTable.Cell>

              <ClayTable.Cell expanded headingCell>
                {Liferay.Language.get('producer-entity')}
                <a href="javascript:;" className="text-muted">
                  <ClayIcon symbol={"sort"} spritemap={spritemap} />
                </a>
              </ClayTable.Cell>

              <ClayTable.Cell headingCell align="center" className="table-cell-ws-nowrap">
                {Liferay.Language.get('in-force-policies')}
                <a href="javascript:;" className="text-muted">
                  <ClayIcon symbol={"sort"} spritemap={spritemap} />
                </a>
              </ClayTable.Cell>

              <ClayTable.Cell headingCell align="center" className="table-cell-ws-nowrap">
                {Liferay.Language.get('future-policies')}
                <a href="javascript:;" className="text-muted">
                  <ClayIcon symbol={"sort"} spritemap={spritemap} />
                </a>
              </ClayTable.Cell>

              <ClayTable.Cell headingCell align="center" className="table-cell-ws-nowrap">
                {Liferay.Language.get('expired-policies')}
                <a href="javascript:;" className="text-muted">
                  <ClayIcon symbol={"sort"} spritemap={spritemap} />
                </a>
              </ClayTable.Cell>

              <ClayTable.Cell headingCell align="right" className="table-cell-ws-nowrap">
                <ClayTooltipProvider delay="100">
                  <ClayIcon
                    className="text-secondary mr-2"
                    data-tooltip-align="bottom-right"
                    symbol="info-circle-open"
                    spritemap={Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg'}
                    title={Liferay.Language.get('this-value-is-an-estimate')}
                  />
                </ClayTooltipProvider>
                {Liferay.Language.get('policy-premium')}
                <a href="javascript:;" className="text-muted">
                  <ClayIcon symbol={"sort"} spritemap={spritemap} />
                </a>
              </ClayTable.Cell>

            </ClayTable.Row>
          </ClayTable.Head>
          <ClayTable.Body>
            {props.accountsList.map((account, index) => (
              <ClayTable.Row
                key={index}
                className="cursor-pointer"
                onClick={(e) => navigate(e, account.accountNumber)}>
                <ClayTable.Cell>
                  <h5 className="font-weight-bold mb-0">{account.accountName}</h5>
                  <small className="text-muted">#{account.accountNumber}</small>
                </ClayTable.Cell>

                <ClayTable.Cell>
                  <h5 className="font-weight-normal mb-0">{account.producerEntity}</h5>
                  <small className="text-muted">{account.producerEntityCode}</small>
                </ClayTable.Cell>

                <ClayTable.Cell align="center">{account.inForcePolicies}</ClayTable.Cell>
                <ClayTable.Cell align="center">{account.futurePolicies}</ClayTable.Cell>
                <ClayTable.Cell align="center">{account.expiredPolicies}</ClayTable.Cell>

                <ClayTable.Cell align="right" className="h3 font-weight-bold">
                  <NumberFormat value={account.policyPremium} displayType={'text'} thousandSeparator={true} prefix={'$'} />
                </ClayTable.Cell>
              </ClayTable.Row>
            ))}
          </ClayTable.Body>
        </ClayTable>
        <hr/>
        <ClayPaginationBarWithBasicItems
          size="sm"
          activeDelta={delta}
          activePage={activePage}
          deltas={deltas}
          ellipsisBuffer={3}
          onDeltaChange={setDelta}
          onPageChange={setActivePage}
          spritemap={Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg'}
          totalItems={props.accountsList.length}
        />
      </React.Fragment>*/
    );
  }
};

export default AccountList;