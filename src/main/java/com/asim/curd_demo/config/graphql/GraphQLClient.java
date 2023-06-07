package com.asim.curd_demo.config.graphql;



import com.asim.curd_demo.config.feign.FeignCustomizeConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "graphQL", url = "${graphql.url}", configuration = FeignCustomizeConfig.class)
public interface GraphQLClient {
    @PostMapping
    String query(@RequestBody GraphQLQueryRequest query);
}

