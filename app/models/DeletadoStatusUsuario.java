package models;
//controla se o usuario foi removido ou nao
public enum DeletadoStatusUsuario {
    //Valores da Constante
    //Quando esta ATIVADO é pq o usuario esta cadastrado e aparecendo no sistema
    //Quando esta NEGATIVADO é pq o usuario foi removido do sistema porém permanece no banco
    ATIVADO, NEGATIVADO
}
