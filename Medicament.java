import java.util.ArrayList;

class Medicament {

    // ════════════════════════════════════════
    // ATTRIBUTS
    // ════════════════════════════════════════
    String codeMedicament;
    String nom;
    double prix;
    int quantiteStock;
    int stockMinimum;
    String datePeremption;
    String categorie;

    static ArrayList<Medicament> liste = new ArrayList<>();

    // ════════════════════════════════════════
    // CONSTRUCTEUR
    // ════════════════════════════════════════
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

    // ════════════════════════════════════════
    // METHODE 1 — ajouterMedicament
    // ════════════════════════════════════════
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

    // ════════════════════════════════════════
    // METHODE 2 — afficherInventaire
    // ════════════════════════════════════════
    public static void afficherInventaire() {
        if (liste.isEmpty()) {
            System.out.println("Aucun medicament enregistre.");
            return;
        }
        System.out.println("\n========================================");
        System.out.printf("%-10s %-20s %-10s %-8s %-12s %-15s%n",
                "Code", "Nom", "Prix", "Stock", "Peremption", "Categorie");
        System.out.println("----------------------------------------");
        for (Medicament m : liste) {
            m.afficher();
        }
        System.out.println("========================================");
        System.out.println("Total : " + liste.size() + " medicament(s)");
    }

    public void afficher() {
        System.out.printf("%-10s %-20s %-10.0f %-8d %-12s %-15s%n",
                codeMedicament, nom, prix, quantiteStock, datePeremption, categorie);
    }

    // ════════════════════════════════════════
    // METHODE 3 — rechercherMedicament
    // ════════════════════════════════════════
    public static void rechercherMedicament(String recherche) {
        boolean trouve = false;
        for (Medicament m : liste) {
            if (m.codeMedicament.equalsIgnoreCase(recherche)
                    || m.nom.toLowerCase().contains(recherche.toLowerCase())) {
                if (!trouve) {
                    System.out.println("\n=== Resultats de recherche ===");
                    System.out.printf("%-10s %-20s %-10s %-8s %-12s %-15s%n",
                            "Code", "Nom", "Prix", "Stock", "Peremption", "Categorie");
                    System.out.println("------------------------------");
                }
                m.afficher();
                trouve = true;
            }
        }
        if (!trouve) {
            System.out.println("Aucun medicament trouve pour : " + recherche);
        }
    }

    // ════════════════════════════════════════
    // METHODE 4 — mettreAJourStock
    // ════════════════════════════════════════
    public static void mettreAJourStock(String code, int quantiteAjoutee) {
        Medicament m = trouverParCode(code);
        if (m == null) {
            System.out.println("ERREUR : Medicament '" + code + "' introuvable.");
            return;
        }
        m.quantiteStock += quantiteAjoutee;
        Sauvegarde.sauvegarder();
        System.out.println("Stock mis a jour. Nouveau stock de '" + m.nom + "' : " + m.quantiteStock);
    }

    // ════════════════════════════════════════
    // METHODE 5 — alerteStockBas
    // ════════════════════════════════════════
    public static void alerteStockBas() {
        boolean alerte = false;
        for (Medicament m : liste) {
            if (m.quantiteStock < m.stockMinimum) {
                if (!alerte) {
                    System.out.printf("%-10s %-20s %-10s %-10s%n",
                            "Code", "Nom", "Stock", "Minimum");
                    System.out.println("------------------------------------------");
                }
                System.out.printf("%-10s %-20s %-10d %-10d  <-- STOCK BAS%n",
                        m.codeMedicament, m.nom, m.quantiteStock, m.stockMinimum);
                alerte = true;
            }
        }
        if (!alerte) {
            System.out.println("Aucun medicament en stock bas.");
        }
    }

    // ════════════════════════════════════════
    // METHODE 6 — alertePeremption
    // Compare les dates au format JJ/MM/AAAA
    // ════════════════════════════════════════
    public static void alertePeremption() {
        boolean alerte = false;
        // Date du jour
        java.time.LocalDate today = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter fmt =
                java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (Medicament m : liste) {
            try {
                java.time.LocalDate dateExp = java.time.LocalDate.parse(m.datePeremption, fmt);
                // Alerte si peremption dans moins de 30 jours ou deja depassee
                long joursRestants = java.time.temporal.ChronoUnit.DAYS.between(today, dateExp);
                if (joursRestants <= 30) {
                    if (!alerte) {
                        System.out.printf("%-10s %-20s %-12s %-15s%n",
                                "Code", "Nom", "Peremption", "Jours restants");
                        System.out.println("-------------------------------------------");
                    }
                    String statut = joursRestants < 0 ? "PERIME !" : joursRestants + " jours";
                    System.out.printf("%-10s %-20s %-12s %-15s%n",
                            m.codeMedicament, m.nom, m.datePeremption, statut);
                    alerte = true;
                }
            } catch (Exception e) {
                System.out.println("Date invalide pour : " + m.nom + " (" + m.datePeremption + ")");
            }
        }
        if (!alerte) {
            System.out.println("Aucun medicament proche de la peremption.");
        }
    }

    // ════════════════════════════════════════
    // METHODE 7 — verifierStock
    // ════════════════════════════════════════
    public static boolean verifierStock(String code, int quantiteDemandee) {
        Medicament m = trouverParCode(code);
        if (m == null) return false;
        return m.quantiteStock >= quantiteDemandee;
    }

    // ════════════════════════════════════════
    // METHODE 8 — trouverParCode
    // ════════════════════════════════════════
    public static Medicament trouverParCode(String code) {
        for (Medicament m : liste) {
            if (m.codeMedicament.equalsIgnoreCase(code)) {
                return m;
            }
        }
        return null;
    }

    // ════════════════════════════════════════
    // SERIALISATION JSON (pour Sauvegarde.java)
    // ════════════════════════════════════════
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
