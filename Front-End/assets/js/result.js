const params = new URLSearchParams(window.location.search);
const tokenId = params.get('tokenId');
const messageTarget = document.querySelector('#result-message');
const wrapper = document.querySelector('#result-wrapper');

displayResult(getResultFromServer(tokenId));

async function getResultFromServer(tokenId) {
    const response = await fetch(
        `http://192.168.100.45:80/api/confirmation/${tokenId}`
    );

    return await response.json();
}

function displayResult(result) {
    if (result === 'you win') {
        messageTarget.textContent = 'Complimenti! Hai vinto';
        wrapper.classList.add('win');
    } else {
        messageTarget.textContent =
            "Purtroppo sei arrivato tardi. Ma non abbatterti! Puoi sempre vincere l'estrazione finale";
    }
}
