import java.util.ArrayList;

class Fournisseur {
    String codeFournisseur;
    String nom;
    String telephone;
    String adresse;
    String email;

    static ArrayList<Fournisseur> liste = new ArrayList<>();

    public Fournisseur(String codeFournisseur, String nom,
                       String telephone, String adresse,
                       String email) {
        this.codeFournisseur = codeFournisseur;
        this.nom             = nom;
        this.telephone       = telephone;
        this.adresse         = adresse;
        this.email           = email;
    }
    public String getCodeFournisseur() {
    return codeFournisseur;
}
    
    // METHODE 1 — ajouterFournisseur
    
   public static void ajouterFournisseur(String code, String nom,
                                      String telephone,
                                      String adresse,
                                      String email) {

    // 🔹 1. Validation des données
    if (code == null || code.trim().isEmpty()) {
        System.out.println("Erreur : Le code fournisseur est obligatoire.");
        return;
    }

    if (nom == null || nom.trim().isEmpty()) {
        System.out.println("Erreur : Le nom est obligatoire.");
        return;
    }

    // 🔹 2. Vérification d'existence (plus propre)
    if (existeFournisseur(code)) {
        System.out.println("Erreur : Un fournisseur avec ce code existe déjà.");
        return;
    }

    // 🔹 3. Création + ajout
    Fournisseur fournisseur = new Fournisseur(code.trim(), nom.trim(),
                                              telephone, adresse, email);

    liste.add(fournisseur);

    // 🔹 4. Sauvegarde sécurisée
    try {
        Sauvegarde.sauvegarder();
        System.out.println("Fournisseur ajouté avec succès.");
    } catch (Exception e) {
        System.out.println("Erreur lors de la sauvegarde !");
        e.printStackTrace();
    }
}
private static boolean existeFournisseur(String code) {
    for (Fournisseur f : liste) {
        if (f.getCodeFournisseur().equalsIgnoreCase(code.trim())) {
            return true;
        }
    }
    return false;
}

    
    // METHODE 2 — afficherTous
    
    public static void afficherTous() {
        if (liste.isEmpty()) {
            System.out.println("Aucun fournisseur trouvé.");
            return;
        }
         System.out.println("\n========================================");
        System.out.printf("%-10s %-20s %-15s %-25s %-25s%n",
                "Code", "Nom", "Telephone", "Adresse", "Email");
        System.out.println("----------------------------------------");
        for (Fournisseur f : liste) {
            f.afficher();
        }
        System.out.println("========================================");
        System.out.println("Total : " + liste.size() + " fournisseur(s)");
    }

    public void afficher() {
        System.out.printf("%-10s %-20s %-15s %-25s %-25s%n",
                codeFournisseur, nom, telephone, adresse, email);
    }

    
    // METHODE 3 — modifierFournisseur
   
    public static void modifierFournisseur(String code,
                                           String nouveauNom,
                                           String nouveauTel,
                                           String nouvelleAdresse,
                                           String nouveauEmail) {
        Fournisseur f = trouverParCode(code);
        if (f == null) {
            System.out.println("ERREUR : Fournisseur '" + code + "' introuvable.");
            return;
        }
        f.nom      = nouveauNom;
        f.telephone = nouveauTel;
        f.adresse  = nouvelleAdresse;
        f.email    = nouveauEmail;
        Sauvegarde.sauvegarder();
        System.out.println("Fournisseur '" + code + "' modifie avec succes.");
    }

    
    // METHODE 4 — supprimerFournisseur
   
    public static void supprimerFournisseur(String code) {
        Fournisseur f = trouverParCode(code);
        if (f == null) {
            System.out.println("ERREUR : Fournisseur '" + code + "' introuvable.");
            return;
        }
        liste.remove(f);
        Sauvegarde.sauvegarder();
        System.out.println("Fournisseur '" + f.nom + "' supprime.");
    }

    
    // METHODE 5 — trouverParCode
    
    public static Fournisseur trouverParCode(String code) {
         for (Fournisseur f : liste) {
            if (f.codeFournisseur.equalsIgnoreCase(code)) return f;
        }
        return null;
    }

    // ════════════════════════════════════════
    // SERIALISATION JSON
    // ════════════════════════════════════════
    public String toJson() {
        return String.format(
            "{\"codeFournisseur\":\"%s\",\"nom\":\"%s\",\"telephone\":\"%s\"," +
            "\"adresse\":\"%s\",\"email\":\"%s\"}",
            codeFournisseur, nom.replace("\"","\\\""),
            telephone, adresse.replace("\"","\\\""), email
        );
    }

    public static Fournisseur fromJson(String json) {
        String code = extraireChamp(json, "codeFournisseur");
        String nom  = extraireChamp(json, "nom");
        String tel  = extraireChamp(json, "telephone");
        String adr  = extraireChamp(json, "adresse");
        String mail = extraireChamp(json, "email");
        return new Fournisseur(code, nom, tel, adr, mail);
    }

    private static String extraireChamp(String json, String cle) {
        String motif = "\"" + cle + "\":\"";
        int debut = json.indexOf(motif) + motif.length();
        int fin   = json.indexOf("\"", debut);
        return json.substring(debut, fin);
    }
}
