package br.com.zup.mercadolivre.handler;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MensageReponse {
    @JsonProperty("mensagem")
    private String mensagem;

    public MensageReponse(String mensagem) {
        this.mensagem=mensagem;
    }

    public MensageReponse() {
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
