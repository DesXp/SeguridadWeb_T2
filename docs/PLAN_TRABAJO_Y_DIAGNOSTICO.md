# Plan de trabajo y diagnostico

Los requisitos de las siguientes secciones se transcriben del Word del caso practico. La distribucion, validacion y estado son organizacion interna del grupo.

## Distribucion general

| Paso | Responsable | Estado |
|---|---|---|
| Paso 1. Proyecto, base de datos y capa conexion | Desiderio Vásquez | Pendiente de validar |
| Paso 2. Capa modelo y capa DAO con consultas seguras | Integrante 2 | Cumple parcialmente |
| Paso 3. Vista Catalogo de vuelos | Desiderio Vásquez | Pendiente de validar |
| Paso 4. Vista Compra: registro de datos y procesamiento | Integrante 3 | Cumple parcialmente |
| Paso 5. Vista Confirmacion de compra | Integrante 4 | Cumple parcialmente |
| Paso 6. Vista Mis pasajes: consulta por DNI e impresion | Integrante 4 | Cumple parcialmente |

## Matriz general de seguimiento

| Paso | Responsable | Requisito del Word | Validacion interna | Evidencia sugerida (ejemplos) | Estado |
|---|---|---|---|---|---|
| Paso 1 | Desiderio Vásquez | Proyecto, base de datos y capa conexion | Probar BD, conexion y despliegue | Capturas del proyecto y tablas | Pendiente de validar |
| Paso 2 | Integrante 2 | Modelo y DAO con consultas seguras | Revisar cada consulta y corregir el laboratorio | Paquetes, pruebas DAO y correccion | Cumple parcialmente |
| Paso 3 | Desiderio Vásquez | Catalogo de vuelos | Probar inicio, lista y boton Comprar | Captura del catalogo | Pendiente de validar |
| Paso 4 | Integrante 3 | Compra y procesamiento | Probar compra valida, errores y precio | Formulario, error y compra | Cumple parcialmente |
| Paso 5 | Integrante 4 | Confirmacion de compra | Probar datos, codigo y `<c:out>` | Captura de confirmacion | Cumple parcialmente |
| Paso 6 | Integrante 4 | Consulta por DNI e impresion | Probar ambos casos e impresion | Consultas e impresion | Cumple parcialmente |

La columna de evidencia contiene ejemplos, no una lista cerrada. Cada integrante debe revisar el Word y la rubrica, y agregar cualquier evidencia adicional que corresponda.

---

## Paso 1 Proyecto base de datos y capa conexión

Responsable interno: **Desiderio Vásquez**.

### Requisitos transcritos del Word

- Ejecuta BD_LATAM.sql en SQL Server y verifica las 3 tablas con sus datos de ejemplo.
- Crea en NetBeans una Web Application (GlassFish 4.1.1, Java EE 7 Web) sin tildes en el nombre y agrega el driver mssql-jdbc-13.4.0.jre8.jar en Libraries.
- Crea los paquetes conexion, modelo, dao y controlador. En conexion, crea la clase ConexionSQL con el método getConexion().

### Pista transcrita del Word

> URL de conexión: jdbc:sqlserver://localhost:1433;databaseName=BD_LatamT2;user=sa;password=123;encrypt=false

### Validacion interna

- Ejecutar la base de datos y confirmar las tres tablas.
- Abrir el proyecto en NetBeans.
- Probar la conexion y el despliegue en GlassFish.
- Registrar las capturas solicitadas por el Word.

Estado interno: **Pendiente de validar**.

---

## Paso 2 Modelo y DAO con consultas seguras

Responsable interno: **Integrante 2**.

### Requisitos transcritos del Word

- Modelo: clases Vuelo, Pasajero y Venta con atributos privados y métodos get/set.
- VueloDAO: listar() devuelve los vuelos con fecha desde hoy; buscar(id) devuelve un vuelo.
- PasajeroDAO: guardar(Pasajero) reutiliza el pasajero si el DNI ya existe; si no, lo registra.
- VentaDAO: registrar(idPasajero, vuelo) genera un código de pasaje aleatorio de 6 caracteres y guarda el precio leído de la BD; listarPorDni(dni) une Venta, Pasajero y Vuelo con INNER JOIN.
- Todas las consultas con PreparedStatement.

### Pistas transcritas del Word

> Usa Statement.RETURN_GENERATED_KEYS para obtener el id insertado y UUID.randomUUID() para el código.

### Validacion interna

- Revisar cada consulta SQL.
- Confirmar que todas utilicen PreparedStatement.
- Corregir la consulta insegura de la copia de laboratorio.
- Probar listado, búsqueda, reutilización por DNI, registro de venta, código, precio e INNER JOIN.

Estado interno: **Cumple parcialmente**.

---

## Paso 3 Vista Catalogo de vuelos

