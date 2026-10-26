package ma.fstt.web;
import ma.fstt.entities.Produit;
import ma.fstt.dao.ProduitDAO;
import ma.fstt.service.ProduitService;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
@WebServlet("/produits") public class ProduitServlet extends HttpServlet {
    private ProduitService service;
    @Override public void init(){service=new ProduitDAO();}
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        try{req.setAttribute("produits",service.listerTous());req.getRequestDispatcher("/WEB-INF/views/produits.jsp").forward(req,resp);}catch(Exception e){throw new ServletException("Chargement des produits",e);}
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{
        req.setCharacterEncoding("UTF-8");
        try{
            String action=req.getParameter("action");if(action==null)action="add";
            if("delete".equals(action))service.supprimer(WebUtil.id(req,"id"));
            else{
                String nom=req.getParameter("nom");double prix=Double.parseDouble(req.getParameter("prix"));int stock=Integer.parseInt(req.getParameter("stock"));
                if(nom==null||nom.isBlank()||!Double.isFinite(prix)||prix<0||stock<0)throw new IllegalArgumentException("Produit invalide.");
                Produit p=new Produit();p.setNom(nom.trim());p.setPrix(prix);p.setStock(stock);
                if("update".equals(action)){p.setIdProduit(WebUtil.id(req,"id"));service.modifier(p);}else service.ajouter(p);
            }
            WebUtil.redirect(req,resp,"/produits","Modifications enregistrées");
        }catch(Exception e){WebUtil.error(req,resp,"/produits",e);}
    }
}
