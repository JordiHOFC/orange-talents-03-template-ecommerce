package br.com.zup.mercadolivre.emailserver;

import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class EmailServerImpl implements EmailServer{
    @Override
    public void send(Usuario destinatario, String corpo, Usuario interessado, String produto,String assunto) {
        String Email="\nRemetente: compras@mercadolivre.com"+
        " \n Destinatario: "+destinatario.getUsername()+
        "\n Assunto: "+ produto+"\n "+
         assunto+": "+ corpo+"\n";
        System.out.println(Email);
    }

}
