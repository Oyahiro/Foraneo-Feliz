function setContainer(response){
	$("#ctnPedidos").empty();
	$("#ctnPedidos").html(response);
}

function listPedidos(){
	var id = $("#idpedido").val();
	
	$.ajax({
		url : "/detalle/onlylist/" + id,
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
	listPedidos();
});
