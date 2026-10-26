package ma.fstt.web;
import ma.fstt.dao.*;
import ma.fstt.entities.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.*;
@WebServlet("/dashboard") public class DashboardServlet extends HttpServlet {
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        try{
            List<Client> clients=new ClientDAO().listerTous();List<Produit> produits=new ProduitDAO().listerTous();List<Commande> commandes=new CommandeDAO().listerTous();
            LignedeCommandeDAO dao=new LignedeCommandeDAO();double total=0;int pending=0;
            for(Commande c:commandes){c.setLignes(dao.listerParCommande(c.getIdCommande()));total+=c.getTotal();if("En attente".equals(c.getStatut()))pending++;}
            req.setAttribute("clientsCount",clients.size());req.setAttribute("productsCount",produits.size());req.setAttribute("ordersCount",commandes.size());req.setAttribute("pendingCount",pending);req.setAttribute("salesTotal",total);
            req.setAttribute("commandes",commandes.subList(0,Math.min(commandes.size(),6)));req.setAttribute("produits",produits.subList(0,Math.min(produits.size(),4)));
            req.getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(req,resp);
        }catch(Exception e){throw new ServletException("Chargement du tableau de bord",e);}
    }
}
