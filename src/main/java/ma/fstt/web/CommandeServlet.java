package ma.fstt.web;
import ma.fstt.dao.*;
import ma.fstt.entities.*;
import ma.fstt.service.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
@WebServlet("/commandes") public class CommandeServlet extends HttpServlet {
    private CommandeService commandes;private ClientDAO clients;private ProduitDAO produits;private LignedeCommandeService lignes;
    @Override public void init(){commandes=new CommandeDAO();clients=new ClientDAO();produits=new ProduitDAO();lignes=new LignedeCommandeDAO();}
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        try{
            List<Commande> list=commandes.listerTous();
            for(Commande c:list)c.setLignes(lignes.listerParCommande(c.getIdCommande()));
            req.setAttribute("commandes",list);req.setAttribute("clients",clients.listerTous());req.setAttribute("produits",produits.listerTous());
            String selected=req.getParameter("selected");
            if(selected!=null&&!selected.isBlank()){
                try{long id=Long.parseLong(selected);for(Commande c:list)if(c.getIdCommande()==id){req.setAttribute("selectedCommande",c);break;}}catch(NumberFormatException ignored){}
            }
            if(req.getAttribute("selectedCommande")==null&&!list.isEmpty())req.setAttribute("selectedCommande",list.get(0));
            req.getRequestDispatcher("/WEB-INF/views/commandes.jsp").forward(req,resp);
        }catch(Exception e){throw new ServletException("Chargement des commandes",e);}
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{
        req.setCharacterEncoding("UTF-8");
        try{
            String action=req.getParameter("action");if(action==null)action="add";
            String selected=req.getParameter("idCommande");String dest="/commandes";
            if("delete".equals(action)){
                long id=WebUtil.id(req,"idCommande");
                // Restore stock before deleting order and its cascade lines.
                for(LignedeCommande l:lignes.listerParCommande(id))lignes.supprimer(l.getIdLigne());
                commandes.supprimer(id);
            }else if("addLine".equals(action)){
                long id=WebUtil.id(req,"idCommande"),idProduit=WebUtil.id(req,"idProduit");int q=Integer.parseInt(req.getParameter("quantite"));
                LignedeCommande l=new LignedeCommande();l.setQuantite(q);Produit p=new Produit();p.setIdProduit(idProduit);l.setProduit(p);lignes.ajouter(id,l);dest="/commandes?selected="+id;
            }else if("deleteLine".equals(action)){
                long id=WebUtil.id(req,"idCommande"),idLigne=WebUtil.id(req,"idLigne");
                boolean belongs=false;for(LignedeCommande l:lignes.listerParCommande(id))if(l.getIdLigne()==idLigne)belongs=true;
                if(!belongs)throw new IllegalArgumentException("Ligne incorrecte.");
                lignes.supprimer(idLigne);dest="/commandes?selected="+id;
            }else if("status".equals(action)){
                long id=WebUtil.id(req,"idCommande");Commande c=commandes.trouverParId(id);
                if(c==null)throw new IllegalArgumentException("Commande introuvable.");
                String status=req.getParameter("statut");if(!Arrays.asList("En attente","En cours","Livree","Annulee").contains(status))throw new IllegalArgumentException("Statut invalide.");
                c.setStatut(status);commandes.modifier(c);dest="/commandes?selected="+id;
            }else{
                Client client=clients.trouverParId(WebUtil.id(req,"idClient"));if(client==null)throw new IllegalArgumentException("Client introuvable.");
                Commande c=new Commande();c.setClient(client);c.setDateCommande(LocalDate.now());c.setStatut("En attente");commandes.ajouter(c);dest="/commandes?selected="+c.getIdCommande();
            }
            WebUtil.redirect(req,resp,dest,"Opération réussie");
        }catch(Exception e){WebUtil.error(req,resp,"/commandes",e);}
    }
}
