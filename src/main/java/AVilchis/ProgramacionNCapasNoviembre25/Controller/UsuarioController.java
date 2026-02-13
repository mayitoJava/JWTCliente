package AVilchis.ProgramacionNCapasNoviembre25.Controller;

import AVilchis.ProgramacionNCapasNoviembre25.ML.Result;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Rol;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Usuario;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("Usuario")
public class UsuarioController {

    private static final String url = "http://localhost:8080/api";

    @GetMapping
    public String getAll(Model model, HttpSession sesion, HttpServletResponse httpResponse) {

        if (sesion.getAttribute("token") == null) {
            return "redirect:/login";
        }

        Usuario userAuth = (Usuario) sesion.getAttribute("UsuarioAutenticado");

        if (userAuth != null && userAuth.Rol.getNombre().equals("Usuario")) {
            return "redirect:/Usuario/detail/" + userAuth.getIdUsuario();
        }

        httpResponse.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        httpResponse.setHeader("Pragma", "no-cache");
        httpResponse.setHeader("Expires", "0");

        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders header = new HttpHeaders();
            header.setBearerAuth((String) sesion.getAttribute("token"));
            HttpEntity<String> requestEntity = new HttpEntity<>(header);

            ResponseEntity<Result<List<Usuario>>> responseUsuario
                    = restTemplate.exchange(url + "/usuario",
                            HttpMethod.GET,
                            requestEntity,
                            new ParameterizedTypeReference<Result<List<Usuario>>>() {
                    });

            ResponseEntity<Result<List<Rol>>> responseRol
                    = restTemplate.exchange(url + "/rol",
                            HttpMethod.GET,
                            requestEntity,
                            new ParameterizedTypeReference<Result<List<Rol>>>() {
                    });

            model.addAttribute("Usuarios", responseUsuario.getBody().Object);
            model.addAttribute("UsuarioBusqueda", new Usuario());
            model.addAttribute("Roles", responseRol.getBody().Object);
            model.addAttribute("UsuarioAutenticado", userAuth);

        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
        }
        return "Index";
    }

}
