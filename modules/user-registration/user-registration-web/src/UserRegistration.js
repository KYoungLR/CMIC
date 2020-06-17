import ClayButton from '@clayui/button';
import React from 'react';
import {Input} from 'com.churchmutual.commons.web';

class UserRegistration extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      businessZipCode: '',
      divisionAgentNumber: '',
      isFormRegistrationCodeSubmitted: false,
      isDivisionAgentNumberEmpty: true,
      isRegistrationCodeEmpty: true,
      registrationCode: '',
      formErrors: {
        businessZipCode: null,
        divisionAgentNumber: null,
        registrationCode: null
      }
    }

    this.formRegistrationCode = React.createRef();
    this.formIdentity = React.createRef();
  }

  isFormRegistrationCodeValid() {
    const { isRegistrationCodeEmpty, formErrors } = this.state;
    return !isRegistrationCodeEmpty && !formErrors.registrationCode;
  }

  isFormIdentityValid() {
    const { isDivisionAgentNumberEmpty, formErrors } = this.state;
    return !isDivisionAgentNumberEmpty && (!formErrors.divisionAgentNumber && !formErrors.businessZipCode);
  }

  onFormRegistrationCodeSubmit(e) {
    e.preventDefault();

    if (!this.isFormRegistrationCodeValid()) {
      this.setState(previousState => ({
        formErrors: {
          ...previousState.formErrors,
          registrationCode: true
        }
      }));

      return;
    }

    this.submitFormRegistrationCode();
  }

  onFormIdentitySubmit(e) {
    e.preventDefault();

    if (!this.isFormIdentityValid()) {
      this.setState(previousState => ({
        formErrors: {
          ...previousState.formErrors,
          businessZipCode: true,
          divisionAgentNumber: true
        }
      }));

      return;
    }

    this.submitFormIdentity();
  }

  setStateField(fieldName, fieldValue) {
    this.setState(previousState => ({
      [fieldName]: fieldValue,
      formErrors: {
        ...previousState.formErrors,
        businessZipCode: null,
        divisionAgentNumber: null
      }
    }));
  }

  submitFormRegistrationCode() {
    let callback = () => this.setState({ isFormRegistrationCodeSubmitted: true });

    let errCallback = () =>
      this.setState(previousState => ({
        formErrors: {
          ...previousState.formErrors,
          registrationCode: true
        }
      }));

    Liferay.Service(
      '/cmic.cmicuser/validate-user-registration',
      {
        registrationCode: this.state.registrationCode
      },
      callback,
      errCallback
    );
  }

  submitFormIdentity() {
    let callback = (isUserValid) => {
      if (!isUserValid) {
        this.setState(previousState => ({
          formErrors: {
            ...previousState.formErrors,
            businessZipCode: true,
            divisionAgentNumber: true
          }
        }));
      }
      else {
        window.location.href = Liferay.ThemeDisplay.getPortalURL() + "/group/broker";
      }
    }

    let errCallback = () =>
      this.setState(previousState => ({
        formErrors: {
          ...previousState.formErrors,
          businessZipCode: true,
          divisionAgentNumber: true
        }
      }));

    Liferay.Service(
      '/cmic.cmicuser/is-user-valid',
      {
        businessZipCode: this.state.businessZipCode,
        divisionAgentNumber: this.state.divisionAgentNumber,
        registrationCode: this.state.registrationCode,
        cmicUUID: ''
      },
      callback,
      errCallback
    );
  }

  setDivisionAgentNumber(fieldName, fieldValue) {
    this.setState(previousState => ({
      fieldName: fieldValue,
      isDivisionAgentNumberEmpty: fieldValue == "",
      [fieldName]: fieldValue,
      formErrors: {
        ...previousState.formErrors,
        businessZipCode: null,
        divisionAgentNumber: null
      }
    }));
  };

  setRegistrationCode(fieldName, fieldValue) {
    this.setState(previousState => ({
      fieldName: fieldValue,
      isRegistrationCodeEmpty: fieldValue == "",
      [fieldName]: fieldValue,
      formErrors: {
        ...previousState.formErrors,
        registrationCode: null
      }
    }));
  };

  registrationCodeForm() {
    return (
      <div className="user-registration-portlet">
        <h1 className="mb-5">{Liferay.Language.get("enter-registration-code")}</h1>

        <form ref={this.formRegistrationCode} onSubmit={(e) => this.onFormRegistrationCodeSubmit(e)}>

          <Input
            fieldName="registrationCode"
            handleFieldChange={(fieldName, fieldValue) => this.setRegistrationCode(fieldName, fieldValue)}
            label={Liferay.Language.get("registration-code")}
            maxLength="150"
            value={this.state.registrationCode}
            showErrors={this.state.formErrors.registrationCode}
            errorMsg={Liferay.Language.get("error.registration-code")}
          />

          <div className="ml-3 mb-5 small">
            <a data-toggle="collapse" role="button" aria-expanded="false" aria-controls="collapseExample"
               className="collapsed text-muted" href="#helpText">
              {Liferay.Language.get("cant-find-your-registration-code")}
            </a>

            <div className="collapse" id="helpText">
              {Liferay.Language.get("registration-help-text")}
            </div>
          </div>
          <ClayButton disabled={!this.isFormRegistrationCodeValid()} displayType="primary" type="submit">
            {Liferay.Language.get("next")}
          </ClayButton>
        </form>
      </div>
    );
  }

  identityForm() {
    return (
      <div className="user-registration-portlet">
        <h1 className="mb-5">{Liferay.Language.get("confirm-your-identity")}</h1>

        <form ref={this.formIdentity} onSubmit={(e) => this.onFormIdentitySubmit(e)}>
          <Input
            fieldName="divisionAgentNumber"
            handleFieldChange={(fieldName, fieldValue) => this.setDivisionAgentNumber(fieldName, fieldValue)}
            label={Liferay.Language.get("division-agent-number")}
            maxLength="150"
            value={this.state.divisionAgentNumber}
            showErrors={this.state.formErrors.divisionAgentNumber}
            errorMsg={Liferay.Language.get("error.division-agent-number")}
            errorMsgPosition="top"
          />

          <Input
            fieldName="businessZipCode"
            handleFieldChange={(fieldName, fieldValue) => this.setStateField(fieldName, fieldValue)}
            label={Liferay.Language.get("business-zip-code")}
            maxLength="12"
            value={this.state.businessZipCode}
            showErrors={this.state.formErrors.businessZipCode}
          />

          <ClayButton disabled={!this.isFormIdentityValid()} displayType="primary" type="submit">
            {Liferay.Language.get("finish")}
          </ClayButton>
        </form>
      </div>
    );
  }

  render() {
    if (!this.state.isFormRegistrationCodeSubmitted) {
      return this.registrationCodeForm();
    }
    else {
      return this.identityForm();
    }
  }
}

export default UserRegistration;