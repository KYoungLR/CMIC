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

  let { label, labelHint, disabled, inputRef, errorMsg, errorMsgPosition, maxLength, type, required, placeholder, value } = props;

  return (
    <ClayForm.Group className={displayError ? "has-error" : ""} filled={defaultValue ? "true" : null}>
      {errorMsgPosition == 'top' && displayError && errorMsg != null &&
        <ClayForm.FeedbackGroup>
          <ClayForm.FeedbackItem className="bg-white pb-4">
            {errorMsg}
          </ClayForm.FeedbackItem>
        </ClayForm.FeedbackGroup>
      }

      <label htmlFor={fieldName}>
          {label}
          {required ? <AsteriskIcon /> : ''}
      </label>

      <ClayInput
        autoComplete={props.autocomplete ? props.autocomplete : null}
        disabled={disabled}
        id={fieldName}
        maxLength={maxLength ? maxLength : "524288"}
        name={fieldName}
        placeholder={placeholder}
        type={type ? type : "text"}
        defaultValue={defaultValue}
        onChange={(e) => onChange(e)}
        ref={inputRef}
        value={value}
      />

      {labelHint ? <small>{labelHint}</small> : ''}

      {errorMsgPosition != 'top' && displayError && errorMsg != null &&
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