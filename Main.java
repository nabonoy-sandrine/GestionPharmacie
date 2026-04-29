import java.util.Scanner;

class Main {

    static Scanner clavier = new Scanner(System.in);

    public static void main(String[] args) {

                       
        Sauvegarde.charger();  // Charger les donnees sauvegardees au demarrage

        while (true) {
            System.out.println("\n=========================================");
            System.out.println("   SYSTEME DE GESTION DE PHARMACIE      ");
            System.out.println("=========================================");
            System.out.println("                    --- MEDICAMENTS ---");
            System.out.println("                1. Ajouter un medicament");
            System.out.println("                2. Afficher l inventaire");
            System.out.println("                3. Rechercher un medicament");
            System.out.println("                4. Mettre a jour le stock");
            System.out.println("                5. Alertes Stock et Peremption");
            System.out.println("                    --- VENTES ---");
            System.out.println("                6. Effectuer une vente");
            System.out.println("                7. Historique des ventes");
            System.out.println("                    --- FOURNISSEURS ---");
            System.out.println("                8. Ajouter un fournisseur");
            System.out.println("                9. Afficher les fournisseurs");
            System.out.println("                10. Modifier un fournisseur");
            System.out.println("                11. Supprimer un fournisseur");
            System.out.println("-----------------------------------------");
            System.out.println("0. Quitter");
            System.out.println("=========================================");
            System.out.print("Entrez votre choix : ");

            int choix = lireEntier();

            switch (choix) {
                case 1:  ajouterMedicament();    break;
                case 2:  Medicament.afficherInventaire(); break;
                case 3:  rechercherMedicament(); break;
                case 4:  mettreAJourStock();     break;
                case 5:  afficherAlertes();      break;
                case 6:  effectuerVente();       break;
                case 7:  Vente.afficherHistorique(); break;
                case 8:  ajouterFournisseur();   break;
                case 9:  Fournisseur.afficherTous(); break;
                case 10: modifierFournisseur();  break;
                case 11: supprimerFournisseur(); break;
                case 0:
                    System.out.println("Au revoir !");
                    return;
                default:
                    System.out.println("Choix invalide. Reessayez.");
            }
        }
    }

    
    //  Ajouter un medicament

    static void ajouterMedicament() {
        System.out.println("\n===== AJOUTER UN MEDICAMENT =====");
        System.out.print("Code                         : "); String code = clavier.nextLine();
        System.out.print("Nom                          : "); String nom  = clavier.nextLine();
        System.out.print("Prix (FCFA)                  : "); double prix = lireDouble();
        System.out.print("Stock initial                : "); int stock   = lireEntier();
        System.out.print("Stock minimum (seuil alerte) : "); int min     = lireEntier();
        System.out.print("Date peremption (JJ/MM/AAAA) : "); String date = clavier.nextLine();
        System.out.print("Categorie                    : "); String cat  = clavier.nextLine();
        Medicament.ajouterMedicament(code, nom, prix, stock, min, date, cat);
    }

    
    // OPTION 3 — Rechercher un medicament
    
    static void rechercherMedicament() {
        System.out.println("\n===== RECHERCHER UN MEDICAMENT =====");
        System.out.print("Entrez le nom ou le code : ");
        String recherche = clavier.nextLine();
        Medicament.rechercherMedicament(recherche);
    }

    // OPTION 4 — Mettre a jour le stock
   
    static void mettreAJourStock() {
        System.out.println("\n===== MISE A JOUR DU STOCK =====");
        System.out.print("Code medicament    : "); String code = clavier.nextLine();
        System.out.print("Quantite a ajouter : "); int qte     = lireEntier();
        Medicament.mettreAJourStock(code, qte);
    }
    // OPTION 5 — Afficher les alertes
    
    static void afficherAlertes() {
        System.out.println("\n===== ALERTES =====");
        System.out.println("\n-- Medicaments en stock bas --");
        Medicament.alerteStockBas();
        System.out.println("\n-- Medicaments proches de la peremption (<=30 jours) --");
        Medicament.alertePeremption();
    }


    // OPTION 6 — Effectuer une vente
    
    static void effectuerVente() {
        System.out.println("\n===== EFFECTUER UNE VENTE =====");

        System.out.println("-- Informations client --");
        System.out.print("Nom       : "); String nom    = clavier.nextLine();
        System.out.print("Prenom    : "); String prenom = clavier.nextLine();
        System.out.print("Telephone : "); String tel    = clavier.nextLine();
        Client client = new Client(nom, prenom, tel);

        System.out.println("\n-- Informations vente --");
        System.out.print("Code vente              : "); String codeVente = clavier.nextLine();
        System.out.print("Code medicament         : "); String codeMed   = clavier.nextLine();
        System.out.print("Quantite                : "); int qte           = lireEntier();
        System.out.print("Date vente (JJ/MM/AAAA) : "); String date      = clavier.nextLine();

        Vente.effectuerVente(codeVente, client, codeMed, qte, date);
    }

    
    // OPTION 8 — Ajouter un fournisseur
    
    static void ajouterFournisseur() {
        System.out.println("\n===== AJOUTER UN FOURNISSEUR =====");
        System.out.print("Code      : "); String code = clavier.nextLine();
        System.out.print("Nom       : "); String nom  = clavier.nextLine();
        System.out.print("Telephone : "); String tel  = clavier.nextLine();
        System.out.print("Adresse   : "); String adr  = clavier.nextLine();
        System.out.print("Email     : "); String mail = clavier.nextLine();
        Fournisseur.ajouterFournisseur(code, nom, tel, adr, mail);
    }

    // OPTION 10 — Modifier un fournisseur
 
    static void modifierFournisseur() {
        System.out.println("\n===== MODIFIER UN FOURNISSEUR =====");
        System.out.print("Code du fournisseur a modifier : "); String code = clavier.nextLine();
        System.out.print("Nouveau nom                    : "); String nom  = clavier.nextLine();
        System.out.print("Nouveau telephone              : "); String tel  = clavier.nextLine();
        System.out.print("Nouvelle adresse               : "); String adr  = clavier.nextLine();
        System.out.print("Nouvel email                   : "); String mail = clavier.nextLine();
        Fournisseur.modifierFournisseur(code, nom, tel, adr, mail);
    }

    
    // OPTION 11 — Supprimer un fournisseur
    
    static void supprimerFournisseur() {
        System.out.println("\n===== SUPPRIMER UN FOURNISSEUR =====");
        System.out.print("Code du fournisseur a supprimer : "); String code = clavier.nextLine();
        Fournisseur.supprimerFournisseur(code);
    }

   
    
    // Evite les erreurs  si l utilisateur entre un mauvais type
    
    static int lireEntier() {
        while (true) {
            try {
                String saisie = clavier.nextLine().trim();
                return Integer.parseInt(saisie);
            } catch (NumberFormatException e) {
                System.out.print("Entier invalide, reessayez : ");
            }
        }
    }

    static double lireDouble() {
        while (true) {
            try {
                String saisie = clavier.nextLine().trim().replace(",", ".");
                return Double.parseDouble(saisie);
            } catch (NumberFormatException e) {
                System.out.print("Nombre invalide, reessayez : ");
            }
        }
    }
}
