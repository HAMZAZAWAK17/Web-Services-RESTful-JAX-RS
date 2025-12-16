# 🎓 Microservice de Gestion des Étudiants

Application REST JAX-RS pour la gestion des étudiants avec WildFly et MySQL.

---

## 🚨 PROBLÈME: Liste des Étudiants Vide?

### ⚡ Solution Rapide (3 minutes)

1. **Peupler la base de données:**
   - Ouvrir **MySQL Workbench**
   - Exécuter le fichier **`fix_empty_list.sql`**
   - Vérifier: 8 étudiants insérés ✅

2. **Tester l'API:**
   - Ouvrir: `http://localhost:8081/microservice-simple/test-diagnostic.html`
   - Cliquer: **"GET /students"**
   - Résultat attendu: **8 étudiants affichés**

3. **Utiliser l'application:**
   - Ouvrir: `http://localhost:8081/microservice-simple/students.html`
   - La liste devrait maintenant s'afficher! 🎉

📖 **Guide détaillé:** Voir [`SOLUTION_RAPIDE.md`](SOLUTION_RAPIDE.md)

---

## 📁 Structure du Projet

```
microservice-simple/
├── src/main/
│   ├── java/
│   │   ├── dao/              # Couche d'accès aux données
│   │   │   ├── IDao.java
│   │   │   ├── DaoImpl.java
│   │   │   ├── TestDao.java
│   │   │   └── TestConnection.java
│   │   ├── model/            # Modèles de données
│   │   │   └── Student.java
│   │   └── web/              # Contrôleurs REST
│   │       ├── SimpleRest.java
│   │       └── StudentController.java
│   └── webapp/
│       ├── index.html        # Page d'accueil
│       ├── students.html     # Gestion des étudiants
│       └── test-diagnostic.html  # 🔍 Page de diagnostic
├── database.sql              # Script de création de la base
├── fix_empty_list.sql        # 🔧 Script de correction
└── pom.xml                   # Configuration Maven
```

---

## 🚀 Démarrage Rapide

### Prérequis
- ☕ Java JDK 8+
- 🐬 MySQL 5.7+
- 🐺 WildFly 10+
- 📦 Maven 3.6+

### Installation

**1. Créer la base de données:**
```sql
-- Dans MySQL Workbench
source database.sql
```

**2. Compiler le projet:**
```powershell
mvn clean package
```

**3. Déployer sur WildFly:**
```powershell
copy target\microservice-simple.war C:\wildfly\standalone\deployments\
```

**4. Démarrer WildFly:**
```powershell
cd C:\wildfly\bin
.\standalone.bat
```

**5. Accéder à l'application:**
- 🏠 Accueil: `http://localhost:8081/microservice-simple/`
- 👥 Étudiants: `http://localhost:8081/microservice-simple/students.html`
- 🔍 Diagnostic: `http://localhost:8081/microservice-simple/test-diagnostic.html`

---

## 🎯 Fonctionnalités

### API REST (JAX-RS)

| Méthode | Endpoint | Description |
|---------|----------|-------------|
| `GET` | `/students` | Liste tous les étudiants |
| `GET` | `/students/{id}` | Récupère un étudiant par ID |
| `POST` | `/students` | Ajoute un nouvel étudiant |
| `PUT` | `/students/{id}` | Modifie un étudiant |
| `DELETE` | `/students/{id}` | Supprime un étudiant |
| `GET` | `/students/debug-test` | Endpoint de test |

### Interface Web

- ✅ **Liste des étudiants** avec affichage en temps réel
- ✅ **Ajout** d'étudiants via formulaire
- ✅ **Modification** en ligne
- ✅ **Suppression** avec confirmation
- ✅ **Validation** des données
- ✅ **Messages** de succès/erreur

---

## 🔧 Outils de Diagnostic

### 1. Page de Diagnostic Web
**Fichier:** `test-diagnostic.html`  
**URL:** `http://localhost:8081/microservice-simple/test-diagnostic.html`

**Fonctionnalités:**
- 🔌 Test de connexion à l'API
- 📋 Récupération de la liste des étudiants
- 🧪 Test de l'endpoint de debug
- ➕ Ajout d'étudiant de test
- 📊 Affichage des résultats en temps réel

### 2. Script de Diagnostic Windows
**Fichier:** `diagnostic.bat`  
**Utilisation:** Double-cliquer sur le fichier

**Vérifications:**
- ✅ MySQL est démarré
- ✅ Fichier WAR existe
- ✅ Application déployée sur WildFly

### 3. Script SQL de Correction
**Fichier:** `fix_empty_list.sql`  
**Utilisation:** Exécuter dans MySQL Workbench

