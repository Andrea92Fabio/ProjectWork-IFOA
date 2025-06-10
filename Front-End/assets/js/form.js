import { observer } from './home.js';
import { isDrinkingAge } from './age-check.js';

const view = document.querySelector('#view-form');
const formElement = view.querySelector('#main-form');

const name = formElement.querySelector('#form-name');
const surname = formElement.querySelector('#form-surname');
const email = formElement.querySelector('#form-email');
const birthdate = formElement.querySelector('#form-birthdate');
const gender = formElement.querySelector('#form-gender');
const residencyCountry = formElement.querySelector('#form-residency-country');

export default function form() {
    view.classList.add('active');
    view.removeAttribute('aria-hidden');
    view.removeAttribute('inert');

    const inputFields = formElement.querySelectorAll('.input-field');

    view.querySelector('#form-birthdate').value = sessionStorage
        .getItem('birthdate')
        .split('T')[0];

    inputFields.forEach((el) => {
        errors[el.id] = '';
    });
    //todo: implement an extra check on submit

    formElement.addEventListener('submit', (e) => {
        e.preventDefault();

        console.log(validateForm());
    });

    function validateForm() {
        const errors = new Map();

        errors.set('form-birthdate', null);
        errors.set('form-name', null);
        errors.set('form-surname', null);

        const birthdateObject = new Date(
            formElement.querySelector('#form-birthdate').value
        );

        if (!isDrinkingAge(birthdateObject)) {
            errors.set(
                'form-birthdate',
                'Devi essere maggiorenne per partecipare'
            );
        }
        if (!validateString(name.value))
            errors.set('form-name', 'Il nome inserito non è valido');

        if (!validateString(surname.value))
            errors.set('form-surname', 'Il cognome inserito non è valido');
        //cap 5 figures
        //

        errors.forEach((el, key) => {
            if (el) {
                formElement.querySelector(`#${key}-errors`).textContent = el;
                formElement
                    .querySelector(`#${key}-wrapper`)
                    .classList.remove('success');
                formElement
                    .querySelector(`#${key}-wrapper`)
                    .classList.add('error');
            } else {
                formElement.querySelector(`#${key}-errors`).textContent = '';
                formElement
                    .querySelector(`#${key}-wrapper`)
                    .classList.remove('error');
                formElement
                    .querySelector(`#${key}-wrapper`)
                    .classList.add('success');
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

function validateString(s) {
    if (s === null || s.replace(' ', '') === '') {
        return false;
    }
    return true;
}
