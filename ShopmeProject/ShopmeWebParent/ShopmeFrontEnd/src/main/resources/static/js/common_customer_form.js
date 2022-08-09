var dropDownCountry;
var dataListState;
var fieldState;

$(document).ready(function(){
	dropDownCountry = $("#country");
	dataListState = $("#listStates");
	fieldState = $("#state");
	
	dropDownCountry.on('change', function(){
		loadStatesForCountry();
		fieldState.val("").focus();
	})
})

function loadStatesForCountry(){
	selectedCountry = $("#country option:selected");
	url = contextPath + "settings/list_states_by_countries/" + selectedCountry.val();
	
	$.get(url, function(res){
		dataListState.empty();
		$.each(res, function(index, state){
			$("<option>").val(state.name).text(state.name).appendTo(dataListState);
		})
	})
}

function checkPasswordMatch(confirmPassword){
	if(confirmPassword.value != $("#pwd").val()){
		confirmPassword.setCustomValidity("Password do not match!");
	} else{
		confirmPassword.setCustomValidity("");
	}
}
