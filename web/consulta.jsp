<%-- 
    Document   : consulta
    Created on : 8/10/2026, 11:58:28 AM
    Author     : Desiderio
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Consulta de Pasajes - LATAM</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        @media print {
            .no-print { display: none !important; }
            body { background-color: white !important; }
        }
    </style>
</head>
<body class="bg-light">
    <div class="container mt-4">
        <div class="no-print">
            <%-- PISTA: Revisa como se imprime el DNI recibido y si necesita escape antes de volver a mostrarlo. --%>
            <h2 class="text-center mb-4">Consulta de Pasajes por DNI</h2>
            <form action="consulta" method="POST" class="form-inline justify-content-center mb-4">
                <div class="form-group mr-2">
                    <input type="text" name="dni" class="form-control" placeholder="Ingrese DNI (8 dígitos)" maxlength="8" value="${dniBuscado}" required>
                </div>
                <button type="submit" class="btn btn-danger">Consultar</button>
                <a href="catalogo" class="btn btn-secondary ml-2">Volver al Catálogo</a>
            </form>

            <c:if test="${not empty mensaje}">
                <div class="alert alert-warning text-center col-md-6 offset-md-3">${mensaje}</div>
            </c:if>
        </div>

        <c:if test="${not empty listaPasajes}">
            <%-- PISTA: La consulta debe devolver unicamente registros asociados al DNI validado. --%>
            <h4 class="text-center mb-3">Pasajes Encontrados</h4>
            <table class="table table-bordered table-striped bg-white">
                <thead class="thead-dark">
                    <tr>
                        <th>Código</th>
                        <th>Pasajero</th>
                        <th>DNI</th>
                        <th>Vuelo</th>
                        <th>Ruta</th>
                        <th>Fecha y Hora</th>
                        <th>Precio</th>
                        <th class="no-print">Acción</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="p" items="${listaPasajes}">
                        <tr>
                            <td><b>${p.codigo}</b></td>
                            <td><c:out value="${p.pasajero.nombres} ${p.pasajero.apellidos}"/></td>
                            <td>${p.pasajero.dni}</td>
                            <td>${p.vuelo.numeroVuelo}</td>
                            <td>${p.vuelo.origen} ➔ ${p.vuelo.destino}</td>
                            <td>${p.vuelo.fecha} ${p.vuelo.hora}</td>
                            <td>S/ ${p.precio}</td>
                            <td class="no-print text-center">
                                <button class="btn btn-sm btn-outline-dark" onclick="window.print()">Imprimir</button>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>
    </div>
</body>
</html>
