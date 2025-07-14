# 📦 Spring Boot E-commerce

Aplicación web de comercio electrónico desarrollada en Java 17 con Spring Boot. Permite la gestión de usuarios, roles (USER y ADMIN), productos, órdenes de compra y carrito, implementando seguridad con Spring Security y vistas Thymeleaf.

---

## 🚀 Características

- Registro e inicio de sesión de usuarios con encriptación BCrypt.
- Roles diferenciados: 
  - **USER**: compra productos, gestiona su carrito y órdenes.
  - **ADMIN**: gestiona productos, usuarios y órdenes.
- Autenticación y autorización con Spring Security.
- CRUD de productos para ADMIN.
- Carrito de compras persistente por sesión.
- Manejo de órdenes de compra.
- Integración con base de datos MySQL usando Spring Data JPA.
- Vistas Thymeleaf basadas en plantillas HTML adaptadas.

---

## 🛠️ Tecnologías

- Java 17
- Spring Boot 3
- Spring Security
- Spring Data JPA
- Thymeleaf
- MySQL
- Maven

---

## 📁 Estructura del Proyecto

- **controller**: Controladores MVC (rutas y lógica web).
- **service**: Lógica de negocio y reglas de aplicación.
- **repository**: Interfaces JPA para acceso a datos.
- **model**: Entidades JPA (Usuario, Producto, Orden, DetalleOrden).
- **templates**: Vistas Thymeleaf (usuario y administrador).

---

## ⚙️ Configuración del Proyecto

1️⃣ Clonar el repositorio:

```bash
git clone https://github.com/JNCallaGiron/e-comerce.git
```

2️⃣ Configurar la conexión a MySQL en `src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:mysql://localhost:3306/TU_BASE_DE_DATOS
spring.datasource.username=TU_USUARIO
spring.datasource.password=TU_PASSWORD
```

3️⃣ Crear la base de datos en MySQL (estructura automática via JPA/Hibernate al arrancar).

4️⃣ Construir el proyecto:

```bash
mvn clean package
```

5️⃣ Ejecutar:

```bash
java -jar target/spring-ecomerce-0.0.1-SNAPSHOT.jar
```

6️⃣ Acceder en navegador:

```
http://localhost:8080
```

---

## 🗄️ Base de Datos

Tablas gestionadas automáticamente con JPA:

- usuarios
- productos
- ordenes
- detalles de orden

**Importante:** La base de datos debe estar creada (vacía o con datos de prueba) antes de iniciar la app.

---

## 🛡️ Seguridad

- Login personalizado en `/usuario/login`.
- Logout en `/usuario/cerrar`.
- Roles:
  - `USER` ➜ acceso a tienda y compras.
  - `ADMIN` ➜ acceso a `/administrador/**` y CRUD de productos, usuarios y órdenes.
- Contraseñas encriptadas con BCrypt.

---

## 🎯 Estado

✅ Funcional para pruebas y desarrollo local.  
🚧 No optimizado para producción (faltan validaciones exhaustivas, hardening de seguridad y despliegue en servidor).  

---

## 🙋‍♂️ Autor

**Jairo Calla**  
👨‍💻 Junior Back-End Developer (Java / Spring Boot)  
📌 [Repositorio del proyecto](https://github.com/JNCallaGiron/e-comerce)

---
