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
	url = contextPath + "states/list_by_countries/" + selectedCountry.val();
	
	$.get(url, function(res){
		dataListState.empty();
		$.each(res, function(index, state){
			$("<option>").val(state.name).text(state.name).appendTo(dataListState);
		})
	})
}