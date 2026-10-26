package ma.fstt.web;
import javax.servlet.http.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
public final class WebUtil {
    private WebUtil() {}
    public static long id(HttpServletRequest req,String name) {return Long.parseLong(req.getParameter(name));}
    public static void redirect(HttpServletRequest req,HttpServletResponse resp,String path,String notice) throws java.io.IOException {
        String url=req.getContextPath()+path;
        if(notice!=null)url+="?notice="+URLEncoder.encode(notice,StandardCharsets.UTF_8);
        resp.sendRedirect(url);
    }
    public static void error(HttpServletRequest req,HttpServletResponse resp,String path,Exception e) throws java.io.IOException {
        String message=e instanceof java.sql.SQLIntegrityConstraintViolationException?"Opération impossible : élément lié à d'autres données ou valeur déjà utilisée.":e.getMessage();
        if(message==null)message="Opération impossible.";
        String url=req.getContextPath()+path+(path.contains("?")?"&":"?")+"error="+URLEncoder.encode(message,StandardCharsets.UTF_8);
        resp.sendRedirect(url);
    }
}
