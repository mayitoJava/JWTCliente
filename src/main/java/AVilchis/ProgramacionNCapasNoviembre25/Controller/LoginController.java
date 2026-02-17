package AVilchis.ProgramacionNCapasNoviembre25.Controller;

import AVilchis.ProgramacionNCapasNoviembre25.ML.Result;
import AVilchis.ProgramacionNCapasNoviembre25.ML.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("login")
public class LoginController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping
    public String cargar(Model model) {
        model.addAttribute("Usuario", new Usuario());
        return "Login";
    }

    @PostMapping
    public String login(@ModelAttribute("Usuario") Usuario usuario, HttpSession sesion, Model model) {
        try {
            ResponseEntity<Result> response = restTemplate.exchange(
                    "http://localhost:8080/login",
                    HttpMethod.POST,
                    new HttpEntity<>(usuario),
                    new ParameterizedTypeReference<Result>() {
            });
            Result result = response.getBody();

            ObjectMapper mapper = new ObjectMapper();//Para convertir el hasMap en un Usuario
            Usuario usuarioRespuesta = mapper.convertValue(result.Objects.get(0), Usuario.class);//Metodo para conversión
            sesion.setAttribute("token", result.Object);
            sesion.setAttribute("UsuarioAutenticado", usuarioRespuesta);
            if (usuarioRespuesta.Rol.getNombre().equals("Ingeniero")) {
                return "redirect:/Usuario";

            } else if (usuarioRespuesta.Rol.getNombre().equals("Usuario")) {
                return "redirect:/Usuario/detail/" + usuarioRespuesta.getIdUsuario();
            }
        } catch (Exception ex) {
            return "Login";
        }
        return "Login";
    }

}
