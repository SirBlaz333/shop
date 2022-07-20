let id = (id) => document.getElementById(id);

let username = id("firstname"),
  lastname = id("lastname"),
  email = id("email"),
  password = id("password"),
  form = id("registration");

let show = (id) => {
    document.getElementById(id).style.display = "block";
}

let hide = (id) => {
    document.getElementById(id).style.display = "none";
}

$(document).ready(function() {
    $('.error').hide();
});

var formIsCorrect;

form.addEventListener("submit", (e) => {
  formIsCorrect = true;
  e.preventDefault();
  var register = window.location.search.substr(1);
  register = register.split("&")[0].trim();
  if(register === "register"){
    validateFirstname();
    validateLastname();
  }
  validateEmail();
  validatePassword();
  if(formIsCorrect === true){
    form.submit();
  }
});

let validateFirstname = () => {
    if(firstname.value.trim() === ""){
        show("firstname-error");
        formIsCorrect = false;
    } else {
        hide("firstname-error");
    }
}

let validateLastname = () => {
    if(lastname.value.trim() === ""){
        show("lastname-error");
        formIsCorrect = false;
    } else {
        hide("lastname-error");
    }
}


let validatePassword = () => {
    if(password.value.trim() === ""){
        show("password-error");
        formIsCorrect = false;
    } else {
        if(password.value.trim().length < 5){
            show("password-length-error");
            formIsCorrect = false;
        }
        else {
            hide("password-error");
            hide("password-length-error");
        }
    }
}

let regex = new RegExp("([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\"\(\[\]!#-[^-~ \t]|(\\[\t -~]))+\")@([!#-'*+/-9=?A-Z^-~-]+(\.[!#-'*+/-9=?A-Z^-~-]+)*|\[[\t -Z^-~]*])");

let validateEmail = () => {
    if(regex.test(email.value.trim()) === false){
        show("email-error");
        formIsCorrect = false;
    } else {
        hide("email-error");
    }
}
