package com.project.soohofit.common.response;

import lombok.Data;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;



@Data
public class CommonResponse {

    private String code;
    private String message;
    private String timestamp;
    private Map<String, Object> data;

    public CommonResponse(ResponseStatus responseStatus) {
        this.code = responseStatus.getCode();
        this.message = responseStatus.getMessage();
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

    public CommonResponse(ResponseStatus responseStatus, Map<String, Object> data){
        this.code = responseStatus.getCode();
        this.message = responseStatus.getMessage();
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
        this.data = data;
    }

    public CommonResponse(ResponseStatus responseStatus, String message) {
        this.code = responseStatus.getCode();
        this.message = message;
        this.timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss"));
    }

    public static CommonResponse of(ResponseStatus responseStatus) {
        return new CommonResponse(responseStatus);
    }

    @Override
    public String toString() {
        return "CommonResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", data=" + data +
                '}';
    }
}
