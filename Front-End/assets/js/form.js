import { observer } from './home.js';
import { isDrinkingAge } from './age-check.js';

export default function form() {
    const view = document.querySelector('#view-form');
    view.classList.add('active');
    view.removeAttribute('aria-hidden');
    view.removeAttribute('inert');

    const form = view.querySelector('#main-form');

    const inputFields = form.querySelectorAll('.input-field');

    view.querySelector('#form-birthdate').value = sessionStorage
        .getItem('birthdate')
        .split('T')[0];

    inputFields.forEach((el) => {
        errors[el.id] = '';
    });
    //todo: implement an extra check on submit

    form.addEventListener('submit', (e) => {
        e.preventDefault();

        const bdayObj = new Date(form.querySelector('#form-birthdate'));

        console.log(validateForm());
    });

    function validateForm() {
        const errors = new Map();

        errors.set('form-birthdate', null);
        errors.set('form-name', null);

        const bdayObj = new Date(form.querySelector('#form-birthdate').value);

        if (!isDrinkingAge(bdayObj)) {
            errors.set(
                'form-birthdate',
                'Devi essere maggiorenne per partecipare'
            );
        }

        //cap 5 figures
        //

        errors.forEach((el, key) => {
            if (el) {
                form.querySelector(`#${key}-errors`).textContent = el;
                form.querySelector(`#${key}-wrapper`).classList.remove(
                    'success'
                );
                form.querySelector(`#${key}-wrapper`).classList.add('error');
            } else {
                form.querySelector(`#${key}-errors`).textContent = '';
                form.querySelector(`#${key}-wrapper`).classList.remove('error');
                form.querySelector(`#${key}-wrapper`).classList.add('success');
            }
        });

        console.log(errors);

        return errors.size == 0;
    }

    //todo: todo: send data as json

    const animatableOnEntranceObjs = [
        ...view.querySelectorAll('.fade-in-left'),
        ...view.querySelectorAll('.fade-in-top'),
        ...view.querySelectorAll('.fade-in-right'),
        ...view.querySelectorAll('.fade-in-bottom'),
    ];

    animatableOnEntranceObjs.forEach((el) => observer.observe(el));
}
