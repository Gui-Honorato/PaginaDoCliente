package controllers;

import java.util.Date;
import java.util.List;

import models.Usuario;
import models.DeletadoStatusUsuario;
import play.mvc.Controller;

public class Usuarios extends Controller{
    public static void formulario(){
        render();
    }
    
    public static void salvarUsuario(Usuario usuarioObj){
        usuarioObj.dataDeEntrada = new Date();
        usuarioObj.save();
        listarUsuarios();

    }
    public static void editarUsuario (Long id) {
        Usuario usuarioEditObj = Usuario.findById(id);
		renderTemplate("Usuarios/formulario.html", usuarioEditObj);
	}
    
    public static void listarUsuarios(){
		
		List<Usuario> usuarioListObj = Usuario.findAll();
		render(usuarioListObj);
    }

    public static void removerUsuario(Long id) {
        Usuario usuarioRemObj = Usuario.findById(id);
        usuarioRemObj.deletadoStatusUsuarioEnum = DeletadoStatusUsuario.NEGATIVADO;
        listarUsuarios();
    }
    
    public static void detalharUsuario(long id) {
        Usuario usuarioDetObj = Usuario.findById(id);
        render(usuarioDetObj);
    }
    
}
