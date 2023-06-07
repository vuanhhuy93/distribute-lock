package com.asim.curd_demo.domains;

import com.asim.curd_demo.model.product.ProductDTO;
import com.asim.curd_demo.repositories.ProductRepository;
import com.asim.curd_demo.utils.ApplicationException;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Log4j2
public class ProductDomain {

    @Autowired
    ProductRepository productRepository;
    // validate product id
    public ProductDTO validateProductInfo(long productId) throws ApplicationException{
        Optional<ProductDTO> productOpt = productRepository.findById(productId);

        if (productOpt.isEmpty())
            throw new ApplicationException(1, "product invalid");


        return productOpt.get();
    }
}
