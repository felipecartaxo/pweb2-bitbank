package br.edu.ifpb.pweb2.bitbank.service;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.ContaRepository;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import br.edu.ifpb.pweb2.bitbank.model.Conta;

import java.util.List;

@Component
public class ContaService implements Service<Conta, Integer> {

    //Injeção de dependência de Conta
    @Autowired
    private ContaRepository contaRepository;

    //Injeção de dependência de Correntista
    @Autowired
    private CorrentistaRepository correntistaRepository;

    @Override
    public List<Conta> findAll() {
        return contaRepository.findAll();
    }

    @Override
    public Conta findById(Integer id) {
        return contaRepository.findById(id);
    }

    @Override
    public Conta save(Conta conta) {
        Correntista correntista = correntistaRepository.findById(conta.getCorrentista().getId());
        //Relaciona a Conta com o Correntista
        conta.setCorrentista(correntista);

        return contaRepository.save(conta);
    }
}
