package ma.fstt.dao;

import ma.fstt.entities.Produit;
import ma.fstt.service.ProduitService;
import ma.fstt.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProduitDAO implements ProduitService {

    @Override
    public void ajouter(Produit produit) throws Exception {

        String sql = "INSERT INTO produit (nom, prix, stock) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produit.getNom());
            ps.setDouble(2, produit.getPrix());
            ps.setInt(3, produit.getStock());

            ps.executeUpdate();
        }
    }

    @Override
    public void modifier(Produit produit) throws Exception {

        String sql = "UPDATE produit SET nom=?, prix=?, stock=? WHERE id_produit=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, produit.getNom());
            ps.setDouble(2, produit.getPrix());
            ps.setInt(3, produit.getStock());
            ps.setLong(4, produit.getIdProduit());

            ps.executeUpdate();
        }
    }

    @Override
    public void supprimer(Long id) throws Exception {

        String sql = "DELETE FROM produit WHERE id_produit=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);
            ps.executeUpdate();
        }
    }

    @Override
    public Produit trouverParId(Long id) throws Exception {

        String sql = "SELECT * FROM produit WHERE id_produit=?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return convertirProduit(rs);
                }
            }
        }

        return null;
    }

    @Override
    public List<Produit> listerTous() throws Exception {

        List<Produit> produits = new ArrayList<>();

        String sql = "SELECT * FROM produit ORDER BY id_produit DESC";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                produits.add(convertirProduit(rs));
            }
        }

        return produits;
    }

    private Produit convertirProduit(ResultSet rs) throws SQLException {

        Produit produit = new Produit();

        produit.setIdProduit(rs.getLong("id_produit"));
        produit.setNom(rs.getString("nom"));
        produit.setPrix(rs.getDouble("prix"));
        produit.setStock(rs.getInt("stock"));

        return produit;
    }
}