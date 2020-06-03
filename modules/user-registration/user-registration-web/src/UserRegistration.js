import ClayButton from "@clayui/button";
import ClayForm, {ClayInput} from "@clayui/form";
import React from 'react';

class UserRegistration extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      isFormPage1Submitted: false,
      isDivisionAgentNumberEmpty: true,
      isRegistrationCodeEmpty: true
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

  submitFormPage1() {
    //TODO CMIC-246 handle submit correctly with web services
    this.setState({ isFormPage1Submitted: true });
  }

  submitFormPage2() {
    //TODO CMIC-246 handle submit correctly with web services
  }

  validateDivisionAgentNumber(e) {
    this.setState({ isDivisionAgentNumberEmpty: e.target.value == "" });
  };

  validateRegistrationCode(e) {
    this.setState({ isRegistrationCodeEmpty: e.target.value == "" });
  };

  formPage1() {
    const { isRegistrationCodeEmpty } = this.state;
    const isSubmitEnabled = !isRegistrationCodeEmpty;

    return (
      <div className="user-registration">
        <div className="h2 font-weight-bold pb-4">{Liferay.Language.get("enter-registration-code")}</div>
        <ClayForm ref={this.form1} onSubmit={(e) => this.onFormPage1Submit(e)}>
          <ClayForm.Group>
            <label htmlFor="registrationCode">{Liferay.Language.get("registration-code")}</label>
            <ClayInput
              id="registrationCode"
              name="registrationCode"
              onChange={(e) => this.validateRegistrationCode(e)}
              placeholder="Registration Code"
              type="text"
              value=""
            />
          </ClayForm.Group>

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
        </ClayForm>
      </div>
    );
  }

  formPage2() {
    const { isDivisionAgentNumberEmpty } = this.state;
    const isSubmitEnabled = !isDivisionAgentNumberEmpty;

    return (
      <div className="user-registration">
        <div className="h2 font-weight-bold pb-4">{Liferay.Language.get("confirm-your-identity")}</div>
        <ClayForm ref={this.form2} onSubmit={(e) => this.onFormPage2Submit(e)}>
          <ClayForm.Group>
            <label htmlFor="divisionAgentNumber">{Liferay.Language.get("division-agent-number")}</label>
            <ClayInput
              id="divisionAgentNumber"
              name="divisionAgentNumber"
              onChange={(e) => this.validateDivisionAgentNumber(e)}
              placeholder="Division Agent #"
              type="text"
              value=""
            />
          </ClayForm.Group>

          <ClayForm.Group>
            <label htmlFor="businessZipCode">{Liferay.Language.get("business-zip-code")}</label>
            <ClayInput
              id="businessZipCode"
              name="businessZipCode"
              placeholder="Business Zip Code"
              type="text"
              value=""
            />
          </ClayForm.Group>

          <ClayButton disabled={!isSubmitEnabled} displayType="primary" type="submit">
            {Liferay.Language.get("finish")}
          </ClayButton>
        </ClayForm>
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