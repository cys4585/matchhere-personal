package com.ssafy.match.group.projectboard.article.controller;

import com.ssafy.match.group.projectboard.article.dto.ProjectArticleRequestDto;
import com.ssafy.match.group.projectboard.article.dto.ProjectArticleInfoResponseDto;
import com.ssafy.match.group.projectboard.article.dto.ProjectArticleSimpleInfoResponseDto;
import com.ssafy.match.group.projectboard.article.service.ProjectArticleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/projectboards")
public class ProjectArticleController {

    private final ProjectArticleService projectArticleService;

    @GetMapping("/{boardId}/articles")
    @ApiOperation(value = "(프로젝트)게시글 리스트 조회", notes = "<strong>받은 게시판 id</strong>를 사용해서 게시글들을 조회한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "게시글 리스트 조회"),
        @ApiResponse(code = 404, message = "BOARD_NOT_FOUND\nMEMBER_NOT_FOUND\nCONTENT_NOT_FOUND\nARTICLE_NOT_FOUND"),
    })
    public ResponseEntity<Page<ProjectArticleSimpleInfoResponseDto>> getArticles(@PathVariable("boardId") Integer boardId,
        @PageableDefault(size = 10) @SortDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return new ResponseEntity<>(projectArticleService.getProjectArticles(boardId, pageable), HttpStatus.OK);
    }

    @GetMapping("/article/{articleId}")
    @ApiOperation(value = "(프로젝트)게시글 상세조회", notes = "<strong>받은 article id</strong>를 사용해서 게시글 상세 조회")
    @ApiResponses({
        @ApiResponse(code = 200, message = "게시글 상제 정보"),
        @ApiResponse(code = 404, message = "BOARD_NOT_FOUND\nMEMBER_NOT_FOUND\nCONTENT_NOT_FOUND\nARTICLE_NOT_FOUND"),
    })
    public ResponseEntity<ProjectArticleInfoResponseDto> getArticleDetail(@PathVariable("articleId") Long articleId) {
        return new ResponseEntity<>(projectArticleService.getProjectArticleDetail(articleId), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation(value = "(프로젝트)게시판의 게시글 생성", notes = "<strong>받은 게시판 id</strong>를 사용해서 게시글을 생성한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "게시글의 Long타입 Id"),
        @ApiResponse(code = 404, message = "BOARD_NOT_FOUND\nMEMBER_NOT_FOUND\nCONTENT_NOT_FOUND"),
    })
    public ResponseEntity<Long> createArticle(@RequestBody ProjectArticleRequestDto dto) {
        return new ResponseEntity<>(projectArticleService.createArticle(dto), HttpStatus.OK);
    }

    @PutMapping("/{articleId}")
    @ApiOperation(value = "(프로젝트)게시판의 게시글 수정", notes = "<strong>받은 게시글 id</strong>를 사용해서 게시글을 수정한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "수정되었습니다."),
        @ApiResponse(code = 404, message = "BOARD_NOT_FOUND\nMEMBER_NOT_FOUND\nCONTENT_NOT_FOUND\nARTICLE_NOT_FOUND"),
    })
    public ResponseEntity<String> updateArticle(@PathVariable("articleId") Long articleId, @RequestBody ProjectArticleRequestDto dto) {
        return new ResponseEntity<>(projectArticleService.updateArticle(articleId, dto));
    }

    @DeleteMapping("/{articleId}")
    @ApiOperation(value = "(프로젝트)게시판의 게시글 삭제", notes = "<strong>받은 게시글 id</strong>를 사용해서 게시글을 삭제한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "수정되었습니다."),
        @ApiResponse(code = 404, message = "BOARD_NOT_FOUND\nMEMBER_NOT_FOUND\nCONTENT_NOT_FOUND\nARTICLE_NOT_FOUND"),
    })
    public ResponseEntity<String> deleteArticle(@PathVariable("articleId") Long articleId) {
        return new ResponseEntity<>("삭제되었습니다.", projectArticleService.deleteArticle(articleId));
    }

    @GetMapping("/view-count/{projectArticleId}")
    @ApiOperation(value = "조회 수 증가", notes = "<strong>받은 프로젝트 게시글 id</strong>로 조회수를 증가시킨다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "처리되었습니다."),
        @ApiResponse(code = 404, message = "ARTICLE_NOT_FOUND"),
    })
    public ResponseEntity<String> plusViewCount(@PathVariable("projectArticleId") Long projectArticleId) {
        return new ResponseEntity<>("처리되었습니다.", projectArticleService.plusViewCount(projectArticleId));
    }
}
