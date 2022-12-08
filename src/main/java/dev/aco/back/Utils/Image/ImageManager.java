package dev.aco.back.Utils.Image;

import java.io.File;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartRequest;

@Component
public class ImageManager {
    @Value("${dev.aco.filepath}")
    private String DIRADRESS;

    public String ImgUpload(MultipartRequest file) {
        List<String> result = new ArrayList<>();
        Optional.ofNullable(file.getFile("uploadimg")).ifPresent((img) -> {
            String fileName = UUID.randomUUID().toString() + "."
                    + FilenameUtils.getExtension(img.getOriginalFilename());
            try {
                img.transferTo(new File(DIRADRESS + fileName));
                result.add(fileName);
            } catch (Exception e) {
                
            }
        });

        return result.get(0);
    }

    public List<Object> ImgRead(String filename) {
        List<Object> result = new ArrayList<>();
        try {
            File file = new File(DIRADRESS + File.separator + URLDecoder.decode(filename, "UTF-8"));
            if (file.exists()) {
                result.add(FileCopyUtils.copyToByteArray(file));
                result.add(Files.probeContentType(file.toPath()));
            }

        } catch (Exception e) {
            
        }
        return result;
    }
}
