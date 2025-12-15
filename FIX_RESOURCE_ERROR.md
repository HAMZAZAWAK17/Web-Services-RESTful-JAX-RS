# 🔧 Solution au Problème "Could not find resource"

## Problème
```
RESTEASY003210: Could not find resource for full path: http://localhost:8081/microservice-simple/students.html
```

## Cause
Le fichier `students.html` n'était pas trouvé car les URLs utilisaient des chemins absolus incompatibles avec le contexte de l'application WildFly.

## ✅ Corrections Appliquées

### 1. Chemins Relatifs dans `students.html`
Tous les appels API ont été modifiés pour utiliser des chemins relatifs :

**Avant** :
```javascript
fetch('/students')              // ❌ Chemin absolu
fetch('/students/' + id)        // ❌ Chemin absolu
```

**Après** :
```javascript
fetch('students')               // ✅ Chemin relatif
fetch('students/' + id)         // ✅ Chemin relatif
```

### 2. Lien de Retour
Le lien "Retour à l'accueil" a été corrigé :

**Avant** :
```html
<a href='/'>← Retour à l'accueil</a>
```

**Après** :
```html
<a href='index.html'>← Retour à l'accueil</a>
```

### 3. Liens dans `index.html`
Tous les liens utilisent maintenant des chemins relatifs :

```html
<a href="hi" class="btn">Hi</a>
<a href="bonjour" class="btn">Bonjour</a>
<a href="students.html" class="btn">Gestion des Étudiants</a>
```

## 📁 Structure des Fichiers

```
src/main/webapp/
├── index.html          ← Page d'accueil
├── students.html       ← Page de gestion des étudiants
└── WEB-INF/
    └── web.xml
```

## 🚀 Comment Tester

1. **Recompiler le projet** (si nécessaire)
2. **Redéployer sur WildFly** :
   ```powershell
   # Copier le WAR
   copy target\microservice-simple.war C:\wildfly\standalone\deployments\
   ```

3. **Accéder aux URLs** :
   - Page d'accueil : `http://localhost:8081/microservice-simple/`
   - Gestion étudiants : `http://localhost:8081/microservice-simple/students.html`

## ✅ Vérification

Une fois redéployé, vous devriez voir :
- ✅ La page d'accueil avec 3 boutons
- ✅ La page de gestion des étudiants
- ✅ La liste des étudiants chargée depuis la base de données
- ✅ Les fonctionnalités CRUD fonctionnelles

## 🔍 Si le Problème Persiste

1. **Vérifier que le fichier existe** :
   ```powershell
   ls src\main\webapp\students.html
   ```

2. **Vérifier les logs WildFly** :
   ```
   C:\wildfly\standalone\log\server.log
   ```

3. **Vider le cache du navigateur** :
   - Ctrl + F5 (Windows)
   - Cmd + Shift + R (Mac)

4. **Redémarrer WildFly** :
   ```powershell
   # Arrêter
   Ctrl + C
   
   # Redémarrer
   cd C:\wildfly\bin
   standalone.bat
   ```

Tout devrait fonctionner maintenant ! 🎉
