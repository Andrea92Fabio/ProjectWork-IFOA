export default function home() {
  const view = document.querySelector("#view-home");
  view.removeAttribute("aria-hidden");
  view.removeAttribute("inert");
  const observer = new IntersectionObserver(
    (entries) => {
      entries.forEach((entry) => {
        if (entry.isIntersecting) {
          entry.target.classList.add("show");
        }
      });
    },
    {
      rootMargin: "0px 0px -40% 0px",
    }
  );

  const cards = document.querySelectorAll(".card");

  const fadeInLeft = document.querySelectorAll(".fade-in-left");
  const fadeInTop = document.querySelectorAll(".fade-in-top");
  const fadeInBottom = document.querySelectorAll(".fade-in-bottom");
  const fadeInRight = document.querySelectorAll(".fade-in-right");

  const animatableOnEntranceObjs = [
    ...fadeInLeft,
    ...fadeInTop,
    ...fadeInBottom,
    ...fadeInRight,
  ];

  cards.forEach((c) => observer.observe(c));
  animatableOnEntranceObjs.forEach((el) => observer.observe(el));
}
