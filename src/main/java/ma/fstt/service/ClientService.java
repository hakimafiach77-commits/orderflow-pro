package ma.fstt.service;

import ma.fstt.entities.Client;
import java.util.List;

public interface ClientService {

    void ajouter(Client client) throws Exception;

    void modifier(Client client) throws Exception;

    void supprimer(Long id) throws Exception;

    Client trouverParId(Long id) throws Exception;

    List<Client> listerTous() throws Exception;
}