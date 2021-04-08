package br.com.zup.mercadolivre.uploaderimageserver;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface UploaderImageServer {
    default void init() {

    }

    String save(MultipartFile file);

    default Resource load(String enderecoImg) {
        return null;
    }

    default void deleteAll() {

    }

    default Stream<?> loadAll(String enderecoDiretorio) {
        return null;
    }

}
