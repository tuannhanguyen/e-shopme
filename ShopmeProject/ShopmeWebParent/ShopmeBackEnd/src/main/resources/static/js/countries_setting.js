var buttonLoad;
var dropDowmnCountry;
var buttonAddCountry;
var buttonUpdateCountry;
var buttonDeleteCountry;
var labelCountryName;
var fieldCountryName;
var fieldCountryCode;

$(document).ready(function() {
	buttonLoad = $("#buttonLoad");
	dropDowmnCountry = $("#dropDownCountries");
	buttonAddCountry = $("#buttonAddCountry");
	buttonUpdateCountry = $("#buttonUpdateCountry");
	buttonDeleteCountry = $("#buttonDeleteCountry");
	labelCountryName = $("#labelCountryName");
	fieldCountryName = $("#fieldCountryName");
	fieldCountryCode = $("#fieldCountryCode");

	buttonLoad.on('click', function() {
		loadCountries();
	})

	dropDowmnCountry.on('change', function() {
		changeFormStateToSelectedCountry();
	});
	
	buttonAddCountry.click(function(){
		if(buttonAddCountry.val() == "Add"){
			addCountry();
		} else{
			changeFormStateToNew();	
		}
	});
	
	buttonUpdateCountry.click(function(){
		updateCountry();
	});
	
	buttonDeleteCountry.click(function(){
		deleteCountry();
	});
});

function validateFormCountry(){
	formCountry = document.getElementById("formCountry");
	if(!formCountry.checkValidity()){
		formCountry.reportValidity();
		return false;
	}
	return true;
}

function addCountry(){
	url = contextPath + "countries/save";
	countryName = fieldCountryName.val();
	countryCode = fieldCountryCode.val();
	jsonData = {name: countryName, code: countryCode};
	
	if(!validateFormCountry()){
		return;
	}
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(res){
		selectNewlyAddedCountry(res, countryCode, countryName);
		showToastMessage("The new Country has been added");
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to sever or server encoutered an error");
	});
}

function updateCountry(){
	url = contextPath + "countries/save";
	
	countryId = $("#dropDownCountries option:selected").val().split("-")[0];
	countryName = fieldCountryName.val();
	countryCode = fieldCountryCode.val();
	
	jsonData = {id: countryId, name: countryName, code: countryCode};
	
	if(!validateFormCountry()){
			return;
		}
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(res){
		$("#dropDownCountries option:selected").text(countryName);
		$("#dropDownCountries option:selected").val(res + "-" + countryCode);
		showToastMessage("The Country has been updated");
		
		changeFormStateToNew();
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to sever or server encoutered an error");
	});
}

function deleteCountry(){
	countryId = dropDowmnCountry.val().split("-")[0];
	url = contextPath  + "countries/delete/" + countryId;
	
	$.ajax({
		type: "DELETE",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function() {
		showToastMessage("The Country has been deleted");
		loadCountries();
		changeFormStateToNew();
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to sever or server encoutered an error");
	});
	
}

function selectNewlyAddedCountry(countryId, countryCode, countryName){
	optionValue = countryId + "-" + countryCode;
	$("<option>").val(optionValue).text(countryName).appendTo(dropDowmnCountry);
	
	$("#dropDownCountries option[value='" + optionValue + "']").prop("selected", true);
	
	fieldCountryCode.val("");
	fieldCountryName.val("").focus();
}

function changeFormStateToNew(){
	buttonAddCountry.val("Add");
	labelCountryName.text("Country Name:");
	
	buttonUpdateCountry.prop('disabled', true);
	buttonDeleteCountry.prop('disabled', true);
	
	fieldCountryName.val("").focus();
	fieldCountryCode.val("");
	
}

function changeFormStateToSelectedCountry(){
	buttonAddCountry.attr("value", "New");
	buttonUpdateCountry.prop('disabled', false);
	buttonDeleteCountry.prop('disabled', false);
	
	selectedCountryName = $("#dropDownCountries option:selected").text();
	fieldCountryName.val(selectedCountryName);
	
	labelCountryName.text("Selected Country:");
	
	countryCode = dropDowmnCountry.val().split("-")[1];
	fieldCountryCode.val(countryCode);
}

function loadCountries() {
	url = contextPath + "countries/list";
	$.get(url, function(responseJson) {
		dropDowmnCountry.empty();

		$.each(responseJson, function(index, country) {
			optionValue = country.id + "-" + country.code;
			$("<option>").val(optionValue).text(country.name).appendTo(dropDowmnCountry);
		})
	}).done(function() {
		buttonLoad.attr("value", "Refresh Country List");
		showToastMessage("All countries have been loaded");
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to sever or server encoutered an error");
	});
}

function showToastMessage(message) {
	$("#toastMessage").text(message);
	$(".toast").toast('show');
}