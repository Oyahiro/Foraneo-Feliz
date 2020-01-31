function cargar(){
	$.ajax({
		url : "/pedido/cargarLista/",
		method : 'POST',
		headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
		contentType : "application/json",
		dataType : "json",
		success : function(response){
			$("#tblDetalle").html("");
			var id=0;
			$.each(response, function(i, item){
				$("#tblDetalle").append("<tr>");
				$("#tblDetalle").append("<td>" + item.platillo.nombre + "</td>");
				$("#tblDetalle").append("<td>" + item.platillo.precio + "</td>");
				$("#tblDetalle").append("<td>" + item.cantidad + "</td>");
				$("#tblDetalle").append("<td>" + item.totalindividual + "</td>");
				$("#tblDetalle").append("<td><button  onclick='delDetail(this)' type='button' id='"+id+"' class='btn btn-sm btn-danger'><i class='fas fa-minus'></i></button></td>");
				id+=1;
			});						
		},
		error : function(err){
			console.log(err);
		}		
	});
}

function addDetail(){
	var detail = {
			platilloid : $("#cmbPlatillo").val(),
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
			var id = 0;
			var rest = new Array();
			var valor = 2;
			$.each(response, function(i, item){
				$("#tblDetalle").append("<tr>");
				$("#tblDetalle").append("<td>" + item.platillo.nombre + "</td>");
				$("#tblDetalle").append("<td>" + item.platillo.precio + "</td>");
				$("#tblDetalle").append("<td>" + item.cantidad + "</td>");
				$("#tblDetalle").append("<td>" + item.totalindividual + "</td>");
				$("#tblDetalle").append("<td><button  onclick='delDetail(this)' type='button' id='"+id+"' class='btn btn-sm btn-danger'><i class='fas fa-minus'></i></button></td>");
				var nombre = item.platillo.restaurante.nombre;
				if(!rest.includes(nombre)){
					rest.push(nombre);
				}
				id+=1;
			});
			valor *= rest.length;
			document.getElementById('valorEnvio').innerHTML= valor;
		},
		error : function(err){
			console.log(err);
		}		
	});
}

function delDetail(comp){
	var id = comp.id;
	
	$.ajax({
		url : "/pedido/delDetail/",
		method : 'POST',
		headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(id),
		success : function(response){
			$("#tblDetalle").html("");
			var id=0;
			$.each(response, function(i, item){
				console.log(item);
				$("#tblDetalle").append("<tr>");
				$("#tblDetalle").append("<td>" + item.platillo.nombre + "</td>");
				$("#tblDetalle").append("<td>" + item.platillo.precio + "</td>");
				$("#tblDetalle").append("<td>" + item.cantidad + "</td>");
				$("#tblDetalle").append("<td>" + item.totalindividual + "</td>");
				$("#tblDetalle").append("<td><button  onclick='delDetail(this)' type='button' id='"+id+"' class='btn btn-sm btn-danger'><i class='fas fa-minus'></i></button></td>");
				id+=1;
			});						
		},
		error : function(err){
			console.log(err);
		}		
	});
}

function cargarPlatillos() {
	var id = document.getElementById("cmbRestaurante").value;
	$.ajax({
		url : "/pedido/platillos/",
		method : 'POST',
		headers: {"X-CSRF-TOKEN": $("input[name='_csrf']").val()},
		contentType : "application/json",
		dataType : "json",
		data : JSON.stringify(id),
		success : function(response){
			var id=0;
			var combo = document.getElementById("cmbPlatillo");
			
			while (combo.length > 0) {
		        combo.remove(combo.length-1);
		    }
			
			$.each(response, function(i, item){
				var option = document.createElement('option');

			    combo.options.add(option, id);
			    combo.options[id].value = item.idplatillo;
			    combo.options[id].innerText = item.nombre;
			    
			    id+=1;
			});				
		},
		error : function(err){
			console.log(err);
		}		
	});
	
}

$(document).ready(function(){
	cargar();
	$("#btnAgregar").click(function(){
		addDetail();
	});
	document.getElementById("cmbRestaurante").addEventListener("change", cargarPlatillos);
});
