<%-- 
    Document   : compra
    Created on : 8/10/2026, 11:58:13 AM
    Author     : Desiderio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Comprar Pasaje - LATAM</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="bg-light">
    <div class="container mt-5" style="max-width: 600px;">
        <div class="card shadow">
            <div class="card-header bg-danger text-white text-center">
                <h3>Registro de Pasajero</h3>
            </div>
            <div class="card-body">
                <%-- PISTA: La validacion HTML ayuda a la experiencia, pero no reemplaza la validacion en CompraServlet. --%>
                <c:if test="${not empty error}">
                    <div class="alert alert-danger">${error}</div>
                </c:if>

                <div class="alert alert-info">
                    <%-- PISTA: El precio mostrado y registrado debe tener como fuente la base de datos, no el formulario. --%>
                    <h5>Detalle del Vuelo</h5>
                    <p class="mb-1"><b>Vuelo:</b> ${vuelo.numeroVuelo} (${vuelo.origen} ➔ ${vuelo.destino})</p>
                    <p class="mb-1"><b>Fecha:</b> ${vuelo.fecha} | <b>Hora:</b> ${vuelo.hora}</p>
                    <p class="mb-0"><b>Precio oficial:</b> S/ ${vuelo.precio}</p>
                </div>

                <form action="compra" method="POST">
                    <input type="hidden" name="idVuelo" value="${vuelo.idVuelo}">

                    <div class="form-group">
                        <label>DNI (8 dígitos):</label>
                        <input type="text" name="dni" class="form-control" maxlength="8" required>
                    </div>
                    <div class="form-group">
                        <label>Nombres:</label>
                        <input type="text" name="nombres" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Apellidos:</label>
                        <input type="text" name="apellidos" class="form-control" required>
                    </div>
                    <div class="form-group">
                        <label>Correo Electrónico:</label>
                        <input type="email" name="correo" class="form-control" required>
                    </div>
                    <button type="submit" class="btn btn-danger btn-block">Confirmar y Pagar</button>
                    <a href="catalogo" class="btn btn-secondary btn-block mt-2">Cancelar</a>
                </form>
            </div>
        </div>
    </div>
</body>
</html>
