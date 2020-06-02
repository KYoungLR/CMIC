import ClayButton from "@clayui/button";
import ClayForm, {ClayInput} from "@clayui/form";
import React from 'react';

class UserRegistration2 extends React.Component {

  constructor(props) {
    super(props);
    this.state = {
      isDivisionAgentNumberEmpty: true
    }

    this.form = React.createRef();
  }

  onSubmit(e) {
    e.preventDefault();

    //TODO CMIC-247 additional validation
    if (this.state.isDivisionAgentNumberEmpty) {
      return false;
    }

    this.submit();
  }

  submit() {
    //TODO CMIC-246 handle submit correctly with web services
  }

  validateDivisionAgentNumber(e) {
    this.setState({ isDivisionAgentNumberEmpty: e.target.value == "" });
  };

  render() {
    const { isDivisionAgentNumberEmpty } = this.state;
    const isSubmitEnabled = !isDivisionAgentNumberEmpty;

    return (
      <div className="user-registration">
        <div className="h2 font-weight-bold pb-4">{Liferay.Language.get("confirm-your-identity")}</div>
        <ClayForm ref={this.form} onSubmit={(e) => this.onSubmit(e)}>
          <ClayForm.Group>
            <label htmlFor="divisionAgentNumber">{Liferay.Language.get("division-agent-number")}</label>
            <ClayInput
              id="divisionAgentNumber"
              name="divisionAgentNumber"
              onChange={(e) => this.validateDivisionAgentNumber(e)}
              placeholder="Division Agent #"
              type="text"
            />
          </ClayForm.Group>

          <ClayForm.Group>
            <label htmlFor="businessZipCode">{Liferay.Language.get("business-zip-code")}</label>
            <ClayInput
              id="businessZipCode"
              name="businessZipCode"
              placeholder="Business Zip Code"
              type="text"
            />
          </ClayForm.Group>

            <ClayButton disabled={!isSubmitEnabled} displayType="primary" type="submit">
              {Liferay.Language.get("finish")}
            </ClayButton>
        </ClayForm>
      </div>
    );
  }
}

export default UserRegistration2;