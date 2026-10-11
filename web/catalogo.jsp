

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Catálogo de Vuelos - LATAM</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        .latam-header { background-color: #E30613; color: white; }
    </style>
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-dark latam-header">
        <a class="navbar-brand" href="catalogo"><b>LATAM Airlines</b> - Vuelos Disponibles</a>
        <div class="ml-auto">
            <a href="consulta" class="btn btn-light btn-sm">Consultar Mis Pasajes</a>
        </div>
    </nav>

    <div class="container mt-4">

        <%-- Mensaje de error enviado por el Servlet --%>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">
                <c:out value="${error}"/>
            </div>
        </c:if>

        <h2 class="mb-4 text-center">Seleccione su Vuelo</h2>

        <table class="table table-striped table-bordered text-center">
            <thead class="thead-dark">
                <tr>
                    <th>Nº Vuelo</th>
                    <th>Origen</th>
                    <th>Destino</th>
                    <th>Fecha</th>
                    <th>Hora</th>
                    <th>Precio (S/)</th>
                    <th>Acción</th>
                </tr>
            </thead>
            <tbody>
               
                <c:forEach var="v" items="${listaVuelos}">
                    <tr>
                        <td><b><c:out value="${v.numeroVuelo}"/></b></td>
                        <td><c:out value="${v.origen}"/></td>
                        <td><c:out value="${v.destino}"/></td>
                        <td><c:out value="${v.fecha}"/></td>
                        <td><c:out value="${v.hora}"/></td>
                        <td class="text-success font-weight-bold">S/ <c:out value="${v.precio}"/></td>
                        <td>
                            <a href="compra?idVuelo=${v.idVuelo}" class="btn btn-danger btn-sm">Comprar</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
