import React from 'react';
import ClayButton from "@clayui/button";

class SelfProvisioning extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      portraitURL: ""
    }
  }

  componentDidMount() {
    this.getPortraitUrl();
  }

  getPortraitUrl() {
    Liferay.Service('/cmic.cmicuser/get-user-portrait',
      {
        userId: Liferay.ThemeDisplay.getUserId()
      })
      .then(res => res.text())
      .then(data => this.setState({portraitURL: data}))
      .catch(() => this.props.displayErrorMessage('error.unable-to-retrieve-user-portrait-picture'));
  }

  render() {
    return (
      <div>
        <div className='flex-column flex-container my-profile-portlet-container'>
          <h1>{Liferay.Language.get('my-profile')}</h1>

          <div>{this.state.portraitURL}</div>

          <h3>User Portrait</h3>

          <h3>{Liferay.Language.get('name')}</h3>
          <p>Patty Jones</p>

          <h3>{Liferay.Language.get('email')}</h3>
          <p>producer.patty@churchmutual.com</p>

          <ClayButton
            borderless='true'
            displayType='primary'
            onClick='#'>
            {Liferay.Language.get('edit-profile')}
          </ClayButton>

          <ClayButton
            displayType='secondary'
            onClick='#'
            outline='true'>
            {Liferay.Language.get('sign-out')}
          </ClayButton>
        </div>
      </div>
    );
  }

}

export default SelfProvisioning;