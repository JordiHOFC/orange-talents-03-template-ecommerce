package br.com.zup.mercadolivre.emailserver;


import br.com.zup.mercadolivre.usuario.Usuario;



public interface EmailServer {
    String createMessageCompra(String produto, String valor, String metodoPagamento);
    void send(Usuario destinatario, String corpo, String produto, String assunto);
}
