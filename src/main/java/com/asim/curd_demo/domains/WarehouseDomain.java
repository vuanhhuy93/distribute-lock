package com.asim.curd_demo.domains;

import com.asim.curd_demo.repositories.WarehouseRepository;
import com.asim.curd_demo.utils.ApplicationException;
import lombok.extern.log4j.Log4j2;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@Log4j2
public class WarehouseDomain {

    @Autowired
    WarehouseRepository warehouseRepository;

    @Autowired
    RedissonClient redissonClient;

    public void updateActiveNumberAnsReverseNumber(long productId, long activeNumberChange, long reverseNumberChange) throws ApplicationException {

        RLock rLock = redissonClient.getFairLock("warehouse-" + productId);


        try {
            rLock.lock(5, TimeUnit.SECONDS);
           boolean result =  warehouseRepository.updateActiveNumberAndReverseNumber(productId, activeNumberChange, reverseNumberChange);
            if (!result)
                 throw new ApplicationException(-200, "not action warehouse");
        } catch (ApplicationException ex){
            log.error("ex", ex);
            throw ex;
        }
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            rLock.unlock();
        }
    }

    public void updateTotalAndReverseNumber(long productId, long total) {

        RLock rLock = redissonClient.getFairLock("warehouse-" + productId);
        try {
            rLock.lock(5, TimeUnit.SECONDS);
           boolean isSuccess =  warehouseRepository.updateTotalAndReverseNumber(productId, total);
            if (!isSuccess){
                throw new ApplicationException(-101, "update warehouse fail");
            }
        } finally {
            rLock.unlock();
        }
    }
}
