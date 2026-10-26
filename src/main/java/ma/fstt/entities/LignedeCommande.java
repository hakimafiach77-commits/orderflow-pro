package ma.fstt.entities;

public class LignedeCommande {

    private Long idLigne;
    private int quantite;
    private double prixUnitaire;
    private Produit produit;

    public LignedeCommande() {
    }

    public LignedeCommande(Long idLigne, int quantite,
                           double prixUnitaire, Produit produit) {
        this.idLigne = idLigne;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.produit = produit;
    }

    public Long getIdLigne() {
        return idLigne;
    }

    public void setIdLigne(Long idLigne) {
        this.idLigne = idLigne;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public double getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(double prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public Produit getProduit() {
        return produit;
    }

    public void setProduit(Produit produit) {
        this.produit = produit;
    }

    public double getSousTotal() {
        return quantite * prixUnitaire;
    }
}