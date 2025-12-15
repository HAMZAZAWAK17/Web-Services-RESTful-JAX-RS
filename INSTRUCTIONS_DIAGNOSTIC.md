# 🔧 Instructions pour Diagnostiquer le Problème

## Situation Actuelle
- ✅ La table STUDENTS contient 8 étudiants
- ❌ L'API retourne `[]` (tableau vide)
- ❌ La page web affiche une liste vide

## 🎯 Étapes à Suivre

### Étape 1 : Recompiler et Redéployer

Les logs ont été ajoutés au code. Il faut recompiler :

#### Option A : Avec Eclipse/IntelliJ
1. Clic droit sur le projet → `Maven` → `Update Project`
2. Clic droit sur le projet → `Run As` → `Maven build...`
3. Goals: `clean package`
4. Cliquez sur `Run`

#### Option B : Manuellement (si Maven est installé)
```powershell
cd c:\Users\Hamza\Desktop\estem-2025\Tp\REST-SOAP-workspace\microservice-simple
mvn clean package
```

#### Option C : Sans Maven
Copiez simplement les fichiers `.java` modifiés et redémarrez WildFly.

### Étape 2 : Redéployer sur WildFly

```powershell
# Arrêter WildFly (Ctrl+C dans la console)

# Supprimer l'ancien déploiement
del C:\wildfly\standalone\deployments\microservice-simple.war
del C:\wildfly\standalone\deployments\microservice-simple.war.deployed

# Copier le nouveau WAR
copy target\microservice-simple.war C:\wildfly\standalone\deployments\

# Redémarrer WildFly
cd C:\wildfly\bin
standalone.bat
```

### Étape 3 : Tester l'API et Regarder les Logs

1. **Ouvrez la console WildFly** (là où vous avez lancé `standalone.bat`)

2. **Dans votre navigateur**, allez à :
   ```
   http://localhost:8081/microservice-simple/students
   ```

3. **Dans la console WildFly**, vous devriez voir :
   ```
   🔍 [StudentController] Appel de getAllStudents()
   🔍 [DaoImpl] Début de getAllStudent()
   📝 [DaoImpl] SQL: SELECT * FROM STUDENTS ORDER BY ID_STUDENT
   ✅ [DaoImpl] Connexion établie et requête exécutée
   👤 [DaoImpl] Premier étudiant: Student [idStudent=1, firstName=Hamza, ...]
   ✅ [DaoImpl] 8 étudiant(s) récupéré(s)
   🏁 [DaoImpl] Fin de getAllStudent() - Retour de 8 étudiants
   📊 [StudentController] Nombre d'étudiants récupérés: 8
   ✅ [StudentController] Premier étudiant: Student [...]
   ```

### Étape 4 : Analyser les Résultats

#### ✅ Si vous voyez "8 étudiant(s) récupéré(s)"
→ **Le problème est résolu !** L'API fonctionne maintenant.
→ Rechargez la page `students.html` (Ctrl+F5)

#### ❌ Si vous voyez "0 étudiant(s) récupéré(s)"
→ **Problème de connexion à la base**
→ Passez à l'Étape 5

#### ❌ Si vous voyez une erreur rouge
→ **Erreur de connexion ou SQL**
→ Copiez l'erreur complète et envoyez-la moi

#### ❌ Si vous ne voyez AUCUN log
→ **L'API n'est pas appelée**
→ Vérifiez que l'URL est correcte

---

## 🧪 Étape 5 : Tester la Connexion Directement

Si le problème persiste, testez la connexion à la base :

### Option A : Avec Java
Exécutez le programme de test :

```powershell
cd c:\Users\Hamza\Desktop\estem-2025\Tp\REST-SOAP-workspace\microservice-simple\src\main\java
javac -cp "C:\wildfly\modules\system\layers\base\com\mysql\main\mysql-connector-java-*.jar" dao/TestConnection.java
java -cp ".;C:\wildfly\modules\system\layers\base\com\mysql\main\mysql-connector-java-*.jar" dao.TestConnection
```

### Option B : Avec MySQL Workbench ou ligne de commande
```sql
-- Se connecter
mysql -u root -p

-- Utiliser la base
USE DB_SDDI_ESTEM;

-- Vérifier les données
SELECT * FROM STUDENTS;
```

---

## 🔍 Problèmes Possibles

### Problème 1 : Mot de passe MySQL incorrect
Si vous avez un mot de passe MySQL, modifiez `DaoImpl.java` :
```java
private final String PASSWORD = "votre_mot_de_passe";  // ← Changez ici
```

### Problème 2 : Port MySQL différent
Si MySQL n'est pas sur le port 3306, modifiez l'URL :
```java
private final String URL = "jdbc:mysql://localhost:VOTRE_PORT/DB_SDDI_ESTEM?...";
```

### Problème 3 : MySQL n'est pas démarré
Vérifiez que MySQL tourne :
```powershell
Get-Process mysqld
```

Si ce n'est pas le cas, démarrez-le :
```powershell
net start MySQL80  # ou le nom de votre service MySQL
```

### Problème 4 : Driver MySQL manquant
Vérifiez que le fichier JAR MySQL est dans WildFly :
```
C:\wildfly\modules\system\layers\base\com\mysql\main\
```

---

## 📋 Checklist

Après avoir suivi ces étapes, vérifiez :

- [ ] Le code a été recompilé
- [ ] Le WAR a été redéployé
- [ ] WildFly a été redémarré
- [ ] L'URL `http://localhost:8081/microservice-simple/students` a été testée
- [ ] Les logs WildFly ont été consultés
- [ ] MySQL est démarré
- [ ] La connexion à MySQL fonctionne

---

## 🆘 Besoin d'Aide ?

Envoyez-moi une capture d'écran de :
1. **La console WildFly** après avoir accédé à `/students`
2. **Le résultat dans le navigateur** pour `/students`
3. **Le résultat de** `SELECT * FROM STUDENTS;` dans MySQL

Avec ces informations, je pourrai identifier le problème exact ! 🎯
