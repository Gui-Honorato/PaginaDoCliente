package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import play.db.jpa.Model;

@Entity
public class Usuario extends Model {
    public String nomeUsuarioString;
    public String numIdentificadorUsuarioString;
    public String emailUsuarioString;
    public String contatoUsuarioString;
    public Date dataDeEntrada;


    @Enumerated(EnumType.STRING)
    public DeletadoStatusUsuario deletadoStatusUsuarioEnum;

    public Usuario(){
        deletadoStatusUsuarioEnum = DeletadoStatusUsuario.ATIVADO;
    }
}
