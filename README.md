# LiterAlura - Catálogo de Libros

## Descripción

LiterAlura es una aplicación Java basada en Spring Boot que permite consultar, buscar y gestionar un catálogo de libros. La aplicación consume la API **Gutendex** (biblioteca digital con más de 70,000 libros del Proyecto Gutenberg) y almacena la información en una base de datos PostgreSQL.

## Características Principales

- **Búsqueda de Libros**: Busca libros por título en la API Gutendex
- **Gestión de Autores**: Consulta y almacena información de autores
- **Persistencia de Datos**: Base de datos PostgreSQL con JPA/Hibernate
- **Consultas Avanzadas**: 
  - Listar todos los libros registrados
  - Listar autores
  - Listar autores vivos en un año específico
  - Filtrar libros por idioma
  - Estadísticas de libros por idioma
- **Interfaz de Usuario**: Menú interactivo en consola
1
## Requisitos

- **Java JDK 17** o superior
- **Maven 4** o superior
- **PostgreSQL 16** o superior
- **Spring Boot 3.2.3**

## Instalación

### 1. Configurar PostgreSQL

Crea la base de datos y ejecuta el siguiente script SQL:

```sql
-- Crear tabla de autores
CREATE TABLE authors (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE NOT NULL,
    birth_year INTEGER,
    death_year INTEGER
);

-- Crear tabla de libros
CREATE TABLE books (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author_id INTEGER REFERENCES authors(id),
    language VARCHAR(10),
    download_count BIGINT
);

-- Crear índices
CREATE INDEX idx_books_author_id ON books(author_id);
CREATE INDEX idx_books_language ON books(language);
CREATE INDEX idx_authors_name ON authors(name);
```

### 2. Configurar la Aplicación

Edita el archivo `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/literalura_db
spring.datasource.username=postgres
spring.datasource.password=tu_contraseña
spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
```

Reemplaza `tu_contraseña` con la contraseña de tu usuario PostgreSQL.

### 3. Compilar y Ejecutar

#### Compilar el proyecto:
```bash
mvn clean compile
```

#### Empaquetar la aplicación:
```bash
mvn package
```

#### Ejecutar la aplicación:
```bash
mvn spring-boot:run
```

O ejecutar el JAR generado:
```bash
java -jar target/literalura-0.0.1-SNAPSHOT.jar
```

## Menú Principal

Una vez ejecutada la aplicación, se mostrará el siguiente menú:

```
╔════════════════════════════════════════╗
║      ¡BIENVENIDO A LITERALURA!         ║
╠════════════════════════════════════════╣
║ 1. Buscar libro por título             ║
║ 2. Listar todos los libros             ║
║ 3. Listar autores                      ║
║ 4. Listar autores vivos en un año      ║
║ 5. Listar libros por idioma            ║
║ 6. Ver estadísticas de idioma          ║
║ 0. Salir                               ║
╚════════════════════════════════════════╝
```

### Opciones Disponibles

#### 1. Buscar libro por título
- Ingresa el título completo o parcial del libro
- La aplicación consultará la API Gutendex
- Si el libro no existe en la base de datos, se guardará automáticamente

#### 2. Listar todos los libros
- Muestra todos los libros almacenados en la base de datos
- Incluye título, autor, idioma y número de descargas

#### 3. Listar autores
- Muestra todos los autores registrados
- Incluye nombre, año de nacimiento y año de fallecimiento

#### 4. Listar autores vivos en un año
- Ingresa un año específico (ej: 1800)
- La aplicación filtrará los autores que estaban vivos en ese año

#### 5. Listar libros por idioma
- Especifica el código del idioma (en, es, fr, de, pt, etc.)
- Muestra todos los libros en ese idioma

#### 6. Ver estadísticas de idioma
- Ingresa el código del idioma
- Muestra la cantidad total de libros en ese idioma

#### 0. Salir
- Cierra la aplicación

## Estructura del Proyecto

```
literalura/
├── src/
│   ├── main/
│   │   ├── java/com/literalura/
│   │   │   ├── LiteraluraApplication.java     # Clase principal (CommandLineRunner)
│   │   │   ├── model/
│   │   │   │   ├── Autor.java                 # Entidad Autor
│   │   │   │   └── Libro.java                 # Entidad Libro
│   │   │   ├── dto/
│   │   │   │   ├── AutorDTO.java              # DTO para mapeo JSON de autores
│   │   │   │   ├── LibroDTO.java              # DTO para mapeo JSON de libros
│   │   │   │   └── RespuestaAPIDTO.java       # DTO para mapeo de respuesta API
│   │   │   ├── repository/
│   │   │   │   ├── AutorRepository.java       # JPA Repository para Autor
│   │   │   │   └── LibroRepository.java       # JPA Repository para Libro
│   │   │   ├── service/
│   │   │   │   ├── ConsultaAPI.java           # Servicio para consumir API Gutendex
│   │   │   │   └── LibroService.java          # Servicio de lógica de negocio
│   │   │   └── principal/
│   │   │       └── Principal.java             # Menú interactivo en consola
│   │   └── resources/
│   │       └── application.properties         # Configuración de la aplicación
│   └── test/                                   # Tests de la aplicación
├── pom.xml                                     # Configuración de Maven
└── README.md                                   # Este archivo
```

