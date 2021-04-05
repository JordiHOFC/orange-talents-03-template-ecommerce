package br.com.zup.mercadolivre.categoria;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {
    private final CategoriaRepository repository;

    public CategoriaController(CategoriaRepository repository) {
        this.repository = repository;
    }
    @PostMapping
    public ResponseEntity<?> cadastrarCategoria(@RequestBody @Valid CategoriaRequest categoriaRequest){
        Categoria categoria= categoriaRequest.toModelo();
        repository.save(categoria);
        return ResponseEntity.ok().build();
    }
}
