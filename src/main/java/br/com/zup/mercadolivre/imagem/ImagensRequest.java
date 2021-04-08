package br.com.zup.mercadolivre.imagem;

import br.com.zup.mercadolivre.uploaderimageserver.UploaderImageServer;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ImagensRequest {
    @NotNull
    @Size(min = 1,message = "você deve inserir uma ou mais imagens")
    private List<MultipartFile> imagens=new ArrayList<>();

    public void setImagens(List<MultipartFile> imagens) {
        this.imagens = imagens;
    }
    public List<Imagem> getImagens(UploaderImageServer server){
        return imagens.stream().map(i-> new Imagem(i.getOriginalFilename(),server.save(i))).collect(Collectors.toList());
    }
}
