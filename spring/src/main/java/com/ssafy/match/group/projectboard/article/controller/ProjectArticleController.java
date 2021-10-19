package com.ssafy.match.group.projectboard.article.controller;


import com.ssafy.match.group.projectboard.article.dto.ProjectArticleRequestDto;
import com.ssafy.match.group.projectboard.article.dto.ProjectArticleInfoDto;
import com.ssafy.match.group.projectboard.article.dto.ProjectArticleListDto;
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

    @GetMapping("/{boardid}/articles")
    @ApiOperation(value = "(프로젝트)게시글 리스트 조회", notes = "<strong>받은 게시판 id</strong>를 사용해서 게시글들을 조회한다.")
    public ResponseEntity<Page<ProjectArticleListDto>> getArticles(
        @PathVariable("boardid") Integer boardid,
        @PageableDefault(size = 10) @SortDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)
        throws Exception {
        return ResponseEntity.ok(projectArticleService.getProjectArticles(boardid, pageable));
    }

    @GetMapping("/{boardid}/articles/title/{title}")
    @ApiOperation(value = "(프로젝트)게시글 리스트 제목 조회", notes = "<strong>받은 게시판 id와 제목</strong>을 사용해서 게시글들을 조회한다.")
    public ResponseEntity<Page<ProjectArticleListDto>> getArticlesByTitle(
        @PathVariable("boardid") Integer boardid, @PathVariable("title") String title,
        @PageableDefault(size = 10) @SortDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)
        throws Exception {
        return ResponseEntity.ok(
            projectArticleService.getProjectArticlesByTitle(boardid, title, pageable));
    }

    @GetMapping("/{boardid}/articles/nickname/{nickname}")
    @ApiOperation(value = "(프로젝트)게시글 리스트 닉네임 조회", notes = "<strong>받은 게시판 id와 닉네임</strong>을 사용해서 게시글들을 조회한다.")
    public ResponseEntity<Page<ProjectArticleListDto>> getArticlesByNickname(
        @PathVariable("boardid") Integer boardid, @PathVariable("nickname") String nickname,
        @PageableDefault(size = 10) @SortDefault(sort = "createDate", direction = Sort.Direction.DESC) Pageable pageable)
        throws Exception {
        return ResponseEntity.ok(
            projectArticleService.getProjectArticlesByNickname(boardid, nickname, pageable));
    }

    @GetMapping("/{boardid}/articles/{articleid}")
    @ApiOperation(value = "(프로젝트)게시글 상세조회", notes = "<strong>받은 article id</strong>를 사용해서 게시글 상세 조회")
    public ResponseEntity<ProjectArticleInfoDto> getArticleDetail(
        @PathVariable("boardid") Integer boardid, @PathVariable("articleid") Long articleid)
        throws Exception {
        return ResponseEntity.ok(projectArticleService.getProjectArticleDetail(articleid));
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

    @PutMapping("/{article-id}")
    @ApiOperation(value = "(프로젝트)게시판의 게시글 수정", notes = "<strong>받은 게시글 id</strong>를 사용해서 게시글을 수정한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "수정되었습니다."),
        @ApiResponse(code = 404, message = "BOARD_NOT_FOUND\nMEMBER_NOT_FOUND\nCONTENT_NOT_FOUND\nARTICLE_NOT_FOUND"),
    })
    public ResponseEntity<String> updateArticle(@PathVariable("article-id") Long articleId, @RequestBody ProjectArticleRequestDto dto) {
        return new ResponseEntity<>(projectArticleService.updateArticle(articleId, dto));
    }

    @DeleteMapping("/{article-id}")
    @ApiOperation(value = "(프로젝트)게시판의 게시글 삭제", notes = "<strong>받은 게시글 id</strong>를 사용해서 게시글을 삭제한다.")
    @ApiResponses({
        @ApiResponse(code = 200, message = "수정되었습니다."),
        @ApiResponse(code = 404, message = "BOARD_NOT_FOUND\nMEMBER_NOT_FOUND\nCONTENT_NOT_FOUND\nARTICLE_NOT_FOUND"),
    })
    public ResponseEntity<String> deleteArticle(@PathVariable("article-id") Long articleId) {
        return new ResponseEntity<>("삭제되었습니다.", projectArticleService.deleteArticle(articleId));
    }
}
