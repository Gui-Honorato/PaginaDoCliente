package security;
/**
 * Objetivo: essa classe tem como objetivo fazer a segurança do sistema
 * 
 * Autor: Guilherme da Silva Honorato (g.honorato@escolar.ifrn.edu.br)
 * 
 * Data de Criação: 01/11/2022
 * ##########################
 * Ultima Alteração:
 * 
 * Programador/Gerente de projeto: Guilherme Honorato
 * Data: 10/12/2022
 * Alteração: teste de funcionalidades e correção de bug no verificadorAdministrador mais especificamente na logica no IF
 * 
 * ###########################
 */
import controllers.Logins;
import models.TipoDeUsuario;
import play.mvc.Before;
import play.mvc.Controller;

public class Seguranca extends Controller {
    //checa se o usuaro esta logado no sistema para libere-lo as funcionalidades
    @Before
	static void checkAuthentication() {
		if (session.get("usuario.email") == null) {
			flash.error("É necessário fazer o login no sistema");
			Logins.formularioLogin();
		}
	}
	//Verifica se o usuario é administrador para liberar as funções que so o admin pode manipular
	@Before
	static void verificarAdministrador() {
		String tipoUsuarioVerificadorString = session.get("usuario.tipoUsuario");
		Administrator adminAnnotation = getActionAnnotation(Administrator.class);
		if (adminAnnotation != null && !TipoDeUsuario.ADMIN.name().equals(tipoUsuarioVerificadorString)) {
			forbidden("Acesso restrito aos administradores do sistema");
		}
	}
}
