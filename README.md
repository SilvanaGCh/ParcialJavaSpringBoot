# Sistema de Ventas - API REST

API REST para gestión de ventas, clientes, productos e inventario desarrollada con Spring Boot.

## Tecnologías Utilizadas

- Java 17
- Spring Boot 3.4.0
- PostgreSQL (Supabase)
- Spring Data JPA
- Swagger/OpenAPI

## Endpoints

### Clientes

#### Listar Clientes
```
GET /clientes
```
Retorna lista de todos los clientes registrados.

#### Obtener Cliente
```
GET /clientes/{id}
```
Retorna un cliente específico por ID.

#### Crear Cliente
```
POST /clientes
```
Crea un nuevo cliente.

Body:
```json
{
    "nombre": "Juan Pérez",
    "email": "juan@email.com",
    "celular": "3001234567"
}
```

#### Actualizar Cliente
```
PUT /clientes/{id}
```
Actualiza un cliente existente.

Body:
```json
{
    "nombre": "Juan Pérez",
    "email": "juan@email.com",
    "celular": "3001234567"
}
```

#### Eliminar Cliente
```
DELETE /clientes/{id}
```
Elimina un cliente por ID.

### Productos

#### Listar Productos
```
GET /productos
```
Retorna lista de todos los productos.

#### Crear Producto
```
POST /productos
```
Crea un nuevo producto.

Body:
```json
{
    "nombre": "Laptop HP",
    "precio": 1500.00,
    "stock": 10,
    "descripcion": "Laptop HP Core i5"
}
```

#### Actualizar Producto
```
PUT /productos/{id}
```
Actualiza un producto existente.

Body:
```json
{
    "nombre": "Laptop HP",
    "precio": 1500.00,
    "stock": 10,
    "descripcion": "Laptop HP Core i5"
}
```

#### Actualizar Stock
```
PUT /productos/{id}/stock?cantidad={cantidad}
```
Actualiza el stock de un producto.

### Ventas

#### Listar Ventas
```
GET /ventas
```
Retorna lista de todas las ventas.

#### Obtener Venta
```
GET /ventas/{id}
```
Retorna una venta específica por ID.

#### Crear Venta
```
POST /ventas
```
Crea una nueva venta.

Body:
```json
{
    "clienteId": 1,
    "estado": "PENDIENTE",
    "detalles": [
        {
            "productoId": 1,
            "cantidad": 2,
            "precioUnitario": 1500.00
        }
    ]
}
```

### Detalles de Venta

#### Listar Detalles por Venta
```
GET /detalles-venta/venta/{ventaId}
```
Retorna los detalles de una venta específica.

#### Actualizar Cantidad
```
PUT /detalles-venta/{id}/cantidad?cantidad={cantidad}
```
Actualiza la cantidad de un detalle de venta.

## Respuestas

Todas las respuestas siguen este formato:
```json
{
    "message": "Success/Error message",
    "status": "HTTP Status",
    "data": {
        // Datos de la respuesta
    }
}
```

## Configuración Local

1. Clonar el repositorio
2. Configurar base de datos en `application.properties`
3. Ejecutar `mvn spring-boot:run`
4. Acceder a Swagger: http://localhost:8080/swagger-ui.html

## Documentación Adicional

Para más detalles sobre los endpoints y modelos, consultar la documentación de Swagger una vez iniciada la aplicación.
