import home from "./home.js";

export default function ageCheck() {
  const form = document.querySelector("#age-check-form");
  const ageInput = document.querySelector("#age-check-birthdate");

  const view = document.querySelector("#view-age-check");

  form.addEventListener("submit", (e) => {
    e.preventDefault();
    const date = new Date(ageInput.value);
    console.log(isDrinkingAge(date));

    if (isDrinkingAge(date)) {
      view.remove();
      home();
    }
  });
}

function isDrinkingAge(birthdate) {
  const today = new Date();

  if (today.getFullYear() - birthdate.getFullYear() > 18) {
    return true;
  } else if (today.getFullYear() - birthdate.getFullYear() < 18) {
    return false;
  } else if (today.getMonth() < birthdate.getMonth()) {
    return false;
  } else if (today.getMonth() > birthdate.getMonth()) {
    return true;
  }

  return today.getDate() >= birthdate.getDate();
}
