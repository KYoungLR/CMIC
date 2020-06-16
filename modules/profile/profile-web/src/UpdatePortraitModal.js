import React, {useRef} from 'react';
import {Dialog, UserAvatar} from 'com.churchmutual.commons.web';

function UpdatePortraitModal(props) {
  const inputRef = useRef(null);

  return <Dialog
    title={Liferay.Language.get('update-profile-picture')}
    confirmButtonText={Liferay.Language.get('done')}
    onClickConfirm={() => props.onClickDone()}
    setVisible={props.setVisible}
    visible={props.visible}
  >
    <div className='container'>
      <div className='row align-items-center'>
        <div className='col-12' align='center'>
          <span className='modal-body-text'>
              {Liferay.Language.get('upload-images-no-larger-than-300kb')}
          </span>
        </div>
        <div className='col-12' align='center'>
          <UserAvatar image={props.portraitURL} className='avatar-square mt-4'/>
          <input id='inputPortraitFile' type='file' ref={inputRef} className='hide'
              onChange={(e) => props.handlePictureFileChanged(e)}/>
        </div>
        <div className='col-12 mt-4' align='center'>
          <button className='btn btn-outline-primary'
              onClick={() => inputRef.current.click()}>
            {Liferay.Language.get('select')}
          </button>
        </div>
      </div>
    </div>
  </Dialog>;
}

export {UpdatePortraitModal};