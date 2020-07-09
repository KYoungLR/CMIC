import React, {useState} from 'react';
import ClayCard from '@clayui/card';
import ClayTable from '@clayui/table';
import NumberFormat from 'react-number-format';
import AccountPagination from './AccountPagination';
import {AccountSearch, AccoutSearchFilter} from './AccountSearch';
import EmptyState from './EmptyState';
import {Navigate, Render, TableHeadings, TableSort} from './AccountTableHelpers';

import ReactBigList, {withCustomFilters} from 'react-big-list';
import {customFilterMap, filterOptions} from './AccountFilters';

let Enhanced = withCustomFilters(ReactBigList, customFilterMap);

const AccountList = (props) => {
  const [pageSize, setPageSize] = useState(10);

  return (
    <Enhanced
      members={props.accountsList}
      paginationProps={{pageSize}}
      queryStringFilter={AccoutSearchFilter}>
      {({
          activeFilters,
          activePage,
          displayedCount,
          displayedMembers,
          displayingFrom,
          displayingTo,
          filteredCount,
          initialCount,
          numPages,
          queryString,
          sortColumn,
          sortDirection,
          setPageNumber,
          setQueryString,
          setSort,
          toggleFilter,
        }) => (
        <React.Fragment>
          <div className="card-header flex-container flex-container-stacked-xs justify-content-between align-items-md-center">
            <ClayCard.Description displayType="title">{Liferay.Language.get('accounts')} ({filteredCount})</ClayCard.Description>
            <div className="mt-4 mt-md-0">
              <AccountSearch
                activePage={activePage}
                queryString={queryString}
                setPageNumber={setPageNumber}
                setQueryString={setQueryString}
              />
            </div>
          </div>

          <ClayCard.Body>
            {!filteredCount ? (
              <EmptyState />
            ) : (
              <React.Fragment>
                <ClayTable className="table-sticky">
                  <ClayTable.Head>
                    <ClayTable.Row>
                      {TableHeadings.map((heading, i) => (
                        <ClayTable.Cell key={i} headingCell {...heading.props}>
                          <TableSort
                            sortable={heading.sortable}
                            sortBy={heading.sortBy}
                            setSort={setSort}
                            sortColumn={sortColumn}
                            sortDirection={sortDirection}
                          >
                            {Render(heading.text)}
                          </TableSort>
                        </ClayTable.Cell>
                      ))}
                    </ClayTable.Row>
                  </ClayTable.Head>

                  <ClayTable.Body>
                    {displayedMembers.map((account, i) => (
                      <ClayTable.Row
                        className="cursor-pointer"
                        key={i}
                        onClick={() => Navigate(account.accountNumber)}
                      >
                        <ClayTable.Cell>
                          <h5 className="font-weight-bold mb-0">{account.accountName}</h5>
                          <small className="text-muted">#{account.accountNumber}</small>
                        </ClayTable.Cell>

                        <ClayTable.Cell>
                          <h5 className="font-weight-normal mb-0">{account.producerName}</h5>
                          <small className="text-muted">{account.producerCode}</small>
                        </ClayTable.Cell>

                        <ClayTable.Cell align="center">{account.inForcePolicy}</ClayTable.Cell>
                        <ClayTable.Cell align="center">{account.futurePolicy}</ClayTable.Cell>
                        <ClayTable.Cell align="center">{account.expiredPolicy}</ClayTable.Cell>

                        <ClayTable.Cell align="right" className="h3 font-weight-bold">
                          <NumberFormat value={account.totalBilledPremium} displayType={'text'} thousandSeparator={true} prefix={'$'} />
                        </ClayTable.Cell>
                      </ClayTable.Row>
                    ))}
                  </ClayTable.Body>
                </ClayTable>
                <hr/>
                <AccountPagination
                  activePage={activePage}
                  displayingFrom={displayingFrom}
                  displayingTo={displayingTo}
                  filteredCount={filteredCount}
                  numPages={numPages}
                  pageSize={pageSize}
                  setPageNumber={setPageNumber}
                  setPageSize={setPageSize}
                />
              </React.Fragment>
            )}
          </ClayCard.Body>
        </React.Fragment>
      )}
    </Enhanced>
  );
};

export default AccountList;