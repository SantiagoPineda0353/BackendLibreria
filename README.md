# BackendLibreria

API REST para la gestión de una biblioteca: usuarios, libros, ejemplares y préstamos. Desarrollada con Spring Boot 3.5.4 y Java 17, usando MySQL como base de datos y Docker para el despliegue.

## Tecnologías

- Java 17
- Spring Boot 3.5.4 (Web, Data JPA, Validation)
- MySQL 8.4
- Maven
- Docker / Docker Compose

## Requisitos previos

- [Docker Desktop](https://www.docker.com/products/docker-desktop/) instalado y corriendo
- Git

## Variables de entorno

El proyecto usa un archivo `.env` en la raíz para configurar la base de datos y el backend. Copia el archivo de ejemplo y ajusta los valores si lo necesitas:

```
DB_NAME=Libreria
DB_USER=libreria_admin
DB_PASSWORD=Libreria_admin.
DB_ROOT_PASSWORD=RootLibreria_admin.
DB_PORT=3307
BACKEND_PORT=8080
```

| Variable | Descripción |
|---|---|
| `DB_NAME` | Nombre de la base de datos |
| `DB_USER` | Usuario de MySQL para la aplicación |
| `DB_PASSWORD` | Contraseña del usuario de la aplicación |
| `DB_ROOT_PASSWORD` | Contraseña del usuario root de MySQL |
| `DB_PORT` | Puerto expuesto en el host para MySQL |
| `BACKEND_PORT` | Puerto expuesto en el host para la API |

## Ejecución del proyecto

```bash
# 1. Clonar el repositorio
git clone https://github.com/SantiagoPineda0353/BackendLibreria.git
cd BackendLibreria

# 2. Crear el archivo .env a partir del ejemplo
copy .env.example .env        # Windows (PowerShell/CMD)
# cp .env.example .env        # Linux/Mac

# 3. Levantar la base de datos y el backend con Docker
docker compose up --build -d

# 4. Verificar que ambos contenedores esten corriendo y saludables
docker compose ps
```

La API queda disponible en `http://localhost:8080` (o el puerto que hayas definido en `BACKEND_PORT`).

## Restaurar datos de prueba

El repositorio incluye un dump con datos de ejemplo en `db/dump/libreria_backup.dump`. Con los contenedores ya corriendo, restáuralo con:

```bash
# 5. Restaurar el dump de datos de prueba (Linux/Mac)
docker exec -e MYSQL_PWD='Libreria_admin.' -i libreria-mysql mysql -u libreria_admin Libreria < db/dump/libreria_backup.dump
```

```powershell
# 5. Restaurar el dump de datos de prueba (Windows/PowerShell)
Get-Content db\dump\libreria_backup.dump | docker exec -e MYSQL_PWD='Libreria_admin.' -i libreria-mysql mysql -u libreria_admin Libreria
```

> Reemplaza el usuario/contraseña por los valores que hayas definido en tu `.env`. Se usa la variable `MYSQL_PWD` en vez de `-p` para evitar problemas de interpretación de la contraseña en algunas terminales. En PowerShell, la redirección `<` no funciona igual que en bash, por lo que se usa `Get-Content` junto con una tubería (`|`) en su lugar.

## Endpoints principales

| Método | Endpoint | Descripción |
|---|---|---|
| POST | `/api/libros` | Crear un libro |
| GET | `/api/libros` | Listar todos los libros |
| GET | `/api/libros/{id}` | Consultar un libro por id |
| PUT | `/api/libros/{id}` | Actualizar un libro |
| DELETE | `/api/libros/{id}` | Eliminar un libro |
| POST | `/api/ejemplares` | Crear un ejemplar de un libro |
| GET | `/api/ejemplares/disponibles?isbn={isbn}` | Listar ejemplares disponibles por ISBN |
| GET | `/api/ejemplares/libro/{libroId}` | Listar ejemplares de un libro |
| POST | `/api/usuarios` | Crear un usuario |
| GET | `/api/usuarios` | Listar todos los usuarios |
| GET | `/api/usuarios/{id}` | Consultar un usuario por id |
| PUT | `/api/usuarios/{id}` | Actualizar un usuario |
| DELETE | `/api/usuarios/{id}` | Eliminar un usuario |
| POST | `/api/prestamos` | Registrar un préstamo (usuarioId + isbn) |
| PUT | `/api/prestamos/{id}/devolver` | Registrar la devolución de un préstamo |
| GET | `/api/prestamos/usuario/{usuarioId}` | Listar préstamos por usuario |
| GET | `/api/prestamos/libro/{libroId}` | Listar préstamos por libro |

Una colección de Postman con ejemplos de todos estos endpoints está disponible en `postman/BackendLibreria.postman_collection.json`.

## Reglas de negocio

- Un libro puede tener varios ejemplares; cada ejemplar tiene un código de inventario único y un estado (`DISPONIBLE`, `PRESTADO`, `DAÑADO`).
- Un usuario no puede tener más de un préstamo con estado `ACTIVO` al mismo tiempo.
- Al registrar un préstamo, se asigna automáticamente el primer ejemplar disponible del libro solicitado (por ISBN).
- El estado del préstamo (`ACTIVO`, `VENCIDO`, `DEVUELTO`) se recalcula dinámicamente comparando la fecha actual con la fecha de devolución esperada.

## Detener el proyecto

```bash
docker compose down
```

Para eliminar también los datos persistidos de la base de datos:

```bash
docker compose down -v
```
