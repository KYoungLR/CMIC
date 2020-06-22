import React, {useState} from 'react';
import {ClaySelect} from '@clayui/form';
import {Dialog} from 'com.churchmutual.commons.web';

const RoleSelect = (props) => {

    const options = props.roleTypes;

    const [visible, setChangeOwnerModalVisible] = useState(false);

    function isRemovingOwnerFromYourself(fieldValue) {
        return fieldValue === 'owner'
          && !isCurrentUser()
          && props.currentUser.role === 'owner';
    }

    function onChange(e) {
        let fieldValue = e.target.value;

        if (isRemovingOwnerFromYourself(fieldValue)) {
            setChangeOwnerModalVisible(true);
        } else {
            props.handleFieldChange(props.user, fieldValue, isCurrentUser());
        }
    }

    function isCurrentUser() {
        return props.user === props.currentUser;
    }

    function confirmUpdateOwner() {
        setChangeOwnerModalVisible(false);

        props.handleFieldChange(props.user, 'owner', isCurrentUser());
    }

    function getOptions() {
        if (props.value === 'owner') {
            return [options[0]];
        }

        if (props.user.statusKey === 'invited') {
            return [options[1], options[2]];
        }

        if (props.currentUser.role === 'owner') {
            return options;
        }

        let ret = [...options];

        ret.shift();

        return ret;
    }

    return (
        <React.Fragment>
            <ClaySelect aria-label="Select Role" id="roleSelector"
              onChange={(e) => onChange(e)} value={props.value}>
                {getOptions().map(item => (
                    <ClaySelect.Option
                        key={item.label}
                        label={Liferay.Language.get(item.label)}
                        value={item.label}
                    />
                ))}
            </ClaySelect>

            <Dialog
              title={Liferay.Language.get('change-owner')}
              buttonConfirmText={Liferay.Language.get('continue')}
              onClickConfirm={() => confirmUpdateOwner()}
              visible={visible}
              setVisible={(show) => setChangeOwnerModalVisible(show)}
              status="warning"
            >
                <div className="lead-text" dangerouslySetInnerHTML={{
                    __html: Liferay.Util.sub(
                        Liferay.Language.get('are-you-sure-you-want-to-make-x-the-account-owner'),
                        props.user.email
                    )
                }}/>
            </Dialog>
        </React.Fragment>
    );
};

export {RoleSelect};
