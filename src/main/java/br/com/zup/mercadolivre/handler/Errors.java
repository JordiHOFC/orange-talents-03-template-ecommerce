package br.com.zup.mercadolivre.handler;

import org.springframework.validation.FieldError;

import java.lang.reflect.Field;

public class Errors {
    private String campo;
    private String erro;

    public Errors(FieldError fieldError){
        this.campo= fieldError.getField();
        this.erro= fieldError.getDefaultMessage();
    }

    public Errors(String campo, String erro) {
        this.campo = campo;
        this.erro = erro;
    }

    public String getCampo() {
        return campo;
    }

    public String getErro() {
        return erro;
    }
}
