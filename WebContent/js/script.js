function validateAddProductForm(form) {
	var element, elementName, elementValue;
	var isProductValid = true;
	var modelPattern = "^(([A-Za-zР-пр-џЈИ]){2}([0-9]){3})$";
	var datePattern = "^((0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-(19[7-9][0-9]|2[0-2][0-9][0-9]))$";
	var pricePattern = "^([1-9])(\\d*)|(\\d*\\.\\d{1,2})$";
	var colorPattern = "^[A-Za-zР-пр-џЈИ]+$";
	for ( var i = 0; i < form.elements.length; i++) {
		element = form.elements[i];
		elementName = element.nodeName.toLowerCase();
		elementValue = element.value;
		if (element.name == "product.producer") {
			if (elementValue == "") {
				isProductValid = false;
				showError("producer", "Producer must be specified");
			} else {
				clearError("producer");
			}
		}
		if (element.name == "product.model") {
			var expr = new RegExp(modelPattern);
			if (!(elementValue.match(expr))) {
				isProductValid = false;
				showError("model", "Model must contain 2 letters and 3 digits");
			} else {
				clearError("model");
			}
		}
		if (element.name == "product.color") {
			var expr = new RegExp(colorPattern);
			if (!(elementValue.match(expr))) {
				isProductValid = false;
				showError("color", "Color must be specified");
			} else {
				clearError("color");
			}
		}

		if (element.name == "product.price") {
			var expr = new RegExp(pricePattern);
			if (!(elementValue.match(expr))) {
				isProductValid = false;
				showError("price", "Price must be e.g 14, 10.0 or 17.12");
			} else {
				clearError("price");
			}
		}
		if (element.name == "product.dateOfIssue") {
			var expr = new RegExp(datePattern);
			if (!(elementValue.match(expr))) {
				isProductValid = false;
				showError("date", "Date must be dd-MM-yyyy");
			} else {
				clearError("date");
			}
		}
		if (element.name == "product.notInStock") {
			isProductValid = false;
			alert("Not in stock: " + elementValue);
		}
	}
	if (isProductValid) {
		return true;
	} else {
		return false;
	}
}

function showError(fieldName, errorMessage) {
	var elementName = "invalid_" + fieldName;
	document.getElementById(elementName).style.display = '';
	document.getElementById(elementName).innerHTML = errorMessage;
}

function clearError(fieldName) {
	var elementName = "invalid_" + fieldName;
	document.getElementById(elementName).style.display = '';
	document.getElementById(elementName).innerHTML = "";
}