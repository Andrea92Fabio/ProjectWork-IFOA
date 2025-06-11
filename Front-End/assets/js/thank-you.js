import { name, email } from './form.js';

const view = document.querySelector('#view-thank-you');

export default function thankYou() {
    view.classList.add('active');
    view.removeAttribute('aria-hidden');
    view.removeAttribute('inert');

    const thankYouName = document.querySelector('#thank-you-name');
    const thankYouEmail = document.querySelector('#thank-you-email');

    thankYouName.textContent = name.value;
    thankYouEmail.textContent = email.value;
}