**Actions:**
- Vérifie la base de données
- Compte les étudiants
- Insère 8 étudiants de test si nécessaire

### 4. Test Java DAO
**Fichier:** `src/main/java/dao/TestDao.java`  
**Utilisation:**
```powershell
mvn exec:java -Dexec.mainClass="dao.TestDao"
```

**Tests:**
- Connexion à la base de données
- Récupération de tous les étudiants
- Ajout d'un étudiant
- Modification d'un étudiant

---

## 📚 Documentation

| Fichier | Description |
|---------|-------------|
| [`SOLUTION_RAPIDE.md`](SOLUTION_RAPIDE.md) | 🚀 Guide de solution rapide (3 min) |
| [`SOLUTION_LISTE_VIDE.md`](SOLUTION_LISTE_VIDE.md) | 📖 Guide détaillé complet |
| [`COMPILATION.md`](COMPILATION.md) | 🔨 Instructions de compilation |
| [`CORRECTIONS.md`](CORRECTIONS.md) | ✅ Historique des corrections |
| [`FIX_RESOURCE_ERROR.md`](FIX_RESOURCE_ERROR.md) | 🔧 Correction erreur ressources |

---

## 🗄️ Configuration Base de Données

**Fichier:** `src/main/java/dao/DaoImpl.java`

```java
private final String URL = "jdbc:mysql://localhost:3306/DB_SDDI_ESTEM";
private final String USER = "root";
private final String PASSWORD = "";
```

**Structure de la table:**
```sql
CREATE TABLE STUDENTS (
    ID_STUDENT INT AUTO_INCREMENT PRIMARY KEY,
    FIRST_NAME_STUDENT VARCHAR(100) NOT NULL,
    LAST_NAME_STUDENT VARCHAR(100) NOT NULL,
    DATE_BIRTH_STUDENT DATE NOT NULL,
    CREATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UPDATED_AT TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
```

---

## 🧪 Tests

### Test API avec cURL
```powershell
# GET - Liste des étudiants
curl http://localhost:8081/microservice-simple/students

# GET - Un étudiant
curl http://localhost:8081/microservice-simple/students/1

# POST - Ajouter un étudiant
curl -X POST http://localhost:8081/microservice-simple/students `
  -H "Content-Type: application/x-www-form-urlencoded" `
  -d "firstName=Jean&lastName=DUPONT&birthDate=2000-01-01"

# PUT - Modifier un étudiant
curl -X PUT http://localhost:8081/microservice-simple/students/1 `
  -H "Content-Type: application/x-www-form-urlencoded" `
  -d "firstName=Jean&lastName=MARTIN&birthDate=2000-01-01"

# DELETE - Supprimer un étudiant
curl -X DELETE http://localhost:8081/microservice-simple/students/1
```

### Test avec Postman
Importer la collection depuis: `postman_collection.json` (à créer)

---

## 🐛 Dépannage

### Problème: Liste vide
➡️ **Solution:** Voir [`SOLUTION_RAPIDE.md`](SOLUTION_RAPIDE.md)

### Problème: Erreur 404
**Cause:** Application non déployée  
**Solution:**
```powershell
copy target\microservice-simple.war C:\wildfly\standalone\deployments\
```

### Problème: Erreur de connexion MySQL
**Cause:** MySQL non démarré  
**Solution:**
```powershell
Start-Service MySQL80
```

### Problème: Port 8081 déjà utilisé
**Cause:** WildFly déjà en cours d'exécution  
**Solution:** Arrêter WildFly (Ctrl+C) et relancer

---

## 📊 Architecture

```
┌─────────────────┐
│   Navigateur    │
│  (students.html)│
└────────┬────────┘
         │ HTTP/REST
         ▼
┌─────────────────┐
│ StudentController│ ← JAX-RS
│   (REST API)    │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│    DaoImpl      │ ← JDBC
│  (Data Access)  │
└────────┬────────┘
         │
         ▼
┌─────────────────┐
│   MySQL DB      │
│  (STUDENTS)     │
└─────────────────┘
```

---

## 👥 Auteur

**Hamza**  
ESTEM - 2025  
TP Web Services RESTful JAX-RS

---

## 📝 Licence

Projet éducatif - ESTEM 2025

---

## 🎉 Statut

✅ **Fonctionnel** - Toutes les fonctionnalités CRUD opérationnelles  
✅ **Testé** - API REST validée  
✅ **Documenté** - Guides de dépannage disponibles

---

**Besoin d'aide?** Consulter [`SOLUTION_RAPIDE.md`](SOLUTION_RAPIDE.md) ou utiliser `test-diagnostic.html` 🔍
