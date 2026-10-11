<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Compra Exitosa - LATAM</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <style>
        :root {
            --latam-blue: #1b1464;
            --latam-magenta: #e60046;
        }

        .latam-card {
            border-top: 5px solid var(--latam-magenta);
        }

        .latam-title {
            color: var(--latam-blue);
        }

        .codigo-pasaje {
            color: var(--latam-magenta);
            letter-spacing: 0.08em;
        }
    </style>
</head>
<body class="bg-light">
    <div class="container mt-5 mb-5" style="max-width: 680px;">
        <c:choose>
            <c:when test="${not empty codigoPasaje and not empty vuelo}">
                <div class="card shadow latam-card">
                    <div class="card-body p-4">
                        <h2 class="text-center latam-title mb-1">¡Compra exitosa!</h2>
                        <p class="text-center text-muted mb-4">Tu pasaje fue registrado correctamente.</p>

                        <div class="text-center mb-4">
                            <div class="text-muted">Código de pasaje</div>
                            <div class="display-4 font-weight-bold codigo-pasaje">
                                <c:out value="${codigoPasaje}"/>
                            </div>
                        </div>

                        <hr>

                        <dl class="row mb-0">
                            <dt class="col-sm-4">Pasajero</dt>
                            <dd class="col-sm-8"><c:out value="${pasajeroNombre}"/></dd>

                            <dt class="col-sm-4">Vuelo</dt>
                            <dd class="col-sm-8"><c:out value="${vuelo.numeroVuelo}"/></dd>

                            <dt class="col-sm-4">Origen</dt>
                            <dd class="col-sm-8"><c:out value="${vuelo.origen}"/></dd>

                            <dt class="col-sm-4">Destino</dt>
                            <dd class="col-sm-8"><c:out value="${vuelo.destino}"/></dd>

                            <dt class="col-sm-4">Fecha</dt>
                            <dd class="col-sm-8"><c:out value="${vuelo.fecha}"/></dd>

                            <dt class="col-sm-4">Hora</dt>
                            <dd class="col-sm-8"><c:out value="${vuelo.hora}"/></dd>

                            <dt class="col-sm-4">Precio</dt>
                            <dd class="col-sm-8 font-weight-bold">S/ <c:out value="${vuelo.precio}"/></dd>
                        </dl>

                        <a href="consulta" class="btn btn-primary btn-block mt-4">Ver Mis Pasajes</a>
                        <a href="catalogo" class="btn btn-outline-secondary btn-block">Volver al Catálogo</a>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="alert alert-warning text-center shadow-sm">
                    No existe una compra para mostrar en esta pantalla.
                    <div class="mt-3">
                        <a href="catalogo" class="btn btn-primary">Ir al Catálogo</a>
                    </div>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
</body>
</html>
