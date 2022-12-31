package dev.aco.back.Admin.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.aco.back.Admin.Service.AdminService;
import dev.aco.back.DTO.Article.ArticleDTO;
import dev.aco.back.DTO.Report.ArticleReportDTO;
import dev.aco.back.DTO.Report.MemberReportDTO;
import dev.aco.back.DTO.User.MemberDTO;
import dev.aco.back.VO.pageVO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("admin")
public class AdminController {
    private final AdminService adser;

    @GetMapping(value = "main/{week}")
    public ResponseEntity<HashMap<String, Object>> getMainpageInformation(@PathVariable("week") Integer week) {
        return new ResponseEntity<HashMap<String, Object>>(
                adser.adminMainboardInformation(Optional.ofNullable(week).orElse(1)), HttpStatus.OK);
    }

    @GetMapping(value="article")
    public ResponseEntity<List<ArticleDTO>> getArticleList(@RequestBody pageVO vo){
        Pageable pageable = PageRequest.of(vo.getRequestedPageNumber(), vo.getRequestedPageSize(), Sort.by(Direction.DESC, "articleId"));
        return new ResponseEntity<>(adser.adminArticleList(pageable), HttpStatus.OK);
    }

    @GetMapping(value="member")
    public ResponseEntity<List<MemberDTO>> getMeberList(@RequestBody pageVO vo){
        Pageable pageable = PageRequest.of(vo.getRequestedPageNumber(), vo.getRequestedPageSize(), Sort.by(Direction.DESC, "memberId"));
        return new ResponseEntity<>(adser.adminMemberList(pageable), HttpStatus.OK);
    }

    @GetMapping(value="articlereport")
    public ResponseEntity<List<ArticleReportDTO>> getReportedArticleList(@RequestBody pageVO vo){
        Pageable pageable = PageRequest.of(vo.getRequestedPageNumber(), vo.getRequestedPageSize(), Sort.by(Direction.DESC, "articleReportId"));
        return new ResponseEntity<>(adser.adminArticleReportList(pageable), HttpStatus.OK);
    }

    @GetMapping(value="memberreport")
    public ResponseEntity<List<MemberReportDTO>> getReportedMemberList(@RequestBody pageVO vo){
        Pageable pageable = PageRequest.of(vo.getRequestedPageNumber(), vo.getRequestedPageSize(), Sort.by(Direction.DESC, "memberReportId"));
        return new ResponseEntity<>(adser.adminMemberReportList(pageable), HttpStatus.OK);
    }

    @DeleteMapping(value = "delete/{which}/{number}")
    public ResponseEntity<Boolean> deleteObject(@PathVariable("which")String which, @PathVariable("number") Long number){
        switch (which) {
            case "article":
                return new ResponseEntity<Boolean>(adser.deleteArticle(number), HttpStatus.OK);
            case "member":
                return new ResponseEntity<Boolean>(adser.deleteMember(number), HttpStatus.OK);
            case "articlereport":
                return new ResponseEntity<Boolean>(adser.deleteArticleReport(number), HttpStatus.OK);
            case "memberreport":
                return new ResponseEntity<Boolean>(adser.deleteMemberReport(number), HttpStatus.OK);
            default:
                return new ResponseEntity<Boolean>(false, HttpStatus.BAD_REQUEST);
        }
    }


}
