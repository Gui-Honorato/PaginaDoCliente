package controllers;

/**
 * Objetivo: essa classe tem como objetivo controlar
 * todas as ações da aplicação web
 * 
 * Autor: Guilherme da Silva Honorato (g.honorato@escolar.ifrn.edu.br)
 * 
 * Data de Criação: 14/10/2022
 * ##########################
 * Ultima Alteração:
 * 
 * Programador/Gerente de projeto: Guilherme Honorato
 * Data: 21/10/2022
 * Alteração: teste de funcionalidades
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

//importações de classes
import models.DeletadoStatus;
import models.Reclamacao;
import play.mvc.Controller;

public class Reclamacoes extends Controller {
    /**
     * action do formulario, só renderiza o formulario
     * 
     */
    public static void formulario() {
        render();
    }
    /**
     * @param reclamacaoObj
     * @param fotoFalha
     * 
     * action de salvar uma reclamação que recebe o model Reclamacao e transforma em objeto, e também recebe um File
     */
    public static void salvarReclamacao(Reclamacao reclamacaoObj, File fotoFalha){
        //caso haja uma foto inserida no input ele vai entrar nesse IF se execultar todos esses comandos
        //esses comandos além de salvar uma reclamação, salvam a foto em arquivo associam a mesma a reclamação no banco de dados pelo nome
        if (fotoFalha != null) {
            //salva o nome da foto no banco assoaciando ela a reclamação
            reclamacaoObj.fotoFalhaNome = fotoFalha.getName();
            //define que o status da reclamação é enviado 
            reclamacaoObj.statusDaReclamacao = "Enviada";
            //define a data que a reclamação foi realizada
            reclamacaoObj.dataReclamacao = new Date();
            //salva a reclamação no banco
            reclamacaoObj.save();
        
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
        }
        //chama a action detalhar para o detalhamento da reclamação
        detalharReclamacao(reclamacaoObj.id);
    }
    /**
     * action da listagem de reclamações
     */
    public static void listarReclamacoes(){
        //recebe um parametro digitado no campo de pesquisa da viewr listarReclamacoes
        String pesquisaCaixaDetexto = params.get("pesquisaTexto");
        //Recebe uma lista em formato de coleção
        List<Reclamacao> reclamacaoListObj = Collections.EMPTY_LIST;

        List<Reclamacao> reclamacaoListArqObj = Collections.EMPTY_LIST;
        //Caso o campo de texto de pesquisa esteja vazio ou no caso de recém carregado ele esteja nulo, ele entrara aqui
        if(pesquisaCaixaDetexto == null || pesquisaCaixaDetexto.isEmpty()){
            //o objeto que esta com a lista recebera um filtro onde ele trara apenas as reclamações que estiverem com ONINTERFACE no seu estado de eclusão pelo usuario
            reclamacaoListObj = Reclamacao.find("deletadoStatusObj = ?1", DeletadoStatus.ONINTERFACE).fetch();
        //Caso seja digitado algo no campo de pesquisa ele entrarar aqui
        } else {
            //o objeto que esta com a lista recebera um filtro onde ele vai trazer todas as reclamações que tiverem o titulo ou o numero do pedido parecidos com o que foi digitado na area de pesquisa E as reclamações que tiverem com ONINTERFACE no seu campo de exclusao pelo usuario
            reclamacaoListObj = Reclamacao.find("(lower(tituloReclamacao) like ?1 OR numPedido like ?2) AND deletadoStatusObj = ?3", "%"+pesquisaCaixaDetexto.toLowerCase()+ "%", "%"+pesquisaCaixaDetexto+"%", DeletadoStatus.ONINTERFACE).fetch();
        }
        //renderiza o objeto pra viwer
        render(reclamacaoListObj);
    }
    /**
     * @param id
     * action de remover um objeto que recebe um long id
     */
    public static void removerReclamacao(Long id){
        //uma filtragem que pega a entidade que esta no banco de dados pelo ID recebido e salva no objeto
        Reclamacao reclamacaoRemObj = Reclamacao.findById(id);
        //seta o atributo de exclusao pelo usuario desse objeto para OFFINTERFACE
        reclamacaoRemObj.deletadoStatusObj = DeletadoStatus.OFFINTERFACE;
        //salva a alteração feita
        reclamacaoRemObj.save();
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
}
