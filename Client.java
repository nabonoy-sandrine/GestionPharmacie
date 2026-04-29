class Client {
    String nom;
    String prenom;
    String telephone;

    // CONSTRUCTEUR
   
    public Client(String nom, String prenom, String telephone) {
        this.nom       = nom;
        this.prenom    = prenom;
        this.telephone = telephone;
    }

    
    // METHODE 1 — afficher
    
    
    public void afficher() {
               System.out.println("Client      : " + prenom + " " + nom);
        System.out.println("Tel         : " + telephone);
    }
}
