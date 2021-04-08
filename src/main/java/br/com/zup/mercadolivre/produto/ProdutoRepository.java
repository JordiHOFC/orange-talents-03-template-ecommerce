package br.com.zup.mercadolivre.produto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ProdutoRepository extends JpaRepository<Produto,Long> {
    @Query(value = "select avg(o.nota) from opiniao o where o.produto_id=?1 GROUP BY (o.produto_id)", nativeQuery = true)
    Double findByAverageNotaInProduct(Long id);
    @Query(value = "select count(*) from opiniao o where o.produto_id=?1", nativeQuery = true)
    Integer countOpiniaoByProdutoId(Long idProduto);
}
