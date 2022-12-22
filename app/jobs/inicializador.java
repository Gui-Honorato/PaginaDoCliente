package jobs;
/**
 * Objetivo: essa classe tem com objetivo criar um usuario root admin assim que o sistema for startado
 * 
 * Autor: Guilherme da Silva Honorato (g.honorato@escolar.ifrn.edu.br)
 * 
 * Data de Criação: 08/12/2022
 * ##########################
 * Ultima Alteração:
 * 
 * Programador/Gerente de projeto: Guilherme Honorato
 * Data: 10/12/2022
 * Alteração: teste de funcionalidades e apagar o usuario root cliente
 * 
 * ###########################
 */

import java.util.Date;

import models.TipoDeUsuario;
import models.Usuario;
import play.jobs.Job;
import play.jobs.OnApplicationStart;
import play.libs.Crypto;


@OnApplicationStart
public class inicializador extends Job{
    @Override
    public void doJob() throws Exception {
        //se o banco nao tiver sido preenchido entra aqui
        if(Usuario.count() == 0){
            //cria o novo usuario e seta todos seus parametros
            Usuario root = new Usuario();
            root.nomeUsuarioString = "Root";
            root.numIdentificadorUsuarioString = "999.999.999.99";
            root.senha = "root123";
            root.emailUsuarioString = "root@admin.com";
            root.contatoUsuarioString = "84991281309";
            root.dataDeEntrada = new Date();
            root.tipoDeUsuarioEnum = TipoDeUsuario.ADMIN;
            root.save();
            System.out.println("Terminal: Banco populado com sucesso");
        }
    }
}
