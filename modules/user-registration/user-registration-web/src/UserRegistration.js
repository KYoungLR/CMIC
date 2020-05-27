import React from 'react';
import ClayButton from "@clayui/button";
import ClayForm, {ClayInput} from "@clayui/form";
import ClayLink from '@clayui/link';

class UserRegistration extends React.Component {

  constructor(props) {
    super(props);
  }

  render() {
    return (
      <div className="user-registration">
        <h1>Confirm Your Identity</h1>
        <ClayForm.Group>
          <ClayInput
            id="registrationCode"
            name="registrationCode"
            placeholder="Registration Code"
            type="text"
          />
          <div className="registration-code-link">
            <ClayLink displayType="secondary" href="#">
              {Liferay.Language.get("cant-find-your-registration-code")}
            </ClayLink>
          </div>
          <ClayInput
            id="divisionAgentNumber"
            name="divisionAgentNumber"
            type="text"
            placeholder="Division Agent #"
          />
          <ClayButton displayType="primary">
            {Liferay.Language.get("sign-up")}
          </ClayButton>
        </ClayForm.Group>
      </div>
    );
  }
}

export default UserRegistration;