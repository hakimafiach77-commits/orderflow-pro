package ma.fstt.dao;
import ma.fstt.entities.*;
import ma.fstt.service.LignedeCommandeService;
import ma.fstt.util.DBConnection;
import java.sql.*;
import java.util.*;

public class LignedeCommandeDAO implements LignedeCommandeService {
    @Override public void ajouter(Long idCommande, LignedeCommande ligne) throws Exception {
        if (ligne.getQuantite() <= 0) throw new IllegalArgumentException("La quantité doit être positive.");
        try(Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                try(PreparedStatement ps = con.prepareStatement("SELECT id_commande FROM commande WHERE id_commande=?")) {
                    ps.setLong(1,idCommande); try(ResultSet rs=ps.executeQuery()){if(!rs.next()) throw new IllegalArgumentException("Commande introuvable.");}
                }
                double prix;
                try(PreparedStatement ps=con.prepareStatement("SELECT prix,stock FROM produit WHERE id_produit=? FOR UPDATE")) {
                    ps.setLong(1,ligne.getProduit().getIdProduit());
                    try(ResultSet rs=ps.executeQuery()) {
                        if(!rs.next()) throw new IllegalArgumentException("Produit introuvable.");
                        if(rs.getInt("stock")<ligne.getQuantite()) throw new IllegalArgumentException("Stock insuffisant.");
                        prix=rs.getDouble("prix");
                    }
                }
                try(PreparedStatement ps=con.prepareStatement("INSERT INTO ligne_commande (quantite,prix_unitaire,id_commande,id_produit) VALUES (?,?,?,?)")) {
                    ps.setInt(1,ligne.getQuantite());ps.setDouble(2,prix);ps.setLong(3,idCommande);ps.setLong(4,ligne.getProduit().getIdProduit());ps.executeUpdate();
                }
                try(PreparedStatement ps=con.prepareStatement("UPDATE produit SET stock=stock-? WHERE id_produit=?")) {
                    ps.setInt(1,ligne.getQuantite());ps.setLong(2,ligne.getProduit().getIdProduit());ps.executeUpdate();
                }
                con.commit();
            }catch(Exception ex){con.rollback();throw ex;}finally{con.setAutoCommit(true);}
        }
    }
    @Override public void modifier(LignedeCommande ligne) throws Exception {
        if(ligne.getQuantite()<=0) throw new IllegalArgumentException("Quantité invalide.");
        try(Connection con=DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                long produit;int ancienne;
                try(PreparedStatement ps=con.prepareStatement("SELECT id_produit,quantite FROM ligne_commande WHERE id_ligne=? FOR UPDATE")){
                    ps.setLong(1,ligne.getIdLigne());try(ResultSet rs=ps.executeQuery()) {if(!rs.next()) throw new IllegalArgumentException("Ligne introuvable.");produit=rs.getLong(1);ancienne=rs.getInt(2);}}
                int difference=ligne.getQuantite()-ancienne;
                try(PreparedStatement ps=con.prepareStatement("UPDATE produit SET stock=stock-? WHERE id_produit=? AND stock>=?")){
                    ps.setInt(1,difference);ps.setLong(2,produit);ps.setInt(3,Math.max(difference,0));if(ps.executeUpdate()!=1) throw new IllegalArgumentException("Stock insuffisant.");}
                try(PreparedStatement ps=con.prepareStatement("UPDATE ligne_commande SET quantite=? WHERE id_ligne=?")) {ps.setInt(1,ligne.getQuantite());ps.setLong(2,ligne.getIdLigne());ps.executeUpdate();}
                con.commit();
            }catch(Exception ex){con.rollback();throw ex;}finally{con.setAutoCommit(true);}
        }
    }
    @Override public void supprimer(Long id) throws Exception {
        try(Connection con=DBConnection.getConnection()){
            con.setAutoCommit(false);
            try{
                long produit;int quantite;
                try(PreparedStatement ps=con.prepareStatement("SELECT id_produit,quantite FROM ligne_commande WHERE id_ligne=? FOR UPDATE")){
                    ps.setLong(1,id);try(ResultSet rs=ps.executeQuery()){if(!rs.next()) throw new IllegalArgumentException("Ligne introuvable.");produit=rs.getLong(1);quantite=rs.getInt(2);}}
                try(PreparedStatement ps=con.prepareStatement("DELETE FROM ligne_commande WHERE id_ligne=?")){ps.setLong(1,id);ps.executeUpdate();}
                try(PreparedStatement ps=con.prepareStatement("UPDATE produit SET stock=stock+? WHERE id_produit=?")){ps.setInt(1,quantite);ps.setLong(2,produit);ps.executeUpdate();}
                con.commit();
            }catch(Exception ex){con.rollback();throw ex;}finally{con.setAutoCommit(true);}
        }
    }
    @Override public LignedeCommande trouverParId(Long id) throws Exception {
        try(Connection con=DBConnection.getConnection();PreparedStatement ps=con.prepareStatement("SELECT l.*,p.nom,p.prix,p.stock FROM ligne_commande l JOIN produit p ON l.id_produit=p.id_produit WHERE l.id_ligne=?")){
            ps.setLong(1,id);try(ResultSet rs=ps.executeQuery()){return rs.next()?convertir(rs):null;}}
    }
    @Override public List<LignedeCommande> listerParCommande(Long idCommande) throws Exception {
        List<LignedeCommande> lignes=new ArrayList<>();
        try(Connection con=DBConnection.getConnection();PreparedStatement ps=con.prepareStatement("SELECT l.*,p.nom,p.prix,p.stock FROM ligne_commande l JOIN produit p ON l.id_produit=p.id_produit WHERE l.id_commande=? ORDER BY l.id_ligne")){
            ps.setLong(1,idCommande);try(ResultSet rs=ps.executeQuery()){while(rs.next()) lignes.add(convertir(rs));}}
        return lignes;
    }
    private LignedeCommande convertir(ResultSet rs) throws SQLException {
        Produit p=new Produit(rs.getLong("id_produit"),rs.getString("nom"),rs.getDouble("prix"),rs.getInt("stock"));
        return new LignedeCommande(rs.getLong("id_ligne"),rs.getInt("quantite"),rs.getDouble("prix_unitaire"),p);
    }
}
