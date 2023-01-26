package dev.aco.back.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.aco.back.DTO.Article.LikeDTO;
import dev.aco.back.service.ArticleService.ArticleLikeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2

public class LikeController {
    private final ArticleLikeService service;

    @PostMapping(value = "/like")
    public ResponseEntity<Long> likeRequest(@RequestBody LikeDTO dto){
        log.info(dto.getLikeId());
        return new ResponseEntity<Long>(service.likeUser(dto), HttpStatus.OK);
    }
}
