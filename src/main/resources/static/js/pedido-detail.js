function addDetail(){
	var detail = {
			platilloid : $("#cmbTipo").val(),
			cantidad : $("#txtCantidad").val()
	}
	console.log(detail);
	
	$.ajax({
		url : "/pedido/addDetail/",
		method : 'POST',
		headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(detail),
		success : function(response){
			$("#tblDetalle").html("");
			console.log(response);
			$.each(response, function(i, item){
				console.log(item);
				$("#tblDetalle").append("<tr>");
				$("#tblDetalle").append("<td>" + item.platillo.nombre + "</td>");
				$("#tblDetalle").append("<td>" + item.platillo.precio + "</td>");
				$("#tblDetalle").append("<td>" + item.cantidad + "</td>");
				$("#tblDetalle").append("<td>" + item.totalindividual + "</td>");
				$("#tblDetalle").append("</tr>");				
			});						
		},
		error : function(err){
			console.log(err);
		}		
	});
}

$(document).ready(function(){
	$("#btnAgregar").click(function(){
		addDetail();
	});
});
