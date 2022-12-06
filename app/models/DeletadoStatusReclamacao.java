package models;

/**
 * Objetivo: essa classe tem como objetivo controlar
 * todas as ações da aplicação web
 * 
 * Autor: jezreel Lucas (jezreel.lucas@escolar.ifrn.edu.br)
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


public enum DeletadoStatusReclamacao {
    //Valores da constante
    //Quando o atributo do banco esta setado em ONINTERFACE quer dizer que a entidade esta salva no banco e aparecendo para o usuario
    //Quando o atributo do banco esta setado em OFFINTERFACE quer dizer que a entidade esta salva no banco mas nao esta aparecendo para o usuario
    ONINTERFACE, OFFINTERFACE
}
