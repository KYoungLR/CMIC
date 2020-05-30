import React from 'react';
import ReactDOM from 'react-dom';

import Typography from './components/typography';
import Colors from './components/colors';
import Elevation from './components/elevation';

export default class extends React.Component {
	render() {
		return (
			<React.Fragment>
				<Typography />
				<Colors />
				<Elevation />
			</React.Fragment>
		);
	}	
}