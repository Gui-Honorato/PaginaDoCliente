package controllers;


/**
 * Objetivo: essa classe tem como objetivo controlar todas as funcões de login do sistema
 * 
 * 
 * Autor: Guilherme da Silva Honorato (g.honorato@escolar.ifrn.edu.br)
 * 
 * Data de Criação: 01/11/2022
 * ##########################
 * Ultima Alteração:
 * 
 * Programador/Gerente de projeto: Guilherme Honorato
 * Data: 10/12/2022
 * Alteração: teste de funcionalidades e conferencia de codigo
 * 
 * ###########################
 */

 //Importação de bibliotecas do java
import groovyjarjarpicocli.CommandLine.IFactory;
import models.DeletadoStatusUsuario;
import models.Usuario;
import play.libs.Crypto;
import play.mvc.Controller;

public class Logins extends Controller {
    /**
     * Renderiza o formulario de login
     */
    public static void formularioLogin(){
        render();
    }

    /**
     * @param login
     * @param senha
     * 
     * Controla o login de dos usuarios direcionando conforme seja sua função no sistema, que são elas clientes e administradores
     */
    public static void logar(String login, String senha){
        //Compara o login e senha informados com os cadastrados no banco
        Usuario usuarioObj = Usuario.find("(emailUsuarioString = ?1 and senha = ?2) AND deletadoStatusUsuarioEnum = ?3", login, Crypto.passwordHash(senha), DeletadoStatusUsuario.ATIVADO).first();
        //Caso os login e senha informados nao forem encontrados entra aqui
        if(usuarioObj == null){
            flash.error("login e senha invalidos");
            formularioLogin();
        }
        //Caso for encontrado entra aqui e direciona ele pra pagina de acordo com sua função no sistema
        else {
            //caso for Administrador entra aqui
            if(usuarioObj.tipoDeUsuarioEnum.name().equals("ADMIN")){
                session.put("usuario.name", usuarioObj.nomeUsuarioString);
                session.put("usuario.id", usuarioObj.id);
                session.put("usuario.email", usuarioObj.emailUsuarioString);
                session.put("usuario.tipoUsuario", usuarioObj.tipoDeUsuarioEnum);
                TelasIniciais.telaIncialAdmins();
            }
            //Caso for cliente entra aqui
            if(usuarioObj.tipoDeUsuarioEnum.name().equals("CLIENT")){
                session.put("usuario.name", usuarioObj.nomeUsuarioString);
                session.put("usuario.id", usuarioObj.id);
                session.put("usuario.email", usuarioObj.emailUsuarioString);
                session.put("usuario.tipoUsuario", usuarioObj.tipoDeUsuarioEnum);
                TelasIniciais.telaInicialClientes();
            }
            
           
        }
    }

    /**
     * Faz o logout do usuario logado
     */
    public static void logout() {
        session.clear();
        flash.success("Sessão Encerrada Com Sucesso");
        formularioLogin();
    }
}
