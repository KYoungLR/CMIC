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
        }
    });

    const onConfirm = () => {
        props.onClickConfirm();

        let modalOpenNode = document.querySelector('.modal-open');

        if (modalOpenNode) {
            modalOpenNode.classList.remove('modal-open')
        }
    };

    let buttonConfirm = props.confirmButtonText ? props.confirmButtonText : "Confirm";

    return props.visible && (
        <ClayModal
            observer={observer}
            size="lg"
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
                        <ClayButton displayType="secondary" onClick={onClose}>
                            {Liferay.Language.get("cancel")}
                        </ClayButton>
                        <ClayButton displayType="primary" onClick={onConfirm}>
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