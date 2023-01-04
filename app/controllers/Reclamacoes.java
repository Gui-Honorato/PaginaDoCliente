package controllers;

/**
 * Objetivo: essa classe tem como objetivo controlar
 * todas as ações da aplicação web da parte de cadastro de reclamações
 * 
 * Autor: Guilherme da Silva Honorato (g.honorato@escolar.ifrn.edu.br)
 * 
 * Data de Criação: 21/10/2022
 * ##########################
 * Ultima Alteração:
 * 
 * Programador/Gerente de projeto: Guilherme Honorato
 * Data: 02/12/2022
 * Alteração: teste de funcionalidades e resolução de bug no pesquisa do listar dos clientes
 * 
 * ###########################
 */






//importações de bibliotecas do java
import java.io.File;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.hibernate.cfg.CollectionSecondPass;

import models.ArquivadoStatus;

import models.DeletadoStatusReclamacao;
import models.Reclamacao;
import models.TipoDeUsuario;
import models.Usuario;
import play.data.validation.Valid;
import play.mvc.Controller;
import play.mvc.With;
import play.mvc.Scope.Session;
import security.Administrator;
import security.Seguranca;

@With(Seguranca.class)
public class Reclamacoes extends Controller {
    /**
     * action do formulario, só renderiza o formulario
     * 
     */
    public static void formulario() {
        render();
    }
    
