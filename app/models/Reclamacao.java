package models;

/**
 * Objetivo: essa classe tem como objetivo controlar
 * todas as ações da aplicação web
 * 
 * Autor: Maria Fernanda (ribeiro.fernanda@escolar.ifrn.edu.br)
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


import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.db.jpa.Model;

/*
 * Atributos da entidade reclamação
 * Esses atributos são preenchidos pelo usuario
 */

@Entity
public class Reclamacao extends Model {
    
    public String nomeDoCliente;
    public String numIdentificadorCliente;
    public String emailCliente;
    public String tituloReclamacao;
    public String numPedido;
    public Date dataPedido;
    public String descReclamacao;
    public String fotoFalhaNome;
    
    //Atributos da entidade reclamação
    //Atributos que sao gerados pelo sistema
    public Date dataReclamacao;
    public String statusDaReclamacao;
    
    //Instacidor do enumerate
    @Enumerated(EnumType.STRING) 
    public DeletadoStatus deletadoStatusObj;
    
    //Contrutor
    //Toda vez que uma reclamação for realizada ela ja iniciara com o atributo de exclusao pelo usuario como ONINTERFACE
    public Reclamacao(){
        deletadoStatusObj = DeletadoStatus.ONINTERFACE;
    }

}
