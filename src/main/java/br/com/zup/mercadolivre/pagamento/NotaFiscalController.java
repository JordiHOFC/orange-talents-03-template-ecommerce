package br.com.zup.mercadolivre.pagamento;

import br.com.zup.mercadolivre.compra.Compra;
import br.com.zup.mercadolivre.usuario.Usuario;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@RestController
public class NotaFiscalController {

     @PersistenceContext
     private EntityManager manager;

     @PostMapping("compras/{idCompra}/{idUsuario}")
     @Transactional
     public ResponseEntity<?> criarNotaFiscal(@PathVariable Long idCompra, @PathVariable Long idUsuario){
         Usuario usuario=manager.find(Usuario.class,idUsuario);
         Compra compra=manager.find(Compra.class,idCompra);
         NotaFiscal notaFiscal=new NotaFiscal(usuario,compra);
         manager.persist(notaFiscal);
         return ResponseEntity.ok(new NotaFiscalResponse(notaFiscal));
     }
}
