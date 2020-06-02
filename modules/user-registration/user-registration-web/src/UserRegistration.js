import ClayButton from "@clayui/button";
import ClayForm, {ClayInput} from "@clayui/form";
import React from 'react';
import ReactDOM from "react-dom";
import UserRegistration2 from "./UserRegistration2";

class UserRegistration extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      isRegistrationCodeEmpty: true
    }

    this.form = React.createRef();
  }

  onSubmit(e) {
    e.preventDefault();

    //TODO CMIC-247 additional validation for registration code
    if (this.state.isRegistrationCodeEmpty) {
      return false;
    }

    this.submit();
  }

  submit() {
    //TODO CMIC-246 handle submit correctly with web services
    let props = this.props;

    ReactDOM.render(
      <UserRegistration2
        portletNamespace={props.portletNamespace}
        contextPath={props.contextPath}
        portletElementId={props.portletElementId}
      />,
      document.getElementById(props.portletElementId)
    );
  }

  validateRegistrationCode(e) {
    this.setState({ isRegistrationCodeEmpty: e.target.value == "" });
  };

  render() {
    const { isRegistrationCodeEmpty } = this.state;
    const isSubmitEnabled = !isRegistrationCodeEmpty;

    return (
      <div className="user-registration">
        <div className="h2 font-weight-bold pb-4">{Liferay.Language.get("enter-registration-code")}</div>
        <ClayForm ref={this.form} onSubmit={(e) => this.onSubmit(e)}>
          <ClayForm.Group>
            <label htmlFor="divisionAgentNum">{Liferay.Language.get("registration-code")}</label>
            <ClayInput
              id="registrationCode"
              name="registrationCode"
              onChange={(e) => this.validateRegistrationCode(e)}
              placeholder="Registration Code"
              type="text"
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
}

export default UserRegistration;