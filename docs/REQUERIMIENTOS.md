# Requerimientos del caso practico

## Datos generales

- Curso: Seguridad de Aplicaciones (02414).
- Caso: sistema web de venta de pasajes para LATAM Airlines Peru.
- Tecnologia indicada: NetBeans 8.2, GlassFish 4.1.1, JDK 8 y SQL Server.
- Base de datos: `BD_LatamT2`.
- Puntaje total: 12 puntos.

## Objetivo

Construir una aplicacion web en Java que permita:

1. Ver un catalogo de vuelos.
2. Comprar un pasaje registrando los datos del pasajero.
3. Confirmar la compra con un codigo de pasaje.
4. Consultar los pasajes ingresando solamente el DNI, sin crear una cuenta.

La aplicacion debe proteger los datos personales y el precio de la compra mediante consultas parametrizadas, validacion en el servidor y lectura del precio desde la base de datos.

## Flujo esperado

`Catalogo de vuelos -> Comprar -> Datos del pasajero -> Confirmacion -> Mis pasajes`

## Consideraciones de entrega

- Ejecutar primero el script `BD_LATAM.sql` en SQL Server Management Studio.
- No modificar la estructura de las tablas.
- El nombre del proyecto no debe tener tildes, `ñ` ni espacios.
- Entregar un unico archivo `.zip` con:
  - La carpeta completa del proyecto NetBeans.
  - El documento Word con las capturas.
  - `integrantes.txt` con integrantes, DNI y porcentaje de aporte.
- Los porcentajes de aporte deben sumar exactamente 100%.
- Si se utilizo IA, declararlo en el anexo XI y validar los resultados.

## Seguridad obligatoria

### Inyeccion SQL

Todas las consultas deben usar `PreparedStatement`. No se permite concatenar valores recibidos del usuario en el SQL.

### Validacion en el servidor

- El DNI debe contener exactamente 8 digitos.
- Los campos obligatorios no deben estar vacios.
- La validacion del HTML por si sola no es suficiente.

### Integridad del precio

El precio debe obtenerse desde la base de datos mediante el vuelo seleccionado. Nunca debe tomarse de un campo enviado por el formulario.

### XSS

Los datos escritos por el usuario deben mostrarse con `<c:out>` en las vistas JSP.

### Exposicion de datos

La consulta por DNI solo debe devolver los pasajes asociados a ese DNI. Si no existen compras, debe mostrarse un mensaje informativo.

## Requisitos funcionales

### 1. Proyecto, base de datos y conexion — 1.5 puntos

- Ejecutar `BD_LATAM.sql` y verificar las tres tablas con datos de ejemplo.
- Crear una aplicacion web Java EE 7 sin tildes en el nombre.
- Agregar el driver `mssql-jdbc-13.4.0.jre8.jar`.
- Crear los paquetes `conexion`, `modelo`, `dao` y `controlador`.
- Implementar `ConexionSQL.getConexion()`.

Pista del docente: URL de ejemplo:

```text
jdbc:sqlserver://localhost:1433;databaseName=BD_LatamT2;user=sa;password=123;encrypt=false
```

### 2. Modelo y DAO seguros — 2.5 puntos

- Crear las clases `Vuelo`, `Pasajero` y `Venta` con atributos privados y metodos get/set.
- `VueloDAO.listar()` debe devolver vuelos desde la fecha actual.
- `VueloDAO.buscar(id)` debe devolver un vuelo por su identificador.
- `PasajeroDAO.guardar()` debe reutilizar el pasajero si el DNI ya existe y registrarlo si no existe.
- `VentaDAO.registrar()` debe:
  - Generar un codigo aleatorio de 6 caracteres.
  - Guardar el precio leido de la base de datos.
- `VentaDAO.listarPorDni()` debe unir `Venta`, `Pasajero` y `Vuelo` mediante `INNER JOIN`.
- Todas las consultas deben usar `PreparedStatement`.

Pista: usar `Statement.RETURN_GENERATED_KEYS` para obtener el ID insertado y `UUID.randomUUID()` para generar el codigo.

Estructura indicada de tablas:

| Tabla | Campos principales |
|---|---|
| `Vuelo` | `id_vuelo`, `numero_vuelo`, `origen`, `destino`, `fecha`, `hora`, `precio` |
| `Pasajero` | `id_pasajero`, `dni` unico de 8 digitos, `nombres`, `apellidos`, `correo` |
| `Venta` | `id_venta`, `codigo` unico, `id_pasajero`, `id_vuelo`, `precio`, `fecha_compra` |

### 3. Catalogo de vuelos — 1.5 puntos

- `CatalogoServlet` debe enviar la lista a `catalogo.jsp`.
- `index.jsp` debe redirigir o reenviar al catalogo.
- Mostrar numero de vuelo, origen, destino, fecha, hora y precio.
- Incluir un boton `Comprar` para cada vuelo.
- Usar Bootstrap y colores de LATAM.

Pista: en `index.jsp` se puede usar `<jsp:forward page="catalogo" />` y en la vista `<c:forEach>`.

### 4. Compra y procesamiento — 2.5 puntos

- Al seleccionar un vuelo, mostrar sus datos y un formulario con DNI, nombres, apellidos y correo.
- Enviar el formulario por `POST` a `/compra`.
- Validar en el Servlet el DNI y los campos obligatorios.
- Si existe un error, volver al formulario con un mensaje.
- Obtener nuevamente el vuelo desde la base de datos.
- Guardar o reutilizar el pasajero y registrar la venta.

Pista: usar `request.setCharacterEncoding("UTF-8")` y `dni.matches("[0-9]{8}")`.

### 5. Confirmacion de compra — 1.5 puntos

`exito.jsp` debe mostrar:

- Mensaje de compra exitosa.
- Codigo del pasaje.
- Nombre del pasajero.
- Numero de vuelo.
- Origen y destino.
- Fecha y hora.
- Precio.
- Enlace a `Mis pasajes`.

Pista: mostrar el nombre del pasajero con `<c:out value="${...}" />` para evitar XSS.

### 6. Mis pasajes e impresion — 2.5 puntos

- `consulta.jsp` debe tener un campo DNI y un boton `Consultar`.
- Enviar el DNI por `POST` a `/consulta`.
- Si no hay compras, mostrar exactamente o equivalentemente: `No se encontraron pasajes comprados para ese DNI`.
- Si hay compras, mostrar codigo, pasajero, DNI, vuelo, origen, destino, fecha, hora y precio.
- Incluir un boton `Imprimir`.

Pista: usar `onclick="window.print()"` y `@media print` para ocultar el menu y los botones.

## Evidencias y capturas solicitadas

- Proyecto en NetBeans y tablas en SQL Server.
- Paquetes `modelo` y `dao`.
- Vista del catalogo.
- Formulario de compra y mensaje para DNI invalido, por ejemplo `123`.
- Confirmacion con codigo de pasaje.
- Consulta con pasajes, consulta sin pasajes y vista de impresion.

## Rubrica resumida

| Criterio | Puntaje |
|---|---:|
| Proyecto, base de datos y conexion | 1.5 |
| Modelo y DAO seguros | 2.5 |
| Catalogo de vuelos | 1.5 |
| Vista de compra | 2.5 |
| Confirmacion | 1.5 |
| Mis pasajes | 2.5 |
| **Total** | **12.0** |

La calificacion maxima exige funcionamiento comprobado, controles de seguridad, vistas completas y las capturas solicitadas.
