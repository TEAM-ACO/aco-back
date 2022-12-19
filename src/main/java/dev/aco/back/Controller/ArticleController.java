package dev.aco.back.Controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.aco.back.DTO.Article.ArticleDTO;
import dev.aco.back.VO.pageVO;
import dev.aco.back.service.ArticleService.ArticleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/article")
public class ArticleController {
    private final ArticleService aser;

    @RequestMapping(value = "list", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArticleDTO>> getArticleList(@RequestBody pageVO vo) {
        log.info(vo);
        Pageable pageable = PageRequest.of(vo.getRequestedPageNumber(), vo.getRequestedPageSize());
        return new ResponseEntity<>(aser.readList(pageable), HttpStatus.OK);
    }

    @RequestMapping(value = "write", method = RequestMethod.POST, consumes = MediaType.ALL_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Long> write(@RequestBody ArticleDTO dto) {
        return new ResponseEntity<>(aser.write(dto), HttpStatus.OK);
    }
}
