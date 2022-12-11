package models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import play.db.jpa.Model;
import play.libs.Crypto;

@Entity
public class Usuario extends Model {
    public String nomeUsuarioString;
    public String numIdentificadorUsuarioString;
    public String senha;
    public String emailUsuarioString;
    public String contatoUsuarioString;
    public Date dataDeEntrada;


    

    public void setSenha(String s){
        senha = Crypto.passwordHash(s);
    }

    @Enumerated(EnumType.STRING)
    public DeletadoStatusUsuario deletadoStatusUsuarioEnum;
    
    @Enumerated(EnumType.STRING)
    public TipoDeUsuario tipoDeUsuarioEnum;

    public Usuario(){
        deletadoStatusUsuarioEnum = DeletadoStatusUsuario.ATIVADO;
    }
}
