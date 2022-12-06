package models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.db.jpa.Model;

@Entity
public class Cliente extends Model {
    public String nomeClienteString;
    public String numIdentificadorClienteString;
    public String emailClienteString;
    public String contatoClienteString;


    @Enumerated(EnumType.STRING)
    public DeletadoStatusCliente deletadoStatusClienteEnum;

    public Cliente(){
        deletadoStatusClienteEnum = DeletadoStatusCliente.ATIVADO;
    }
}
