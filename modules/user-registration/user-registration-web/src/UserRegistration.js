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
    }

    this.formRegistrationCode = React.createRef();
    this.formIdentity = React.createRef();
  }

  onFormRegistrationCodeSubmit(e) {
    e.preventDefault();

    //TODO CMIC-247 additional validation for registration code
    if (this.state.isRegistrationCodeEmpty) {
      return false;
    }

    this.submitFormRegistrationCode();
  }

  onFormIdentitySubmit(e) {
    e.preventDefault();

    //TODO CMIC-247 additional validation
    if (this.state.isDivisionAgentNumberEmpty) {
      return false;
    }

    this.submitFormIdentity();
  }

  setStateField(fieldName, fieldValue) {
    this.setState(previousState => ({
      [fieldName]: fieldValue
    }));
  }

  submitFormRegistrationCode() {
    let data = new FormData(this.formRegistrationCode.current);

    fetch(`/o/user-registration/validate-user-registration/`, {
      method: 'post',
      headers: new Headers(),
      body: data
    }).then((response) =>  {
      if (!response.ok) {
        throw response;
      }

      this.setState({ isFormRegistrationCodeSubmitted: true });
    }).catch(
      //TODO CMIC-247 handle error
    );
  }

  submitFormIdentity() {
    let data = new FormData(this.formIdentity.current);

    fetch(`/o/user-registration/is-user-valid/`, {
      method: 'post',
      headers: new Headers(),
      body: data
    }).then((response) =>  {
      if (!response.ok) {
        throw response;
      }

      window.location.href = Liferay.ThemeDisplay.getPortalURL() + '/group/broker';
    }).catch(
      //TODO CMIC-247 handle error
    );
  }

  validateAndSetDivisionAgentNumber(fieldName, fieldValue) {
    this.setStateField(fieldName, fieldValue);

    this.setState({ isDivisionAgentNumberEmpty: fieldValue == '' });
  };

  validateAndSetRegistrationCode(fieldName, fieldValue) {
    this.setStateField(fieldName, fieldValue);

    this.setState({ isRegistrationCodeEmpty: fieldValue == '' });
  };

  registrationCodeForm() {
    const { isRegistrationCodeEmpty } = this.state;
    const isSubmitEnabled = !isRegistrationCodeEmpty;

    return (
      <div className="user-registration-portlet">
        <h1 className="mb-5">{Liferay.Language.get("enter-registration-code")}</h1>

        <form ref={this.formRegistrationCode} onSubmit={(e) => this.onFormRegistrationCodeSubmit(e)}>

          <Input
            fieldName="registrationCode"
            handleFieldChange={(fieldName, fieldValue) => this.validateAndSetRegistrationCode(fieldName, fieldValue)}
            label={Liferay.Language.get("registration-code")}
            value={this.state.registrationCode}
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
          <ClayButton disabled={!isSubmitEnabled} displayType="primary" type="submit">
            {Liferay.Language.get("next")}
          </ClayButton>
        </form>
      </div>
    );
  }

  identityForm() {
    const { isDivisionAgentNumberEmpty } = this.state;
    const isSubmitEnabled = !isDivisionAgentNumberEmpty;

    return (
      <div className="user-registration-portlet">
        <h1 className="mb-5">{Liferay.Language.get("confirm-your-identity")}</h1>

        <form ref={this.formIdentity} onSubmit={(e) => this.onFormIdentitySubmit(e)}>
          <Input
            fieldName="divisionAgentNumber"
            handleFieldChange={(fieldName, fieldValue) => this.validateAndSetDivisionAgentNumber(fieldName, fieldValue)}
            label={Liferay.Language.get("division-agent-number")}
            value={this.state.divisionAgentNumber}
          />

          <Input
            fieldName="businessZipCode"
            handleFieldChange={(fieldName, fieldValue) => this.setStateField(fieldName, fieldValue)}
            label={Liferay.Language.get("business-zip-code")}
            value={this.state.businessZipCode}
          />

          <ClayButton disabled={!isSubmitEnabled} displayType="primary" type="submit">
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