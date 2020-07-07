import React from 'react';
import ClayIcon from '@clayui/icon';
import {Dialog} from 'com.churchmutual.commons.web';

function EditProfileModal(props) {
  const spritemap = Liferay.ThemeDisplay.getPathThemeImages() + '/clay/icons.svg';
  
  return <Dialog
    size="md"
    title={Liferay.Language.get('edit-profile')}
    confirmButtonText={Liferay.Language.get('done')}
    onClickConfirm={() => props.onClickDone()}
    setVisible={props.setVisible}
    visible={props.visible}
    hideCancel={true}
  >
    <ul className="list-unstyled">
      <li className="mb-4">
        <a href="/web/broker/b2c-edit-email" target="_blank">{Liferay.Language.get('update-email')}</a>
        <ClayIcon spritemap={spritemap} symbol="shortcut" className="ml-2" />
      </li>
      <li className="mb-4">
        <a href="/web/broker/b2c-edit-profile" target="_blank">{Liferay.Language.get('update-profile-and-password')}</a>
        <ClayIcon spritemap={spritemap} symbol="shortcut" className="ml-2" />
      </li>
      <li>
        <a href="/web/broker/b2c-edit-mfa" target="_blank">{Liferay.Language.get('configure-multi-factor-authentication')}</a>
        <ClayIcon spritemap={spritemap} symbol="shortcut" className="ml-2" />
      </li>
    </ul>
  </Dialog>;
}

export {EditProfileModal};