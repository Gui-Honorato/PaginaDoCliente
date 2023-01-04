package models;
/**
 * Objetivo: essa classe tem como objetivo hospedar a entidade usuario
 * 
 * Autor: Guilherme Honorato (g.honorato@escolar.ifrn.edu.br)
 * 
 * Data de Criação: 31/10/2022
 * ##########################
 * Ultima Alteração:
 * 
 * Programador/Gerente de projeto: Guilherme Honorato
 * Data: 10/10/2022
 * Alteração: teste de funcionalidades e adição do atributo contato
 * 
 * ###########################
 */
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;

import org.hibernate.validator.constraints.CreditCardNumber;



import play.data.validation.Email;
import play.data.validation.Match;
import play.data.validation.Required;
import play.db.jpa.Model;
import play.libs.Crypto;

@Entity
public class Usuario extends Model {
    //atributos de Usuario
    @Required
    public String nomeUsuarioString;
    @Required
    public String numIdentificadorUsuarioString;
    public String senha;
    @Required
    @Email
    public String emailUsuarioString;
    @Required
    public String contatoUsuarioString;
    
    public Date dataDeEntrada;


    
    //criptografia de senha
    public void setSenha(String s){
        senha = Crypto.passwordHash(s);
    }
    //instaciador de enumerated
    @Enumerated(EnumType.STRING)
    public DeletadoStatusUsuario deletadoStatusUsuarioEnum;
    
    @Required
    @Enumerated(EnumType.STRING)
    public TipoDeUsuario tipoDeUsuarioEnum;
    //contrutor de usuario
    public Usuario(){
        deletadoStatusUsuarioEnum = DeletadoStatusUsuario.ATIVADO;
    }
}