Responsable interno: **Desiderio Vásquez**.

### Requisitos transcritos del Word

- CatalogoServlet (/catalogo) envía la lista de vuelos a catalogo.jsp; index.jsp redirige al catálogo.
- catalogo.jsp muestra en una tabla: número de vuelo, origen, destino, fecha, hora y precio, con un botón Comprar en cada vuelo. Usa Bootstrap y los colores de LATAM.

### Pista transcrita del Word

> En index.jsp usa `<jsp:forward page="catalogo"/>` y en la vista recorre la lista con `<c:forEach>`.

### Validacion interna

- Iniciar desde index.jsp.
- Confirmar que aparezcan los vuelos vigentes.
- Revisar todos los campos solicitados.
- Probar el botón Comprar.
- Preparar la captura solicitada.

Estado interno: **Pendiente de validar**.

---

## Paso 4 Vista Compra registro de datos y procesamiento

Responsable interno: **Integrante 3**.

### Requisitos transcritos del Word

- Al pulsar Comprar, compra.jsp muestra el vuelo seleccionado y un formulario con DNI, nombres, apellidos y correo del pasajero.
- El botón Comprar pasaje envía los datos por POST a CompraServlet (/compra), que valida el DNI y los campos obligatorios; si hay error, vuelve al formulario con un mensaje.
- Si los datos son correctos, obtiene el precio con VueloDAO.buscar(id), guarda al pasajero y registra la venta.

### Pista transcrita del Word

> Usa request.setCharacterEncoding("UTF-8") para tildes y ñ, y dni.matches("[0-9]{8}") para validar.

### Validacion interna

- Probar una compra valida.
- Probar DNI inválido y campos obligatorios vacíos.
- Confirmar que la validación se realice en el Servlet.
- Intentar modificar el precio enviado y comprobar que se use el precio de la BD.
- Preparar las capturas solicitadas.

Estado interno: **Cumple parcialmente**.

---

## Paso 5 Vista Confirmacion de compra

Responsable interno: **Integrante 4**.

### Requisitos transcritos del Word

- Al finalizar la compra, exito.jsp muestra el mensaje de compra exitosa, el código del pasaje, el nombre del pasajero y los detalles del vuelo: número, origen, destino, fecha, hora y precio.
- Incluye un enlace a la vista Mis pasajes.

### Pista transcrita del Word

> Muestra el nombre del pasajero con `<c:out value="${...}"/>` para evitar XSS.

### Validacion interna

- Completar una compra valida.
- Confirmar el código y todos los detalles del vuelo.
- Confirmar el enlace a Mis pasajes.
- Probar datos de texto con caracteres HTML.
- Preparar la captura solicitada.

Estado interno: **Cumple parcialmente**.

---

## Paso 6 Vista Mis pasajes consulta por DNI e impresion

Responsable interno: **Integrante 4**.

### Requisitos transcritos del Word

- consulta.jsp tiene un campo DNI y un botón Consultar que envía el DNI por POST a ConsultaServlet (/consulta).
- Si el DNI no tiene compras, muestra: “No se encontraron pasajes comprados para ese DNI”.
- Si tiene compras, muestra cada pasaje (código, pasajero, DNI, vuelo, origen, destino, fecha, hora y precio) con un botón Imprimir.

### Pista transcrita del Word

> Usa `onclick="window.print()"` para imprimir y `@media print` en el CSS para ocultar el menú y los botones.

### Validacion interna

- Consultar un DNI con pasajes.
- Consultar un DNI sin pasajes.
- Probar un DNI invalido.
- Confirmar que solo se muestren pasajes del DNI consultado.
- Probar la impresión y revisar la vista resultante.
- Preparar las capturas solicitadas.

Estado interno: **Cumple parcialmente**.

---

## Requisitos de seguridad transcritos del Word

- Inyección SQL (OWASP A1): todas las consultas deben usar PreparedStatement; está prohibido concatenar valores en el SQL.
- Validación en el servidor: el Servlet valida que el DNI tenga exactamente 8 dígitos y que los campos obligatorios no estén vacíos; la validación del HTML no es suficiente.
- Manipulación de parámetros: el precio del pasaje se obtiene de la base de datos, nunca del formulario.
- XSS: los datos que escribe el usuario se muestran con `<c:out>`.
- Exposición de datos: la consulta por DNI solo muestra los pasajes de ese DNI; si no hay, muestra un mensaje.

## Registro interno de validacion

Cada integrante debe registrar para su paso:

- Archivos revisados.
- Prueba valida ejecutada.
- Prueba de error o seguridad ejecutada.
- Resultado obtenido.
- Evidencia adicional solicitada por el Word o la rubrica.

La copia `Evaluacion_T2_Latam_laboratorio` contiene una consulta insegura para practicar la correccion del paso 2. La version principal conserva el codigo funcional.
