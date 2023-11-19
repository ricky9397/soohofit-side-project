package com.project.soohofit.common.response;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.Map;

public class DataResponse extends CommonResponse{

    private Map<String, Object> data;

    public DataResponse(ResponseStatus responseStatus) {
        this(responseStatus, new HashMap());
    }

    public DataResponse(ResponseStatus responseStatus, String message) {
        super(responseStatus, message);
    }

    public DataResponse(ResponseStatus responseStatus, Map<String, Object> data) {
        super(responseStatus, data);
        this.data = data;
    }

    public static DataResponse of(ResponseStatus responseStatus) {
        return new DataResponse(responseStatus);
    }

    public static DataResponse of(ResponseStatus responseStatus, String message) {
        return new DataResponse(responseStatus, message);
    }

    public static DataResponse of(ResponseStatus responseStatus, Map<String, Object> data) {
        return new DataResponse(responseStatus, data);
    }

    public static DataResponse of(ResponseStatus responseStatus, String name, Object object) {
        Map<String, Object> data = new HashMap<String, Object>();
        data.put(name, object);
        return new DataResponse(responseStatus, data);
    }

    public static DataResponse of(ResponseStatus responseStatus, Object object) {
        if(object == null) {
            return new DataResponse(ResponseStatus.NO_CONTENT, (Map)null);
        }else{
            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, Object> data = (Map)objectMapper.convertValue(object, Map.class);
            return new DataResponse(responseStatus, data);
        }
    }

}
