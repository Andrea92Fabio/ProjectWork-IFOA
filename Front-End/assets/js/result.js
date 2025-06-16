const params = new URLSearchParams(window.location.search);
const tokenId = params.get('tokenId');
getResultFromServer(tokenId);

async function getResultFromServer(tokenId) {
    const response = await fetch(
        `http://192.168.100.30:80/api/confirmation/${tokenId}`
    );

    const json = await response.json();

    alert(json.message);
    console.log(response);
}
