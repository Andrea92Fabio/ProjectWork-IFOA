import { observer } from './home.js';
import { isDrinkingAge } from './age-check.js';
import thankYou from './thank-you.js';
import errorView from './error-view.js';

const view = document.querySelector('#view-form');
const formElement = view.querySelector('#main-form');

export const name = formElement.querySelector('#form-name');
const surname = formElement.querySelector('#form-surname');
export const email = formElement.querySelector('#form-email');
const birthdate = formElement.querySelector('#form-birthdate');
const gender = formElement.querySelector('#form-gender');
const residencyCountry = formElement.querySelector('#form-residency-country');
const residencyAddress = formElement.querySelector('#form-residency-address');
const residencyZipCode = formElement.querySelector('#form-residency-zip-code');
const shippingCountry = formElement.querySelector('#form-shipping-country');
const shippingAddress = formElement.querySelector('#form-shipping-address');
const shippingZipCode = formElement.querySelector('#form-shipping-zip-code');
const fiscalCode = formElement.querySelector('#form-fiscal-code');

//todo:add provincia for shipping and residency and add phonenumber
const user = {
    name: null,
    surname: null,
    gender: null,
    email: null,
    residencyCountry: null,
    fiscalCode: null,
    residencyZipCode: null,
    residencyAddress: null,
    shipCountry: null,
    shipZipCode: null,
    shipAddress: null,
    privacy: true,
    rules: true,
    birthDate: null,
    phoneNumber: 3923936702,
};

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
    formElement.addEventListener('submit', async (e) => {
        e.preventDefault();

        if (validateForm()) {
            user.name = name.value;
            user.surname = surname.value;
            user.gender = gender.value;
            user.email = email.value;
            user.residencyCountry = residencyCountry.value;
            user.fiscalCode = fiscalCode.value;
            user.residencyZipCode = residencyZipCode.value;
            user.residencyAddress = residencyAddress.value;
            user.shipCountry = shippingCountry.value;
            user.shipZipCode = shippingZipCode.value;
            user.shipAddress = shippingAddress.value;
            user.birthDate = birthdate.value;

            const jsonUser = JSON.stringify(user);
            console.log(jsonUser);

            try {
                const res = await fetch(
                    'http://192.168.100.30/api/submission',
                    {
                        method: 'POST',
                        body: jsonUser,
                        headers: {
                            'Content-type': 'application/json',
                        },
                    }
                );

                if (res.status > 201) {
                    const error = await res.json();
                    const code = error.error;
                    throw new Error(code);
                }
            } catch (error) {
                view.classList.remove('active');

                console.error('Error message', error.message);

                errorView();

                return;
            }

            view.classList.remove('active');
            thankYou();
        }
    });
    //todo: add rules and privacy checker and add it on front end
    function validateForm() {
        const errors = new Map();

        errors.set('form-name', null);
        errors.set('form-surname', null);
        errors.set('form-email', null);
        errors.set('form-birthdate', null);
        errors.set('form-gender', null);
        errors.set('form-residency-country', null);
        errors.set('form-residency-address', null);
        errors.set('form-residency-zip-code', null);
        errors.set('form-fiscal-code', null);
        errors.set('form-shipping-country', null);
        errors.set('form-shipping-address', null);
        errors.set('form-shipping-zip-code', null);

        const checkerMap = new Map(errors);

        const birthdateObject = new Date(
            formElement.querySelector('#form-birthdate').value
        );

        if (!isDrinkingAge(birthdateObject)) {
            errors.set(
                'form-birthdate',
                'Devi essere maggiorenne per partecipare'
            );
        }
        if (!validateStringNotEmptyOrWhitespaceOnly(name.value)) {
            errors.set('form-name', 'Il nome inserito non è valido');
        }

        if (!validateStringNotEmptyOrWhitespaceOnly(surname.value))
            errors.set('form-surname', 'Il cognome inserito non è valido');

        //Inserire email
        if (!validateEmail(email.value)) {
            errors.set('form-email', `L'email inserita non è valida`);
        }
        //inserire paese residenza
        if (!validateCountry(residencyCountry.value)) {
            errors.set(
                'form-residency-country',
                `Il paese di residenza inserito non è valido`
            );
        }

        if (!validateStringNotEmptyOrWhitespaceOnly(residencyAddress.value))
            errors.set(
                'form-residency-address',
                `L'indirizzo di residenza inserito non è valido`
            );

        if (!validateStringNotEmptyOrWhitespaceOnly(shippingAddress.value))
            errors.set(
                'form-shipping-address',
                `L'indirizzo di spedizione inserito non è valido`
            );
        //cap 5 figures
        if (!validateZipCode(residencyZipCode.value)) {
            errors.set(
                'form-residency-zip-code',
                `Il CAP che hai inserito non è valido`
            );
        }
        //Codice fiscale
        if (!validateFiscalCode(fiscalCode.value, residencyCountry.value))
            errors.set(
                'form-fiscal-code',
                `Hai inserito un codice fiscale sbagliato`
            );
        //
        if (!validateZipCode(shippingZipCode.value)) {
            errors.set(
                'form-shipping-zip-code',
                `Il CAP che hai inserito non è valido`
            );
        }
        if (!validateCountry(shippingCountry.value)) {
            errors.set(
                'form-shipping-country',
                `Il paese di spedizione che hai inserito non è valido`
            );
        }

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
        console.log(checkerMap);

        let isValidForm = true;

        //todo: delete checker map and set is valid form inside the if statements
        checkerMap.forEach((el, key) => {
            if (checkerMap.get(key) != errors.get(key)) {
                isValidForm = false;
            }
        });

        return isValidForm;
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

function validateStringNotEmptyOrWhitespaceOnly(s) {
    if (s === null || s.replace(' ', '') === '') {
        return false;
    }
    return true;
}

function validateZipCode(s) {
    const reg = /^[\d]{5}/;
    if (reg.test(s)) {
        return true;
    }
    return false;
}

function validateFiscalCode(s, t) {
    if (t === 'san marino') {
        return true;
    }

    if (s.length === 16 || s.length === 17) {
        return true;
    }
    return false;
}

function validateEmail(s) {
    const reg = /^[a-zA-Z0-9]{1}[a-zA-Z0-9.]+@[a-zA-Z0-9.]+[.]{1}[a-zA-Z]{2,}$/; //tiberiu.melinescu  @gmail .com
    if (reg.test(s)) {
        return true;
    }
    return false;
}

function validateCountry(s) {
    if (s === 'italy' || s === 'san marino') {
        return true;
    }
    return false;
}
