package br.edu.ifpb.pweb2.bitbank.controller;

import br.edu.ifpb.pweb2.bitbank.model.Conta;
import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import br.edu.ifpb.pweb2.bitbank.service.ContaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/contas")
public class ContaController {

    @Autowired
    private CorrentistaRepository correntistaRepository;

    @Autowired
    private ContaService contaService;

    @RequestMapping("/form")
    //O ModelAndView é um objeto em que é possível definir, ao mesmo tempo, atributos e a página para onde o sistema deve ir ao terminar a execução do método
    public ModelAndView getForm(ModelAndView modelAndView, Model model) {
        modelAndView.setViewName("contas/form");
        modelAndView.addObject("conta", new Conta(new Correntista()));

        return modelAndView;
    }

    @ModelAttribute("correntistaItems")
    //Devolve um objeto que é uma lista de correntistas cadastrados. Eles serão usados no campo select
    public List<Correntista> getCorrentistas() {
        return correntistaRepository.findAll();
    }

    @RequestMapping(value="/save", method = RequestMethod.POST)
    public ModelAndView adicioneConta(Conta conta, ModelAndView modelAndView) {
        contaService.save(conta);
        modelAndView.setViewName("contas/list");
        modelAndView.addObject("contas", contaService.findAll());

        return modelAndView;
    }

    @RequestMapping("/list")
    public ModelAndView liste(ModelAndView modelAndView) {
        modelAndView.setViewName("contas/list");
        modelAndView.addObject("contas", contaService.findAll());

        return modelAndView;
    }
}
