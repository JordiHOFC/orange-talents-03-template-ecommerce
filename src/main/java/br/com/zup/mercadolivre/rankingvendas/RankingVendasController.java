package br.com.zup.mercadolivre.rankingvendas;

import br.com.zup.mercadolivre.compra.Compra;
import br.com.zup.mercadolivre.handler.MensageReponse;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RestController
@RequestMapping("/rankingvendedores")
public class RankingVendasController {
    @PersistenceContext
    private EntityManager manager;

    @PostMapping("/{idVendedor}/{idCompra}")
    @Transactional
    public ResponseEntity<?> atualizarRanking(@PathVariable Long idVendedor, @PathVariable Long idCompra){
        Usuario vendedor=manager.find(Usuario.class,idVendedor);
        Compra compra=manager.find(Compra.class,idCompra);
        Venda venda= new Venda(vendedor,compra);
        manager.persist(venda);
        return ResponseEntity.ok(new MensageReponse("Ranking atualizado com sucesso!"));
    }

}
