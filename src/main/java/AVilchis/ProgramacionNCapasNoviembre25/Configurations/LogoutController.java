package AVilchis.ProgramacionNCapasNoviembre25.Configurations;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("logout")
public class LogoutController {

    @GetMapping
    public String salir(HttpSession session, HttpServletRequest request, HttpServletResponse response) {

        if (session != null && session.getAttribute("token") != null) {
            request.getSession().invalidate();
            response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
            response.setHeader("Pragma", "no-cache");
            response.setHeader("Expires", "0");
        }
        return "Logout";
    }

}
