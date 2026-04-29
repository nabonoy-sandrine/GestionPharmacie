import java.util.ArrayList;

class Vente {

  
    // ATTRIBUTS
    
    String codeVente;
    Client client;
    String codeMedicament;
    int    quantite;
    double montantTotal;
    String dateVente;

    static ArrayList<Vente> liste = new ArrayList<>();

    
    // CONSTRUCTEUR
    
    public Vente(String codeVente, Client client,
                 String codeMedicament, int quantite,
                 double montantTotal, String dateVente) {
        this.codeVente      = codeVente;
        this.client         = client;
        this.codeMedicament = codeMedicament;
        this.quantite       = quantite;
        this.montantTotal   = montantTotal;
        this.dateVente      = dateVente;
    }

    // ════════════════════════════════════════
    // METHODE 1 — effectuerVente
    // ETAPE 1 : Chercher le medicament
    // ETAPE 2 : Verifier le stock
    // ETAPE 3 : Calculer le montant
    // ETAPE 4 : Diminuer le stock
    // ETAPE 5 : Enregistrer la vente
    // ETAPE 6 : Generer le ticket
    // ════════════════════════════════════════
    public static void effectuerVente(String codeVente,
                                      Client client,
                                      String codeMedicament,
                                      int quantite,
                                      String dateVente) {
        // ETAPE 1 — Chercher le medicament
        Medicament med = Medicament.trouverParCode(codeMedicament);
        if (med == null) {
            System.out.println("ERREUR : Medicament '" + codeMedicament + "' introuvable.");
            return;
        }

        // ETAPE 2 — Verifier le stock
        if (!Medicament.verifierStock(codeMedicament, quantite)) {
            System.out.println("ERREUR : Stock insuffisant. Stock disponible : "
                    + med.quantiteStock + " unite(s).");
            return;
        }

        // ETAPE 3 — Calculer le montant
        double montant = med.prix * quantite;

        // ETAPE 4 — Diminuer le stock automatiquement
        med.quantiteStock -= quantite;

        // ETAPE 5 — Enregistrer la vente
        Vente v = new Vente(codeVente, client, codeMedicament, quantite, montant, dateVente);
        liste.add(v);
        Sauvegarde.sauvegarder(); // Sauvegarde automatique

        // ETAPE 6 — Generer le ticket
        genererTicket(codeVente, client, med, quantite, montant, dateVente);
    }

    // ════════════════════════════════════════
    // METHODE 2 — genererTicket
    // ════════════════════════════════════════
    private static void genererTicket(String codeVente,
                                      Client client,
                                      Medicament med,
                                      int quantite,
                                      double montant,
                                      String dateVente) {
        System.out.println("\n=========================================");
        System.out.println("           TICKET DE CAISSE              ");
        System.out.println("=========================================");
        System.out.println("N Vente     : " + codeVente);
        System.out.println("Date        : " + dateVente);
        System.out.println("-----------------------------------------");
        client.afficher();
        System.out.println("-----------------------------------------");
        System.out.println("Medicament  : " + med.nom);
        System.out.println("Categorie   : " + med.categorie);
        System.out.println("Quantite    : " + quantite);
        System.out.printf ("Prix unit.  : %.0f FCFA%n", med.prix);
        System.out.println("-----------------------------------------");
        System.out.printf ("TOTAL       : %.0f FCFA%n", montant);
        System.out.println("Stock rest. : " + med.quantiteStock + " unites");
        System.out.println("=========================================");
        System.out.println("      Merci de votre visite !            ");
        System.out.println("=========================================");
    }

    // ════════════════════════════════════════
    // METHODE 3 — afficherHistorique
    // ════════════════════════════════════════
    public static void afficherHistorique() {
        if (liste.isEmpty()) {
            System.out.println("Aucune vente enregistree.");
            return;
        }
        System.out.println("\n======================================================");
        System.out.printf("%-10s %-15s %-12s %-10s %-10s %-10s%n",
                "Code", "Client", "Medicament", "Qte", "Total", "Date");
        System.out.println("------------------------------------------------------");
        for (Vente v : liste) {
            v.afficher();
        }
        System.out.println("======================================================");
        double totalGeneral = 0;
        for (Vente v : liste) totalGeneral += v.montantTotal;
        System.out.printf("Chiffre d affaires total : %.0f FCFA (%d vente(s))%n",
                totalGeneral, liste.size());
    }

    public void afficher() {
        System.out.printf("%-10s %-15s %-12s %-10d %-10.0f %-10s%n",
                codeVente,
                client.prenom + " " + client.nom,
                codeMedicament,
                quantite,
                montantTotal,
                dateVente);
    }

    // ════════════════════════════════════════
    // SERIALISATION JSON
    // ════════════════════════════════════════
    public String toJson() {
        return String.format(
            "{\"codeVente\":\"%s\",\"clientNom\":\"%s\",\"clientPrenom\":\"%s\"," +
            "\"clientTel\":\"%s\",\"codeMedicament\":\"%s\"," +
            "\"quantite\":%d,\"montantTotal\":%.2f,\"dateVente\":\"%s\"}",
            codeVente,
            client.nom.replace("\"","\\\""),
            client.prenom.replace("\"","\\\""),
            client.telephone,
            codeMedicament,
            quantite, montantTotal, dateVente
        );
    }

    public static Vente fromJson(String json) {
        String code    = extraireChaine(json, "codeVente");
        String cNom    = extraireChaine(json, "clientNom");
        String cPrenom = extraireChaine(json, "clientPrenom");
        String cTel    = extraireChaine(json, "clientTel");
        String codeMed = extraireChaine(json, "codeMedicament");
        int    qte     = Integer.parseInt(extraireChaine(json, "quantite", false));
        double montant = Double.parseDouble(extraireChaine(json, "montantTotal", false));
        String date    = extraireChaine(json, "dateVente");
        Client c = new Client(cNom, cPrenom, cTel);
        return new Vente(code, c, codeMed, qte, montant, date);
    }

    private static String extraireChaine(String json, String cle) {
        return extraireChaine(json, cle, true);
    }

    private static String extraireChaine(String json, String cle, boolean estChaine) {
        String motif = "\"" + cle + "\":" + (estChaine ? "\"" : "");
        int debut = json.indexOf(motif) + motif.length();
        if (estChaine) {
            int fin = json.indexOf("\"", debut);
            return json.substring(debut, fin);
        } else {
            int fin = json.indexOf(",", debut);
            if (fin == -1) fin = json.indexOf("}", debut);
            return json.substring(debut, fin).trim();
        }
    }
}
