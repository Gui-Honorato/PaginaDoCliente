package controllers;

import groovyjarjarpicocli.CommandLine.IFactory;
import models.Usuario;
import play.libs.Crypto;
import play.mvc.Controller;

public class Logins extends Controller {
    public static void formularioLogin(){
        render();
    }
    public static void logar(String login, String senha){
        Usuario usuarioObj = Usuario.find("emailUsuarioString = ?1 and senha = ?2", login, Crypto.passwordHash(senha)).first();

        if(usuarioObj == null){
            flash.error("login e senha invalidos");
            formularioLogin();
        }
        else {
            if(usuarioObj.tipoDeUsuarioEnum.name().equals("ADMIN")){
                session.put("usuario.id", usuarioObj.id);
                session.put("usuario.email", usuarioObj.emailUsuarioString);
                session.put("usuario.tipoUsuario", usuarioObj.tipoDeUsuarioEnum);
                Usuarios.formulario();
            }
            if(usuarioObj.tipoDeUsuarioEnum.name().equals("CLIENT")){
                session.put("usuario.id", usuarioObj.id);
                session.put("usuario.email", usuarioObj.emailUsuarioString);
                session.put("usuario.tipoUsuario", usuarioObj.tipoDeUsuarioEnum);
                Reclamacoes.formulario();
            }
            
           
        }
    }
    public static void logout() {
        session.clear();
        flash.success("Sessão Encerrada Com Sucesso");
        formularioLogin();
    }
}
