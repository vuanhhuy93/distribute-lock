package com.asim.curd_demo.repositories.impl;

import com.asim.curd_demo.config.graphql.GraphQLClient;
import com.asim.curd_demo.config.graphql.GraphQLUtils;
import com.asim.curd_demo.model.product.ProductResponse;
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
    public boolean updateActiveNumberAndReverseNumber(long productId, long activeNumberChange, long reverseNumberChange) {

        String query = QueryFile.readFile(QueryFile.WAREHOUSE_UPDATE_REVERSE_ACTIVE);

        query = query.replace("$product_id" ,  String.valueOf(productId));

        if (activeNumberChange < 0){
            query = query.replace("$active_number_condition" ,  String.valueOf(activeNumberChange * -1));
        } else {
            query = query.replace("$active_number_condition" ,  "0");
        }
        query = query.replace("$active_number_change" ,  String.valueOf(activeNumberChange));


        if (reverseNumberChange < 0){
            query = query.replace("$reverse_number_condition" ,  String.valueOf(reverseNumberChange * -1));
        } else {
            query = query.replace("$reverse_number_condition" ,  "0");
        }
        query = query.replace("$reverse_number_change" ,  String.valueOf(reverseNumberChange));




        String queryResult = GraphQLUtils.query(graphQLClient, query);
        log.debug("result {}" , queryResult);

        UpdateWarehouseResponse response = JsonUtils.toJson(queryResult, UpdateWarehouseResponse.class);

        if (response == null || response.getData() == null
                || CollectionUtils.isEmpty(response.getData().getReturning().getItems())){

            log.debug("response not convert to DTO or data is null");
            return false;
        }

        return true;
    }

    @Override
    public boolean updateTotalAndReverseNumber(long productId, long total) {

        String query = QueryFile.readFile(QueryFile.WAREHOUSE_UPDATE_REVERSE_TOTAL);

        query = query.replace("$product_id" ,  String.valueOf(productId));

        query = query.replace("$total" ,  String.valueOf(total));

        String queryResult = GraphQLUtils.query(graphQLClient, query);
        log.debug("result {}" , queryResult);

        UpdateWarehouseResponse response = JsonUtils.toJson(queryResult, UpdateWarehouseResponse.class);

        if (response == null || response.getData() == null){

            log.debug("response not convert to DTO or data is null");
            return false;
        }

        return  !CollectionUtils.isEmpty(response.getData().getReturning().getItems());
    }
}
