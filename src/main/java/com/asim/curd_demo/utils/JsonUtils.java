package com.asim.curd_demo.utils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


public class JsonUtils {
    private static final Logger log = LogManager.getLogger(JsonUtils.class);
    private static final ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    public static String convertObjectToJson(Object obj){
        try{
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public static <T> T toJson(String jSonObj, Class<T> clazz){
        try{
            return mapper.readValue(jSonObj, clazz);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }


    public static void main(String[] args) {
        BCryptPasswordEncoder abc = new BCryptPasswordEncoder();
        System.out.println(abc.encode("123456"));
    }
}
