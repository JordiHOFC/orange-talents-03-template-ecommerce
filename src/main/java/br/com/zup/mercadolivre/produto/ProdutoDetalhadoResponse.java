package br.com.zup.mercadolivre.produto;

import br.com.zup.mercadolivre.imagem.Imagem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoDetalhadoResponse {
    private String nome;
    private String descricao;
    private BigDecimal preco;
    private Integer quantidadeDisponivel;
    private List<String> linksImagens=new ArrayList<>();
    private List<CaracteristicasResponse> caracteristicas=new ArrayList<>();
    private List<OpiniaoResponse> opinioes=new ArrayList<>();
    private List<PerguntaResponse> perguntas =new ArrayList<>();
    private Double mediaAvaliacao;
    private Integer quantidadeAvaliacoes;

    public ProdutoDetalhadoResponse(Produto produto, Integer qtdAvaliacao, Double mediaAvaliacao) {
        this.nome= produto.getNome();
        this.descricao=produto.getDescricao();
        this.quantidadeDisponivel= produto.getQuantidade();
        this.caracteristicas=produto.getCaracteristicas().stream().map(CaracteristicasResponse::new).collect(Collectors.toList());
        this.opinioes=produto.getOpinoes().stream().map(OpiniaoResponse::new).collect(Collectors.toList());
        this.linksImagens=produto.getImagems().stream().map(Imagem::getUrl).collect(Collectors.toList());
        this.perguntas=produto.getPerguntas().stream().map(PerguntaResponse::new).collect(Collectors.toList());
        this.preco=produto.getValor();
        this.mediaAvaliacao=mediaAvaliacao;
        this.quantidadeAvaliacoes=qtdAvaliacao;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public Integer getQuantidadeDisponivel() {
        return quantidadeDisponivel;
    }

    public List<String> getLinksImagens() {
        return linksImagens;
    }

    public List<CaracteristicasResponse> getCaracteristicas() {
        return caracteristicas;
    }

    public List<OpiniaoResponse> getOpinioes() {
        return opinioes;
    }

    public List<PerguntaResponse> getPerguntas() {
        return perguntas;
    }

    public Double getMediaAvaliacao() {
        return mediaAvaliacao;
    }

    public Integer getQuantidadeAvaliacoes() {
        return quantidadeAvaliacoes;
    }
}
