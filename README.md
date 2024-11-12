# Banking Application

Une application bancaire simple avec dépôt, retrait et historique des transactions, développée avec Spring Boot, PostgreSQL, et Liquibase pour la gestion de la base de données.

## Prérequis

- **Java 17** : Assurez-vous d'avoir Java 17 installé sur votre machine.
- **Maven** : Assurez-vous d'avoir Maven installé pour gérer les dépendances et construire l'application.
- **Docker** et **Docker Compose** : Utilisés pour exécuter la base de données PostgreSQL.

## Installation et Configuration

1. **Cloner le dépôt** :
   ```bash
   git clone <URL_DU_DÉPÔT>
   cd banking
   ```

2. **Configurer la base de données avec Docker** :

   Assurez-vous que Docker est en cours d'exécution, puis lancez le conteneur PostgreSQL avec Docker Compose.
   ```bash
   docker-compose up -d
   ```
   Cela va démarrer un conteneur PostgreSQL avec :

   - **Utilisateur** : bankUser
   - **Mot de passe** : bankPassword
   - **Base de données** : bankdb


3. **Configurer les paramètres de l'application** :

   Le fichier ```src/main/resources/application.properties``` est déjà configuré pour se connecter à la base de données PostgreSQL avec les informations ci-dessus.


4. **Construire le projet avec Maven** :

   Depuis le répertoire racine du projet, exécutez la commande suivante pour télécharger les dépendances et construire le projet :
   ```bash
    mvn clean install
   ```

## Démarrer l'Application
1. **Exécuter l'application** : 

   Vous pouvez démarrer l'application Spring Boot avec la commande suivante :
   ```bash
    mvn spring-boot:run
   ```

2. **Vérifier les logs** : 

   Assurez-vous que l'application démarre correctement en vérifiant les logs de la console pour toute erreur.

## API Endpoints
L'application expose les points d'API suivants :

1. **Comptes** :
   - ```POST /api/v1/accounts/{id}/deposit?amount={amount}``` : Effectuer un dépôt sur le compte.
   - ```POST /api/v1/accounts/{id}/withdraw?amount={amount}``` : Effectuer un retrait du compte.
   - ```GET /api/v1/accounts/{id}``` : Obtenir l'état actuel du compte.

2. **Transactions** :
   - ```GET /api/v1/transactions/account/{accountId}/history``` : Obtenir l'historique des transactions pour un compte.

## Tests
Les tests unitaires et d'intégration sont inclus en appliquant le TDD. Pour exécuter les tests, utilisez la commande suivante :
   ```bash
    mvn test
   ```

## Arrêter les services
Lorsque vous avez terminé, vous pouvez arrêter le conteneur PostgreSQL en utilisant :
   ```bash
    docker-compose down
   ```

## Logging Aspect
   L'application utilise un aspect de **logging** basé sur **Spring AOP** pour enregistrer automatiquement le début, la fin, et les exceptions des méthodes dans les couches ```Controller``` et ```Service```.

   **Fonctionnement**

- **Début de méthode** : Log lors du démarrage de l'exécution d'une méthode.
- **Fin de méthode** : Log en fin d'exécution de la méthode.
- **Exceptions** : Log de l'exception et de son message si une erreur survient.
   

   **Exemple de logs**

   Pour la méthode ```getTransactionHistory``` :
   ```plaintext
  INFO  [TransactionController] - Démarrage de la méthode getTransactionHistory
  INFO  [TransactionServiceImpl] - Démarrage de la méthode getTransactionHistory
  INFO  [TransactionServiceImpl] - Fin de la méthode getTransactionHistory
  INFO  [TransactionController] - Fin de la méthode getTransactionHistory
   ```

   En cas d'exception :
   ```plaintext
  ERROR [TransactionServiceImpl] - Exception dans getTransactionHistory() avec cause = {message de l'exception}
   ```

## Remarques
- **Gestion des Migrations** : 
Les scripts de migration Liquibase sont situés dans ```src/main/resources/db/changelog```. Le fichier principal est ```changelog-master.xml```, qui inclut les fichiers de création de tables.
- **Swagger UI** : 
Ajoutez une dépendance Swagger si vous souhaitez documenter et tester l'API via une interface utilisateur.