package br.edu.ifpb.pweb2.bitbank.controller;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import br.edu.ifpb.pweb2.bitbank.service.CorrentistaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/correntistas")
public class CorrentistaController {

    //Injeção de dependência da camada de serviços de Correntista
    @Autowired
    private CorrentistaService correntistaService;

    //Formulário de cadastro de Correntista
    @GetMapping("/form")
    public ModelAndView getForm(Correntista correntista, ModelAndView model) {
        model.addObject("correntista", correntista);
        model.setViewName("correntistas/form");

        return model;
    }

    //Realiza o cadastra do Correntista
    @PostMapping
    public ModelAndView save(Correntista correntista, ModelAndView model, RedirectAttributes attr) {
        correntistaService.save(correntista);
        //Mensagem temporária que será exibida após cadastrar um Correntista
        attr.addFlashAttribute("mensagem", "Correntista inserido com sucesso!");
        //Feita a inserção de um correntista, redireciona para a listagem ("...bitbank/correntistas")
        model.setViewName("redirect:/correntistas");

        //return "correntistas/list";

        //Agora que iremos utilizar o padrão post-redirect-get, iremos então devolver uma url ao invés de um template
        return model;
    }

    //Lista todos os Correntistas cadastrados
    @GetMapping
    public ModelAndView listAll(ModelAndView model) {
        model.addObject("correntistas", correntistaService.findAll());
        model.setViewName("correntistas/list");

        return model;
    }

    //Busca um Correntista por Id
    @GetMapping("/{id}")
    public ModelAndView getCorrentistaById(@PathVariable(value = "id") Integer id, ModelAndView model) {
        model.setViewName("correntistas/form");
        model.addObject("correntista", correntistaService.findById(id));

        return model;
    }
}