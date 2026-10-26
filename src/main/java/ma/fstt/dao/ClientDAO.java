
package ma.fstt.dao;

import ma.fstt.entities.Client;
import ma.fstt.service.ClientService;
import ma.fstt.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ClientDAO implements ClientService {

    @Override
    public void ajouter(Client client) throws SQLException {
        String sql = "INSERT INTO client (nom, email, telephone) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, client.getNom());
            ps.setString(2, client.getEmail());
            ps.setString(3, client.getTelephone());

            ps.executeUpdate();
        }
    }

    @Override
    public void modifier(Client client) throws SQLException {
        String sql = "UPDATE client SET nom=?, email=?, telephone=? WHERE id_client=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, client.getNom());
            ps.setString(2, client.getEmail());
            ps.setString(3, client.getTelephone());
            ps.setLong(4, client.getIdClient());

            ps.executeUpdate();
        }
    }

    @Override
    public void supprimer(Long id) throws SQLException {
        String sql = "DELETE FROM client WHERE id_client=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Client trouverParId(Long id) throws SQLException {
        String sql = "SELECT * FROM client WHERE id_client=?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return convertirEnClient(rs);
                }
            }
        }
        return null;
    }

    @Override
    public List<Client> listerTous() throws SQLException {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT * FROM client ORDER BY id_client";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                clients.add(convertirEnClient(rs));
            }
        }
        return clients;
    }

    private Client convertirEnClient(ResultSet rs) throws SQLException {
        Client client = new Client();

        client.setIdClient(rs.getLong("id_client"));
        client.setNom(rs.getString("nom"));
        client.setEmail(rs.getString("email"));
        client.setTelephone(rs.getString("telephone"));

        return client;
    }
}
