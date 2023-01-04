package models;

/**
 * Objetivo: essa classe tem como objetivo hospedar o atributo reclaamção
 * 
 * Autor: Maria Fernanda (ribeiro.fernanda@escolar.ifrn.edu.br) e jezreel Lucas (jezreel.lucas@escolar.ifrn.edu.br)
 * 
 * Data de Criação: 21/10/2022
 * ##########################
 * Ultima Alteração:
 * 
 * Programador/Gerente de projeto: Guilherme Honorato
 * Data: 02/12/2022
 * Alteração: teste de funcionalidades e resolução de bug na lista de reclamações de administradores
 *  
 * 
 * ###########################
 */


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import play.data.validation.MinSize;
import play.data.validation.Required;
import play.db.jpa.Model;

/*
 * Atributos da entidade reclamação
 * Esses atributos são preenchidos pelo usuario
 */

@Entity
public class Reclamacao extends Model {
    //atributos da reclamação
    @Required
    public String tituloReclamacao;
    @Required
    @MinSize(4)
    public String numPedido;
    @Required
    public Date dataPedido;
    @Required
    public String descReclamacao;
    public String fotoFalhaNome;
    public String respostaDaEmpresa;
    //relacionameto com a tabela usuario
    @ManyToOne
    public Usuario usuarioReclamador;

    
    //Atributos da entidade reclamação
    //Atributos que sao gerados pelo sistema
    public Date dataReclamacao;
    public String statusDaReclamacao;
    
    //Instacidor do enumerate
    @Enumerated(EnumType.STRING) 
    public DeletadoStatusReclamacao deletadoStatusReclamacaoEnum;
    
    @Enumerated(EnumType.STRING)
    public ArquivadoStatus arquivadoStatusEnum;
    
    //Contrutor
    //Toda vez que uma reclamação for realizada ela ja iniciara com o atributo de exclusao pelo usuario como ONINTERFACE
    public Reclamacao(){
        arquivadoStatusEnum = ArquivadoStatus.OFFARQUIVO;
        deletadoStatusReclamacaoEnum = DeletadoStatusReclamacao.ONINTERFACE;
    }

}
