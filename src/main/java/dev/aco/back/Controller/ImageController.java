package dev.aco.back.Controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.aco.back.Utils.Image.ImageManager;
import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final ImageManager imageManager;
    @PostMapping(value = "upload")
    public ResponseEntity<String> imageUpload(MultipartFile request) {
        return new ResponseEntity<>(imageManager.ImgUpload(request), HttpStatus.OK);
    }

    @GetMapping(value = "/images/{name}")
    public ResponseEntity<byte[]> imageRead(@PathVariable("name") String name) {
        byte[] file = (byte[]) imageManager.ImgRead(name).get(0);
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", imageManager.ImgRead(name).get(1).toString());
        return new ResponseEntity<>(file, header, HttpStatus.OK);
    }
}
