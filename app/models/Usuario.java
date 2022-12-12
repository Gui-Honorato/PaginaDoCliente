package models;
/**
 * Objetivo: essa classe tem como objetivo hospedar a entidade usuario
 * 
 * Autor: Guilherme Honorato (g.honorato@escolar.ifrn.edu.br)
 * 
 * Data de Criação: 01/11/2022
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

import play.db.jpa.Model;
import play.libs.Crypto;

@Entity
public class Usuario extends Model {
    //atributos de Usuario
    public String nomeUsuarioString;
    public String numIdentificadorUsuarioString;
    public String senha;
    public String emailUsuarioString;
    public String contatoUsuarioString;
    public Date dataDeEntrada;


    
    //criptografia de senha
    public void setSenha(String s){
        senha = Crypto.passwordHash(s);
    }
    //instaciador de enumerated
    @Enumerated(EnumType.STRING)
    public DeletadoStatusUsuario deletadoStatusUsuarioEnum;
    
    @Enumerated(EnumType.STRING)
    public TipoDeUsuario tipoDeUsuarioEnum;
    //contrutor de usuario
    public Usuario(){
        deletadoStatusUsuarioEnum = DeletadoStatusUsuario.ATIVADO;
    }
}
