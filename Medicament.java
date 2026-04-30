import java.util.ArrayList;

class Medicament {

    
    // ATTRIBUTS
   
    String codeMedicament;
    String nom;
    double prix;
    int quantiteStock;
    int stockMinimum;
    String datePeremption;
    String categorie;

    static ArrayList<Medicament> liste = new ArrayList<>();

    
    // CONSTRUCTEUR
    
    public Medicament(String codeMedicament, String nom,
                      double prix, int quantiteStock,
                      int stockMinimum, String datePeremption,
                      String categorie) {
        this.codeMedicament = codeMedicament;
        this.nom            = nom;
        this.prix           = prix;
        this.quantiteStock  = quantiteStock;
        this.stockMinimum   = stockMinimum;
        this.datePeremption = datePeremption;
        this.categorie      = categorie;
    }

    
    // METHODE 1 — ajouterMedicament
   
    public static void ajouterMedicament(String code, String nom,
                                         double prix, int stock,
                                         int stockMin,
                                         String datePeremption,
                                         String categorie) {
        // Verifier que le code n'existe pas deja
        for (Medicament m : liste) {
            if (m.codeMedicament.equalsIgnoreCase(code)) {
                System.out.println("ERREUR : Le medicament avec le code " + code + " existe deja.");
                return;
            }
        }
        Medicament m = new Medicament(code, nom, prix, stock, stockMin, datePeremption, categorie);
        liste.add(m);
        Sauvegarde.sauvegarder(); // Sauvegarde automatique
        System.out.println("Medicament '" + nom + "' ajoute avec succes.");
    }

    
    // METHODE 2 — afficherInventaire
    
    public static void afficherInventaire() {
        
    }

    public void afficher() {
        System.out.printf("%-10s %-20s %-10.0f %-8d %-12s %-15s%n",
                codeMedicament, nom, prix, quantiteStock, datePeremption, categorie);
    }

    
    // METHODE 3 — rechercherMedicament
    
    public static void rechercherMedicament(String recherche) {
        
    }

   
    // METHODE 4 — mettreAJourStock par saidou
public static void mettreAJourStock(String code, int quantiteAjoutee) {
    for (Medicament med : listeMedicaments) {
        if (med.code.equalsIgnoreCase(code)) {
            med.quantite += quantiteAjoutee;
            System.out.println("Stock mis à jour pour : " + med.nom);
            System.out.println("Nouvelle quantité : " + med.quantite);
            return;
        }
    }
    System.out.println("Médicament introuvable.");
}


// METHODE 5 — alerteStockBas
public static void alerteStockBas() {
    System.out.println("=== Médicaments en stock bas ===");
    
    for (Medicament med : listeMedicaments) {
        if (med.quantite <= 5) {
            System.out.println(
                med.code + " | " +
                med.nom + " | Quantité : " +
                med.quantite
            );
        }
    }
}


// METHODE 6 — alertePeremption
public static void alertePeremption() {
   System.out.println("=== Médicaments en stock bas ===");
    
    for (Medicament med : listeMedicaments) {
        if (med.quantite <= 5) {
            System.out.println(
                med.code + " | " +
                med.nom + " | Quantité : " +
                med.quantite
            );
        }
    }


    
    // METHODE 7 — verifierStock
    
    public static boolean verifierStock(String code, int quantiteDemandee) {
        Medicament m = trouverParCode(code);
        if (m == null) return false;
        return m.quantiteStock >= quantiteDemandee;
    }

    
    // METHODE 8 — trouverParCode
   
    public static Medicament trouverParCode(String code) {
        for (Medicament m : liste) {
            if (m.codeMedicament.equalsIgnoreCase(code)) {
                return m;
            }
        }
        return null;
    }

    
    // SERIALISATION JSON (pour Sauvegarde.java)
    
    public String toJson() {
        return String.format(
            "{\"codeMedicament\":\"%s\",\"nom\":\"%s\",\"prix\":%.2f," +
            "\"quantiteStock\":%d,\"stockMinimum\":%d," +
            "\"datePeremption\":\"%s\",\"categorie\":\"%s\"}",
            codeMedicament, nom.replace("\"", "\\\""),
            prix, quantiteStock, stockMinimum, datePeremption, categorie
        );
    }

    public static Medicament fromJson(String json) {
        String code   = extraireChamp(json, "codeMedicament");
        String nom    = extraireChamp(json, "nom");
        double prix   = Double.parseDouble(extraireChamp(json, "prix"));
        int qte       = Integer.parseInt(extraireChamp(json, "quantiteStock"));
        int min       = Integer.parseInt(extraireChamp(json, "stockMinimum"));
        String date   = extraireChamp(json, "datePeremption");
        String cat    = extraireChamp(json, "categorie");
        return new Medicament(code, nom, prix, qte, min, date, cat);
    }

    private static String extraireChamp(String json, String cle) {
        String motif = "\"" + cle + "\":";
        int debut = json.indexOf(motif) + motif.length();
        boolean estChaine = json.charAt(debut) == '"';
        if (estChaine) {
            debut++;
            int fin = json.indexOf("\"", debut);
            return json.substring(debut, fin);
        } else {
            int fin = json.indexOf(",", debut);
            if (fin == -1) fin = json.indexOf("}", debut);
            return json.substring(debut, fin).trim();
        }
    }
}
