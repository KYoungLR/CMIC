import React, {useState} from 'react';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import {ClayPaginationBarWithBasicItems} from '@clayui/pagination-bar';
import ClayTable from '@clayui/table';

const TransactionList = (props) => {
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
  
  return (
    <React.Fragment>
      <ClayTable className="table-heading-nowrap">
        <ClayTable.Head>
          <ClayTable.Row>
            <ClayTable.Cell expanded headingCell>{Liferay.Language.get('transaction-date')}</ClayTable.Cell>
            <ClayTable.Cell expanded headingCell>{Liferay.Language.get('transaction-type')}</ClayTable.Cell>
            <ClayTable.Cell headingCell align="center">{Liferay.Language.get('download')}</ClayTable.Cell>
          </ClayTable.Row>
        </ClayTable.Head>
        <ClayTable.Body>
          {props.transactionList.map((transaction, index) => (
            <ClayTable.Row key={index}>
              <ClayTable.Cell>{transaction.transactionDate}</ClayTable.Cell>
              <ClayTable.Cell>{transaction.transactionType}</ClayTable.Cell>
              <ClayTable.Cell align="center">
                <ClayButton displayType="unstyled" monospaced className="text-primary">
                  <ClayIcon symbol={"download"} spritemap={spritemap} className="lexicon-icon-lg" />
                </ClayButton>
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
        totalItems={props.transactionList.length}
      />
    </React.Fragment>
  );
};

export default TransactionList;