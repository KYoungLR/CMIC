import React from 'react';
import ClayButton from '@clayui/button';
import {Toast, UserAvatar} from 'com.churchmutual.commons.web';
import {UpdatePortraitModal} from './UpdatePortraitModal';

class ProfileWeb extends React.Component {

  constructor(props) {
    super(props);

    this.state = {
      email: '',
      fullName: '',
      portraitURL: '',
      updatePortraitModal: {
        file: null,
        previewURL: null,
        visible: false,
      },
      errorMessage: ''
    }
  }

  componentDidMount() {
    this.getUserInformation();
  }

  getUserId() {
    return Liferay.ThemeDisplay.getUserId();
  }

  getUserInformation() {
    fetch(`/o/profile/${this.getUserId()}/user-information`)
      .then(res => res.json())
      .then(data => this.setState({
        email: data.email,
        fullname: data.fullname,
        portraitURL: data.portraitURL
      }))
      .catch(() => this.displayErrorMessage('your-request-failed-to-complete'));
  }

  setUpdatePortraitModalVisible(value) {
    this.setState({
      updatePortraitModal: {
        ...this.state.updatePortraitModal,
        visible: value
      }
    });
  }

  setPortraitPreview(event) {
    let file = event.target.files[0];

    if (!file) {
      return;
    }

    let reader = new FileReader();

    reader.onload = (e) =>  this.setState({
      updatePortraitModal: {
        file: file,
        previewURL: e.target.result,
        visible: true
      }
    });

    reader.readAsDataURL(file);
  }

  cancelUpdatePortrait() {
    this.setState({
      portrait: {
        file: null,
        previewURL: null,
        visible: false,
      }
    });
  }

  updatePortrait() {
    let file = this.state.updatePortraitModal.file;

    if (!file) {
      this.setUpdatePortraitModalVisible(false);
      return;
    }

    if (file.size > 300000) {
      this.displayErrorMessage('upload-images-no-larger-than-300kb');
      return;
    }

    fetch(`/o/profile/${this.getUserId()}/portrait`,
      {
        method: 'POST',
        body: file
      })
      .then(res => res.json())
      .then(data => {
        this.setState({portraitURL: data.portraitURL});
        this.setUpdatePortraitModalVisible(false);
      })
      .catch(() => this.displayErrorMessage('your-request-failed-to-complete'));
  }

  displayErrorMessage(message) {
    this.setState({
      errorMessage: message
    });
  }

  render() {
    return (
      <div>
        <div className='flex-column flex-container my-profile-portlet-container'>
          <h3 className='mb-3'>{Liferay.Language.get('my-profile')}</h3>
          <UserAvatar image={this.state.portraitURL} onClick={() => this.setUpdatePortraitModalVisible(true)} />

          <h3 className='mt-3'>{Liferay.Language.get('name')}</h3>
          <p>Patty Jones</p>

          <h3>{Liferay.Language.get('email')}</h3>
          <p>producer.patty@churchmutual.com</p>

          <ClayButton
            borderless='true'
            displayType='primary'>
            {Liferay.Language.get('edit-profile')}
          </ClayButton>

          <ClayButton
            displayType='secondary'
            outline='true'>
            {Liferay.Language.get('sign-out')}
          </ClayButton>

          <UpdatePortraitModal
            portraitURL={this.state.updatePortraitModal.previewURL ? this.state.updatePortraitModal.previewURL : this.state.portraitURL}
            setVisible={(visible) => this.setUpdatePortraitModalVisible(visible)}
            visible={this.state.updatePortraitModal.visible}
            handlePictureFileChanged={(event) => this.setPortraitPreview(event)}
            onClickDone={() => this.updatePortrait()}
            onClickCancel={() => this.cancelUpdatePortrait()}
          />

          <Toast
            message={this.state.errorMessage}
            displayType='danger'
            title={Liferay.Language.get('error-colon')}
            onClose={() => this.displayErrorMessage('')} />
        </div>
      </div>
    );
  }

}

export default ProfileWeb;