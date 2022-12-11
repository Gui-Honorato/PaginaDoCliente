package security;

import controllers.Logins;
import models.TipoDeUsuario;
import play.mvc.Before;
import play.mvc.Controller;

public class Seguranca extends Controller {
    
    @Before
	static void checkAuthentication() {
		if (session.get("usuario.email") == null) {
			flash.error("É necessário fazer o login no sistema");
			Logins.formularioLogin();
		}
	}
	@Before
	static void verificarAdministrador() {
		String tipoUsuarioVerificadorString = session.get("usuario.tipoUsuario");
		Administrator adminAnnotation = getActionAnnotation(Administrator.class);
		if (adminAnnotation != null && !TipoDeUsuario.ADMIN.name().equals(tipoUsuarioVerificadorString)) {
			forbidden("Acesso restrito aos administradores do sistema");
		}
	}
}
