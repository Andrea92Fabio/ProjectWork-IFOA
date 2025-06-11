const params = new URLSearchParams(window.location.search);
const tokenId = params.get('tokenId');
getResultFromServer(tokenId);

async function getResultFromServer(tokenId) {
    const response = await fetch(
        `http://localhost:80/api/confirmation/${tokenId}`
    );

    console.log(response);
}
