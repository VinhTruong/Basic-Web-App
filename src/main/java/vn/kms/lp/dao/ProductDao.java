package vn.kms.lp.dao;

import java.util.List;

import vn.kms.lp.model.ProductModel;

public interface ProductDao {

    List<ProductModel> findByName(String name);

    List<ProductModel> findByCategory(String category);

    List<ProductModel> findByPrice(int From, int To);

    boolean updateProduct(String id, String name, String category, String desc, String price);

    boolean addProduct(String name, String category, String desc, String price);

    boolean deleteProduct(String Id);
}
