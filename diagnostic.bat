@echo off
echo ========================================
echo   DIAGNOSTIC - Liste Etudiants Vide
echo ========================================
echo.

echo 1. Verification de MySQL...
echo.
sc query MySQL80 | findstr "RUNNING"
if %errorlevel% neq 0 (
    echo [ERREUR] MySQL n'est pas demarre!
    echo Solution: Demarrer MySQL depuis les services Windows
    pause
    exit /b 1
)
echo [OK] MySQL est en cours d'execution
echo.

echo 2. Test de connexion a la base de donnees...
echo.
echo Veuillez executer cette commande manuellement dans MySQL Workbench:
echo.
echo USE DB_SDDI_ESTEM;
echo SELECT COUNT(*) as total_etudiants FROM STUDENTS;
echo.
echo Si le resultat est 0, executez le script database.sql
echo.

echo 3. Verification du fichier WAR...
echo.
if exist "target\microservice-simple.war" (
    echo [OK] Le fichier WAR existe
    dir target\microservice-simple.war | findstr "microservice"
) else (
    echo [ERREUR] Le fichier WAR n'existe pas!
    echo Solution: Compiler le projet avec Maven
)
echo.

echo 4. Verification du deploiement WildFly...
echo.
if exist "C:\wildfly\standalone\deployments\microservice-simple.war" (
    echo [OK] L'application est deployee sur WildFly
    dir "C:\wildfly\standalone\deployments\microservice-simple.war*"
) else (
    echo [ERREUR] L'application n'est pas deployee!
    echo Solution: Copier le WAR vers WildFly
)
echo.

echo ========================================
echo   ACTIONS A EFFECTUER
echo ========================================
echo.
echo 1. Ouvrir MySQL Workbench
echo 2. Se connecter a localhost:3306
echo 3. Executer: USE DB_SDDI_ESTEM;
echo 4. Executer: SELECT COUNT(*) FROM STUDENTS;
echo.
echo Si le resultat est 0:
echo    - Ouvrir le fichier database.sql
echo    - Executer tout le script
echo    - Verifier que 8 etudiants sont inseres
echo.
echo 5. Redemarrer WildFly
echo 6. Tester: http://localhost:8081/microservice-simple/students
echo.

pause
