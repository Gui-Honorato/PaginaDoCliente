package controllers;
/**
 * Objetivo: controlar as telas iniciais de admin e cliente
 * 
 * Autor: Guilherme da Silva Honorato (g.honorato@escolar.ifrn.edu.br)
 * 
 * Data de Criação: 01/11/2022
 * ##########################
 * Ultima Alteração:
 * 
 * Programador/Gerente de projeto: Guilherme Honorato
 * Data: 10/12/2022
 * Alteração: teste de funcionalidades
 * 
 * ###########################
 */

import play.mvc.Controller;
import play.mvc.With;
import security.Administrator;
import security.Seguranca;
/*
 * 
 * basicamente essa classe so manda renderizar as telas iniciais do administrador e do cliente
 * 
 */
@With(Seguranca.class)
public class TelasIniciais extends Controller {
    
    @Administrator
    public static void telaIncialAdmins(){
        render();
    }
    public static void telaInicialClientes(){
        render();
    }
}
