function handleCredentialResponse(response) { //Dados de resposta;
    const data = jwt_decode(response.credential);

    userPefil.setAttribute("src", data.picture);
    userName.textContent = data.name;
}

window.onload = function () {
    google.accounts.id.initialize({
        client_id: "990912109947-7c5j3kepg8nq3adh0ahg28cogkgepkhp.apps.googleusercontent.com",
        callback: handleCredentialResponse
    });

    google.accounts.id.renderButton(
        document.getElementById("buttonGoogle"), {
            theme: "outline",
            size: "large",
            type: "standard",
            shape:"pill",
            theme: "outline",
            text: "continue_with",
            size: "large",
            logo_alignment: "left",
            width: "223"
        }
    );

    google.accounts.id.prompt();
}