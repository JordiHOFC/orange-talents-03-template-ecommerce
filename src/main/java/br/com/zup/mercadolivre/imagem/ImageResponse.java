package br.com.zup.mercadolivre.imagem;

public class ImageResponse {
    private String nome;
    private String url;

    public ImageResponse(Imagem imagem) {
        this.nome = imagem.getNome();
        this.url = imagem.getUrl();
    }



    public String getNome() {
        return nome;
    }

    public String getUrl() {
        return url;
    }
}
