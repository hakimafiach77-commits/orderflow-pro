package ma.fstt.dao;

import ma.fstt.entities.Client;
import ma.fstt.entities.Commande;
import ma.fstt.service.CommandeService;
import ma.fstt.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommandeDAO implements CommandeService {

    @Override
    public void ajouter(Commande commande) throws Exception {

        String sql = "INSERT INTO commande " +
                "(date_commande, statut, id_client) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(
                     sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setDate(1, Date.valueOf(commande.getDateCommande()));
            ps.setString(2, commande.getStatut());
            ps.setLong(3, commande.getClient().getIdClient());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    commande.setIdCommande(rs.getLong(1));
                }
            }
        }
    }

    @Override
    public void modifier(Commande commande) throws Exception {

        String sql = "UPDATE commande SET date_commande=?, " +
                "statut=?, id_client=? WHERE id_commande=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(commande.getDateCommande()));
            ps.setString(2, commande.getStatut());
            ps.setLong(3, commande.getClient().getIdClient());
            ps.setLong(4, commande.getIdCommande());

            ps.executeUpdate();
        }
    }

    @Override
    public void supprimer(Long id) throws Exception {

        String sql = "DELETE FROM commande WHERE id_commande=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Commande trouverParId(Long id) throws Exception {

        String sql = "SELECT * FROM commande WHERE id_commande=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return convertirCommande(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Commande> listerTous() throws Exception {

        List<Commande> commandes = new ArrayList<>();

        String sql = "SELECT * FROM commande ORDER BY id_commande DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                commandes.add(convertirCommande(rs));
            }
        }

        return commandes;
    }

    private Commande convertirCommande(ResultSet rs) throws Exception {

        Commande commande = new Commande();

        commande.setIdCommande(rs.getLong("id_commande"));

        Date date = rs.getDate("date_commande");
        if (date != null) {
            commande.setDateCommande(date.toLocalDate());
        }

        commande.setStatut(rs.getString("statut"));

        Long idClient = rs.getLong("id_client");

        ClientDAO clientDAO = new ClientDAO();
        Client client = clientDAO.trouverParId(idClient);

        commande.setClient(client);

        return commande;
    }
}