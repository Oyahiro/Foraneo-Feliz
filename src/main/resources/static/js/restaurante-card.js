function setContainer(response){
	$("#ctnPlatillos").empty();
	$("#ctnPlatillos").html(response);
}

function listPlatillos(){
	var id = $("#idrestaurante").val();
	$.ajax({
		url : "/platillo/onlylist/" + id,
		method : 'GET',
		success : function(response){
			setContainer(response);
		},
		error : function(err){
			console.log(err);
		}		
	});
}

$(document).ready(function(){
	listPlatillos();
});
