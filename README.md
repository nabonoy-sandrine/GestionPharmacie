# Système de Gestion de Pharmacie — Java POO

## Structure du projet


GestionPharmacie/
├── Medicament.java     ← Etudiante Nabonoy
├── Fournisseur.java    ← Etudiant Evans
├── Client.java         ← Etudiant Frederic
├── Vente.java          ← Etudiants MOUSTAPHA ET MOUCTAR
├── Sauvegarde.java     
├── Main.java           ← (
├── data.json           ← Créé automatiquement au premier lancement
└── README.md




> **Note :** L'étudiant 3 a peu de travail sur Client.java. Il peut aider l'étudiant 4 sur Vente.java.



## Persistance des données

Les données sont **automatiquement sauvegardées** dans le fichier `data.json`
après chaque ajout, modification ou vente.

Au prochain lancement, toutes les données sont **rechargées automatiquement**.
Il n'y a rien à faire — cela fonctionne tout seul.


[SAUVEGARDE] Donnees chargees : 3 medicament(s), 2 fournisseur(s), 5 vente(s).


---

## Menu principal

```
=========================================
   SYSTEME DE GESTION DE PHARMACIE
=========================================
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
-----------------------------------------
0.  Quitter
=========================================
```

---

## Architecture — Comment la sauvegarde fonctionne

```
Main.java
  └── au démarrage → Sauvegarde.charger()
                         ├── lit data.json
                         ├── recharge Medicament.liste
                         ├── recharge Fournisseur.liste
                         └── recharge Vente.liste

Medicament.ajouterMedicament()
Medicament.mettreAJourStock()
Fournisseur.ajouterFournisseur()    →  Sauvegarde.sauvegarder()
Fournisseur.modifierFournisseur()          └── réécrit data.json
Fournisseur.supprimerFournisseur()
Vente.effectuerVente()
```

---

