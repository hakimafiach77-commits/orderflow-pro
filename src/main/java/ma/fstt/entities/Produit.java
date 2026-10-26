
package ma.fstt.entities;

public class Produit {
    private Long idProduit;
    private String nom;
    private double prix;
    private int stock;

    public Produit() {}

    public Produit(Long idProduit, String nom, double prix, int stock) {
        this.idProduit = idProduit;
        this.nom = nom;
        this.prix = prix;
        this.stock = stock;
    }

    public Long getIdProduit() {
        return idProduit;
    }

    public void setIdProduit(Long idProduit) {
        this.idProduit = idProduit;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public double getPrix() {
        return prix;
    }

    public void setPrix(double prix) {
        this.prix = prix;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        if (idProduit == null) {
            return "assets/images/products/default.svg";
        }

        switch (idProduit.intValue()) {
            case 1:
                return "assets/images/products/laptop.svg";
            case 2:
                return "assets/images/products/mouse.svg";
            case 3:
                return "assets/images/products/keyboard.svg";
            case 4:
                return "assets/images/products/monitor.svg";
            case 5:
                return "assets/images/products/printer.svg";
            case 6:
                return "assets/images/products/headphones.svg";
            case 7:
                return "assets/images/products/ssd.svg";
            case 8:
                return "assets/images/products/webcam.svg";
            case 9:
                return "assets/images/products/wireless-mouse.svg";
            default:
                return "assets/images/products/default.svg";
        }
    }
}
