<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Mis Pasajes - LATAM</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        :root {
            --latam-blue: #1b1464;
            --latam-magenta: #e60046;
        }

        .latam-title {
            color: var(--latam-blue);
        }

        .btn-latam {
            color: #fff;
            background-color: var(--latam-magenta);
            border-color: var(--latam-magenta);
        }

        .btn-latam:hover {
            color: #fff;
            filter: brightness(0.92);
        }

        @media print {
            @page {
                size: landscape;
                margin: 10mm;
            }

            .no-print {
                display: none !important;
            }

            body {
                background: #fff !important;
            }

            .container {
                max-width: none !important;
                width: 100% !important;
                margin: 0 !important;
                padding: 0 !important;
            }

            .table-responsive {
                overflow: visible !important;
            }

            .table {
                width: 100% !important;
                font-size: 11px;
            }

            body.printing-one .pasajes-table tbody tr {
                display: none;
            }

            body.printing-one .pasajes-table tbody tr.print-target {
                display: table-row;
            }
        }
    </style>
</head>
<body class="bg-light">
    <div class="container mt-4 mb-5">
        <div class="no-print">
            <h2 class="text-center latam-title mb-4">Mis Pasajes</h2>

            <form action="consulta" method="POST" class="form-inline justify-content-center mb-4">
                <div class="form-group mr-2 mb-2">
                    <label for="dni" class="sr-only">DNI</label>
                    <input
                        id="dni"
                        type="text"
                        name="dni"
                        class="form-control"
                        placeholder="Ingrese DNI (8 dígitos)"
                        maxlength="8"
                        minlength="8"
                        pattern="[0-9]{8}"
                        inputmode="numeric"
                        value="${fn:escapeXml(dniBuscado)}"
                        required>
                </div>
                <button type="submit" class="btn btn-latam mb-2">Consultar</button>
                <a href="catalogo" class="btn btn-secondary ml-2 mb-2">Volver al Catálogo</a>
            </form>

            <c:if test="${not empty mensaje}">
                <div class="alert alert-warning text-center col-md-8 offset-md-2">
                    <c:out value="${mensaje}"/>
                </div>
            </c:if>
        </div>

        <c:if test="${not empty listaPasajes}">
            <h4 class="text-center mb-3 latam-title">Pasajes encontrados</h4>

            <div class="table-responsive">
                <table class="table table-bordered table-striped bg-white pasajes-table">
                    <thead class="thead-dark">
                        <tr>
                            <th>Código</th>
                            <th>Pasajero</th>
                            <th>DNI</th>
                            <th>Vuelo</th>
                            <th>Origen</th>
                            <th>Destino</th>
                            <th>Fecha</th>
                            <th>Hora</th>
                            <th>Precio</th>
                            <th class="no-print">Acción</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="p" items="${listaPasajes}">
                            <tr>
                                <td><strong><c:out value="${p.codigo}"/></strong></td>
                                <td>
                                    <c:out value="${p.pasajero.nombres}"/>
                                    <c:out value="${p.pasajero.apellidos}"/>
                                </td>
                                <td><c:out value="${p.pasajero.dni}"/></td>
                                <td><c:out value="${p.vuelo.numeroVuelo}"/></td>
                                <td><c:out value="${p.vuelo.origen}"/></td>
                                <td><c:out value="${p.vuelo.destino}"/></td>
                                <td><c:out value="${p.vuelo.fecha}"/></td>
                                <td><c:out value="${p.vuelo.hora}"/></td>
                                <td>S/ <c:out value="${p.precio}"/></td>
                                <td class="no-print text-center">
                                    <button type="button" class="btn btn-sm btn-outline-dark" onclick="imprimirPasaje(this)">
                                        Imprimir
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:if>
    </div>

    <script>
        function limpiarFilaImpresion() {
            document.body.classList.remove('printing-one');
            var fila = document.querySelector('.print-target');
            if (fila) {
                fila.classList.remove('print-target');
            }
        }

        function imprimirPasaje(boton) {
            limpiarFilaImpresion();
            var fila = boton.closest('tr');
            if (fila) {
                fila.classList.add('print-target');
                document.body.classList.add('printing-one');
            }
            window.print();
        }

        window.addEventListener('afterprint', limpiarFilaImpresion);
    </script>
</body>
</html>
