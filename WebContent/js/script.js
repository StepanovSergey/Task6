/*Validate product form*/
function validateProduct(form) {
	clearAllErrorMessages(form);
	var element;
	var nameTagPattern = "attributes";
	var producerTagPattern = "producer";
	var modelTagPattern = "model";
	var dateTagPattern = "date";
	var priceTagPattern = "price";
	var colorTagPattern = "color";
	var notInStockTagPattern = "not_in_stock";
	var checkboxTagName = "checkboxes";
	var modelPattern = "^(([A-Za-zÀ-ßà-ÿ¨¸]){2}([0-9]){3})$";
	var datePattern = "^((0[1-9]|[1-2][0-9]|3[0-1])-(0[1-9]|1[0-2])-(19[7-9][0-9]|2[0-2][0-9][0-9]))$";
	var pricePattern = "^([1-9]([0-9]*)|([0-9]*\.[0-9]{1,2}))$";
	var colorPattern = "^[A-Za-zÀ-ßà-ÿ¨¸]+$";
	var lineNumber = 1;
	var priceElement = null;

	for ( var i = 0; i < form.elements.length; i++) {
		element = form.elements[i];
		elementName = element.name;
		if (test(elementName, nameTagPattern)) {
			validate(element, lineNumber, "Name must be specified");
		}
		if (test(elementName, producerTagPattern)) {
			validate(element, lineNumber, "Producer must be specified");
		}
		if (test(elementName, modelTagPattern)) {
			validateByPattern(element, modelPattern, lineNumber,
					"Model must contain 2 letters and 3 digits");
		}
		if (test(elementName, dateTagPattern)) {
			validateByPattern(element, datePattern, lineNumber,
					"Date must be dd-MM-yyyy");
		}
		if ((test(elementName, priceTagPattern))
				|| (test(elementName, notInStockTagPattern))) {
			priceElement = element;
		}
		if (test(elementName, colorTagPattern)) {
			validateByPattern(element, colorPattern, lineNumber,
					"Color must be letters");
		}
		if (elementName == checkboxTagName) {
			if (!(element.checked)) {
				validateByPattern(priceElement, pricePattern, lineNumber,
						"Price must be e.g 14, 10.0 or 17.12");
			}
			lineNumber++;
		}
	}
	var size = document.getElementById('error_div').innerHTML.length;
	if (size == 0) {
		return true;
	} else {
		return false;
	}

}

function validate(element, lineNumber, errorMessage) {
	if (element.value == "") {
		showError(lineNumber, errorMessage);
		element.style.backgroundColor = "#d0d0d0";
	}
}

function validateByPattern(element, pattern, lineNumber, errorMessage) {
	var expr = new RegExp(pattern);
	var elementValue = element.value;
	if (!(elementValue.match(expr))) {
		element.style.backgroundColor = '#FF4F4F';
		showError(lineNumber, errorMessage);
	}
}

function test(value, pattern) {
	var expr = new RegExp(pattern);
	if (value.match(expr)) {
		return true;
	} else {
		return false;
	}
}

/* Show error message on add product page in hidden div */
function showError(lineNumber, errorMessage) {
	var elementName = 'error_div';
	errorMessage = 'Line ' + lineNumber + ': ' + errorMessage + '<br>';
	document.getElementById(elementName).innerHTML += errorMessage;
}

/* Clean error message on add product page in hidden div */
function clearAllErrorMessages(form) {
	var elementName = 'error_div';
	document.getElementById(elementName).innerHTML = '';
	for ( var i = 0; i < form.elements.length; i++) {
		var element = form.elements[i];
		if (element.type == "text") {
			element.style.backgroundColor = "";
		}
	}
}