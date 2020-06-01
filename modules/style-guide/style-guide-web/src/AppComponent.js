import React from 'react';
import ReactDOM from 'react-dom';

import Typography from './components/typography';
import Colors from './components/colors';
import Elevation from './components/elevation';
import Buttons from './components/buttons';

export default class extends React.Component {
	render() {
		return (
			<React.Fragment>
				<Typography />
				<Colors />
				<Elevation />
				<Buttons />
			</React.Fragment>
		);
	}	
}