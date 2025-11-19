package br.edu.ifpb.pweb2.bitbank.config;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import br.edu.ifpb.pweb2.bitbank.repository.CorrentistaRepository;
import br.edu.ifpb.pweb2.bitbank.util.PasswordUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

// Classe que inicializa o correntista administrador na aplicação
@Component
public class InicializadorCorrentista implements ApplicationRunner {

    @Autowired
    private CorrentistaRepository correntistaRepository;

    @Override
    // Cria um correntista administrador sempre que a aplicação for iniciada
    public void run(ApplicationArguments args) {
        if (correntistaRepository.findByEmail("admin@bitbank.com") == null) {
            Correntista correntista = new Correntista();
            correntista.setNome("Administrador");
            correntista.setEmail("admin@bitbank.com");
            correntista.setSenha(PasswordUtil.hashPassword("123"));
            correntista.setAdmin(true);

            correntistaRepository.save(correntista);
            System.out.println("Correntista admin inserido com sucesso.");
        }
        // Caso o correntista admin já exista, exibe uma mensagem de erro
        else {
            System.out.println("Correntista admin já existe.");
        }

        // Cria um correntista comum assim que a aplicação for iniciada
        if (correntistaRepository.findByEmail("user@bitbank.com") == null) {
            Correntista user = new Correntista();
            user.setNome("Usuário Padrão");
            user.setEmail("user@bitbank.com");
            user.setSenha(PasswordUtil.hashPassword("1234"));
            user.setAdmin(false);

            correntistaRepository.save(user);
            System.out.println("Correntista padrão inserido com sucesso.");
        }
        // Caso o correntista usuário já exista, exibe uma mensagem de erro
        else {
            System.out.println("Correntista padrão já existe.");
        }
    }
}
