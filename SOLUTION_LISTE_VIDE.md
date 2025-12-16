# 🔧 Solution: Liste des Étudiants Vide

## 🔍 Diagnostic du Problème

La liste des étudiants est vide pour l'une de ces raisons:

### 1. ❌ La base de données n'existe pas
### 2. ❌ La table STUDENTS est vide
### 3. ❌ Problème de connexion à la base de données
### 4. ❌ L'application n'est pas correctement déployée

---

## ✅ Solution Étape par Étape

### Étape 1: Vérifier MySQL

```powershell
# Vérifier si MySQL est en cours d'exécution
Get-Service -Name MySQL*
```

Si MySQL n'est pas démarré:
```powershell
# Démarrer MySQL
Start-Service MySQL80  # ou MySQL57, selon votre version
```

---

### Étape 2: Créer/Peupler la Base de Données

**Option A: Via MySQL Workbench (Recommandé)**
1. Ouvrir MySQL Workbench
2. Se connecter à votre serveur local (localhost:3306)
3. Ouvrir le fichier `database.sql`
4. Exécuter tout le script (⚡ icône éclair ou Ctrl+Shift+Enter)
5. Vérifier que 8 étudiants ont été insérés

**Option B: Via Ligne de Commande**
```powershell
# Se connecter à MySQL
mysql -u root -p

# Exécuter le script
source C:\Users\Hamza\Desktop\estem-2025\Tp\REST-SOAP-workspace\microservice-simple\database.sql

# Vérifier les données
USE DB_SDDI_ESTEM;
SELECT * FROM STUDENTS;
```

Vous devriez voir **8 étudiants** dans la table.

---

### Étape 3: Vérifier la Connexion depuis Java

Exécuter la classe de test pour vérifier la connexion:

```powershell
cd C:\Users\Hamza\Desktop\estem-2025\Tp\REST-SOAP-workspace\microservice-simple

# Compiler et exécuter le test
mvn clean compile
mvn exec:java -Dexec.mainClass="dao.TestDao"
```

**Résultat attendu:**
```
========================================
   TEST DU DAO - Gestion des Étudiants
========================================

📋 Test 1: Récupération de tous les étudiants
----------------------------------------------
Student [idStudent=1, firstName=Hamza, lastName=BENALI, birthDate=2000-05-15]
Student [idStudent=2, firstName=Fatima, lastName=ZAHRA, birthDate=1999-08-26]
...
```

Si vous voyez les étudiants ici, la connexion fonctionne! ✅

---

### Étape 4: Recompiler et Redéployer

```powershell
# 1. Nettoyer et compiler
mvn clean package

# 2. Arrêter WildFly (Ctrl+C dans le terminal WildFly)

# 3. Supprimer l'ancien déploiement
Remove-Item C:\wildfly\standalone\deployments\microservice-simple.war -ErrorAction SilentlyContinue
Remove-Item C:\wildfly\standalone\deployments\microservice-simple.war.deployed -ErrorAction SilentlyContinue
Remove-Item C:\wildfly\standalone\deployments\microservice-simple.war.failed -ErrorAction SilentlyContinue

# 4. Copier le nouveau WAR
Copy-Item target\microservice-simple.war C:\wildfly\standalone\deployments\

# 5. Redémarrer WildFly
cd C:\wildfly\bin
.\standalone.bat
```

---

### Étape 5: Tester l'API REST

**Test 1: API directe**
Ouvrir dans le navigateur:
```
http://localhost:8081/microservice-simple/students
```

Vous devriez voir un JSON avec les 8 étudiants:
```json
[
  {
    "idStudent": 1,
    "firstName": "Hamza",
    "lastName": "BENALI",
    "birthDate": "2000-05-15"
  },
  ...
]
```

**Test 2: Page HTML**
```
http://localhost:8081/microservice-simple/students.html
```

La liste devrait maintenant s'afficher! 🎉

---

## 🔍 Diagnostic Avancé

### Vérifier les Logs WildFly

Ouvrir le fichier de log:
```
C:\wildfly\standalone\log\server.log
```

Chercher les messages de `[StudentController]` et `[DaoImpl]`:
- ✅ `Nombre d'étudiants récupérés: 8` → Tout fonctionne!
- ❌ `Nombre d'étudiants récupérés: 0` → La table est vide
- ❌ Erreur SQL → Problème de connexion

### Vérifier la Console du Navigateur

1. Ouvrir `students.html`
2. Appuyer sur F12 pour ouvrir les DevTools
3. Aller dans l'onglet **Console**
4. Aller dans l'onglet **Network**
5. Rafraîchir la page (F5)
6. Chercher la requête `students`

**Résultat attendu:**
- Status: `200 OK`
- Response: JSON avec les étudiants

**Si erreur:**
- Status: `500` → Erreur serveur (voir logs WildFly)
- Status: `404` → URL incorrecte
- CORS error → Problème de configuration

---

## 🎯 Checklist Finale

Avant de dire que ça fonctionne, vérifier:

- [ ] MySQL est démarré
- [ ] La base `DB_SDDI_ESTEM` existe
- [ ] La table `STUDENTS` contient 8 étudiants
- [ ] Le test `TestDao` affiche les étudiants
- [ ] WildFly est démarré sans erreurs
- [ ] L'URL `http://localhost:8081/microservice-simple/students` retourne du JSON
- [ ] La page `students.html` affiche la liste

---

## 🚨 Si Ça Ne Fonctionne Toujours Pas

### Vérification Manuelle de la Base de Données

```sql
-- Se connecter à MySQL
USE DB_SDDI_ESTEM;

-- Vérifier la structure de la table
DESCRIBE STUDENTS;

-- Compter les étudiants
SELECT COUNT(*) FROM STUDENTS;

-- Afficher tous les étudiants
SELECT * FROM STUDENTS;
```

### Réinitialisation Complète

Si vraiment rien ne fonctionne:

```powershell
# 1. Supprimer et recréer la base
mysql -u root -p -e "DROP DATABASE IF EXISTS DB_SDDI_ESTEM;"
mysql -u root -p < database.sql

# 2. Nettoyer complètement le projet
mvn clean
Remove-Item target -Recurse -Force -ErrorAction SilentlyContinue

# 3. Recompiler
mvn package

# 4. Nettoyer WildFly
Remove-Item C:\wildfly\standalone\deployments\* -Force

# 5. Redéployer
Copy-Item target\microservice-simple.war C:\wildfly\standalone\deployments\

# 6. Redémarrer WildFly
# (Ctrl+C puis relancer standalone.bat)
```

---

## 📞 Besoin d'Aide?

Si le problème persiste, fournir:
1. Le résultat de `SELECT COUNT(*) FROM STUDENTS;`
2. Les logs WildFly (dernières 50 lignes)
3. La console du navigateur (onglet Network)
4. Le résultat de `mvn exec:java -Dexec.mainClass="dao.TestDao"`

Bonne chance! 🍀
