import Component from 'metal-component';

class TestHarness extends Component {

	constructor(selector) {
		super();

		this._attachListeners(selector);
	}

	_attachListeners(selector) {
		let container = document.querySelector(selector);
		let buttons = container.querySelectorAll('.js-invoke');

		for (var i = 0; i < buttons.length; i++) {
			var button = buttons[i];

			button.addEventListener('click', event => {
				let currentTarget = event.currentTarget;
				let resourceURL = currentTarget.dataset.url;

				currentTarget.innerText = 'Loading...';

				fetch(resourceURL).then(response => {
					return response.json();
				}).then(data => {
					let responseContainer = container.querySelector('#response');

					if (responseContainer) {
						responseContainer.innerText = JSON.stringify(data, undefined, 4);
					}
				}).then(() => {
					currentTarget.innerText = 'Invoke';
				});
			});
		}
	}

}

export default TestHarness;