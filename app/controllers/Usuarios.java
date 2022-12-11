package controllers;

import java.util.Date;
import java.util.List;

import models.Usuario;
import models.DeletadoStatusUsuario;
import play.mvc.Controller;
import play.mvc.With;
import security.Administrator;
import security.Seguranca;


@With(Seguranca.class)

public class Usuarios extends Controller{
    @Administrator
    public static void formulario(){
        render();
    }
    @Administrator
    public static void salvarUsuario(Usuario usuarioObj, String senha){
        if(senha.equals("") == false){
            usuarioObj.senha = senha;
        }
        usuarioObj.dataDeEntrada = new Date();
        usuarioObj.save();
        listarUsuarios();

    }
    @Administrator
    public static void editarUsuario (Long id) {
        Usuario usuarioEditObj = Usuario.findById(id);
		renderTemplate("Usuarios/formulario.html", usuarioEditObj);
	}
    @Administrator
    public static void listarUsuarios(){
		
		List<Usuario> usuarioListObj = Usuario.findAll();
		render(usuarioListObj);
    }
    @Administrator
    public static void removerUsuario(Long id) {
        Usuario usuarioRemObj = Usuario.findById(id);
        usuarioRemObj.deletadoStatusUsuarioEnum = DeletadoStatusUsuario.NEGATIVADO;
        listarUsuarios();
    }
    @Administrator
    public static void detalharUsuario(long id) {
        Usuario usuarioDetObj = Usuario.findById(id);
        render(usuarioDetObj);
    }
    
}
