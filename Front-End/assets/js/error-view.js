import { name } from './form.js';

const view = document.querySelector('#view-form-error');

export default function errorView() {
    view.classList.add('active');
    view.removeAttribute('aria-hidden');
    view.removeAttribute('inert');

    const errorName = document.querySelector('#error-name');

    errorName.textContent = name.value;
}
