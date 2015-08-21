package vn.kms.lp.dao;

import java.util.List;

import vn.kms.lp.model.ProductModel;

public interface ProductFactory {
    
    List<ProductModel> findByName(String name);
    List<ProductModel> findByCategory(String category);
    List<ProductModel> findByPrice(int From, int To);
    void fetchData();
    
}
