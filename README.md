# Bibliothèque Universitaire - Université Norbert Zongo

Application web de gestion de bibliothèque universitaire développée en méthode Agile (Scrum).

## Technologies
- **Backend** : Java 21, Spring Boot 3.2, Spring Security (JWT)
- **Base de données** : MySQL
- **Frontend** : HTML5, CSS3, JavaScript (Vanilla)
- **Tests** : JUnit 5, Mockito, JaCoCo (couverture 93%)
- **Versionnement** : Git + GitHub

## Acteurs
- **Étudiant** : Consulte le catalogue, emprunte, réserve, consulte son historique
- **Bibliothécaire** : Gère le catalogue, les emprunts, les retours, les pénalités
- **Administrateur** : Gère les utilisateurs, les statistiques et les rapports

## Fonctionnalités
1. Gestion du catalogue (ajout, modification, suppression d'ouvrages)
2. Recherche avancée (titre, auteur, ISBN, catégorie, mots-clés)
3. Emprunt et retour d'ouvrages
4. Réservation d'ouvrages
5. Gestion des pénalités de retard
6. Notifications automatiques par email
7. Historique des emprunts
8. Gestion des utilisateurs (inscription, authentification JWT)
9. Statistiques et rapports
10. Gestion des catégories et rayons

## Design Patterns
- **Factory** : `UtilisateurFactory` - création des utilisateurs selon le rôle
- **Observer** : `OuvrageDisponible` - notification quand un ouvrage est disponible
- **Strategy** : `CalculPenalite` - calcul des pénalités (standard ou boursier)
- **Facade** : `BibliothecaireFacade` - interface simplifiée pour les opérations complexes

## Lancer le projet

### Prérequis
- Java 21
- MySQL
- Maven 3.x

### Démarrage
```bash
# Configurer la base de données dans src/main/resources/application.properties
mvn spring-boot:run
```
L'application sera disponible sur http://localhost:8081

### Comptes de test
| Email | Mot de passe | Rôle |
|-------|-------------|------|
| admin@unz.bf | Admin123 | Administrateur |
| fatima.kabore@unz.bf | Admin123 | Étudiant |
| sali.traore@unz.bf | Admin123 | Bibliothécaire |

## Tests
```bash
mvn test                    # Lancer les tests
mvn test jacoco:report      # Rapport de couverture
```
Rapport JaCoCo disponible dans `target/site/jacoco/index.html`

## Structure du projet
## Equipe
Projet réalisé dans le cadre du cours de Génie Logiciel - Université Norbert Zongo
