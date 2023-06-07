package com.asim.curd_demo.config.graphql;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GraphQLQueryRequest {
    private String query;
}
