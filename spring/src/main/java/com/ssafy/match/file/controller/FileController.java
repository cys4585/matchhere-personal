package com.ssafy.match.file.controller;

import com.ssafy.match.file.dto.UploadFileResponse;
import com.ssafy.match.file.entity.DBFile;
import com.ssafy.match.file.service.DBFileStorageService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.apache.http.client.methods.HttpPost;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {

//    private static final Logger logger = LoggerFactory.getLogger(FileController.class);
    @Autowired
    private DBFileStorageService dbFileStorageService;

    @PostMapping(value = "/uploadFile")
    public UploadFileResponse uploadFile(@RequestPart("file") MultipartFile file) {

        DBFile dbFile = dbFileStorageService.storeFile(file);

//        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
//            .path("/file/downloadFile/")
//            .path(dbFile.getId())
//            .toUriString();
// 현재 uri 만들어주는데 uri 바뀌면 못찾음, pdf 같은 경우는 다운로드 uri만 보내면 되는데 db파일 데이터 불필요하게 보냄
// servleturicomponentbuiler 사용으로 테스트 환경이 바뀌면 안될가능성

        return new UploadFileResponse(dbFile.getId(), dbFile.getFile_name(), dbFile.getDownload_uri(),
            file.getContentType(), file.getSize());

    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
            .stream()
            .map(file -> uploadFile(file))
            .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        DBFile dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(dbFile.getFile_type()))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFile_name() + "\"")
            .body(new ByteArrayResource(dbFile.getData()));

    }

}
