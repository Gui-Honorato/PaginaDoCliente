package controllers;
/**
 * Objetivo: essa classe tem como objetivo controlar
 * todas as ações da aplicação web da parte de cadastro de usuarios
 * 
 * Autor: Guilherme da Silva Honorato (g.honorato@escolar.ifrn.edu.br)
 * 
 * Data de Criação: 31/10/2022
 * ##########################
 * Ultima Alteração:
 * 
 * Programador/Gerente de projeto: Guilherme Honorato
 * Data: 02/12/2022
 * Alteração: teste de funcionalidades e resolução de bug no salvar senha caso o usuario for editado
 * 
 * ###########################
 */

import java.util.Collections;
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
    /**
     * Renderiza o formulario de cadastro
     */
    @Administrator
    public static void formulario(){
        render();
    }
    /**
     * @param usuarioObj
     * @param senha
     * 
     * salva usuario
     */
    @Administrator
    public static void salvarUsuario(Usuario usuarioObj, String senha){
        //verifica se tem senha caso o usuario for editado para nao recadastrar a senha
        if(senha.equals("") == false){
            usuarioObj.senha = senha;
        }
        //cadastra uma data de cadastro do usuario no sistema
        usuarioObj.dataDeEntrada = new Date();
        //salva o usuario
        usuarioObj.save();
        //apresenta uma mensagem de sucesso de cadastro
        flash.success("Usuario Cadastrado com sucesso!");
        //chama a lista de usuarios
        listarUsuarios();

    }
    /**
     * @param id
     * 
     * edita o usuario
     */
    @Administrator
    public static void editarUsuario (Long id) {
        //pega o usuario a ser editado
        Usuario usuarioEditObj = Usuario.findById(id);
		//renderiza as informações do usuario de volta no formulario
        renderTemplate("Usuarios/formulario.html", usuarioEditObj);
	}
    /**
     * lista os usuarios
     */
    @Administrator
    public static void listarUsuarios(){
		//recebe o valor da caixa do pesquisar por um usuario
        String pesquisaCaixaDetextoString = params.get("pesquisaTexto");
        //coleção de usuarios
        List<Usuario> usuarioListObj = Collections.EMPTY_LIST;
        //caso a caixa de pesquisa estiver em branco entra aqui
        if(pesquisaCaixaDetextoString == null || pesquisaCaixaDetextoString.isEmpty()){
            //pesquisa no banco os usuarios ativos que nao foram removido
            usuarioListObj = Usuario.find("deletadoStatusUsuarioEnum = ?1", DeletadoStatusUsuario.ATIVADO).fetch();
            //caso a caixa de pesquisa tiver algo entra aqui
        } else {
            //pesquisa no banco o pesquisado na caixa entre os usuarios ativos que nao foram removidos
            usuarioListObj = Usuario.find("(lower(nomeUsuarioString) like ?1 OR numIdentificadorUsuarioString like ?2) AND deletadoStatusUsuarioEnum = ?3", "%"+pesquisaCaixaDetextoString.toLowerCase()+ "%", "%"+pesquisaCaixaDetextoString+"%", DeletadoStatusUsuario.ATIVADO).fetch();
        }
        //renderiza a lista
		render(usuarioListObj);
    }
    /**
     * @param id
     * 
     * remove o usuario do sistema
     */
    @Administrator
    public static void removerUsuario(Long id) {
        //recebe o usuario a ser removido
        Usuario usuarioRemObj = Usuario.findById(id);
        //seta como removido do sistema no banco
        usuarioRemObj.deletadoStatusUsuarioEnum = DeletadoStatusUsuario.NEGATIVADO;
        //salva alteração
        usuarioRemObj.save();
        //mensagem de sucesso na remoção
        flash.success("Usuario removido com sucesso");
        //renderiza a lista de usuarios
        listarUsuarios();
    }
    /**
     * @param id
     * detalha usuario
     */
    @Administrator
    public static void detalharUsuario(long id) {
        //recebe usuario a ser detalhado
        Usuario usuarioDetObj = Usuario.findById(id);
        //renderiza as informações
        render(usuarioDetObj);
    }
    
}
