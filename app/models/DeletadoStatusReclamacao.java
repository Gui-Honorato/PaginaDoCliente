package models;


//Controla se a reclamação foi removida ou nao
public enum DeletadoStatusReclamacao {
    //Valores da constante
    //Quando o atributo do banco esta setado em ONINTERFACE quer dizer que a entidade esta salva no banco e aparecendo para o usuario
    //Quando o atributo do banco esta setado em OFFINTERFACE quer dizer que a entidade esta salva no banco mas nao esta aparecendo para o usuario
    ONINTERFACE, OFFINTERFACE
}
