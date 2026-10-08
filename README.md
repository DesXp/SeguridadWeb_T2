# Sistema web de venta de pasajes

Aplicacion web Java para consultar vuelos, registrar pasajeros, realizar ventas y consultar pasajes por DNI.

## Tecnologias

- Java 8
- NetBeans 8.2
- Java EE 7 Web
- GlassFish 4.1.1
- SQL Server
- JDBC de Microsoft SQL Server
- JSP, Servlets y Bootstrap

## Configuracion del entorno

Instala las versiones indicadas por el entorno academico:

1. JDK 8.
2. NetBeans 8.2.
3. GlassFish 4.1.1.
4. SQL Server y, opcionalmente, SQL Server Management Studio.
5. Git, si vas a trabajar desde GitHub.

### Archivos que deben descargarse del Drive del equipo

Descarga los archivos compartidos por el equipo y colocalos en las siguientes rutas:

| Archivo | Ubicacion | Uso |
|---|---|---|
| `BD_LATAM.sql` | Cualquier carpeta local | Crear la base de datos y cargar los datos de ejemplo |
| `mssql-jdbc-13.4.0.jre8.jar` | `lib/mssql-jdbc-13.4.0.jre8.jar` | Permitir la conexion JDBC desde Java 8 |

No se incluye un enlace privado de Drive en este README. El enlace debe compartirse por el canal interno del equipo.

### Preparar la base de datos

1. Abre SQL Server Management Studio.
2. Abre el archivo `BD_LATAM.sql` descargado del Drive.
3. Ejecuta el script completo.
4. Verifica que exista la base de datos `BD_LatamT2`.
5. Verifica que existan las tablas `Vuelo`, `Pasajero` y `Venta`, con sus datos de ejemplo.
6. No modifiques la estructura de las tablas solicitada por el caso práctico.

### Preparar el proyecto en NetBeans

1. Clona o descarga el repositorio.
2. Abre NetBeans 8.2.
3. Selecciona **File > Open Project** y abre la carpeta del proyecto.
4. Verifica que el proyecto use JDK 8.
5. Registra GlassFish 4.1.1 en **Tools > Servers**.
6. En las propiedades del proyecto, verifica GlassFish como servidor.
7. En **Libraries**, confirma que exista `mssql-jdbc-13.4.0.jre8.jar`.
8. Si falta, copia el `.jar` en `lib` y agrégalo desde **Properties > Libraries > Add JAR/Folder**.

### Configuracion de conexion local

La configuracion actual utiliza en `ConexionSQL.java`:

```text
Servidor: localhost:1433
Base de datos: BD_LatamT2
Usuario: sa
Contraseña: 123
```

Confirma que SQL Server este iniciado, que el usuario `sa` este habilitado y que la contraseña local sea correcta. Esta configuracion es solo para el ejercicio local y no debe publicarse en un repositorio publico.

### Ejecutar la aplicacion

1. Deten cualquier ejecucion anterior de GlassFish si NetBeans informa que un archivo esta bloqueado.
2. Ejecuta **Clean and Build**.
3. Ejecuta **Run**.
4. Accede al catalogo de vuelos desde la URL que muestre NetBeans.
5. Prueba el flujo de catalogo, compra, confirmacion y consulta por DNI.

Si aparece `Unable to delete ... mssql-jdbc...jar`, detén GlassFish y vuelve a ejecutar **Clean and Build**. Si aparece `Cannot open database`, verifica que el nombre sea exactamente `BD_LatamT2`.

## Flujo funcional

```text
Catalogo -> Compra -> Confirmacion -> Consulta por DNI -> Impresion
```

## Controles de seguridad contemplados

- Consultas parametrizadas con `PreparedStatement`.
- Validacion del DNI en el servidor.
- Precio obtenido desde la base de datos.
- Consulta de pasajes filtrada por DNI.
- Escape de datos dinamicos en las vistas JSP mediante JSTL, pendiente de completar en todas las salidas.

## Alcance publico

Este repositorio contiene una version educativa y generalizada del proyecto. La documentacion de evaluacion, nombres de integrantes, porcentajes, capturas y material interno se mantiene fuera del repositorio publico.

## Uso de inteligencia artificial

Durante el desarrollo se utilizaron herramientas de inteligencia artificial como apoyo para revisar requisitos, explorar alternativas y organizar parte del codigo y la documentacion. El equipo reviso, adapto y valido los resultados.
