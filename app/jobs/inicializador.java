package jobs;

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
        if(Usuario.count() == 0){
            Usuario root = new Usuario();
            root.nomeUsuarioString = "Root Honorato";
            root.numIdentificadorUsuarioString = "999.999.999.99";
            root.senha = "root12";
            root.emailUsuarioString = "honorato@admin.com";
            root.contatoUsuarioString = "84991281309";
            root.dataDeEntrada = new Date();
            root.tipoDeUsuarioEnum = TipoDeUsuario.ADMIN;
            root.save();

            Usuario rootClient = new Usuario();
            rootClient.nomeUsuarioString = "Root Honorato cliente";
            rootClient.numIdentificadorUsuarioString = "999.999.999.99";
            rootClient.senha = "root123";
            rootClient.emailUsuarioString = "honoratoCliente@admin.com";
            rootClient.contatoUsuarioString = "84991281309";
            rootClient.dataDeEntrada = new Date();
            rootClient.tipoDeUsuarioEnum = TipoDeUsuario.CLIENT;
            rootClient.save();

            System.out.println("Terminal: Banco populado com sucesso");
        }
    }
}
