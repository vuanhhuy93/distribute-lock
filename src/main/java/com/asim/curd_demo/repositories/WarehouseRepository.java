package com.asim.curd_demo.repositories;

import com.asim.curd_demo.model.warehouse.ProductWarehouseDTO;
import com.asim.curd_demo.model.warehouse.ProductWarehouseResponse;
import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository {

    boolean updateActiveNumberAndReverseNumber(long productId, long activeNumberChange, long reverseNumberChange, double version);

    boolean updateTotalAndReverseNumber(long productId, long total, double version);

    ProductWarehouseDTO findById(long productId);
}
