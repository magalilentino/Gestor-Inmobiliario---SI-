@echo off
echo Instalando base de datos inmobiliaria limpia...
echo.

REM Configuración de MySQL
set MYSQL_USER=root
set MYSQL_PASS=1234
set DB_NAME=inmobiliaria
set SQL_FILE=inmobiliaria_backup.sql

REM Verificar si MySQL está instalado
mysql --version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERROR: MySQL no está instalado o no está en el PATH
    echo Por favor instale MySQL Server primero
    pause
    exit /b 1
)

echo Eliminando base de datos existente si existe...
mysql -u %MYSQL_USER% -p%MYSQL_PASS% --binary-mode=1 -e "DROP DATABASE IF EXISTS %DB_NAME%;"

echo Creando base de datos nueva...
mysql -u %MYSQL_USER% -p%MYSQL_PASS% --binary-mode=1 -e "CREATE DATABASE %DB_NAME% CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;"

echo Importando estructura y datos...
mysql -u %MYSQL_USER% -p%MYSQL_PASS% --binary-mode=1 %DB_NAME% < %SQL_FILE%

echo.
echo ¡Base de datos instalada correctamente y lista para Spring Boot!
echo.
echo Configuración para Spring Boot:
echo spring.datasource.url=jdbc:mysql://localhost:3306/%DB_NAME%
echo spring.datasource.username=%MYSQL_USER%
echo spring.datasource.password=%MYSQL_PASS%
echo.
pause
