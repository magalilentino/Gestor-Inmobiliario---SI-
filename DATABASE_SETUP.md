# 🏠 Gestor Inmobiliario - Configuración de Base de Datos

## 🚀 Instalación Rápida

### Prerequisitos
- MySQL Server 8.0 o superior
- Java 17 o superior
- Maven

### 📋 Pasos para configurar la base de datos

#### Opción 1: Instalación Automática (Windows)
```bash
# Ejecutar el script de instalación
instalar_bd.bat
```

#### Opción 2: Instalación Manual
```bash
# 1. Crear la base de datos
mysql -u root -p -e "CREATE DATABASE inmobiliaria CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"

# 2. Importar estructura y datos
mysql -u root -p inmobiliaria < inmobiliaria_backup.sql
```

#### Opción 3: Docker (Recomendado para desarrollo)
```bash
# Iniciar con Docker Compose
docker-compose up -d
```

### ⚙️ Configuración de Spring Boot

Actualiza tu `application.properties`:

```properties
# Base de datos
spring.datasource.url=jdbc:mysql://localhost:3306/inmobiliaria
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Hibernate
spring.jpa.hibernate.ddl-auto=none
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect
spring.jpa.hibernate.naming.physical-strategy=org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl
spring.jpa.hibernate.naming.implicit-strategy=org.hibernate.boot.model.naming.ImplicitNamingStrategyLegacyJpaImpl
```

### 🗂️ Estructura de la Base de Datos

La base de datos `inmobiliaria` contiene las siguientes tablas:
- `agente` - Usuarios del sistema
- `propiedad` - Propiedades inmobiliarias
- `inquilino` - Información de inquilinos
- `alquiler` - Contratos de alquiler
- `pago` - Registro de pagos
- Y más...

### 🏃‍♂️ Ejecutar la aplicación

```bash
# Compilar y ejecutar
./mvnw spring-boot:run

# La aplicación estará disponible en:
# http://localhost:8080
```

## 📋 Usuarios de prueba

Después de importar la base de datos, puedes usar estos usuarios para probar:
- **DNI**: [Ver en la tabla agente]
- **Password**: [Según lo configurado]

## 🔧 Solución de problemas

### Error de conexión a MySQL
1. Verificar que MySQL esté ejecutándose
2. Comprobar usuario y contraseña
3. Verificar que la base de datos `inmobiliaria` existe

### Error de columnas
Si aparecen errores sobre nombres de columnas, verificar que estén configuradas las naming strategies en `application.properties`.

## 📞 Soporte

Si tienes problemas con la configuración, verifica:
1. ✅ MySQL Server instalado y ejecutándose
2. ✅ Base de datos `inmobiliaria` creada
3. ✅ Datos importados desde `inmobiliaria_backup.sql`
4. ✅ Configuración correcta en `application.properties`