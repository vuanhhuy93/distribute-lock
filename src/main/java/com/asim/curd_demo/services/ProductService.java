package com.asim.curd_demo.services;

import com.asim.curd_demo.domains.WarehouseDomain;
import com.asim.curd_demo.model.response.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    WarehouseDomain warehouseDomain;

    public BaseResponse<String> changeProduct(long productId, long total){

        warehouseDomain.updateActiveNumberAnsReverseNumber(productId, total * -1, total);
        BaseResponse<String> response = new BaseResponse<>();

        return response;
    }
}
