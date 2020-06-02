import React from 'react';
import ClayForm, {ClayInput} from "@clayui/form";
import AsteriskIcon from "./AsteriskIcon";

const Input = (props) => {

  let { fieldName, showErrors, defaultValue, handleFieldChange } = props;

  const onChange = (e) => {
    let fieldValue = e.target.value;
    handleFieldChange(fieldName, fieldValue);
  };

  let displayError = showErrors && !defaultValue;

  let { label, labelHint, disabled, inputRef, errorMsg, type, required } = props;

  return (
    <ClayForm.Group className={displayError ? "has-error" : ""} filled={defaultValue ? "true" : null}>
      <label htmlFor={fieldName}>
          {label}
          {required ? <AsteriskIcon /> : ''}
          {labelHint ? <small><i> {labelHint.toLowerCase()}</i></small> : ''}
      </label>

      <ClayInput
        autocomplete={props.autocomplete ? props.autocomplete : null}
        disabled={disabled}
        id={fieldName}
        name={fieldName}
        type={type ? type : "text"}
        defaultValue={defaultValue}
        onChange={(e) => onChange(e)}
        ref={inputRef}
      />

      {displayError  &&
        <ClayForm.FeedbackGroup>
          <ClayForm.FeedbackItem>
            {errorMsg}
          </ClayForm.FeedbackItem>
        </ClayForm.FeedbackGroup>
      }
    </ClayForm.Group>
  );
};

export default Input;