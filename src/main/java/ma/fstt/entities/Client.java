package ma.fstt.entities;

public class Client {

    private Long idClient;
    private String nom;
    private String email;
    private String telephone;

    public Client() {
    }

    public Client(Long idClient, String nom,
                  String email, String telephone) {
        this.idClient = idClient;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}