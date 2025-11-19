package br.edu.ifpb.pweb2.bitbank.config;

import br.edu.ifpb.pweb2.bitbank.interceptor.AuthInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptionConfigurator implements WebMvcConfigurer {

    // Injetamos um AuthInterceptor na classe de configuração
    // para registrá-lo e configurá-lo no método de call-back
    // da classe de configuração InterceptorConfiguration
    @Autowired
    AuthInterceptor authInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // As duas linhas após o registro configuram
        // para quais recursos o interceptador será chamado (addPathPaterrns)
        // e para quais deverá ser ignorado (excludePathPatterns)
        registry.addInterceptor(authInterceptor)
                .addPathPatterns("/**", "/correntistas/**", "/contas/**")
                .excludePathPatterns("/auth/**", "/css/**", "/images/**");
    }
}
