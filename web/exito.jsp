<%-- 
    Document   : exito
    Created on : 8/10/2026, 11:58:22 AM
    Author     : Desiderio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Compra Exitosa - LATAM</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5" style="max-width: 600px;">
        <div class="card shadow border-success">
            <div class="card-header bg-success text-white text-center">
                <h3>¡Compra Exitosa!</h3>
            </div>
            <div class="card-body text-center">
                <%-- PISTA: Compara las salidas directas con c:out e identifica que datos pueden provenir del usuario. --%>
                <h5 class="text-muted">Código de Pasaje</h5>
                <h1 class="text-danger font-weight-bold display-4 border p-2 bg-white">${codigoPasaje}</h1>
                <hr>
                <p class="text-left"><b>Pasajero:</b> <c:out value="${pasajeroNombre}"/></p>
                <p class="text-left"><b>Vuelo:</b> ${vuelo.numeroVuelo} (${vuelo.origen} ➔ ${vuelo.destino})</p>
                <p class="text-left"><b>Fecha y Hora:</b> ${vuelo.fecha} - ${vuelo.hora}</p>
                <p class="text-left text-success font-weight-bold"><b>Precio Pagado:</b> S/ ${vuelo.precio}</p>
                
                <a href="consulta" class="btn btn-primary btn-block mt-3">Ver Mis Pasajes</a>
                <a href="catalogo" class="btn btn-outline-secondary btn-block">Volver al Catálogo</a>
            </div>
        </div>
    </div>
</body>
</html>
