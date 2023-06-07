package com.asim.curd_demo.repositories;

import org.springframework.stereotype.Repository;

@Repository
public interface WarehouseRepository {

    boolean updateActiveNumberAndReverseNumber(long productId, long activeNumberChange, long reverseNumberChange);

    boolean updateTotalAndReverseNumber(long productId, long total);
}
