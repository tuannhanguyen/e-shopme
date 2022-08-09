function clearFilter(){
	window.location = moduleURL;
}

function showDeleteConfirmModal(link, entityName){
	entityId = link.attr("entityId");
	//alert(link.attr("href"));
	$("#yesBtn").attr("href", link.attr("href"));
	$("#confirmText").text("Are you sure delete this " + entityName + " ID " + entityId  + "?");
	$("#confirmModal").modal();
}