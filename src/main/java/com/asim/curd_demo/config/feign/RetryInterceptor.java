package com.asim.curd_demo.config.feign;

import lombok.extern.log4j.Log4j2;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Log4j2
public class RetryInterceptor implements Interceptor {
    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request request = chain.request();
        Response response = null;
        boolean responseOK = false;
        int tryCount = 0;
        while (!responseOK && tryCount < 3) {
            try {
                response = chain.proceed(request);
                responseOK = response.isSuccessful();
            } catch (Exception e) {
                log.error("Request is not successful, retrying... {}", tryCount);
            } finally {
                tryCount++;
            }
        }
        // otherwise just pass the original response on
        return response;
    }
}
