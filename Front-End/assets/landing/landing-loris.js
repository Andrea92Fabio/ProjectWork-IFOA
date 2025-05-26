const observer = new IntersectionObserver(
  (entries) => {
    entries.forEach((entry) => {
      if (entry.isIntersecting) {
        entry.target.classList.add("show");
      }
    });
  },
  {
    rootMargin: "-40% 0px",
  }
);

const cards = document.querySelectorAll(".card");

cards.forEach((c) => observer.observe(c));