## Dependencias

Las siguientes dependencias están configuradas en el `pom.xml`:

- **Spring Boot Starter Data JPA**: Para la persistencia de datos con Hibernate/JPA
- **PostgreSQL Driver**: Controlador JDBC para PostgreSQL
- **Jackson Databind**: Para el mapeo de JSON a objetos Java
- **Spring Boot Starter Test**: Para pruebas unitarias

## Modelos de Datos

### Entidad: Autor

| Campo | Columna BD | Tipo | Descripción |
|-------|-----------|------|-------------|
| id | id | SERIAL | Identificador único (PK) |
| nombre | name | VARCHAR(255) | Nombre del autor (único) |
| anioNacimiento | birth_year | INTEGER | Año de nacimiento |
| anioFallecimiento | death_year | INTEGER | Año de fallecimiento (nullable) |

**Tabla**: `authors`
**Índices**: `idx_authors_name` en la columna `name`

### Entidad: Libro

| Campo | Columna BD | Tipo | Descripción |
|-------|-----------|------|-------------|
| id | id | SERIAL | Identificador único (PK) |
| titulo | title | VARCHAR(255) | Título del libro |
| autor | author_id | INTEGER (FK) | Referencia al autor |
| idioma | language | VARCHAR(10) | Código del idioma (ej: "en", "es") |
| numeroDescargas | download_count | BIGINT | Cantidad de descargas |

**Tabla**: `books`
**Índices**: 
- `idx_books_author_id` en la columna `author_id`
- `idx_books_language` en la columna `language`

## API Utilizada

**API Gutendex**: https://gutendex.com/

La aplicación realiza solicitudes GET a:
- `https://gutendex.com/books?search={titulo}` - Búsqueda de libros por título

### Ejemplo de Respuesta JSON

```json
{
  "results": [
    {
      "title": "Orgullo y Prejuicio",
      "authors": [
        {
          "name": "Jane Austen",
          "birth_year": 1775,
          "death_year": 1817
        }
      ],
      "languages": ["en"],
      "download_count": 15000
    }
  ]
}
```

## Consultas Derivadas (Derived Queries)

El proyecto utiliza derived queries en los repositorios JPA para consultas personalizadas:

```java
// Encontrar autores vivos en un año específico
List<Autor> encontrarAutoresVivosEnAnio(Integer anio);

// Contar libros por idioma
Long contarLibrosPorIdioma(String idioma);

// Encontrar libros por idioma
List<Libro> findByIdioma(String idioma);
```

## Anotaciones Utilizadas

### JSON Processing
- `@JsonIgnoreProperties`: Ignora propiedades JSON desconocidas
- `@JsonAlias`: Mapea propiedades JSON con diferentes nombres en Java

### JPA/Persistence
- `@Entity`: Define una clase como entidad persistente
- `@Table`: Especifica el nombre de la tabla
- `@Id`: Define la clave primaria
- `@GeneratedValue`: Configuración de generación automática de IDs
- `@Column`: Define propiedades de columnas
- `@ManyToOne`: Relación muchos a uno
- `@JoinColumn`: Especifica la columna de clave foránea

## Ejemplo de Uso

### 1. Buscar un libro

```
Opción seleccionada: 1
Ingresa el título del libro a buscar: 1984

✓ Libro guardado exitosamente:
========== LIBRO ==========
Título: 1984
Autor: George Orwell
Idioma: en
Descargas: 45000
===========================
```

### 2. Listar autores vivos en 1945

```
Opción seleccionada: 4
Ingresa el año para buscar autores vivos: 1945

========== AUTORES VIVOS EN 1945 ==========
Autor{id=1, nombre='George Orwell', anioNacimiento=1903, anioFallecimiento=1950}
Autor{id=2, nombre='J.R.R. Tolkien', anioNacimiento=1892, anioFallecimiento=1973}
```

### 3. Estadísticas por idioma

```
Opción seleccionada: 6
Ingresa el código del idioma para ver estadísticas: en

Total de libros en idioma 'en': 12
```

## Licencia

Este proyecto es de código abierto y está disponible bajo la licencia MIT.

## Autor

- GV- Jean Pool
- Desarrollado como parte del Challenge ONE de Alura.
