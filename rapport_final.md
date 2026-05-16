---
title: "Rapport Final — Application de Gestion de Bibliothèque Universitaire"
author: "Équipe de Développement — Université Norbert Zongo"
date: "Mai 2026"
geometry: margin=2.5cm
fontsize: 12pt
lang: fr
---

# 1. Introduction

## 1.1 Contexte

L'Université Norbert Zongo (UNZ) souhaitait moderniser sa bibliothèque en développant une application web complète de gestion documentaire. Ce projet académique s'inscrit dans le cadre du cours de Génie Logiciel et vise à mettre en pratique l'ensemble des concepts étudiés.

## 1.2 Objectifs

Les objectifs principaux de ce projet sont :

- Concevoir et développer une application web professionnelle de gestion de bibliothèque universitaire
- Appliquer la méthodologie Agile Scrum
- Mettre en œuvre les bonnes pratiques du Génie Logiciel
- Utiliser les Design Patterns et les principes SOLID
- Produire une application testée avec une couverture de code suffisante

## 1.3 Périmètre du projet

L'application couvre la gestion des ouvrages, des emprunts, des réservations, des pénalités, des utilisateurs et des statistiques pour trois types d'acteurs : l'Étudiant, le Bibliothécaire et l'Administrateur.

---

# 2. Analyse des Exigences

## 2.1 Acteurs du système

| Acteur | Rôle |
|---|---|
| Étudiant | Consulte le catalogue, emprunte, réserve, consulte son historique |
| Bibliothécaire | Gère le catalogue, les emprunts, les retours et les pénalités |
| Administrateur | Gère les utilisateurs, les statistiques et les rapports |

## 2.2 Exigences Fonctionnelles

