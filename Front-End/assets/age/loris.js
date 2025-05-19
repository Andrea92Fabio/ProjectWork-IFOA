const form = document.querySelector('#wall-birthdate')
const inputsFields = document.querySelectorAll('.__input')

const day = document.getElementById("day")
const month = document.getElementById("month")
const year = document.getElementById("year")

// 🔒 Input restrictions
inputsFields.forEach((field) => {
    field.addEventListener('input', (e) => {
        e.target.value = e.target.value.replace(/\D/g, ""); // Digits only

        const maxLengths = {
            day: 2,
            month: 2,
            year: 4
        }

        const id = e.target.id
        const maxLength = maxLengths[id] || 4
        if (e.target.value.length > maxLength) {
            e.target.value = e.target.value.slice(0, maxLength)
        }
    });

    field.addEventListener("keydown", (e) => {
        if (e.key === ".") e.preventDefault();
    });
});

// 📆 Year logic
year.addEventListener('blur', (e) => {
    const value = parseInt(e.target.value)
    const today = new Date()
    const currentYear = today.getFullYear()

    if (!value || e.target.value.length < 4) {
        console.log("Invalid year")
        return
    }

    if (value > currentYear) {
        console.log("Future year not allowed.")
        return
    }

    const age = currentYear - value
    if (age > 18) {
        console.log("Definitely over 18 ✅")
    } else if (age < 18) {
        console.log("Definitely a minor ❌")
    } else {
        month.style.display = '' // Ask month if exactly 18 years
        console.log("Check month next...")
    }
})

// 📆 Month logic
month.addEventListener('blur', (e) => {
    const yearVal = parseInt(year.value)
    const monthVal = parseInt(month.value)
    const today = new Date()

    if (!monthVal || monthVal < 1 || monthVal > 12) {
        console.log("Invalid month")
        return
    }

    const age = today.getFullYear() - yearVal
    const currentMonth = today.getMonth() + 1 // JS is 0-indexed

    if (age > 18) {
        console.log("Definitely over 18 ✅")
    } else if (age < 18 || (age === 18 && monthVal > currentMonth)) {
        console.log("Still a minor ❌")
    } else if (monthVal < currentMonth) {
        console.log("Definitely over 18 ✅")
    } else if (monthVal === currentMonth) {
        day.style.display = ''
        console.log("Check day next...")
    }
})

// 📆 Day logic
day.addEventListener('blur', (e) => {
    const dayVal = parseInt(day.value)
    const monthVal = parseInt(month.value)
    const yearVal = parseInt(year.value)

    const today = new Date()
    const birthDate = new Date(yearVal, monthVal - 1, dayVal)

    // Validate real calendar date
    if (
        dayVal < 1 || dayVal > 31 ||
        birthDate.getMonth() !== monthVal - 1 ||
        birthDate.getDate() !== dayVal
    ) {
        console.log("Invalid date combination ❌")
        return
    }

    // Final age check
    const ageDifMs = today - birthDate
    const ageDate = new Date(ageDifMs)
    const finalAge = ageDate.getUTCFullYear() - 1970

    if (finalAge >= 18) {
        console.log("✅ Final age is 18 or older")
    } else {
        console.log("❌ Final age is under 18")
    }
})


