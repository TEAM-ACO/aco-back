package dev.aco.back.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.aco.back.DTO.Article.ArticleDTO;
import dev.aco.back.service.ArticleService.ArticleService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/article")
public class ArticleController {   
    private final ArticleService aser;
    @RequestMapping(value = "list", method = RequestMethod.POST)
    public ResponseEntity<List<ArticleDTO>> getArticleList(){
        return new ResponseEntity<>(aser.readList(), HttpStatus.OK);
    }
    
}
