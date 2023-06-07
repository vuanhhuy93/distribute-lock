package com.asim.curd_demo.config.feign;

import feign.Client;
import feign.Response;
import okhttp3.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.nio.charset.Charset;
import java.util.*;
import java.util.concurrent.TimeUnit;

public final class OkHttpClient implements Client {
    private final okhttp3.OkHttpClient delegate;

    public OkHttpClient() {
        this(new okhttp3.OkHttpClient());
    }

    public OkHttpClient(okhttp3.OkHttpClient delegate) {
        this.delegate = delegate;
    }

    static Request toOkHttpRequest(feign.Request input) {
        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(input.url());
        MediaType mediaType = null;
        boolean hasAcceptHeader = false;
        Iterator var4 = input.headers().keySet().iterator();

        while (var4.hasNext()) {
            String field = (String) var4.next();
            if (field.equalsIgnoreCase("Accept")) {
                hasAcceptHeader = true;
            }

            Iterator var6 = ((Collection) input.headers().get(field)).iterator();

            while (var6.hasNext()) {
                String value = (String) var6.next();
                requestBuilder.addHeader(field, value);
                if (field.equalsIgnoreCase("Content-Type")) {
                    mediaType = MediaType.parse(value);
                    if (input.charset() != null) {
                        mediaType.charset(input.charset());
                    }
                }
            }
        }

        if (!hasAcceptHeader) {
            requestBuilder.addHeader("Accept", "*/*");
        }

        byte[] inputBody = input.body();
        boolean isMethodWithBody = feign.Request.HttpMethod.POST == input.httpMethod() || feign.Request.HttpMethod.PUT == input.httpMethod() || feign.Request.HttpMethod.PATCH == input.httpMethod();
        if (isMethodWithBody) {
            requestBuilder.removeHeader("Content-Type");
            if (inputBody == null) {
                inputBody = new byte[0];
            }
        }

        RequestBody body = inputBody != null ? RequestBody.create(inputBody, mediaType) : null;
        requestBuilder.method(input.httpMethod().name(), body);
        return requestBuilder.build();
    }

    private static Response toFeignResponse(okhttp3.Response response, feign.Request request) throws IOException {
        return Response.builder().protocolVersion(enumForName(feign.Request.ProtocolVersion.class, response.protocol())).status(response.code()).reason(response.message()).request(request).headers(toMap(response.headers())).body(toBody(response.body())).build();
    }

    public static <T extends Enum<?>> T enumForName(Class<T> enumClass, Protocol object) {
        String name = Objects.nonNull(object) ? object.name() : null;
        Enum[] var3 = (Enum[]) enumClass.getEnumConstants();
        int var4 = var3.length;
        for (int var5 = 0; var5 < var4; ++var5) {
            T enumItem = (T) var3[var5];
            if (enumItem.name().equalsIgnoreCase(name) || enumItem.toString().equalsIgnoreCase(name)) {
                return enumItem;
            }
        }

        return null;
    }

    private static Map<String, Collection<String>> toMap(Headers headers) {
        return Collections.unmodifiableMap(headers.toMultimap());
    }

    private static Response.Body toBody(final ResponseBody input) throws IOException {
        if (input != null && input.contentLength() != 0L) {
            final Integer length = input.contentLength() >= 0L && input.contentLength() <= 2147483647L ? (int) input.contentLength() : null;
            return new Response.Body() {
                public void close() throws IOException {
                    input.close();
                }

                public Integer length() {
                    return length;
                }

                public boolean isRepeatable() {
                    return false;
                }

                public InputStream asInputStream() throws IOException {
                    return input.byteStream();
                }

//                public Reader asReader() throws IOException {
//                    return input.charStream();
//                }

                public Reader asReader(Charset charset) throws IOException {
                    return input.charStream();
                }
            };
        } else {
            if (input != null) {
                input.close();
            }

            return null;
        }
    }

    public Response execute(feign.Request input, feign.Request.Options options) throws IOException {
        okhttp3.OkHttpClient requestScoped;
        if (this.delegate.connectTimeoutMillis() == options.connectTimeoutMillis() && this.delegate.readTimeoutMillis() == options.readTimeoutMillis() && this.delegate.followRedirects() == options.isFollowRedirects()) {
            requestScoped = this.delegate;
        } else {
            requestScoped = this.delegate.newBuilder().connectTimeout(options.connectTimeoutMillis(), TimeUnit.MILLISECONDS).readTimeout(options.readTimeoutMillis(), TimeUnit.MILLISECONDS).followRedirects(options.isFollowRedirects()).build();
        }
        Request request = toOkHttpRequest(input);
        okhttp3.Response response = requestScoped.newCall(request).execute();
        Response build = toFeignResponse(response, input).toBuilder().request(input).build();
        return build;
    }
}
