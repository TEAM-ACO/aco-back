package dev.aco.back.Controller;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.aco.back.DTO.Article.ArticleDTO;
import dev.aco.back.DTO.Article.ReplyDTO;
import dev.aco.back.VO.pageVO;
import dev.aco.back.service.ArticleService.ArticleService;
import dev.aco.back.service.ArticleService.Reply.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping(value = "/article")
public class ArticleController {
    private final ArticleService aser;
    private final ReplyService rser;

    @PostMapping(value = "list", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArticleDTO>> getArticleList(@RequestBody pageVO vo) {
        Pageable pageable = PageRequest.of(vo.getRequestedPageNumber(), vo.getRequestedPageSize(), Sort.by(Direction.DESC, "articleId"));
        return new ResponseEntity<>(aser.readList(pageable), HttpStatus.OK);
    }

    @PostMapping(value = "menu/{menu}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArticleDTO>> getArticleListByMenu(@RequestBody pageVO vo , @PathVariable Integer menu) {
        Pageable pageable = PageRequest.of(vo.getRequestedPageNumber(), vo.getRequestedPageSize(), Sort.by(Direction.DESC, "articleId"));
        return new ResponseEntity<>(aser.readListByMenu(pageable, menu), HttpStatus.OK);
    }

    @PostMapping(value = "list/{memberid}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArticleDTO>> getArticleListByMemberId(@RequestBody pageVO vo, @PathVariable Long memberid) {
        log.info(vo);
        Pageable pageable = PageRequest.of(vo.getRequestedPageNumber(), vo.getRequestedPageSize(), Sort.by(Direction.DESC, "articleId"));
        return new ResponseEntity<>(aser.readListByMemberId(pageable, memberid), HttpStatus.OK);
    }

    @PostMapping(value = "search/{keywords}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ArticleDTO>> searchingArticles(@RequestBody pageVO vo, @PathVariable String keywords) {
        log.info(vo);
        Pageable pageable = PageRequest.of(vo.getRequestedPageNumber(), vo.getRequestedPageSize(), Sort.by(Direction.DESC, "articleId"));
        return new ResponseEntity<>(aser.readListByKeywords(pageable, keywords), HttpStatus.OK);
    }

    @PostMapping(value = "write", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> write(ArticleDTO dto) {
        return new ResponseEntity<>(aser.write(dto), HttpStatus.OK);
    }

    @PostMapping(value = "modify", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Long> modify(ArticleDTO dto) {
        return new ResponseEntity<>(aser.articleModify(dto), HttpStatus.OK);
    }

    @PostMapping(value = "delete")
    public ResponseEntity<Boolean> delete(@RequestBody ArticleDTO dto) {
        return new ResponseEntity<>(aser.articleDelete(dto.getArticleId()), HttpStatus.OK);
    }

    @PostMapping(value = "reply/write")
    public ResponseEntity<Boolean> writeReply(@RequestBody ReplyDTO dto){
        return new ResponseEntity<>(rser.writeReply(dto), HttpStatus.OK);
    }

    @PostMapping(value = "reply/{article}")
    public ResponseEntity<List<ReplyDTO>> readReplyByArticleId(@PathVariable Long article, @RequestBody pageVO vo){
        Pageable pageable = PageRequest.of(vo.getRequestedPageNumber(), vo.getRequestedPageSize());
        return new ResponseEntity<>(rser.readReplyByArticleId(article, pageable), HttpStatus.OK);
    }

    @PostMapping(value = "reply/delete")
    public ResponseEntity<Boolean> deleteReplyByArticleIdMemberId(@RequestBody ReplyDTO dto){
        return new ResponseEntity<>(rser.deleteReplyMemberIdArticleId(dto), HttpStatus.OK);
    }

    @GetMapping(value = "random")
    public ResponseEntity<String> randomTip(){
        return new ResponseEntity<>(aser.randomTip(), HttpStatus.OK);
    }
}
