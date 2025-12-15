# 🔧 Compilation Sans Maven

## Problème Résolu ✅

Le fichier `pom.xml` a été corrigé pour éviter les conflits avec WildFly :
- ✅ JAX-RS API en scope `provided` (fourni par WildFly)
- ✅ Servlet API en scope `provided` (fourni par WildFly)
- ✅ Suppression des dépendances RESTEasy (déjà dans WildFly)

---

## Option 1 : Compiler avec Eclipse

Si vous utilisez **Eclipse** :

1. **Importer le projet** :
   - `File` → `Import` → `Existing Maven Projects`
   - Sélectionnez le dossier `microservice-simple`
   - Cliquez sur `Finish`

2. **Mettre à jour Maven** :
   - Clic droit sur le projet → `Maven` → `Update Project`
   - Cochez `Force Update of Snapshots/Releases`
   - Cliquez sur `OK`

3. **Compiler** :
   - Clic droit sur le projet → `Run As` → `Maven build...`
   - Dans `Goals`, tapez : `clean package`
   - Cliquez sur `Run`

4. **Récupérer le WAR** :
   - Le fichier sera dans `target/microservice-simple.war`

---

## Option 2 : Compiler avec IntelliJ IDEA

Si vous utilisez **IntelliJ IDEA** :

1. **Ouvrir le projet** :
   - `File` → `Open`
   - Sélectionnez le dossier `microservice-simple`

2. **Recharger Maven** :
   - Ouvrez la vue `Maven` (à droite)
   - Cliquez sur l'icône de rechargement 🔄

3. **Compiler** :
   - Dans la vue Maven, double-cliquez sur :
     - `Lifecycle` → `clean`
     - `Lifecycle` → `package`

4. **Récupérer le WAR** :
   - Le fichier sera dans `target/microservice-simple.war`

---

## Option 3 : Compiler Manuellement (Sans Maven)

### Étape 1 : Créer la structure WAR

```powershell
# Créer les dossiers
New-Item -ItemType Directory -Force -Path "build\WEB-INF\classes"
New-Item -ItemType Directory -Force -Path "build\WEB-INF\lib"
```

### Étape 2 : Compiler le code Java

```powershell
# Compiler SimpleRest.java
javac -d build\WEB-INF\classes src\main\java\web\SimpleRest.java
```

**Note** : Si vous avez une erreur car les classes JAX-RS ne sont pas trouvées, vous devez ajouter les JARs de WildFly au classpath.

### Étape 3 : Copier les fichiers

```powershell
# Copier web.xml
Copy-Item src\main\webapp\WEB-INF\web.xml build\WEB-INF\

# Copier index.html
Copy-Item src\main\webapp\index.html build\
```

### Étape 4 : Créer le WAR

```powershell
# Aller dans le dossier build
cd build

# Créer le fichier WAR (nécessite jar.exe de Java)
jar -cvf ..\microservice-simple.war *

# Retourner au dossier principal
cd ..
```

Le fichier `microservice-simple.war` sera créé à la racine du projet.

---

## Option 4 : Utiliser le Maven Wrapper (Recommandé)

Si Maven n'est pas installé, vous pouvez utiliser le **Maven Wrapper** :

### Installation du Maven Wrapper

```powershell
# Télécharger et installer le wrapper
mvn -N io.takari:maven:wrapper
```

Ensuite, utilisez `mvnw.cmd` au lieu de `mvn` :

```powershell
.\mvnw.cmd clean package
```

---

## Option 5 : Installer Maven

### Télécharger Maven

1. Allez sur : https://maven.apache.org/download.cgi
2. Téléchargez `apache-maven-3.9.x-bin.zip`
3. Extrayez dans `C:\Program Files\Apache\maven`

### Configurer le PATH

```powershell
# Ajouter Maven au PATH (PowerShell en admin)
[Environment]::SetEnvironmentVariable("Path", $env:Path + ";C:\Program Files\Apache\maven\bin", "Machine")
```

### Vérifier l'installation

```powershell
mvn -version
```

### Compiler

```powershell
mvn clean package
```

---

## 🎯 Après la Compilation

Une fois le WAR créé, déployez-le sur WildFly :

```powershell
# Copier le WAR
copy microservice-simple.war C:\wildfly\standalone\deployments\

# Démarrer WildFly
cd C:\wildfly\bin
standalone.bat
```

---

## ✅ Vérification

Après le déploiement, vérifiez les logs WildFly. Vous devriez voir :

```
Deployed "microservice-simple.war" (runtime-name : "microservice-simple.war")
```

**Sans erreur** cette fois ! 🎉

Ensuite, testez :
- http://localhost:8080/microservice-simple/
- http://localhost:8080/microservice-simple/hi
- http://localhost:8080/microservice-simple/bonjour

---

## 🐛 Si vous avez encore des erreurs

Vérifiez que :
1. ✅ Le `pom.xml` a bien les scopes `provided`
2. ✅ WildFly est bien démarré
3. ✅ Le port 8080 n'est pas utilisé par une autre application
4. ✅ Java 11 ou supérieur est installé

Consultez les logs dans : `C:\wildfly\standalone\log\server.log`
