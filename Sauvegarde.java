import java.io.*;
import java.util.ArrayList;

// ════════════════════════════════════════════════════════════
//  SAUVEGARDE.java
//  Gere la persistance des donnees dans le fichier data.json
//  Toutes les donnees (medicaments, fournisseurs, ventes)
//  sont sauvegardees automatiquement apres chaque modification
//  et rechargees au demarrage du programme.
//
//  Format du fichier data.json :
//  {
//    "medicaments": [ {...}, {...} ],
//    "fournisseurs": [ {...}, {...} ],
//    "ventes":       [ {...}, {...} ]
//  }
// ════════════════════════════════════════════════════════════
class Sauvegarde {

    static final String FICHIER = "data.json";

    // ════════════════════════════════════════
    // SAUVEGARDER — ecrit toutes les donnees
    // dans data.json (appele automatiquement)
    // ════════════════════════════════════════
    public static void sauvegarder() {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FICHIER))) {

            pw.println("{");

            // ----- Medicaments -----
            pw.println("  \"medicaments\": [");
            for (int i = 0; i < Medicament.liste.size(); i++) {
                String ligne = "    " + Medicament.liste.get(i).toJson();
                if (i < Medicament.liste.size() - 1) ligne += ",";
                pw.println(ligne);
            }
            pw.println("  ],");

            // ----- Fournisseurs -----
            pw.println("  \"fournisseurs\": [");
            for (int i = 0; i < Fournisseur.liste.size(); i++) {
                String ligne = "    " + Fournisseur.liste.get(i).toJson();
                if (i < Fournisseur.liste.size() - 1) ligne += ",";
                pw.println(ligne);
            }
            pw.println("  ],");

            // ----- Ventes -----
            pw.println("  \"ventes\": [");
            for (int i = 0; i < Vente.liste.size(); i++) {
                String ligne = "    " + Vente.liste.get(i).toJson();
                if (i < Vente.liste.size() - 1) ligne += ",";
                pw.println(ligne);
            }
            pw.println("  ]");

            pw.println("}");

        } catch (IOException e) {
            System.out.println("[SAUVEGARDE] ERREUR ecriture : " + e.getMessage());
        }
    }

    // ════════════════════════════════════════
    // CHARGER — lit data.json au demarrage
    // et reconstruit les listes en memoire
    // ════════════════════════════════════════
    public static void charger() {
        File f = new File(FICHIER);
        if (!f.exists()) {
            System.out.println("[SAUVEGARDE] Nouveau fichier data.json sera cree au premier enregistrement.");
            return;
        }

        try {
            // Lire tout le contenu du fichier
            StringBuilder sb = new StringBuilder();
            BufferedReader br = new BufferedReader(new FileReader(FICHIER));
            String ligne;
            while ((ligne = br.readLine()) != null) {
                sb.append(ligne.trim());
            }
            br.close();

            String json = sb.toString();

            // ----- Charger medicaments -----
            String blockMed = extraireBloc(json, "medicaments");
            if (!blockMed.isEmpty()) {
                ArrayList<String> objets = extraireObjets(blockMed);
                for (String obj : objets) {
                    Medicament.liste.add(Medicament.fromJson(obj));
                }
            }

            // ----- Charger fournisseurs -----
            String blockFour = extraireBloc(json, "fournisseurs");
            if (!blockFour.isEmpty()) {
                ArrayList<String> objets = extraireObjets(blockFour);
                for (String obj : objets) {
                    Fournisseur.liste.add(Fournisseur.fromJson(obj));
                }
            }

            // ----- Charger ventes -----
            String blockVente = extraireBloc(json, "ventes");
            if (!blockVente.isEmpty()) {
                ArrayList<String> objets = extraireObjets(blockVente);
                for (String obj : objets) {
                    Vente.liste.add(Vente.fromJson(obj));
                }
            }

            System.out.println("[SAUVEGARDE] Donnees chargees : "
                    + Medicament.liste.size()  + " medicament(s), "
                    + Fournisseur.liste.size() + " fournisseur(s), "
                    + Vente.liste.size()        + " vente(s).");

        } catch (Exception e) {
            System.out.println("[SAUVEGARDE] ERREUR lecture : " + e.getMessage());
        }
    }

    // ════════════════════════════════════════
    // Utilitaire : extrait le contenu d'un
    // tableau JSON  "cle": [ ... ]
    // Tolere les espaces apres ":" et "["
    // ════════════════════════════════════════
    private static String extraireBloc(String json, String cle) {
        // Chercher "cle" puis avancer jusqu'au [
        String motif = "\"" + cle + "\"";
        int pos = json.indexOf(motif);
        if (pos == -1) return "";
        pos += motif.length();
        // Avancer jusqu'au [
        while (pos < json.length() && json.charAt(pos) != '[') pos++;
        if (pos >= json.length()) return "";
        pos++; // sauter le [
        // Trouver le ] fermant en comptant les niveaux
        int niveau = 1;
        int i = pos;
        while (i < json.length() && niveau > 0) {
            char c = json.charAt(i);
            if (c == '[') niveau++;
            else if (c == ']') niveau--;
            i++;
        }
        return json.substring(pos, i - 1).trim();
    }

    // ════════════════════════════════════════
    // Utilitaire : extrait les objets JSON {}
    // d'un tableau JSON
    // ════════════════════════════════════════
    private static ArrayList<String> extraireObjets(String bloc) {
        ArrayList<String> objets = new ArrayList<>();
        if (bloc.isEmpty()) return objets;
        int i = 0;
        while (i < bloc.length()) {
            if (bloc.charAt(i) == '{') {
                int debut = i;
                int niveau = 1;
                i++;
                while (i < bloc.length() && niveau > 0) {
                    char c = bloc.charAt(i);
                    if (c == '{') niveau++;
                    else if (c == '}') niveau--;
                    i++;
                }
                objets.add(bloc.substring(debut, i));
            } else {
                i++;
            }
        }
        return objets;
    }
}
