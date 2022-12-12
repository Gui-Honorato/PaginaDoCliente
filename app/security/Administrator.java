package security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
//Controla o nivel de acesso de administrador
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Administrator {

}