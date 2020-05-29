import React from 'react';
import ReactDOM from 'react-dom';

import Typography from './components/typography';
import Colors from './components/colors';

export default class extends React.Component {
	render() {
		return (
			<React.Fragment>
				<Typography />
				<Colors />
			</React.Fragment>
		);
	}	
}