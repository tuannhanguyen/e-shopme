$(document).ready(function(){
	$("a[name='linkRemoveDetails']").each(function(index){
		$(this).click(function(){
			removeDetailSectionByIndex(index);
		});
	});
});
function addNextDetailSection(index){
	allDivDetails = $("[id^='divDetail']");
	divDetailCount = allDivDetails.length;
	//alert(divDetailCount);
	nextDivDetailId = divDetailCount;
	
	htmlDetailSection = 
		`<div class="form-inline" id="divDetail${nextDivDetailId}">
			<input type="hidden" name="detailIDs" value="0">
			<label class="m-3">Name</label>
			<input type="text" class="form-control w-25" name="detailNames">
			<label class="m-3">Value</label>
			<input type="text" class="form-control w-25" name="detailValues">
		</div>`;
	
	$("#divProductDetails").append(htmlDetailSection);
	
	previousDivDetailSection = allDivDetails.last();
	previousDetailId = previousDivDetailSection.attr("id");
	//console.log(previousDetailId);
	
	htmlRemoveLink = `
		<a class="fa fa-times-circle fa-2x float-right m-2" 
		href="javascript:removeDetailSectionById('${previousDetailId}')"
		title="Remove this detail" ></a>
		`;
		
	previousDivDetailSection.append(htmlRemoveLink);
	
	$("input[name='detailNames']").last().focus();
	
}

function removeDetailSectionById(id){
	//alert(id);
	$("#"+id).remove();
}

function removeDetailSectionByIndex(index){
	$("#divDetail" + index).remove();
}
