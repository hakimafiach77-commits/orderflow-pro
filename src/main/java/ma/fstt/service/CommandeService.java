package ma.fstt.service;

import ma.fstt.entities.Commande;
import java.util.List;

public interface CommandeService {

    void ajouter(Commande commande) throws Exception;

    void modifier(Commande commande) throws Exception;

    void supprimer(Long id) throws Exception;

    Commande trouverParId(Long id) throws Exception;

    List<Commande> listerTous() throws Exception;
}