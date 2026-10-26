package ma.fstt.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Commande {

    private Long idCommande;
    private LocalDate dateCommande;
    private String statut;
    private Client client;
    private List<LignedeCommande> lignes = new ArrayList<>();

    public Commande() {
    }

    public Commande(Long idCommande, LocalDate dateCommande,
                    String statut, Client client) {
        this.idCommande = idCommande;
        this.dateCommande = dateCommande;
        this.statut = statut;
        this.client = client;
    }

    public Long getIdCommande() {
        return idCommande;
    }

    public void setIdCommande(Long idCommande) {
        this.idCommande = idCommande;
    }

    public LocalDate getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(LocalDate dateCommande) {
        this.dateCommande = dateCommande;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public List<LignedeCommande> getLignes() {
        return lignes;
    }

    public void setLignes(List<LignedeCommande> lignes) {
        this.lignes = lignes;
    }

    public double getTotal() {
        double total = 0;

        if (lignes != null) {
            for (LignedeCommande ligne : lignes) {
                total += ligne.getSousTotal();
            }
        }

        return total;
    }
}