package ma.fstt.web;
import ma.fstt.dao.ClientDAO;
import ma.fstt.entities.Client;
import ma.fstt.service.ClientService;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
@WebServlet("/clients") public class ClientServlet extends HttpServlet {
    private ClientService service;
    @Override public void init(){service=new ClientDAO();}
    @Override protected void doGet(HttpServletRequest req,HttpServletResponse resp)throws ServletException,IOException{
        try{req.setAttribute("clients",service.listerTous());req.getRequestDispatcher("/WEB-INF/views/clients.jsp").forward(req,resp);}catch(Exception e){throw new ServletException("Chargement des clients",e);}
    }
    @Override protected void doPost(HttpServletRequest req,HttpServletResponse resp)throws IOException{
        req.setCharacterEncoding("UTF-8");
        try{
            String action=req.getParameter("action");if(action==null)action="add";
            if("delete".equals(action)){service.supprimer(WebUtil.id(req,"id"));}
            else {
                String nom=req.getParameter("nom"),email=req.getParameter("email"),tel=req.getParameter("telephone");
                if(nom==null||nom.isBlank()||email==null||!email.contains("@"))throw new IllegalArgumentException("Nom ou email invalide.");
                Client c=new Client();c.setNom(nom.trim());c.setEmail(email.trim());c.setTelephone(tel==null?"":tel.trim());
                if("update".equals(action)){c.setIdClient(WebUtil.id(req,"id"));service.modifier(c);}else service.ajouter(c);
            }
            WebUtil.redirect(req,resp,"/clients","Modifications enregistrées");
        }catch(Exception e){WebUtil.error(req,resp,"/clients",e);}
    }
}