    /**
     * Formulario que os adms podem fazer as reclamações pelos clientes
     */
    @Administrator
    public static void formularioAdmin(){
        List<Usuario> usuariosListSelectObj = Usuario.find("tipoDeUsuarioEnum = ?1", TipoDeUsuario.CLIENT).fetch();
        render(usuariosListSelectObj);
    }
    /**
     * @param reclamacaoObj
     * @param fotoFalha
     * 
     * action de salvar uma reclamação que recebe o model Reclamacao e transforma em objeto, e também recebe um File
     */
    public static void salvarReclamacao(@Valid Reclamacao reclamacaoObj, File fotoFalha){
        if(validation.hasErrors()){
            validation.keep();
            params.flash();
            formulario();
        }
        
        
        //caso haja uma foto inserida no input ele vai entrar nesse IF se execultar todos esses comandos
        //esses comandos além de salvar uma reclamação, salvam a foto em arquivo associam a mesma a reclamação no banco de dados pelo nome
        if (fotoFalha != null) {
            //salva o nome da foto no banco assoaciando ela a reclamação
            reclamacaoObj.fotoFalhaNome = fotoFalha.getName();
            //define que o status da reclamação é enviado 
            reclamacaoObj.statusDaReclamacao = "Enviada";
            //define a data que a reclamação foi realizada
            reclamacaoObj.dataReclamacao = new Date();
            session.get("usuario.id");
            //salva a reclamação no banco
            reclamacaoObj.save();
            //informa o sucesso no cadastro na tela do usuario
            flash.success("Sua reclamação foi cadastrada com sucesso!");

            //cria uma pasta dentro da pasta uploads com o nome do id da reclamação
            new File("PaginaDoCliente/uploads/" + reclamacaoObj.id).mkdir();
            //salva o caminho da foto nesse objeto
            File localizacaoFotoObj = new File("PaginaDoCliente/uploads/" + reclamacaoObj.id + "/" +reclamacaoObj.fotoFalhaNome);
            //verifica se o caminho existe, se ele existir ele deleta pra colocar uma nova foto (em caso de edição)
            if (localizacaoFotoObj.exists()) {
                localizacaoFotoObj.delete();
            }
            //finalmente salva a foto no diretorio que esta dentro desse objeto
            fotoFalha.renameTo(localizacaoFotoObj);
        //Caso nao exista foto ele entra aqui nesse else (mais usado na hora da edição pois ele nao retorna a foto pro file por questões de segurança do proprio HTML)
        } else { 
            //define que o status da reclamação é enviado 
            reclamacaoObj.statusDaReclamacao = "Enviada";
            //define a data que a reclamação foi realizada
            reclamacaoObj.dataReclamacao = new Date();
            //salva a reclamação no banco
            reclamacaoObj.save();
             //informa o sucesso no cadastro na tela do usuario
            flash.success("Reclamação cadastrada com sucesso!");
        
        }
        //chama a action detalhar para o detalhamento da reclamação

         
       if(session.get("usuario.tipoUsuario").equals("CLIENT")){
           detalharReclamacao(reclamacaoObj.id);
       }
       if(session.get("usuario.tipoUsuario").equals("ADMIN")){
        listarReclamacoesAdmins();
       }


    }
    /**
     * action da listagem de reclamações
     */
    public static void listarReclamacoes(){
        //recebe um parametro digitado no campo de pesquisa da viewr listarReclamacoes
        String pesquisaCaixaDetexto = params.get("pesquisaTexto");
        //Recebe uma lista em formato de coleção para as reclamações desarquivadas
        List<Reclamacao> reclamacaoListObj = Collections.EMPTY_LIST;
        //Recebe uma lista em fformato de coleção para as reclamações arquivados
        List<Reclamacao> reclamacaoListArqObj = Collections.EMPTY_LIST;
        //recebe o email do usuario logado
        String usuarioEmailString = session.get("usuario.email");
        
        //Caso o campo de texto de pesquisa esteja vazio ou no caso de recém carregado ele esteja nulo, ele entrara aqui
        if(pesquisaCaixaDetexto == null || pesquisaCaixaDetexto.isEmpty()){
            //Pesquisa no banco as reclamações do usuario logado que estao cadastradas não removidas e desarquivadas
            reclamacaoListObj = Reclamacao.find("deletadoStatusReclamacaoEnum = ?1 AND arquivadoStatusEnum = ?2 AND usuarioReclamador.emailUsuarioString = ?3", DeletadoStatusReclamacao.ONINTERFACE, ArquivadoStatus.OFFARQUIVO, usuarioEmailString).fetch();
            //Pesquisa no banco as reclamações do usuario logado que estao cadastradas não removidas e arquivadas
            reclamacaoListArqObj = Reclamacao.find("deletadoStatusReclamacaoEnum = ?1 AND arquivadoStatusEnum = ?2 AND usuarioReclamador.emailUsuarioString = ?3", DeletadoStatusReclamacao.ONINTERFACE, ArquivadoStatus.ONARQUIVO, usuarioEmailString).fetch();
        //Caso seja digitado algo no campo de pesquisa ele entrarar aqui
        } else {
            //Recebe o parametro pesquisado e pesquisa por ele, além de pesquisa por todos os parametros infomado nos comentarios das listas anteriores e reclamações desarquivadas
            reclamacaoListObj = Reclamacao.find("((lower(tituloReclamacao) like ?1 OR numPedido like ?2) AND deletadoStatusReclamacaoEnum = ?3) AND arquivadoStatusEnum = ?4 AND usuarioReclamador.emailUsuarioString = ?5", "%"+pesquisaCaixaDetexto.toLowerCase()+ "%", "%"+pesquisaCaixaDetexto+"%", DeletadoStatusReclamacao.ONINTERFACE, ArquivadoStatus.OFFARQUIVO, usuarioEmailString).fetch();
           //Recebe o parametro pesquisado e pesquisa por ele, além de pesquisa por todos os parametros infomado nos comentarios das listas anteriores e reclamações arquivadas
            reclamacaoListArqObj = Reclamacao.find("((lower(tituloReclamacao) like ?1 OR numPedido like ?2) AND deletadoStatusReclamacaoEnum = ?3) AND arquivadoStatusEnum = ?4 AND usuarioReclamador.emailUsuarioString = ?5", "%"+pesquisaCaixaDetexto.toLowerCase()+ "%", "%"+pesquisaCaixaDetexto+"%", DeletadoStatusReclamacao.ONINTERFACE, ArquivadoStatus.ONARQUIVO, usuarioEmailString).fetch();
        }
        //renderiza o objeto pra viwer
        render(reclamacaoListObj, reclamacaoListArqObj);
    }
    /**
     * @param id
     * action de remover um objeto que recebe um long id
     */
    public static void removerReclamacao(Long id){
        //uma filtragem que pega a entidade que esta no banco de dados pelo ID recebido e salva no objeto
        Reclamacao reclamacaoRemObj = Reclamacao.findById(id);
        //seta o atributo de exclusao pelo usuario desse objeto para OFFINTERFACE
        reclamacaoRemObj.deletadoStatusReclamacaoEnum = DeletadoStatusReclamacao.OFFINTERFACE;
        //salva a alteração feita
        reclamacaoRemObj.save();
        //apresenta uma mensagem de sucesso na remoção na tela do usuario
        flash.success("Sua reclamação foi removida");
        //chama a action listagem
        listarReclamacoes();
        //feito isso, a entidade nao aparecera para o usuario, porém ficara salva no banco de dados
    }
    /**
     * @param id
     * action de detalhar uma reclamação que recebe um long id
     */
    public static void detalharReclamacao(Long id) {
        //uma filtragem que pega a entidade que esta no banco de dados pelo ID recebido e salva no objeto
        Reclamacao reclamacaoDetalharObj = Reclamacao.findById(id);
        //renderiza o objeto para viewr detalharReclamacao
        render(reclamacaoDetalharObj);
    }
    /**
     * @param id
     * action de edição de objeto que recebe um long id
     */
    public static void editarReclamacao(Long id){
         //uma filtragem que pega a entidade que esta no banco de dados pelo ID recebido e salva no objeto
        Reclamacao reclamacaoEditObj = Reclamacao.findById(id);
        //renderiza o objeto para viewr formulario
        renderTemplate("Reclamacoes/formulario.html", reclamacaoEditObj);
        //feito isso os dados salvos serao carregados nos seus devidos campus do formulario podendo ser editado e salvos novamente
    }

