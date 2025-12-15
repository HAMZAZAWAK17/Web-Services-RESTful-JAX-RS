# 🔍 Diagnostic: Liste Vide des Étudiants

## Problème
La page `students.html` affiche une liste vide alors que la table STUDENTS contient des données.

## 🧪 Étapes de Diagnostic

### 1. Tester l'API Directement dans le Navigateur

Ouvrez cette URL dans votre navigateur :
```
http://localhost:8081/microservice-simple/students
```

**Résultats possibles :**

#### ✅ Si vous voyez du JSON avec des données :
```json
[
  {
    "idStudent": 1,
    "firstName": "John",
    "lastName": "Doe",
    "birthDate": "2000-01-01"
  }
]
```
→ **L'API fonctionne !** Le problème vient du JavaScript dans `students.html`

#### ❌ Si vous voyez `[]` (tableau vide) :
→ **Problème de base de données** - Voir section "Vérifier la Base de Données"

#### ❌ Si vous voyez une erreur 404 :
→ **L'API n'est pas accessible** - Voir section "Vérifier le Déploiement"

#### ❌ Si vous voyez une erreur 500 :
→ **Erreur serveur** - Voir les logs WildFly

---

### 2. Utiliser la Page de Test

Accédez à :
```
http://localhost:8081/microservice-simple/test-api.html
```

Cette page va :
- Tester l'API GET /students
- Afficher les erreurs détaillées
- Montrer les URLs utilisées
- Afficher les données reçues

Cliquez sur **"Tester GET /students"** et regardez le résultat.

---

### 3. Vérifier les Logs WildFly

Ouvrez la console WildFly (là où vous avez lancé `standalone.bat`) et cherchez :

```
🔍 [StudentController] Appel de getAllStudents()
📊 [StudentController] Nombre d'étudiants récupérés: X
```

**Si vous voyez :**
- `Nombre d'étudiants récupérés: 0` → Problème de base de données
- `Nombre d'étudiants récupérés: X` (X > 0) → L'API fonctionne, problème dans le frontend
- Aucun log → L'API n'est pas appelée

---

### 4. Vérifier la Base de Données

#### A. Vérifier que MySQL est démarré
```powershell
# Vérifier si MySQL tourne
Get-Process mysqld
```

#### B. Se connecter à MySQL et vérifier les données
```sql
-- Se connecter
mysql -u root -p

-- Utiliser la base
USE DB_SDDI_ESTEM;

-- Vérifier la table
DESCRIBE STUDENTS;

-- Compter les étudiants
SELECT COUNT(*) FROM STUDENTS;

-- Afficher tous les étudiants
SELECT * FROM STUDENTS;
```

**Vérifiez que :**
- ✅ La base `DB_SDDI_ESTEM` existe
- ✅ La table `STUDENTS` existe
- ✅ La table contient des données
- ✅ Les colonnes sont : `ID_STUDENT`, `FIRST_NAME_STUDENT`, `LAST_NAME_STUDENT`, `DATE_BIRTH_STUDENT`

---

### 5. Vérifier la Console du Navigateur

1. Ouvrez `students.html`
2. Appuyez sur **F12** pour ouvrir les outils de développement
3. Allez dans l'onglet **Console**
4. Rechargez la page (**F5**)

**Cherchez des erreurs comme :**
- `Failed to fetch` → Problème de connexion à l'API
- `404 Not Found` → URL incorrecte
- `CORS error` → Problème de sécurité
- `SyntaxError: Unexpected token` → Problème de parsing JSON

---

## 🔧 Solutions Possibles

### Solution 1 : Problème de Connexion à la Base

Si la base de données ne retourne rien, vérifiez `DaoImpl.java` :

```java
private final String URL = "jdbc:mysql://localhost:3306/DB_SDDI_ESTEM?...";
private final String USER = "root";
private final String PASSWORD = "";  // ← Vérifiez le mot de passe
```

**Testez la connexion :**
```powershell
mysql -u root -p
# Entrez votre mot de passe (ou laissez vide)
```

### Solution 2 : Mauvais Nom de Colonnes

Vérifiez que les noms de colonnes dans `DaoImpl.java` correspondent à votre table :

```java
student.setFirstName(rs.getString("FIRST_NAME_STUDENT"));  // ← Doit correspondre
student.setLastName(rs.getString("LAST_NAME_STUDENT"));
student.setBirthDate(rs.getDate("DATE_BIRTH_STUDENT"));
```

### Solution 3 : Problème de Déploiement

Si l'API n'est pas accessible :

1. **Recompiler** :
   ```powershell
   mvn clean package
   ```

2. **Redéployer** :
   ```powershell
   copy target\microservice-simple.war C:\wildfly\standalone\deployments\
   ```

3. **Vérifier le déploiement** dans les logs WildFly :
   ```
   Deployed "microservice-simple.war"
   ```

### Solution 4 : Problème JavaScript

Si l'API retourne des données mais la page est vide, ouvrez la console du navigateur (F12) et vérifiez :

1. **L'URL appelée** :
   ```javascript
   fetch('students')  // ← Doit être relatif
   ```

2. **Le parsing JSON** :
   ```javascript
   .then(response => response.json())  // ← Doit réussir
   ```

3. **Les noms de propriétés** :
   ```javascript
   student.firstName  // ← Doit correspondre au JSON
   student.lastName
   student.birthDate
   ```

---

## 📋 Checklist de Vérification

- [ ] MySQL est démarré
- [ ] La base `DB_SDDI_ESTEM` existe
- [ ] La table `STUDENTS` contient des données
- [ ] WildFly est démarré
- [ ] L'application est déployée (pas d'erreur dans les logs)
- [ ] L'URL `http://localhost:8081/microservice-simple/students` retourne du JSON
- [ ] La console du navigateur ne montre pas d'erreur
- [ ] Les logs WildFly montrent "Nombre d'étudiants récupérés: X" (X > 0)

---

## 🆘 Besoin d'Aide ?

Envoyez-moi :
1. Le résultat de `http://localhost:8081/microservice-simple/students` dans le navigateur
2. Les logs de la console WildFly
3. Les erreurs dans la console du navigateur (F12)
4. Le résultat de `SELECT * FROM STUDENTS;` dans MySQL

Avec ces informations, je pourrai identifier le problème exact ! 🎯
