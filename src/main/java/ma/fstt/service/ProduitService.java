package ma.fstt.service;

import ma.fstt.entities.Produit;
import java.util.List;

public interface ProduitService {

    void ajouter(Produit produit) throws Exception;

    void modifier(Produit produit) throws Exception;

    void supprimer(Long id) throws Exception;

    Produit trouverParId(Long id) throws Exception;

    List<Produit> listerTous() throws Exception;
}