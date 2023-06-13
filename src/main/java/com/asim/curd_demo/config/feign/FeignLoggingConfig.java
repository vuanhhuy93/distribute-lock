package com.asim.curd_demo.config.feign;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import feign.Logger;
import feign.Request;
import feign.Response;
import feign.Util;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;

@Log4j2
public class FeignLoggingConfig extends Logger {
    static Gson GSON = new GsonBuilder().serializeNulls().create();

    @Override
    protected void logRequest(String configKey, Level logLevel, Request request) {
        try {
            this.log(configKey, "---> %s %s", request.httpMethod().name(), request.url());
            String bodyText = "Empty";
            if (request.body() != null) {
                bodyText = request.charset() != null ? new String(request.body(), request.charset()) : null;
                JsonElement jsonElement = JsonParser.parseString(bodyText);
                bodyText = GSON.toJson(jsonElement);
            }
            log(configKey, "---> REQUEST DATA %s", bodyText);
        } catch (Exception e) {
//            log.error(e.getMessage());
        }
    }

    @Override
    protected Response logAndRebufferResponse(String configKey, Level logLevel, Response response, long elapsedTime) {
        try {
            String reason = response.reason() != null && logLevel.compareTo(Level.NONE) > 0 ? " " + response.reason() : "";
            int status = response.status();
            String protocolVersion = resolveProtocolVersion(response.protocolVersion());
            // ---------------- Logging headers ----------------
//        Iterator headersIterator = response.headers().keySet().iterator();
//        while (headersIterator.hasNext()) {
//            String field = (String) headersIterator.next();
//            Iterator valuesIterator = Util.valuesOrEmpty(response.headers(), field).iterator();
//
//            while (valuesIterator.hasNext()) {
//                String value = (String) valuesIterator.next();
//                this.log(configKey, "%s: %s", field, value);
//            }
//        }
            // -------------------------------------------------

            //----------------- Logging response body ---------------
            int bodyLength = 0;
            if (response.body() != null
                    && status != HttpStatus.SC_NO_CONTENT
                    && status != HttpStatus.SC_RESET_CONTENT) {

                byte[] bodyData = Util.toByteArray(response.body().asInputStream());
                bodyLength = bodyData.length;
                if (bodyLength > 0) {
                    String body = new String(bodyData, Util.UTF_8);
                    JsonElement jsonElement = JsonParser.parseString(body);
                    body = GSON.toJson(jsonElement);
                    this.log(configKey, "<--- RESPONSE DATA %s", body);
                }

                this.log(configKey, "<--- %s %s%s (%sms)", protocolVersion, status, reason, elapsedTime);
                return response.toBuilder().body(bodyData).build();
            }
            this.log(configKey, "<--- %s %s%s (%sms)", protocolVersion, status, reason, elapsedTime);
            // -------------------------------------------------
            return response;
        } catch (Exception e) {
//           log.error(e.getMessage());
        }
        return null;
    }


    @Override
    protected void log(String configKey, String format, Object... args) {
        log.debug(format(configKey, format, args));
    }

    protected String format(String configKey, String format, Object... args) {
        return String.format(methodTag(configKey) + format, args);
    }
}