package com.asim.curd_demo.domains;

import com.asim.curd_demo.model.warehouse.ProductWarehouseDTO;
import com.asim.curd_demo.repositories.WarehouseRepository;
import com.asim.curd_demo.utils.ApplicationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class WarehouseDomain {

    @Autowired
    WarehouseRepository warehouseRepository;


    public void updateActiveNumberAnsReverseNumber(long productId, long activeNumberChange, long reverseNumberChange) throws ApplicationException {
        ProductWarehouseDTO productWarehouseDTO = warehouseRepository.findById(productId);

        if (productWarehouseDTO == null){
            throw new ApplicationException(-200, "not action product on ware house");
        }

        if (activeNumberChange < 0 &&  (activeNumberChange * -1) > productWarehouseDTO.getActiveNumber()){

            // case tru hang

            throw new ApplicationException(-201, "product active is invalid");

        }


        if (reverseNumberChange < 0 &&  (reverseNumberChange * -1) > productWarehouseDTO.getReverse_number()){

            // case tru hang

            throw new ApplicationException(-201, "product reverse is invalid");

        }


        try {

           boolean result =  warehouseRepository.updateActiveNumberAndReverseNumber(productId, activeNumberChange, reverseNumberChange);
            if (!result)
                 throw new ApplicationException(-200, "not action warehouse");
        } catch (ApplicationException ex){
            log.error("ex", ex);
            throw ex;
        }
        catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    public void updateTotalAndReverseNumber(long productId, long total) {


           boolean isSuccess =  warehouseRepository.updateTotalAndReverseNumber(productId, total);
            if (!isSuccess){
                throw new ApplicationException(-101, "update warehouse fail");
            }

    }
}
