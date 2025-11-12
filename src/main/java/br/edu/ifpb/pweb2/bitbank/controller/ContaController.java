package br.edu.ifpb.pweb2.bitbank.controller;

import br.edu.ifpb.pweb2.bitbank.model.Conta;
import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import br.edu.ifpb.pweb2.bitbank.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/contas")
public class ContaController {

    //Injeção de dependência de Correntista
    @Autowired
    private CorrentistaRepository correntistaRepository;

    //Injeção de dependência de Conta
    @Autowired
    private ContaService contaService;

    //Formulário de cadastro de Conta
    @GetMapping("/form")
    //O ModelAndView é um objeto em que é possível definir, ao mesmo tempo, atributos e a página para onde o sistema deve ir ao terminar a execução do método
    public ModelAndView getForm(ModelAndView model) {
        model.setViewName("contas/form");
        model.addObject("conta", new Conta(new Correntista()));

        return model;
    }

    //Devolve um objeto que é uma lista de correntistas cadastrados. Eles serão usados no campo select
    @ModelAttribute("correntistaItems")
    public List<Correntista> getCorrentistas() {
        return correntistaRepository.findAll();
    }

    //Realiza o cadastro da Conta
    @PostMapping
    public ModelAndView save(Conta conta, ModelAndView model, RedirectAttributes attr) {
        contaService.save(conta);
        attr.addFlashAttribute("mensagem", "Conta inserida com sucesso!");
        model.setViewName("redirect:/contas");
        //model.addObject("contas", contaService.findAll());

        return model;
    }

    @GetMapping
    public ModelAndView listAll(ModelAndView model) {
        model.addObject("contas", contaService.findAll());
        model.setViewName("contas/list");

        return model;
    }

    @GetMapping("/{id}")
    public ModelAndView getContaById(@PathVariable(value = "id") Integer id, ModelAndView model) {
        model.addObject("conta", contaService.findById(id));
        model.setViewName("contas/form");

        return model;
    }
}
