import React from 'react';
import ReactDOM from 'react-dom';

const mainContent = document.getElementById('main-content');

class CardPageHeader extends React.Component {
	constructor(props) {
		super(props);

		this.el = document.createElement('div');
		this.el.classList.add('card-page-header');
	}

	componentDidMount() {
		mainContent.insertBefore(this.el, mainContent.childNodes[0]);
	}

	componentWillUnmount() {
		mainContent.removeChild(this.el);
	}

	render() {
		return ReactDOM.createPortal(
			this.props.children,
			this.el
		);
	}
}

export default CardPageHeader;