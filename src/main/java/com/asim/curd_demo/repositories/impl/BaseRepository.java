package com.asim.curd_demo.repositories.impl;

import com.asim.curd_demo.config.graphql.GraphQLClient;
import com.asim.curd_demo.config.graphql.GraphQLUtils;
import com.asim.curd_demo.utils.QueryFile;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;
@Log4j2
public class BaseRepository {
    @Autowired
    public GraphQLClient graphQLClient;

    public String execute(String filePath, Map<String, String> variables){

        String query = QueryFile.parseRequest(filePath);

        for (Map.Entry<String, String> entry:  variables.entrySet()){
            query = query.replace(entry.getKey() , entry.getValue());
        }
        String queryResult = GraphQLUtils.query(graphQLClient, query);
        log.debug("result {}" , queryResult);

        return queryResult;

    }
}
