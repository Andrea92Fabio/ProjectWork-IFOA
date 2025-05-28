import { observer } from "./home.js";

export default function form() {
  const view = document.querySelector("#view-form");
  view.classList.add("active");
  view.removeAttribute("aria-hidden");
  view.removeAttribute("inert");

  const inputFields = view.querySelectorAll(".input-field");

  inputFields[0].value = sessionStorage.getItem("birthdate").split("T")[0];

  //todo: implement check on blur for input fields (maybe a function with a switch for the type of input)

  //todo: implement an extra check on submit

  //todo: todo: send data as json

  const animatableOnEntranceObjs = [
    ...view.querySelectorAll(".fade-in-left"),
    ...view.querySelectorAll(".fade-in-top"),
    ...view.querySelectorAll(".fade-in-right"),
    ...view.querySelectorAll(".fade-in-bottom"),
  ];

  animatableOnEntranceObjs.forEach((el) => observer.observe(el));
}
