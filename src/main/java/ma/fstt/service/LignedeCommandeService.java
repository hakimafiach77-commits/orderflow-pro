package ma.fstt.service;

import ma.fstt.entities.LignedeCommande;
import java.util.List;

public interface LignedeCommandeService {

    void ajouter(Long idCommande, LignedeCommande ligne) throws Exception;

    void modifier(LignedeCommande ligne) throws Exception;

    void supprimer(Long id) throws Exception;

    LignedeCommande trouverParId(Long id) throws Exception;

    List<LignedeCommande> listerParCommande(Long idCommande) throws Exception;
}