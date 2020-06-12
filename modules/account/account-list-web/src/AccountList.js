import React, {useState} from 'react';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';
import NumberFormat from 'react-number-format';

const AccountList = (props) => {
  const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/cmic/icons.svg';
  const [activePage, setActivePage] = useState(1);
  const [delta, setDelta] = useState(10);

  const deltas = [
    { label: 10 },
    { label: 20 },
    { label: 30 },
    { label: 40 },
    { label: 50 }
  ];

  if (props.isLoading) {
    return (<ClayLoadingIndicator />);
  }
  else {
    return (
      <React.Fragment>
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
                {Liferay.Language.get('amount-billed')}
                <a href="javascript:;" className="text-muted">
                  <ClayIcon symbol={"sort"} spritemap={spritemap} />
                </a>
              </ClayTable.Cell>

            </ClayTable.Row>
          </ClayTable.Head>
          <ClayTable.Body>
            {props.accountsList.map((account, index) => (
              <ClayTable.Row key={index}>
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
                  <NumberFormat value={account.amountBilled} displayType={'text'} thousandSeparator={true} prefix={'$'} />
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
      </React.Fragment>
    );
  }
};

export default AccountList;