package com.asim.curd_demo.config.graphql;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class GraphQLUtils {

    private static final String DATA = "data";
    private static final String ERROR = "error";

    private static final TypeAdapter<Boolean> booleanAsIntAdapter = new TypeAdapter<Boolean>() {

        @Override public void write(JsonWriter out, Boolean value) throws IOException {
            if (value == null) {
                out.nullValue();
            } else {
                out.value(value);
            }
        }
        @Override public Boolean read(JsonReader in) throws IOException {
            JsonToken peek = in.peek();
            switch (peek) {
                case BOOLEAN:
                    return in.nextBoolean();
                case NULL:
                    in.nextNull();
                    return null;
                case NUMBER:
                    return in.nextInt() != 0;
                case STRING:
                    String str = in.nextString();
                    return "1".equalsIgnoreCase(str) || "true".equalsIgnoreCase(str);
                default:
                    throw new IllegalStateException("Expected BOOLEAN or NUMBER but was " + peek);
            }
        }
    };

    public static String query(GraphQLClient client, String query) {
        GraphQLQueryRequest customizeQuery = GraphQLQueryRequest.builder()
                .query(query)
                .build();
        return client.query(customizeQuery);
    }

    public static <T extends Object> T parseResponse(String queryResult, Class<T> clazz) {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(Boolean.class, booleanAsIntAdapter)
                .registerTypeAdapter(boolean.class, booleanAsIntAdapter)
                .create();
        JsonObject jsonObject = JsonParser.parseString(queryResult).getAsJsonObject();
        if (jsonObject.has(DATA)) {
            String data = gson.toJson(jsonObject.get(DATA));
            return gson.fromJson(data, clazz);
        }
        return null;
    }
}