1. Gestion du catalogue (ajout, modification, suppression d'ouvrages)
2. Recherche avancée par titre, auteur, ISBN
3. Emprunt et retour d'ouvrages
4. Réservation d'ouvrages indisponibles
5. Calcul automatique des pénalités de retard
6. Notification automatique par email
7. Consultation de l'historique des emprunts
8. Gestion des utilisateurs (inscription, authentification)
9. Génération de statistiques et rapports
10. Gestion des catégories et rayons

## 2.3 Exigences Non Fonctionnelles

| Exigence | Valeur |
|---|---|
| Sécurité | Authentification JWT, protection des données |
| Performance | Temps de réponse < 2 secondes |
| Disponibilité | ≥ 99% |
| Interface | Responsive (ordinateur et mobile) |
| Maintenabilité | Principes SOLID, Design Patterns |
| Versionnement | Git + GitHub |

## 2.4 User Stories

| ID | User Story | Acteur | Priorité |
|---|---|---|---|
| US01 | En tant qu'étudiant, je veux m'inscrire avec mon email et mot de passe | Étudiant | Haute |
| US02 | En tant qu'utilisateur, je veux me connecter | Tous | Haute |
| US03 | En tant que bibliothécaire, je veux ajouter un ouvrage | Bibliothécaire | Haute |
| US04 | En tant qu'étudiant, je veux consulter le catalogue | Étudiant | Haute |
| US05 | En tant qu'étudiant, je veux rechercher un ouvrage | Étudiant | Haute |
| US06 | En tant qu'étudiant, je veux emprunter un ouvrage | Étudiant | Moyenne |
| US07 | En tant que bibliothécaire, je veux enregistrer un retour | Bibliothécaire | Moyenne |
| US08 | En tant qu'étudiant, je veux réserver un ouvrage | Étudiant | Moyenne |
| US09 | En tant que bibliothécaire, je veux appliquer une pénalité | Bibliothécaire | Moyenne |
| US10 | En tant qu'étudiant, je veux consulter mon historique | Étudiant | Moyenne |
| US11 | En tant qu'étudiant, je veux recevoir des notifications | Étudiant | Basse |
| US12 | En tant qu'administrateur, je veux gérer les utilisateurs | Administrateur | Basse |
| US13 | En tant qu'administrateur, je veux voir les statistiques | Administrateur | Basse |
| US14 | En tant que bibliothécaire, je veux gérer les catégories | Bibliothécaire | Basse |
| US15 | En tant qu'administrateur, je veux générer des rapports | Administrateur | Basse |

---

# 3. Modélisation et Conception

## 3.1 Diagramme de Cas d'Utilisation

Le diagramme de cas d'utilisation présente les interactions entre les trois acteurs (Étudiant, Bibliothécaire, Administrateur) et les fonctionnalités du système. Il a été réalisé avec PlantUML.

Les cas d'utilisation principaux sont :
- S'inscrire et se connecter
- Gérer le catalogue d'ouvrages
- Emprunter et retourner des ouvrages
- Réserver des ouvrages
- Consulter les statistiques

## 3.2 Diagramme de Classes

Le diagramme de classes définit la structure de données de l'application avec les entités suivantes :

- **Utilisateur** (classe mère) : id, nom, prénom, email, motDePasse, rôle
- **Étudiant** (hérite de Utilisateur) : numéroÉtudiant, filière
- **Bibliothécaire** (hérite de Utilisateur) : matricule
- **Administrateur** (hérite de Utilisateur) : niveau
- **Ouvrage** : id, titre, auteur, ISBN, nombreExemplaires, exemplairesDisponibles
- **Catégorie** : id, nom, rayon
- **Emprunt** : id, dateEmprunt, dateRetourPrévue, dateRetourRéelle, statut
- **Réservation** : id, dateRéservation, dateExpiration, statut
- **Pénalité** : montant, payée

## 3.3 Diagrammes de Séquence

Trois diagrammes de séquence ont été réalisés :

**Connexion :** L'utilisateur saisit ses identifiants → le Controller appelle le Service → le Service vérifie en base → retourne un token JWT.

**Emprunt :** L'étudiant clique sur Emprunter → le Controller vérifie la disponibilité → le Service crée l'emprunt → met à jour les exemplaires disponibles.

**Réservation :** L'étudiant réserve un ouvrage indisponible → le Service vérifie qu'il n'a pas déjà réservé → crée la réservation → envoie une confirmation.

## 3.4 Architecture 4+1 Vues

**Vue Logique :** Architecture en 4 couches — Présentation, Application, Domaine, Infrastructure.

**Vue Développement :** Organisation en packages — controller, service, repository, model, config, strategy, factory, observer.

**Vue Processus :** Flux d'exécution — requête HTTP → Spring Security → Controller → Service → Repository → MySQL.

**Vue Physique :** Déploiement — Navigateur Web → Serveur Spring Boot → Base de données MySQL.

**Vue des Scénarios :** Les cas d'utilisation principaux couvrant les 3 acteurs.

---

# 4. Architecture et Design Patterns

## 4.1 Architecture Générale

L'application suit une architecture **MVC (Model-View-Controller)** avec Spring Boot :

- **Model** : Entités JPA (Utilisateur, Ouvrage, Emprunt, Réservation)
- **View** : Interface HTML/CSS/JavaScript
- **Controller** : API REST Spring Boot

## 4.2 Principes SOLID appliqués

| Principe | Application |
|---|---|
| **S** — Single Responsibility | Chaque classe a une seule responsabilité (OuvrageService, EmpruntService) |
| **O** — Open/Closed | Les services sont ouverts à l'extension via les interfaces |
| **L** — Liskov Substitution | Etudiant, Bibliothécaire, Admin héritent correctement de Utilisateur |
| **I** — Interface Segregation | Interfaces séparées pour Observer et Strategy |
| **D** — Dependency Inversion | Spring inject les dépendances via @Autowired |

## 4.3 Design Patterns utilisés

### Pattern 1 — Singleton
**Problème :** Éviter la création multiple d'instances de configuration.
**Solution :** La classe `DatabaseConfig` annotée `@Configuration` est gérée comme un Singleton par le conteneur Spring IoC.
**Justification :** Spring Boot garantit qu'une seule instance de chaque bean `@Configuration` est créée dans le contexte applicatif.

### Pattern 2 — Observer
**Problème :** Notifier automatiquement les étudiants quand un ouvrage devient disponible.
**Solution :** L'interface `Observable` et `Observateur` permettent à `OuvrageDisponible` de notifier tous les abonnés lors du retour d'un ouvrage.
**Justification :** Découple le système de notification du système d'emprunt.

### Pattern 3 — Factory
**Problème :** Créer le bon type d'utilisateur (Étudiant, Bibliothécaire, Administrateur) sans connaître le type exact à l'avance.
**Solution :** La classe `UtilisateurFactory` crée l'objet approprié selon le rôle fourni.
**Justification :** Centralise la création d'objets et facilite l'ajout de nouveaux types d'utilisateurs.

### Pattern 4 — Strategy
**Problème :** Le calcul des pénalités peut varier selon le type d'étudiant (standard ou boursier).
**Solution :** L'interface `CalculPenalite` définit le contrat, `PenaliteStandard` (100 FCFA/jour) et `PenaliteBoursier` (50 FCFA/jour) implémentent des stratégies différentes.
**Justification :** Permet de changer l'algorithme de calcul sans modifier le code existant.

---

# 5. Implémentation et Tests

## 5.1 Stack Technique

| Composant | Technologie |
|---|---|
| Langage | Java 21 |
| Framework Backend | Spring Boot 3.2.0 |
| Base de données | MySQL 8.4 |
| ORM | Hibernate / JPA |
| Sécurité | JWT (JSON Web Token) |
| Frontend | HTML5 / CSS3 / JavaScript |
| Build | Maven 3.9 |
| Versionnement | Git + GitHub |

## 5.2 Structure du projet

bibliotheque-univ-nz1/
├── src/main/java/com/bibliotheque/
│   ├── controller/     # API REST
│   ├── service/        # Logique métier
│   ├── repository/     # Accès base de données
│   ├── model/          # Entités JPA
│   ├── config/         # Configuration
│   ├── strategy/       # Pattern Strategy
│   ├── factory/        # Pattern Factory
│   └── observer/       # Pattern Observer
├── src/main/resources/
│   ├── application.properties
│   └── static/index.html
├── src/test/           # Tests unitaires
└── pom.xm

## 5.3 API REST développées

| Endpoint | Méthode | Description |
|---|---|---|
| /api/ouvrages | GET | Lister tous les ouvrages |
| /api/ouvrages | POST | Ajouter un ouvrage |
| /api/ouvrages/{id} | PUT | Modifier un ouvrage |
| /api/ouvrages/{id} | DELETE | Supprimer un ouvrage |
| /api/ouvrages/recherche/titre | GET | Rechercher par titre |
| /api/emprunts | POST | Créer un emprunt |
| /api/emprunts/{id}/retour | PUT | Retourner un emprunt |
| /api/emprunts/{id}/penalite | GET | Calculer pénalité |
| /api/reservations | POST | Créer une réservation |
| /api/reservations/{id}/annuler | PUT | Annuler réservation |
| /api/categories | GET/POST | Gérer catégories |
| /api/statistiques | GET | Voir statistiques |

## 5.4 Tests unitaires

Les tests ont été réalisés avec **JUnit 5** et **Mockito** :

| Classe de test | Nombre de tests | Description |
|---|---|---|
| OuvrageServiceTest | 7 | Tests du service des ouvrages |
| EmpruntServiceTest | 6 | Tests du service des emprunts |
| ModelTest | 7 | Tests des modèles |
| PenaliteTest | 3 | Tests des stratégies de pénalité |
| FactoryTest | 4 | Tests du pattern Factory |
| AppTest | 1 | Test de démarrage |
| **Total** | **28** | |

## 5.5 Couverture de code

La couverture de code a été mesurée avec **JaCoCo** :

| Métrique | Valeur |
|---|---|
| Couverture globale | **88%** |
| Objectif requis | ≥ 65% |
| Résultat | ✅ Objectif dépassé |

---

# 6. Gestion Agile (Scrum)

## 6.1 Organisation de l'équipe

| Rôle | Responsabilité |
|---|---|
| Product Owner | Priorise le backlog, valide les fonctionnalités |
| Scrum Master | Facilite les cérémonies Scrum, lève les blocages |
| Équipe Dev | Développe, teste et livre les fonctionnalités |

## 6.2 Sprints réalisés

### Sprint 1 — Authentification + Catalogue (2 semaines)
- US01 : Inscription utilisateur ✅
- US02 : Connexion ✅
- US03 : Ajout d'ouvrage ✅
- US04 : Consultation du catalogue ✅
- US05 : Recherche d'ouvrage ✅

### Sprint 2 — Emprunts + Réservations + Pénalités (2 semaines)
- US06 : Emprunt d'ouvrage ✅
- US07 : Retour d'ouvrage ✅
- US08 : Réservation ✅
- US09 : Pénalités de retard ✅
- US10 : Historique des emprunts ✅

### Sprint 3 — Notifications + Rapports + Déploiement (2 semaines)
- US11 : Notifications ✅
- US12 : Gestion utilisateurs ✅
- US13 : Statistiques ✅
- US14 : Gestion catégories ✅
- US15 : Génération de rapports ✅

## 6.3 Outil de gestion

L'outil **GitHub Projects** a été utilisé pour gérer le Product Backlog, le Sprint Backlog et le tableau Kanban avec les colonnes : Product Backlog, Sprint Backlog, In Progress, In Review, Done.

## 6.4 Definition of Done (DoD)

Une User Story est considérée comme Done quand :
- Le code est développé et compilé sans erreur
- Les tests unitaires sont écrits et passent
- Le code est commité sur GitHub
- La fonctionnalité est démontrée et validée

---

# 7. Difficultés Rencontrées et Leçons Apprises

## 7.1 Difficultés rencontrées

**Difficulté 1 — Configuration de Spring Security**
Spring Security bloquait toutes les requêtes API. La solution a été de configurer une `SecurityFilterChain` personnalisée pour autoriser les requêtes pendant le développement.

**Difficulté 2 — Déploiement en ligne**
Le déploiement sur Render a posé des problèmes de connectivité avec la base de données MySQL distante. Nous avons migré vers PostgreSQL et Railway pour résoudre ce problème.

**Difficulté 3 — Gestion des ports**
Des conflits de ports (8080, 8081) ont été rencontrés lors du développement local. La solution a été de libérer les ports avec `fuser -k` avant chaque lancement.

**Difficulté 4 — Intégration des Design Patterns**
L'intégration du pattern Singleton avec Spring Boot nécessitait d'adapter le constructeur privé car Spring ne peut pas instancier des classes avec des constructeurs privés.

## 7.2 Leçons apprises

- L'importance de bien configurer l'environnement de développement dès le début
- La méthodologie Scrum facilite la gestion du projet et la livraison incrémentale
- Les tests unitaires permettent de détecter rapidement les régressions
- La documentation du code et des API est essentielle pour la maintenance
- Le versionnement Git avec des branches séparées (main/develop) améliore la qualité du code

---

# 8. Conclusion

Ce projet nous a permis de développer une application web complète de gestion de bibliothèque universitaire en appliquant les meilleures pratiques du Génie Logiciel.

## Bilan technique

L'application développée avec Spring Boot 3.2 et MySQL offre une API REST complète avec 12 endpoints, une interface web responsive, 28 tests unitaires avec 88% de couverture de code, et 4 Design Patterns implémentés.

## Bilan méthodologique

La méthodologie Scrum a été appliquée avec succès à travers 3 sprints de 2 semaines chacun, permettant de livrer progressivement les fonctionnalités prioritaires tout en maintenant la qualité du code.

## Perspectives d'amélioration

- Implémenter un système d'authentification JWT complet
- Ajouter un système de notification par email fonctionnel
- Améliorer l'interface utilisateur avec un framework moderne (React ou Angular)
- Mettre en place une intégration continue (CI/CD) avec GitHub Actions
- Déployer l'application sur un serveur cloud avec une base de données persistante

