package br.com.zup.mercadolivre.imagem;

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
    public List<Imagem> getImagens(){
        return imagens.stream().map(Imagem::new).collect(Collectors.toList());
    }
}
