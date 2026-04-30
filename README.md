#  Système de Gestion d'une pharmacie en Java

## Présentation du projet

Ce projet est un **système de gestion de pharmacie** développé en Java dans le cadre du cours de **Programmation Orientée Objet (POO)**

Une pharmacie doit gérer au quotidien :
- Des **médicaments** : savoir combien il en reste, lesquels vont bientôt expirer
- Des **ventes** : enregistrer ce qui est vendu, générer un ticket de caisse
- Des **fournisseurs** : savoir à qui commander quand le stock est bas
- Des **alertes** : être prévenu quand un médicament manque ou est périmé

Ce programme simule toutes ces opérations dans une **application console Java**, avec une **sauvegarde automatique** des données dans un fichier `data.json` pour ne rien perdre entre deux exécutions.


##  Objectifs pédagogiques

Ce projet met en pratique les concepts suivants :
- **Classes et objets** : `Medicament`, `Fournisseur`, `Client`, `Vente`,'Commande'
- **Attributs et constructeurs**
- **Méthodes statiques et d'instance**
- **ArrayList** pour stocker les listes d'objets
- **Scanner** pour lire les saisies de l'utilisateur
- **Fichiers** : lecture et écriture pour la persistance des données
- **Collaboration Git** : branches, commits, pull requests

##  Structure du projet

GestionPharmacie/
Medicament.java     → Classe Medicament : ajout, recherche, stock, alertes
Fournisseur.java    → Classe Fournisseur : ajout, modification, suppression
Client.java         → Classe Client : nom, prénom, téléphone
Vente.java          → Classe Vente : effectuer une vente, générer ticket
Sauvegarde.java     → Sauvegarde et chargement automatique (data.json)
Main.java           → Menu principal, point d'entrée du programme
data.json           → Base de données locale (créée automatiquement)
README.md           → Ce fichier



##  Menu principal


   SYSTEME DE GESTION DE PHARMACIE
----------------------------------------
--- MEDICAMENTS ---
1.  Ajouter un medicament
2.  Afficher l inventaire
3.  Rechercher un medicament
4.  Mettre a jour le stock
5.  Alertes Stock et Peremption
--- VENTES ---
6.  Effectuer une vente
7.  Historique des ventes
--- FOURNISSEURS ---
8.  Ajouter un fournisseur
9.  Afficher les fournisseurs
10. Modifier un fournisseur
11. Supprimer un fournisseur
0.  Quitter




## Exemple de ticket de caisse 


         TICKET DE CAISSE

N Vente     : VEN001
Date        : 28/04/2026

Client      : Safi samira
Tel         : 699000001

Medicament  : Paracetamol 500mg
Categorie   : Comprime
Quantite    : 3
Prix unit.  : 500 FCFA

TOTAL       : 1500 FCFA

     


## Persistance des données

Les données sont **sauvegardées automatiquement** dans `data.json` après chaque opération (ajout, vente, modification). Au prochain lancement, elles sont **rechargées automatiquement** :

Le fichier `data.json` ressemble à ceci :
json
{
  "medicaments": [
    {"codeMedicament":"MED001","nom":"Paracetamol","prix":500.00,"quantiteStock":97,...}
  ],
  "fournisseurs": [...],
  "ventes": [...]
}


## Workflow Git Instructions pour chaque étudiant

### Cloner le projet

git clone https://github.com/nabonoy-sandrine/GestionPharmacie.git
cd GestionPharmacie


###  Aller sur sa branche
bash
git checkout branche-medicament    # NABONOY
git checkout branche-fournisseur   # Evans
git checkout branche-client        # Frédéric
git checkout branche-vente         # Moustapha & Mouctar


### Récupérer la dernière version avant de coder

git pull origin ma-branche

### Envoyer son travail

git add MonFichier.java
git commit -m "Description claire de ce que j'ai fait"
git push


### Créer une Pull Request sur GitHub
1. Aller sur `https://github.com/nabonoy-sandrine/GestionPharmacie`
2. Cliquer sur **"Compare & pull request"**
3. Vérifier : `base: main` ← `compare: ma-branche`
4. Ajouter un titre et description
5. Cliquer sur **"Create pull request"**


## Fusion finale 

Après avoir accepté toutes les Pull Requests sur GitHub :

git checkout main
git pull origin main
javac *.java
java Main