    /**
     * @param id
     * Arquiva a reclamação
     */
    public static void arquivarReclamacao(Long id) {
        //Recebe a reclamação e coloca em objeto
        Reclamacao reclamacaoArqObj = Reclamacao.findById(id);
        //seta como arquivado no banco
        reclamacaoArqObj.arquivadoStatusEnum = ArquivadoStatus.ONARQUIVO;
        //salva as alterações
        reclamacaoArqObj.save();
        //apresenta mensagem de sucesso na tela
        flash.success("Sua reclamação foi arquivada");
        //renderiza o listar novamente
        listarReclamacoes();
    }
      /**
     * @param id
     * desarquiva a reclamação
     */
    public static void desarquivarReclamacao(Long id){
         //Recebe a reclamação e coloca em objeto
        Reclamacao reclamacaoArqObj = Reclamacao.findById(id);
        //seta como desarquivado no banco
        reclamacaoArqObj.arquivadoStatusEnum = ArquivadoStatus.OFFARQUIVO;
         //salva as alterações
        reclamacaoArqObj.save();
        //renderiza o listar novamente
        listarReclamacoes();
    }
    
    /**
     * 
     * lista todas as reclamações para os administradores
     */
    @Administrator
    public static void listarReclamacoesAdmins(){
      //recebe o parametro de pesquisa por reclamações
       String pesquisaCaixaDetexto = params.get("pesquisaTexto");
       //recebe uma lista coleção
       List<Reclamacao> reclamacaoListAdminsObj = Collections.EMPTY_LIST;
        //caso a caixa de pesquisa esteja vazia entra aqui
       if(pesquisaCaixaDetexto == null || pesquisaCaixaDetexto.isEmpty()){
        //pesquisa no banco se a reclamação nao esta removida e se esta com status de enviada ou em Analise
        //as reclamações que ja foram respondidas nao apareceram mais pro adm
        reclamacaoListAdminsObj = Reclamacao.find("deletadoStatusReclamacaoEnum = ?1 AND (statusDaReclamacao = ?2 OR statusDaReclamacao = ?3)", DeletadoStatusReclamacao.ONINTERFACE, "Enviada", "Em analise").fetch();
        //caso a caixa de pesquisa tiver algo entra aqui
       } else {
        //Pesquisa pelas mesmas coisas do anterior e pelo titulo da reclamação ou nome do cliente que fez a reclamação
        reclamacaoListAdminsObj = Reclamacao.find("(lower(tituloReclamacao) like ?1 OR lower(usuarioReclamador.nomeUsuarioString) like ?2) AND deletadoStatusReclamacaoEnum = ?3  AND (statusDaReclamacao = ?4 OR statusDaReclamacao = ?5)", "%"+pesquisaCaixaDetexto.toLowerCase()+ "%", "%"+pesquisaCaixaDetexto.toLowerCase()+"%", DeletadoStatusReclamacao.ONINTERFACE, "Enviada", "Em analise").fetch();
    }
    //renderiza a lista
       render(reclamacaoListAdminsObj);
    }
    
    /**
     * @param id
     * detalha a reclamação para os administradores
     */
    @Administrator
    public static void detalharReclamacaoAdmins(Long id){
        //recebe a reclamação solicitada
        Reclamacao reclamacaoDetalharAdminsObj = Reclamacao.findById(id);
        //seta o status da reclamação para em analise
        reclamacaoDetalharAdminsObj.statusDaReclamacao = "Em analise";
        //salva alterações
        reclamacaoDetalharAdminsObj.save();
        //recebe a resposta do adm para o cliete
        String respostaDaEmpresaTextString = params.get("respostaDaEmpresaText");
        //caso a caixa estiver vazia entra aqui
        if(respostaDaEmpresaTextString == null || respostaDaEmpresaTextString.isEmpty()){
            reclamacaoDetalharAdminsObj.respostaDaEmpresa = "";
            reclamacaoDetalharAdminsObj.save();
        }
        //caso a caixa de resposta da empresa for preenchida entra aqui
        else {
            //cadastra a resposta e salva no banco
            reclamacaoDetalharAdminsObj.respostaDaEmpresa = respostaDaEmpresaTextString;
            reclamacaoDetalharAdminsObj.statusDaReclamacao = "Respondida";
            reclamacaoDetalharAdminsObj.save();
            //apresenta mensagem de sucesso
            flash.success("Reclamação respondida com sucesso");
            //chama o listar
            Reclamacoes.listarReclamacoesAdmins();
        }
        render(reclamacaoDetalharAdminsObj);
    }
    /**
     * @param id
     * 
     * Remove a reclamação do lado do administrador
     */
    @Administrator
    public static void removerReclamacaoAdmins(Long id){
        //recebe a reclamação
        Reclamacao reclamacaoRemObj = Reclamacao.findById(id);
        //seta como removida no banco
        reclamacaoRemObj.deletadoStatusReclamacaoEnum = DeletadoStatusReclamacao.OFFINTERFACE;
        //salva as alterações
        reclamacaoRemObj.save();
        //apresenta mensagem de sucesso
        flash.success("Sua reclamação foi removida");
        //manda listar as reclamações
        listarReclamacoesAdmins();
    }
}
