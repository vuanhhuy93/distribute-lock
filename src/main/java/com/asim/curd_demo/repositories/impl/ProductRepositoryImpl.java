package com.asim.curd_demo.repositories.impl;

import com.asim.curd_demo.config.graphql.GraphQLClient;
import com.asim.curd_demo.config.graphql.GraphQLUtils;
import com.asim.curd_demo.model.product.ProductDTO;
import com.asim.curd_demo.model.product.ProductResponse;
import com.asim.curd_demo.repositories.ProductRepository;
import com.asim.curd_demo.utils.JsonUtils;
import com.asim.curd_demo.utils.QueryFile;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.Optional;

@Repository
@Log4j2
public class ProductRepositoryImpl implements ProductRepository {
    private GraphQLClient graphQLClient;
    protected ClassLoader classLoader;


    public ProductRepositoryImpl(@Autowired GraphQLClient graphQLClient) {
        this.graphQLClient = graphQLClient;
        classLoader = this.getClass().getClassLoader();
    }

    @Override
    public Optional<ProductDTO> findById(long productId) {

        if (productId < 1)
            return Optional.empty();

        String query = QueryFile.parseRequest(classLoader, QueryFile.PRODUCT_FIND_BY_ID);

        query = query.replace("$id", String.valueOf(productId));
        String queryResult = GraphQLUtils.query(graphQLClient, query);
        log.debug("result {}" , queryResult);

        ProductResponse response = JsonUtils.toJson(queryResult, ProductResponse.class);

        if (response == null || CollectionUtils.isEmpty(response.getData().getProductItems()))
            return Optional.empty();

        return Optional.of(response.getData().getProductItems().get(0));

    }
}
