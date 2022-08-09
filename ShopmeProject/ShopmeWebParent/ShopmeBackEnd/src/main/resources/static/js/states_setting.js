var buttonLoadCountriesForStates;
var dropDownCountriesForStates;
var dropDownStates;
var labelStateName;
var buttonAddState;
var buttonUpdateState;
var buttonDeleteState;
var fieldStateName;

$(document).ready(function(){
	buttonLoadCountriesForStates = $("#buttonLoadCountriesForStates");
	dropDownCountriesForStates = $("#dropDownCountriesForStates");
	dropDownStates = $("#dropDownStates");
	labelStateName = $("#labelStateName");
	fieldStateName = $("#fieldStateName");
	buttonAddState = $("#buttonAddState");
	buttonUpdateState = $("#buttonUpdateState");
	buttonDeleteState = $("#buttonDeleteState");
	
	buttonLoadCountriesForStates.click(function(){
		loadCountriesForStates();
	});
	
	dropDownCountriesForStates.on('change', function(){
		loadStatesByCountry();
	});
	
	dropDownStates.on('change', function(){
		changeFormStateToSelectedState();
	});
	
	buttonAddState.click(function(){
		if(buttonAddState.val() == "Add"){
			addState();
		} else{
			setFormStateToNew();	
		}
	});
	
	buttonUpdateState.click(function(){
		updateState();
	});
	
	buttonDeleteState.click(function(){
		deleteState();
	});
	
});

function validetaFormState(){
	formState= document.getElementById("formState");
	if(!formState.checkValidity()){
		formState.reportValidity();
		return false;
	}
	return true;
}

function addState(){
	url = contextPath + "states/save";
	stateName =  fieldStateName.val();
	country = $("#dropDownCountriesForStates option:selected");
	countryId = country.val();
	countryName = country.text(); 
	jsonData = {name: stateName, country: {id: countryId, name: countryName}};
	
	if(!validetaFormState()) return;
	
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(res){
		selectNewlyAddedState(res, fieldStateName.val());
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to sever or server encoutered an error");
	});
}

function updateState(){
	url = contextPath + "states/save";
	stateId = dropDownStates.val();
	stateName = fieldStateName.val();
	countryId = dropDownCountriesForStates.val();
	jsonData = {id: stateId, name: stateName, country: {id: countryId}};
	
	if(!validetaFormState()) return;
	
	$.ajax({
		type: "POST",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		},
		data: JSON.stringify(jsonData),
		contentType: 'application/json'
	}).done(function(res){
		$("#dropDownStates option[value='" + res + "']").text(stateName);
		setFormStateToNew();
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to sever or server encoutered an error");
	});
}

function deleteState(){
	stateId = dropDownStates.val();
	url = contextPath + "states/country/delete/" + stateId;
	
	$.ajax({
		type: "DELETE",
		url: url,
		beforeSend: function(xhr){
			xhr.setRequestHeader(csrfHeaderName, csrfValue);
		}
	}).done(function(){
		$("#dropDownStates option[value='"+ stateId +"']").remove();
		setFormStateToNew();
	});
}

function selectNewlyAddedState(stateId, stateName){
	$("<option>").val(stateId).text(stateName).appendTo(dropDownStates);
	$("#dropDownStates option[value='" + stateId + "']").prop("selected", true);
	fieldStateName.val("").focus();
}

function setFormStateToNew(){
	buttonAddState.val("Add");
	labelStateName.text("State Name:");
	
	fieldStateName.val("").focus();
	
	buttonUpdateState.prop('disabled', true);
	buttonDeleteState.prop('disabled', true);
}

function changeFormStateToSelectedState(){
	labelStateName.text("Selected State:");
	optionValue = $("#dropDownStates option:selected").text();
	fieldStateName.val(optionValue);
	
	buttonAddState.val("New");
	
	buttonUpdateState.prop('disabled', false);
	buttonDeleteState.prop('disabled', false);
}

function loadStatesByCountry(optionValue){
	optionValue = dropDownCountriesForStates.val();
	
	url = contextPath + "states/list_by_countries/" + optionValue;
	
	dropDownStates.empty();
	
	$.get(url, function(response){
		$.each(response, function(index, state){
			$("<option>").val(state.id).text(state.name).appendTo(dropDownStates);
		})
	})
}

function loadCountriesForStates(){
	
	url = contextPath + "countries/list";
	
	dropDownCountriesForStates.empty();
	
	$.get(url, function(response){
		$.each(response, function(index, country){
			$("<option>").val(country.id).text(country.name).appendTo(dropDownCountriesForStates);
		})
	}).done(function(){
		buttonLoadCountriesForStates.val("Refresh Country");
		showToastMessage("All countries have been loaded");
		optionValue = $("#dropDownCountriesForStates option:first").val();
		loadStatesByCountry(optionValue);
	}).fail(function() {
		showToastMessage("ERROR: Could not connect to sever or server encoutered an error");
	});
	
	
}