const submitBtn = document.getElementById("form-submit")
const userField = document.getElementById("usuario");
const passField = document.getElementById("password");

userField.addEventListener("input", submitButtonEnabler)
passField.addEventListener("input", submitButtonEnabler)

  
function submitButtonEnabler() {
	const dniPattern = /^\d{8}[A-Z]$/;
	
	let validUser = dniPattern.test(userField.value);
	let validPass = passField.value.length >= 5;
	
	if (validUser && validPass) {
		submitBtn.disabled = false;
	} else {
		submitBtn.disabled = true;
	}
}
function resetForm() {
    userField.value = "";
    passField.value = "";
    submitBtn.disabled = true;
  }

  // 1) Reseteamos al cargar la página
  resetForm();

  // 2) Volvemos a resetear si la página se carga desde back/forward cache
  window.addEventListener("pageshow", event => {
    if (event.persisted) resetForm();
  });