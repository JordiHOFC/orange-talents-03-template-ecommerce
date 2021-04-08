package br.com.zup.mercadolivre.uploaderimageserver;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Component
public class UploaderImageServerImpl implements UploaderImageServer{

    @Override
    public String save(MultipartFile file) {
        int hashId=Objects.hash(file.getOriginalFilename()+file.getContentType());
        return "imageBB.i/"+hashId;
    }
}
