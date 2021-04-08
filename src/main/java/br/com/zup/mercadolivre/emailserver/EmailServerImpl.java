package br.com.zup.mercadolivre.emailserver;

import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.stereotype.Component;

@Component
public class EmailServerImpl implements EmailServer{
    @Override
    public void send(Usuario destinatario, String corpo, Usuario interessado, String produto) {
        String Email="\nRemetente: compras@mercadolivre.com"+" \n Destinatario: "+destinatario.getUsername()+
        "\n Assunto: Pergunta sobre produto: "+ produto+"\n "+
        "Pergunta: "+ corpo+"\n";
        System.out.println(Email);
    }
}
