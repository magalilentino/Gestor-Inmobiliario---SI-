@echo off
echo Instalando base de datos inmobiliaria...
echo.

REM Verificar si MySQL está instalado
mysql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: MySQL no está instalado o no está en el PATH
    echo Por favor instale MySQL Server primero
    pause
    exit /b 1
)

echo Creando base de datos...
mysql -u root -p -e "CREATE DATABASE IF NOT EXISTS inmobiliaria CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"

echo Importando estructura y datos...
mysql -u root -p inmobiliaria < inmobiliaria_backup.sql

echo.
echo ¡Base de datos instalada correctamente!
echo.
echo Configuración para Spring Boot:
echo spring.datasource.url=jdbc:mysql://localhost:3306/inmobiliaria
echo spring.datasource.username=root
echo spring.datasource.password=TU_PASSWORD
echo.
pause