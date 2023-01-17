package dev.aco.back.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import dev.aco.back.Utils.Image.ImageManager;
import dev.aco.back.service.MemberService.MemberService;
import lombok.RequiredArgsConstructor;
@RestController
@RequiredArgsConstructor
@RequestMapping("/image")
public class ImageController {
    private final ImageManager imageManager;
    private final MemberService mser;
    @PostMapping(value = "upload")
    public ResponseEntity<String> imageUpload(MultipartFile request) {
        return new ResponseEntity<>(imageManager.ImgUpload(request), HttpStatus.OK);
    }

    @GetMapping(value = "/images/{name}")
    public ResponseEntity<byte[]> imageRead(@PathVariable("name") String name) {
        byte[] file = (byte[]) imageManager.ImgRead(name).get(0);
        return new ResponseEntity<>(file, HttpStatus.OK);
    }

    @GetMapping(value = "/user/{memberid}")
    public ResponseEntity<byte[]> imageReadByMemberId(@PathVariable("memberid") Long memberid) {
        return new ResponseEntity<>(mser.getImageByMemberId(memberid), HttpStatus.OK);
    }

}
