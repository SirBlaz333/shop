// Document is ready
$(document).ready(function () {

    $('#firstname-error').hide();
    let firstnameError = true;
    $('#firstname').keyup(function () {
        validateFirstname();
    });

    function validateFirstname() {
        let firstname = $('#firstname').val();
        if (firstname.length == '') {
            $('#firstname-error').show();
            firstnameError = false;
        }
        else {
            $('#firstname-error').hide();
            firstnameError = true;
        }
    }


    $('#lastname-error').hide();
    let lastnameError = true;
    $('#lastname').keyup(function () {
        validateLastname();
    });

    function validateLastname() {
        let lastname = $('#lastname').val();
        if (lastname.length == '') {
            $('#lastname-error').show();
            lastnameError = false;
        }
        else {
            $('#lastname-error').hide();
            lastnameError = true;
        }
    }


    $('#email-error').hide();
    let emailError = true;
    $('#email').keyup(function () {
        validateEmail();
    });

    function validateEmail() {
        let email = $('#email').val();
        let regex = new RegExp("([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])");
        if (!regex.test(email)) {
            $('#email-error').show();
            emailError = false;
        }
        else {
            $('#email-error').hide();
            emailError = true;
        }
    }


    $('#password-error').hide();
    $('#password-length-error').hide();
    let passwordError = true;
    $('#password').keyup(function () {
        validatePassword();
    });

    function validatePassword() {
        let password = $('#password').val();
        if (password == '') {
            $('#password-error').show();
            $('#password-length-error').hide();
            passwordError = false;
        } else if (password.length < 5){
            $('#password-length-error').show();
            $('#password-error').hide();
            passwordError = false;
        }
        else {
            $('#password-error').hide();
            $('#password-length-error').hide();
            passwordError = true;
        }
    }


    $('button').click(function () {
        validateFirstname();
        validateLastname();
        validateEmail();
        validatePassword();
        if (firstnameError && lastnameError && emailError
         && passwordError){
            return true;
        } else {
            return false;
        }
    });
});
