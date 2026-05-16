---
title: "Dossier de Conception — Application de Gestion de Bibliothèque Universitaire"
author: "Équipe de Développement — Université Norbert Zongo"
date: "Mai 2026"
geometry: margin=2.5cm
fontsize: 12pt
lang: fr
---

# 1. Cahier des Charges

## 1.1 Présentation du projet

L'Université Norbert Zongo souhaite moderniser sa bibliothèque en développant une application web complète de gestion de bibliothèque universitaire permettant de gérer efficacement les ressources documentaires, les emprunts, les réservations et les interactions entre les différents acteurs.

## 1.2 Acteurs du système

- **Etudiant** : Consulte le catalogue, recherche des ouvrages, emprunte, réserve, consulte son historique et reçoit des notifications.
- **Bibliothécaire** : Gère le catalogue, les emprunts, les retours, les pénalités et les notifications.
- **Administrateur** : Gère les utilisateurs, les statistiques, les configurations du système et les rapports.

## 1.3 Exigences Fonctionnelles

1. Gestion du catalogue (ajout, modification, suppression d'ouvrages, auteurs, exemplaires)
2. Recherche avancée d'ouvrages (par titre, auteur, ISBN, mots-clés, catégorie)
3. Emprunt et retour d'ouvrages
4. Réservation d'ouvrages (quand tous les exemplaires sont empruntés)
5. Gestion des pénalités de retard
6. Notification automatique pour rappel de retour et disponibilité
7. Consultation de l'historique des emprunts par l'étudiant
8. Gestion des utilisateurs (inscription, authentification, profils)
9. Génération de rapports et statistiques
10. Gestion des catégories et rayons de la bibliothèque

## 1.4 Exigences Non Fonctionnelles

- Le système doit être sécurisé (authentification JWT, protection des données personnelles)
- Interface responsive (accessible sur ordinateur et mobile)
- Temps de réponse inférieur à 2 secondes pour les opérations courantes
- Disponibilite superieure ou egale a 99%
- Respect des principes SOLID et utilisation d'au moins 4 Design Patterns
- Code versionné avec Git

## 1.5 User Stories

| ID | User Story | Acteur | Points |
|---|---|---|---|
| US01 | En tant qu'étudiant, je veux m'inscrire avec mon email et mot de passe, afin d'accéder à l'application. | Etudiant | 3 |
| US02 | En tant qu'utilisateur, je veux me connecter avec mon email et mot de passe, afin d'accéder à mon espace. | Tous | 2 |
| US03 | En tant que bibliothécaire, je veux ajouter un ouvrage avec titre, auteur, ISBN et catégorie, afin de gérer le catalogue. | Bibliothécaire | 3 |
| US04 | En tant qu'étudiant, je veux consulter la liste de tous les ouvrages, afin de voir ce qui est disponible. | Etudiant | 2 |
| US05 | En tant qu'étudiant, je veux rechercher un ouvrage par titre, auteur ou ISBN, afin de trouver rapidement ce que je cherche. | Etudiant | 3 |
| US06 | En tant qu'étudiant, je veux emprunter un ouvrage disponible, afin de le lire chez moi. | Etudiant | 3 |
| US07 | En tant que bibliothécaire, je veux enregistrer le retour d'un ouvrage, afin de le remettre disponible. | Bibliothécaire | 2 |
| US08 | En tant qu'étudiant, je veux réserver un ouvrage déjà emprunté, afin d'être notifié quand il est disponible. | Etudiant | 4 |
| US09 | En tant que bibliothécaire, je veux appliquer une pénalité de retard automatique, afin de respecter les règles de la bibliothèque. | Bibliothécaire | 3 |
| US10 | En tant qu'étudiant, je veux consulter mon historique d'emprunts, afin de suivre mes lectures. | Etudiant | 2 |
| US11 | En tant qu'étudiant, je veux recevoir un email de rappel avant la date de retour, afin de ne pas oublier. | Etudiant | 4 |
| US12 | En tant qu'administrateur, je veux gérer les comptes utilisateurs, afin de contrôler les accès. | Administrateur | 3 |
| US13 | En tant qu'administrateur, je veux voir les statistiques des ouvrages les plus empruntés, afin de mieux gérer la bibliothèque. | Administrateur | 4 |
| US14 | En tant que bibliothécaire, je veux gérer les catégories et rayons, afin d'organiser le catalogue. | Bibliothécaire | 2 |
| US15 | En tant qu'administrateur, je veux générer un rapport des emprunts, afin de le soumettre à la direction. | Administrateur | 5 |

---

# 2. Diagramme de Cas d'Utilisation

Le diagramme de cas d'utilisation a été réalisé avec PlantUML. Il présente les trois acteurs principaux et leurs interactions avec le système.

## Acteurs

- **Etudiant** : Peut s'inscrire, se connecter, consulter le catalogue, rechercher, emprunter, réserver, consulter son historique et recevoir des notifications.
- **Bibliothécaire** : Peut se connecter, gérer le catalogue, enregistrer les retours, appliquer des pénalités et envoyer des notifications.
- **Administrateur** : Peut se connecter, gérer les utilisateurs, voir les statistiques, générer des rapports et gérer les catégories.

## Cas d'utilisation principaux

| Cas d'utilisation | Acteurs concernés |
|---|---|
| S'inscrire | Etudiant |
| Se connecter | Tous |
| Consulter le catalogue | Etudiant |
| Rechercher un ouvrage | Etudiant |
| Emprunter un ouvrage | Etudiant |
| Réserver un ouvrage | Etudiant |
| Consulter historique | Etudiant |
| Recevoir notifications | Etudiant |
| Gérer le catalogue | Bibliothécaire |
| Enregistrer un retour | Bibliothécaire |
| Appliquer une pénalité | Bibliothécaire |
| Gérer les utilisateurs | Administrateur |
| Voir les statistiques | Administrateur |
| Générer des rapports | Administrateur |
| Gérer les catégories | Administrateur |

---

# 3. Diagramme de Classes

## 3.1 Classes principales

### Utilisateur (classe mère)
- id : Long
- nom : String
- prenom : String
- email : String (unique)
- motDePasse : String
- role : Role (enum)
- dateInscription : LocalDateTime

### Etudiant (hérite de Utilisateur)
- numeroEtudiant : String
- filiere : String

### Bibliothécaire (hérite de Utilisateur)
- matricule : String

### Administrateur (hérite de Utilisateur)
- niveau : String

### Ouvrage
- id : Long
- titre : String
- auteur : String
- isbn : String (unique)
- description : String
- anneePublication : int
- nombreExemplaires : int
- exemplairesDisponibles : int
- categorie : Categorie (ManyToOne)

### Categorie
- id : Long
- nom : String
- rayon : String

### Emprunt
- id : Long
- etudiant : Etudiant (ManyToOne)
- ouvrage : Ouvrage (ManyToOne)
- dateEmprunt : LocalDate
- dateRetourPrevue : LocalDate
- dateRetourReelle : LocalDate
- statut : StatutEmprunt (enum)

### Reservation
- id : Long
- etudiant : Etudiant (ManyToOne)
- ouvrage : Ouvrage (ManyToOne)
- dateReservation : LocalDate
- dateExpiration : LocalDate
- statut : StatutReservation (enum)

## 3.2 Relations entre classes

- Utilisateur est la classe mère de Etudiant, Bibliothécaire et Administrateur (héritage)
- Ouvrage est lié à Categorie (ManyToOne)
- Emprunt est lié à Etudiant et Ouvrage (ManyToOne)
- Reservation est liée à Etudiant et Ouvrage (ManyToOne)

---

# 4. Diagrammes de Séquence

## 4.1 Séquence — Connexion

1. L'utilisateur saisit son email et mot de passe sur l'interface web
2. L'interface envoie une requête POST /api/auth/login au Controller
3. Le Controller appelle AuthService.authentifier()
4. Le Service interroge la base de données via le Repository
5. Si les identifiants sont corrects, un token JWT est généré et retourné
6. Sinon, une erreur 401 est retournée

## 4.2 Séquence — Emprunt d'un ouvrage

1. L'étudiant clique sur "Emprunter" sur un ouvrage
2. L'interface envoie POST /api/emprunts avec etudiantId et ouvrageId
3. Le Controller appelle EmpruntService.creerEmprunt()
4. Le Service vérifie la disponibilité de l'ouvrage
5. Si disponible : crée l'emprunt, décrémente exemplairesDisponibles, sauvegarde
6. Si indisponible : retourne une erreur avec suggestion de réservation

## 4.3 Séquence — Réservation d'un ouvrage

1. L'étudiant clique sur "Réserver"
2. L'interface envoie POST /api/reservations
3. Le Controller appelle ReservationService.creerReservation()
4. Le Service vérifie qu'il n'existe pas déjà une réservation active
5. Crée la réservation avec statut EN_ATTENTE
6. Retourne la confirmation à l'étudiant

---

# 5. Architecture 4+1 Vues

## 5.1 Vue Logique

L'application est organisée en 4 couches :

- **Couche Présentation** : Interface HTML/CSS/JavaScript accessible via navigateur
- **Couche Application** : Controllers REST Spring Boot qui traitent les requêtes HTTP
- **Couche Domaine** : Services métier (OuvrageService, EmpruntService, ReservationService)
- **Couche Infrastructure** : Repositories JPA, Base de données MySQL

## 5.2 Vue Développement

Organisation des packages Java :

- com.bibliotheque.controller : API REST (OuvrageController, EmpruntController, ReservationController, StatistiqueController, CategorieController)
- com.bibliotheque.service : Logique métier (OuvrageService, EmpruntService, ReservationService)
- com.bibliotheque.repository : Accès données (OuvrageRepository, EmpruntRepository, ReservationRepository, UtilisateurRepository, CategorieRepository)
- com.bibliotheque.model : Entités JPA (Utilisateur, Etudiant, Ouvrage, Emprunt, Reservation, Categorie)
- com.bibliotheque.config : Configuration (DatabaseConfig, SecurityConfig)
- com.bibliotheque.strategy : Pattern Strategy (CalculPenalite, PenaliteStandard, PenaliteBoursier)
- com.bibliotheque.factory : Pattern Factory (UtilisateurFactory)
- com.bibliotheque.observer : Pattern Observer (Observable, Observateur, OuvrageDisponible)

## 5.3 Vue Processus

Flux d'exécution d'une requête :

1. Le navigateur envoie une requête HTTP
2. Spring Security vérifie l'authentification
3. Le Controller reçoit et valide la requête
4. Le Service applique la logique métier
5. Le Repository interroge la base de données MySQL
6. La réponse JSON est retournée au client

## 5.4 Vue Physique

Déploiement de l'application :

- **Client** : Navigateur web (Chrome, Firefox)
- **Serveur applicatif** : Spring Boot avec Tomcat embarqué
- **Base de données** : MySQL 8.4
- **Versionnement** : GitHub

## 5.5 Vue des Scénarios

Les scénarios principaux couverts par l'application :

1. Authentification et gestion des profils
2. Gestion du catalogue d'ouvrages
3. Processus d'emprunt et de retour
4. Processus de réservation
5. Génération de statistiques et rapports

---

# 6. Justification des Design Patterns

## 6.1 Pattern Singleton

**Classe** : DatabaseConfig

**Problème résolu** : Eviter la création de multiples instances de configuration de la base de données.

**Solution** : La classe DatabaseConfig annotée @Configuration est gérée comme un Singleton par le conteneur IoC de Spring Boot. Une seule instance est créée et partagée dans toute l'application.

**Avantage** : Garantit la cohérence de la configuration et économise les ressources.

## 6.2 Pattern Observer

**Classes** : Observable, Observateur, OuvrageDisponible

**Problème résolu** : Notifier automatiquement tous les étudiants ayant réservé un ouvrage lorsque celui-ci devient disponible après un retour.

**Solution** : L'interface Observable définit les méthodes ajouterObservateur(), supprimerObservateur() et notifierObservateurs(). La classe OuvrageDisponible implémente Observable et notifie tous les Observateurs lors du retour d'un ouvrage.

**Avantage** : Découple le système de notification du système d'emprunt. L'ajout de nouveaux types de notifications ne nécessite pas de modifier le code existant.

## 6.3 Pattern Factory

**Classe** : UtilisateurFactory

**Problème résolu** : Créer le bon type d'utilisateur (Etudiant, Bibliothécaire ou Administrateur) sans que le code client connaisse le type exact à instancier.

**Solution** : La méthode statique creerUtilisateur(role, nom, email) retourne l'objet approprié selon le rôle fourni en paramètre.

**Avantage** : Centralise la logique de création d'utilisateurs. L'ajout d'un nouveau type d'utilisateur ne nécessite de modifier que la Factory.

## 6.4 Pattern Strategy

**Classes** : CalculPenalite, PenaliteStandard, PenaliteBoursier

**Problème résolu** : Le calcul des pénalités de retard peut varier selon le profil de l'étudiant (étudiant standard ou boursier).

**Solution** : L'interface CalculPenalite définit la méthode calculer(joursRetard). PenaliteStandard applique 100 FCFA par jour de retard, PenaliteBoursier applique 50 FCFA par jour.

**Avantage** : Permet de changer l'algorithme de calcul sans modifier le code du service. Respecte le principe Open/Closed des principes SOLID.
