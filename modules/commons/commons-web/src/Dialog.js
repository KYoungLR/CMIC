import ClayModal, {useModal} from '@clayui/modal';
import React, {useRef} from 'react';
import ClayButton from "@clayui/button";
import {removeOutsideClickEvent} from "./removeOutsideClickEvent";
const Dialog = (props) => {

    const wrapperRef = useRef(null);
    removeOutsideClickEvent(wrapperRef);

    const { observer, onClose } = useModal({
        onClose: () => {
            props.setVisible(false);

            if (props.onClickCancel != null){
                props.onClickCancel();
            }
        }
    });

    const onConfirm = () => {
        props.onClickConfirm();

        let modalOpenNode = document.querySelector('.modal-open');

        if (modalOpenNode) {
            modalOpenNode.classList.remove('modal-open')
        }
    };

    const buttonConfirm = props.confirmButtonText ? props.confirmButtonText : 'Confirm';
    const size = props.size ? props.size : 'lg';

    return props.visible && (
        <ClayModal
            observer={observer}
            size={size}
            spritemap={Liferay.ThemeDisplay.getPathThemeImages() + "/lexicon/icons.svg"}
            status={props.status ? props.status : null}
        >
            <div ref={wrapperRef}>
            <ClayModal.Header>{props.title}</ClayModal.Header>
            <ClayModal.Body>
                {props.children}
            </ClayModal.Body>
            {!props.hideFooter && <ClayModal.Footer
                last={
                    <ClayButton.Group spaced>
                        {!props.hideCancel && <ClayButton displayType="outline-secondary" small onClick={onClose}>
                            {Liferay.Language.get("cancel")}
                        </ClayButton>}
                        <ClayButton displayType="primary" small onClick={onConfirm}>
                            {buttonConfirm}
                        </ClayButton>
                    </ClayButton.Group>
                }
            />
            }
            </div>
        </ClayModal>);
}

export default Dialog;