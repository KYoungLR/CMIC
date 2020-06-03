import ClayButton from "@clayui/button";
import React from 'react';
import {Input} from "com.churchmutual.commons.web";

class UserRegistration extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      businessZipCode: "",
      divisionAgentNumber: "",
      isFormPage1Submitted: false,
      isDivisionAgentNumberEmpty: true,
      isRegistrationCodeEmpty: true,
      registrationCode: "",
    }

    this.form1 = React.createRef();
    this.form2 = React.createRef();
  }

  onFormPage1Submit(e) {
    e.preventDefault();

    //TODO CMIC-247 additional validation for registration code
    if (this.state.isRegistrationCodeEmpty) {
      return false;
    }

    this.submitFormPage1();
  }

  onFormPage2Submit(e) {
    e.preventDefault();

    //TODO CMIC-247 additional validation
    if (this.state.isDivisionAgentNumberEmpty) {
      return false;
    }

    this.submitFormPage2();
  }

  setStateField(fieldName, fieldValue) {
    this.setState(previousState => ({
      [fieldName]: fieldValue
    }));
  }

  submitFormPage1() {
    let data = new FormData(this.form1.current);

    fetch(`/o/user-registration/validate-user-registration/`, {
      method: 'post',
      headers: new Headers(),
      body: data
    }).then((response) =>  {
      if (!response.ok) {
        throw response;
      }

      this.setState({ isFormPage1Submitted: true });
    }).catch(
      //TODO CMIC-247 handle error
    );
  }

  submitFormPage2() {
    let data = new FormData(this.form2.current);

    fetch(`/o/user-registration/is-user-valid/`, {
      method: 'post',
      headers: new Headers(),
      body: data
    }).then((response) =>  {
      if (!response.ok) {
        throw response;
      }

      window.location.href = Liferay.ThemeDisplay.getPortalURL() + "/group/broker";
    }).catch(
      //TODO CMIC-247 handle error
    );
  }

  validateAndSetDivisionAgentNumber(fieldName, fieldValue) {
    this.setStateField(fieldName, fieldValue);

    this.setState({ isDivisionAgentNumberEmpty: fieldValue == "" });
  };

  validateAndSetRegistrationCode(fieldName, fieldValue) {
    this.setStateField(fieldName, fieldValue);

    this.setState({ isRegistrationCodeEmpty: fieldValue == "" });
  };

  formPage1() {
    const { isRegistrationCodeEmpty } = this.state;
    const isSubmitEnabled = !isRegistrationCodeEmpty;

    return (
      <div className="user-registration">
        <div className="h2 font-weight-bold pb-4">{Liferay.Language.get("enter-registration-code")}</div>
        <form ref={this.form1} onSubmit={(e) => this.onFormPage1Submit(e)}>
          <Input
            fieldName="registrationCode"
            handleFieldChange={(fieldName, fieldValue) => this.validateAndSetRegistrationCode(fieldName, fieldValue)}
            label={Liferay.Language.get("registration-code")}
            placeholder={Liferay.Language.get("registration-code")}
            value={this.state.registrationCode}
          />

          <div className="registration-code-link">

            <a data-toggle="collapse" role="button" aria-expanded="false" aria-controls="collapseExample"
               className="collapsed secondary" href="#helpText">
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

  formPage2() {
    const { isDivisionAgentNumberEmpty } = this.state;
    const isSubmitEnabled = !isDivisionAgentNumberEmpty;

    return (
      <div className="user-registration user-registration-2">
        <div className="h2 font-weight-bold pb-4">{Liferay.Language.get("confirm-your-identity")}</div>
        <form ref={this.form2} onSubmit={(e) => this.onFormPage2Submit(e)}>
          <Input
            fieldName="divisionAgentNumber"
            handleFieldChange={(fieldName, fieldValue) => this.validateAndSetDivisionAgentNumber(fieldName, fieldValue)}
            label={Liferay.Language.get("division-agent-number")}
            placeholder={Liferay.Language.get("division-agent-number")}
            value={this.state.divisionAgentNumber}
          />

          <Input
            fieldName="businessZipCode"
            handleFieldChange={(fieldName, fieldValue) => this.setStateField(fieldName, fieldValue)}
            label={Liferay.Language.get("business-zip-code")}
            placeholder={Liferay.Language.get("business-zip-code")}
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
    if (!this.state.isFormPage1Submitted) {
      return this.formPage1()
    }
    else {
      return this.formPage2()
    }
  }
}

export default UserRegistration;