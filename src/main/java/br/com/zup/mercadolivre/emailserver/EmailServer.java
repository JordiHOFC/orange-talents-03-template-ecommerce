package br.com.zup.mercadolivre.emailserver;


import br.com.zup.mercadolivre.usuario.Usuario;



public interface EmailServer {
    void send(Usuario destinatario, String corpo, Usuario interessado, String produto, String assunto);
}
