package dev.aco.back.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import dev.aco.back.Utils.Image.ImageManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final ImageManager imageManager;

    @RequestMapping(value = "/images/{name}", method = RequestMethod.GET, consumes = MediaType.ALL_VALUE)
    public ResponseEntity<byte[]> imageRead(@ModelAttribute("name") String name) {
        byte[] file = (byte[]) imageManager.ImgRead(name).get(0);
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", imageManager.ImgRead(name).get(1).toString());
        return new ResponseEntity<>(file, header, HttpStatus.OK);
    }
}
