const view = document.querySelector('#view-thank-you');

export default function thankYou() {
    view.classList.add('active');
    view.removeAttribute('aria-hidden');
    view.removeAttribute('inert');
}
