package com.asim.curd_demo.repositories;

import com.asim.curd_demo.model.product.ProductDTO;

import java.util.Optional;

public interface ProductRepository {

    Optional<ProductDTO> findById(long productId);
}
