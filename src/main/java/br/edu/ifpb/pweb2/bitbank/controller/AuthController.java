package br.edu.ifpb.pweb2.bitbank.controller;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import br.edu.ifpb.pweb2.bitbank.util.PasswordUtil;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CorrentistaRepository correntistaRepository;

    @GetMapping
    public ModelAndView getForm(ModelAndView model) {
        // Especifica que a view corresponde ao caminho auth/login e será renderizada como resposta
        model.setViewName("auth/login");
        // Cria uma nova instância da classe Correntista e a disponibiliza para a view
        model.addObject("correntista", new Correntista());
        return model;
    }

    // Recebe os dados do formulário de login e valida as credenciais. Se forem válidas,
    // cria uma sessão para o usuário e redireciona para a página inicial. Caso contrário,
    // redireciona de volta para a página de login com uma mensagem de erro
    @PostMapping
    public ModelAndView valide(Correntista correntista, HttpSession session, ModelAndView model,
                               RedirectAttributes redirectAttts) {
        if ((correntista = this.isValido(correntista)) != null) {
            session.setAttribute("usuario", correntista);
            model.setViewName("redirect:/home");
        } else {
            redirectAttts.addFlashAttribute("mensagem", "Login e/ou senha inválidos!");
            model.setViewName("redirect:/auth");
        }
        return model;
    }

    // Encerra a sessão do usuário e redireciona para a página de login
    @GetMapping("/logout")
    public ModelAndView logout(ModelAndView mav, HttpSession session) {
        session.invalidate();
        mav.setViewName("redirect:/auth");
        return mav;
    }

    // Verifica as credenciais do correntista
    private Correntista isValido(Correntista correntista) {
        Correntista correntistaBD = correntistaRepository.findByEmail(correntista.getEmail());
        boolean valido = false;
        if (correntistaBD != null) {
            if (PasswordUtil.checkPass(correntista.getSenha(), correntistaBD.getSenha())) {
                valido = true;
            }
        }
        return valido ? correntistaBD : null;
    }
}
