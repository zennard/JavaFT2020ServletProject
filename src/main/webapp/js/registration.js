const check = function () {
    const passwordValue = document.getElementById('inputPassword').value;
    const repeatedPasswordValue = document.getElementById('confirmPassword').value;
    const fieldsEmpty = passwordValue === "" && repeatedPasswordValue === "";
    if (passwordValue == repeatedPasswordValue || fieldsEmpty) {
        document.getElementById('message').innerHTML = '';
        document.getElementById('submit').disabled = false;
    } else {
        document.getElementById('message').style.color = 'red';
        document.getElementById('message').innerHTML = 'Not matching';
        document.getElementById('submit').disabled = true;
    }
}