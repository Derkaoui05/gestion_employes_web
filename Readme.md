# 🏢 Système de Gestion des Employés

Application web de gestion des employés avec suivi des avances et absences, développée avec Spring Boot et Thymeleaf.

![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.x-brightgreen)
![Java](https://img.shields.io/badge/Java-17+-orange)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)
![License](https://img.shields.io/badge/License-MIT-yellow)

## 📋 Table des Matières

- [Aperçu](#aperçu)
- [Fonctionnalités](#fonctionnalités)
- [Technologies Utilisées](#technologies-utilisées)
- [Prérequis](#prérequis)
- [Installation](#installation)
- [Configuration](#configuration)
- [Utilisation](#utilisation)
- [Structure du Projet](#structure-du-projet)
- [API Endpoints](#api-endpoints)
- [Base de Données](#base-de-données)
- [Captures d'Écran](#captures-décran)
- [Contribution](#contribution)
- [Auteur](#auteur)

## 🎯 Aperçu

Cette application permet de gérer efficacement les informations des employés, leurs avances sur salaire et leurs absences. Elle calcule automatiquement les salaires hebdomadaires en tenant compte des déductions.

## ✨ Fonctionnalités

### Gestion des Employés
- ✅ Ajouter, modifier et supprimer des employés
- ✅ Consulter la liste complète des employés
- ✅ Gestion des informations personnelles (CIN, Nom, Prénom, Salaire)

### Gestion des Avances
- 💰 Enregistrer les avances sur salaire
- 📅 Suivi des dates d'avance
- 📊 Calcul automatique du total des avances par employé

### Gestion des Absences
- 📉 Enregistrer les absences avec pénalités
- 📅 Suivi des dates d'absence
- 📊 Calcul automatique des pénalités totales

### Rapports et Calculs
- 📈 Calcul du salaire hebdomadaire net (Jeudi à Jeudi)
- 📑 Rapport hebdomadaire détaillé par employé
- 💵 Déductions automatiques (avances + pénalités d'absence)
- 📊 Vue d'ensemble des transactions par employé

## 🛠️ Technologies Utilisées

### Backend
- **Spring Boot 3.x** - Framework principal
- **Spring Data JPA** - Persistance des données
- **Spring Web MVC** - Architecture web
- **Hibernate** - ORM (Object-Relational Mapping)

### Frontend
- **Thymeleaf** - Moteur de template
- **Tailwind CSS** - Framework CSS
- **HTML5** - Structure
- **JavaScript** - Interactions dynamiques

### Base de Données
- **MySQL 8.0** - Système de gestion de base de données

### Outils
- **Maven** - Gestion des dépendances
- **Git** - Contrôle de version

## 📦 Prérequis

Avant de commencer, assurez-vous d'avoir installé :

- ☕ **Java JDK 17** ou supérieur
- 🗄️ **MySQL 8.0** ou supérieur
- 🔧 **Maven 3.6+**
- 💻 Un IDE (IntelliJ IDEA, Eclipse, VS Code)

## 🚀 Installation

### 1. Cloner le projet

```bash
git clone https://github.com/votre-username/gestion-employes.git
cd gestion-employes
```

### 2. Créer la base de données

Connectez-vous à MySQL et exécutez :

```sql
CREATE DATABASE gestion_employes;
USE gestion_employes;
```

### 3. Configurer l'application

Modifiez le fichier `src/main/resources/application.properties` :

```properties
# Configuration de la base de données
spring.datasource.url=jdbc:mysql://localhost:3306/gestion_employes
spring.datasource.username=votre_username
spring.datasource.password=votre_password
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# Configuration JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL8Dialect

# Configuration du serveur
server.port=8080

# Configuration Thymeleaf
spring.thymeleaf.cache=false
```

### 4. Installer les dépendances

```bash
mvn clean install
```

### 5. Lancer l'application

```bash
mvn spring-boot:run
```

L'application sera accessible à : **http://localhost:8080**

## ⚙️ Configuration

### Variables d'Environnement (Optionnel)

Vous pouvez également configurer l'application via des variables d'environnement :

```bash
export DB_URL=jdbc:mysql://localhost:3306/gestion_employes
export DB_USERNAME=root
export DB_PASSWORD=password
```

### Configuration Maven (pom.xml)

Principales dépendances :

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
    </dependency>
</dependencies>
```

## 💻 Utilisation

### Page d'Accueil

Accédez à `http://localhost:8080/` pour voir le tableau de bord principal avec :
- Gestion des Employés
- Avance / Absence
- Total Hebdomadaire

### Ajouter un Employé

1. Cliquez sur **"Gestion des Employés"**
2. Remplissez le formulaire avec :
    - CIN (Carte d'Identité Nationale)
    - Nom
    - Prénom
    - Salaire mensuel
3. Cliquez sur **"Enregistrer"**

### Enregistrer une Avance ou Absence

1. Cliquez sur **"Avance / Absence"**
2. Sélectionnez l'employé
3. Choisissez le type (Avance ou Absence)
4. Entrez le montant
5. Sélectionnez la date
6. Cliquez sur **"Enregistrer"**

### Consulter le Rapport Hebdomadaire

1. Cliquez sur **"Total Hebdomadaire"**
2. Visualisez les calculs pour la semaine en cours (Jeudi à Jeudi)
3. Consultez les détails par employé

## 📁 Structure du Projet

```
gestion-employes/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/yassir/projet/gestion_employes/
│   │   │       ├── Controller/
│   │   │       │   ├── HomeController.java
│   │   │       │   ├── EmployeController.java
│   │   │       │   ├── AvanceAbsenceController.java
│   │   │       │   └── RapportController.java
│   │   │       │
│   │   │       ├── Entity/
│   │   │       │   ├── Employe.java
│   │   │       │   ├── Avance.java
│   │   │       │   └── Absence.java
│   │   │       │
│   │   │       ├── Repository/
│   │   │       │   ├── EmployeRepository.java
│   │   │       │   ├── AvanceRepository.java
│   │   │       │   └── AbsenceRepository.java
│   │   │       │
│   │   │       ├── Service/
│   │   │       │   └── GestionService.java
│   │   │       │
│   │   │       └── GestionEmployesApplication.java
│   │   │
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── index.html
│   │       │   ├── employes.html
│   │       │   ├── ajout-avance-absence.html
│   │       │   └── rapport.html
│   │       │
│   │       ├── static/
│   │       │   ├── css/
│   │       │   └── js/
│   │       │
│   │       └── application.properties
│   │
│   └── test/
│
├── pom.xml
├── README.md
└── .gitignore
```

## 🔌 API Endpoints

### Page d'Accueil
```
GET  /              - Page d'accueil
```

### Gestion des Employés
```
GET  /employes      - Liste des employés
POST /employes/add  - Ajouter un employé
POST /employes/delete/{cin} - Supprimer un employé
```

### Gestion Avances/Absences
```
GET  /avance-absence     - Formulaire d'ajout
POST /avance-absence/add - Enregistrer avance/absence
```

### Rapports
```
GET  /rapport       - Rapport hebdomadaire
```

## 🗄️ Base de Données

### Schéma de la Base de Données

#### Table `employe`
```sql
CREATE TABLE employe (
    cin VARCHAR(20) PRIMARY KEY,
    nom VARCHAR(100),
    prenom VARCHAR(100),
    salaire FLOAT
);
```

#### Table `avance`
```sql
CREATE TABLE avance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    montant FLOAT,
    date_avance DATE,
    employe_cin VARCHAR(20),
    FOREIGN KEY (employe_cin) REFERENCES employe(cin)
);
```

#### Table `absence`
```sql
CREATE TABLE absence (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    penalite FLOAT,
    date_absence DATE,
    employe_cin VARCHAR(20),
    FOREIGN KEY (employe_cin) REFERENCES employe(cin)
);
```

### Relations

- **Employe** `1 ─── N` **Avance** (Un employé peut avoir plusieurs avances)
- **Employe** `1 ─── N` **Absence** (Un employé peut avoir plusieurs absences)

## 📸 Captures d'Écran

### Page d'Accueil
Interface moderne avec navigation intuitive vers toutes les fonctionnalités.

### Gestion des Employés
Liste complète avec options d'ajout et de suppression.

### Avance / Absence
Formulaire simple pour enregistrer les transactions avec vue des transactions par employé.

### Rapport Hebdomadaire
Tableau détaillé avec calculs automatiques des salaires nets.

## 🤝 Contribution

Les contributions sont les bienvenues ! Pour contribuer :

1. Forkez le projet
2. Créez une branche pour votre fonctionnalité (`git checkout -b feature/NouvelleFeature`)
3. Committez vos changements (`git commit -m 'Ajout d'une nouvelle fonctionnalité'`)
4. Poussez vers la branche (`git push origin feature/NouvelleFeature`)
5. Ouvrez une Pull Request

## 📝 Roadmap

- [ ] Authentification et autorisation des utilisateurs
- [ ] Export des rapports en PDF
- [ ] Graphiques et statistiques avancées
- [ ] Notification par email
- [ ] API REST pour intégration mobile
- [ ] Multi-langues (Français, Arabe, Anglais)
- [ ] Gestion des congés payés
- [ ] Historique des modifications

## 🐛 Bugs Connus

- Aucun bug critique connu actuellement

Pour signaler un bug, ouvrez une [issue](https://github.com/Derkaoui05/gestion_employes_web/issues).

## 📄 Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

## 👤 Auteur

**Yassir**

- GitHub: [@Derkaoui05](https://github.com/Derkaoui05)
- Email: derkaouidev@gmail.com

## 🙏 Remerciements

- Spring Boot pour le framework robuste
- Tailwind CSS pour le design moderne
- MySQL pour la gestion des données
- La communauté open source

---

⭐ **N'oubliez pas de mettre une étoile si ce projet vous a aidé !**

---
