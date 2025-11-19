package br.edu.ifpb.pweb2.bitbank.interceptor;

import br.edu.ifpb.pweb2.bitbank.model.Correntista;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        boolean allowed = false;
//        HttpSession httpSession = request.getSession(false);
//
//        // Verifica se a sessãp http não é nula
//        // e se existe um atributo "usuario" do tipo Correntista
//        if (httpSession != null && ((Correntista) httpSession.getAttribute("usuario")) != null) {
//            // Tem um correntista guardado na sessão? Se sim, fez login
//            allowed = true;
//        }
//        // Se estas duas coisas não forem verdadeiras,
//        // alguém não autenticado está tentando acessar a página,
//        // então negue o acesso e redirecione-o para a tela de autenticação
//        else {
//            String baseUrl = request.getContextPath(); // Nome da aplicação
//            // Ou não tem sessão ou tem, mas não tem correntista nela
//            // Redirecione para auth.html
//            String paginaLogin = baseUrl + "/auth";
//            response.sendRedirect(response.encodeRedirectURL(paginaLogin));
//            allowed = false;
//        }
//        return allowed;


        // Alterações para contemplar o caso em que
        // um usuário normal efetou login e
        // conseguiu acessar recursos de administrador
        HttpSession httpSession = request.getSession(false);

        if (httpSession != null) {
            Correntista usuario = (Correntista) httpSession.getAttribute("usuario");

            if (usuario != null) {
                String contextPath = request.getContextPath();
                String path = request.getRequestURI();

                // Remove o contextPath do path
                String relativePath = path.substring(contextPath.length());

                // Verifica se a URI acessada começa com "/correntistas" ou "/contas"
                boolean requerAdmin = relativePath.startsWith("/correntistas") || relativePath.startsWith("/contas");

                // Se exige admin, verificar se o usuário é admin
                if (requerAdmin && !usuario.isAdmin()) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso negado");
                    return false;
                }
                // Está logado e tem permissão
                return true;
            }
        }

            // Sem sessão ou usuário não autenticado
            String baseUrl = request.getContextPath();
            response.sendRedirect(response.encodeRedirectURL(baseUrl + "/auth"));

            return false;
        }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // Sem uso
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        // Sem uso
    }
}
