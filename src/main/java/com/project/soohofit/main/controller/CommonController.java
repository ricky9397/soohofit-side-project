package com.project.soohofit.main.controller;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import com.project.soohofit.common.minio.FileUtil;
import org.apache.commons.compress.utils.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.io.InputStream;

@Log4j2
@Controller("/common")
public class CommonController {

    @Autowired
    private FileUtil fileUtil;

    @GetMapping("/download/{fileName}")
    @ResponseBody
    public ResponseEntity<byte[]> downloadFile(@PathVariable String fileName) {
        try {
            InputStream fileStream = fileUtil.downloadFile(fileName);

            if (fileStream == null) {
                return ResponseEntity.notFound().build();
            }

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", fileName);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(IOUtils.toByteArray(fileStream));
        } catch (IOException e) {
            log.error(e.getMessage());
            return ResponseEntity.status(500).build();
        }
    }

}
