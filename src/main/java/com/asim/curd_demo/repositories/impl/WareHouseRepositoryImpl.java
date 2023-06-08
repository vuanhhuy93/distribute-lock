package com.asim.curd_demo.repositories.impl;

import com.asim.curd_demo.config.graphql.GraphQLClient;
import com.asim.curd_demo.config.graphql.GraphQLUtils;
import com.asim.curd_demo.model.warehouse.ProductWarehouseDTO;
import com.asim.curd_demo.model.warehouse.ProductWarehouseResponse;
import com.asim.curd_demo.model.warehouse.UpdateWarehouseResponse;
import com.asim.curd_demo.repositories.WarehouseRepository;
import com.asim.curd_demo.utils.JsonUtils;
import com.asim.curd_demo.utils.QueryFile;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

@Repository
@Log4j2
public class WareHouseRepositoryImpl implements WarehouseRepository {

    private GraphQLClient graphQLClient;

    public WareHouseRepositoryImpl(@Autowired GraphQLClient graphQLClient) {
        this.graphQLClient = graphQLClient;
    }
    @Override
    public boolean updateActiveNumberAndReverseNumber(long productId, long activeNumberChange, long reverseNumberChange, double version) {

        String query = QueryFile.readFile(QueryFile.WAREHOUSE_UPDATE);

        query = query.replace("$productid" ,  String.valueOf(productId));

        query = query.replace("$updatenumbertotal" ,  "0");

        query = query.replace("$updateactivenumber" ,  String.valueOf(activeNumberChange));

        query = query.replace("$updatereversenumber" ,  String.valueOf(reverseNumberChange));



        String queryResult = GraphQLUtils.query(graphQLClient, query);
        log.debug("result {}" , queryResult);

        UpdateWarehouseResponse response = JsonUtils.toJson(queryResult, UpdateWarehouseResponse.class);

        if (response == null || response.getData() == null
                || response.getData().getReturning().getProductId() == null){

            log.debug("response not convert to DTO or data is null");
            return false;
        }

        return true;
    }

    @Override
    public boolean updateTotalAndReverseNumber(long productId, long total, double version) {

        String query = QueryFile.readFile(QueryFile.WAREHOUSE_UPDATE);

        query = query.replace("$productid" ,  String.valueOf(productId));

        query = query.replace("$updatenumbertotal" ,  String.valueOf(total));

        query = query.replace("$updateactivenumber" ,  "0");

        query = query.replace("$updatereversenumber" ,  String.valueOf(total));



        String queryResult = GraphQLUtils.query(graphQLClient, query);
        log.debug("result {}" , queryResult);

        UpdateWarehouseResponse response = JsonUtils.toJson(queryResult, UpdateWarehouseResponse.class);

        if (response == null || response.getData() == null
                || response.getData().getReturning().getProductId() == null){

            log.debug("response not convert to DTO or data is null");
            return false;
        }

        return true;
    }

    @Override
    public ProductWarehouseDTO findById(long productId) {
        String query = QueryFile.readFile(QueryFile.WAREHOUSE_FIND_BY_ID);

        query = query.replace("$product_id" ,  String.valueOf(productId));

        String queryResult = GraphQLUtils.query(graphQLClient, query);
        log.debug("result {}" , queryResult);

        ProductWarehouseResponse response = JsonUtils.toJson(queryResult, ProductWarehouseResponse.class);

        if (response == null || response.getData() == null || CollectionUtils.isEmpty(response.getData().getItems())){

            log.debug("response not convert to DTO or data is null");
            return null;
        }

        return response.getData().getItems().get(0);

    }


}
