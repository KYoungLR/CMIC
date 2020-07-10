import React from 'react';
import ClayButton from '@clayui/button';
import ClayIcon from '@clayui/icon';
import ClayLoadingIndicator from '@clayui/loading-indicator';
import ClayTable from '@clayui/table';
import NumberFormat from 'react-number-format';

const navigate = (e, id) => {
  // TODO - pass account number

  Liferay.Util.navigate('account-details');
}

const AccountList = (props) => {
  const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/cmic/icons.svg';

  if (props.isLoading) {
    return (<ClayLoadingIndicator />);
  }
  else {
    return (
      <React.Fragment>
        <ClayTable>
          <ClayTable.Head>
            <ClayTable.Row>
              <ClayTable.Cell expanded headingCell>
                {Liferay.Language.get('name')}
                <a href="javascript:;" className="text-muted">
                  <ClayIcon symbol={"sort"} spritemap={spritemap} />
                </a>
              </ClayTable.Cell>

              <ClayTable.Cell expanded headingCell align="center" className="table-cell-expand-smallest">
                {Liferay.Language.get('in-force-policies')}
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
              <ClayTable.Row
                key={index}
                className="cursor-pointer"
                onClick={(e) => navigate(e, account.accountNumber)}>
                <ClayTable.Cell>
                  <h5 className="font-weight-bold mb-0">{account.name}</h5>
                  <small className="text-muted">#{account.accountNumber}</small>
                </ClayTable.Cell>
                <ClayTable.Cell align="center">{account.numInForcePolicies}</ClayTable.Cell>
                <ClayTable.Cell align="right" className="h3 font-weight-bold">
                  <NumberFormat value={account.totalBilledPremium} displayType={'text'} thousandSeparator={true} prefix={'$'} />
                </ClayTable.Cell>
              </ClayTable.Row>
            ))}
          </ClayTable.Body>
        </ClayTable>
        <div className="pt-2 px-2"><a href="accounts" className="link-action">{Liferay.Language.get('see-all-accounts')}</a></div>
      </React.Fragment>
    );
  }
};

export default AccountList;