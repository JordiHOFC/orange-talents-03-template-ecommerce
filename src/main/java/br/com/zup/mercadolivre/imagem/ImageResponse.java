package br.com.zup.mercadolivre.imagem;

public class ImageResponse {
    private String produto;
    private String nome;
    private String url;

    public ImageResponse(String produto, String nome, String url) {
        this.produto = produto;
        this.nome = nome;
        this.url = url;
    }

    public String getProduto() {
        return produto;
    }

    public String getNome() {
        return nome;
    }

    public String getUrl() {
        return url;
    }
}
