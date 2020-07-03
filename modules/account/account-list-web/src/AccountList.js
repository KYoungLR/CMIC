import React, {useState, useMemo} from 'react';
import ClayButton from '@clayui/button';
import ClayCard from '@clayui/card';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';
import {useTable, useSortBy, useGlobalFilter} from 'react-table';
import AccountSearch from './AccountSearch';
import EmptyState from './EmptyState';
import TableColumns from './AccountTableColumns';

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
    const columns = useMemo(() => TableColumns, []);
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
              return (<EmptyState />);
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
    );
  }
};

export default AccountList;