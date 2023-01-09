package dev.aco.back.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dev.aco.back.DTO.Report.ArticleReportDTO;
import dev.aco.back.DTO.Report.MemberReportDTO;
import dev.aco.back.service.ReportService.ReportService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {
    private final ReportService rser;

    @RequestMapping(value = "/member", method = RequestMethod.POST)
    public ResponseEntity<Integer> memberReportRequest(@RequestBody MemberReportDTO dto){
        return new ResponseEntity<Integer>(rser.reportUser(dto), HttpStatus.OK);
    }

    @RequestMapping(value = "/article", method = RequestMethod.POST)
    public ResponseEntity<Integer> articleReportRequest(@RequestBody ArticleReportDTO dto){
        return new ResponseEntity<Integer>(rser.reportArticle(dto), HttpStatus.OK);
    }

}
