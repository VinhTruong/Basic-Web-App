package vn.kms.lp.test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import vn.kms.lp.dao.impl.ProductDaoImpl;
import vn.kms.lp.model.ProductModel;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestProductFactory {
    // Pseudo data
    private static String id;
    private static String name;
    private static String category;
    private static String desc;
    private static String price;
    private static ProductDaoImpl productFactory;

    public TestProductFactory() {
        id = "5";
        name = "AAA";
        category = "web";
        desc = "desc";
        price = "1234";
    }

    @Test
    public void testFetchProduct() throws SQLException {
        productFactory = new ProductDaoImpl();
        boolean actual = productFactory.fetchData();
        boolean expected = true;

        assertThat(actual, is(expected));
    }

    @Test
    public void testUpdateProduct() throws SQLException {

        productFactory = new ProductDaoImpl();
        boolean actual = productFactory.updateProduct(id, name, category, desc, price);
        boolean expected = true;

        assertThat(actual, is(expected));
    }

    @Test
    public void testAddProduct() throws SQLException {
        // Pseudo data
        productFactory = new ProductDaoImpl();
        boolean actual = productFactory.addProduct(name, category, desc, price);
        boolean expected = true;

        assertThat(actual, is(expected));
    }

    @Test
    public void testDeleteProduct() throws SQLException {
        ProductDaoImpl productFactory = new ProductDaoImpl();
        productFactory.fetchData();
        String existedId = Integer.toString(productFactory.getProducts().get(productFactory.getProducts().size() - 1)
                .getId());
        boolean actual = productFactory.deleteProduct(existedId);
        boolean expected = true;

        assertThat(actual, is(expected));
    }

    @Test
    public void testProductFindByCategory() throws SQLException {
        // Initial pseudo data
        ProductModel pseudoModel1 = new ProductModel(1, "aaa", "postgres", "desc", 1);
        ProductModel pseudoModel2 = new ProductModel(2, "bbb", "webapp", "desc", 2);
        ProductModel pseudoModel3 = new ProductModel(3, "ccc", "postgres", "desc", 3);
        ProductModel pseudoModel4 = new ProductModel(4, "ddd", "junit", "desc", 4);

        List<ProductModel> products = new ArrayList<ProductModel>();
        products.add(pseudoModel1);
        products.add(pseudoModel2);
        products.add(pseudoModel3);
        products.add(pseudoModel4);

        productFactory = new ProductDaoImpl();
        productFactory.setProducts(products);

        List<ProductModel> actual = new ArrayList<ProductModel>();
        actual = productFactory.findByCategory("postgres");

        String expectedResult1 = pseudoModel1.getName() + pseudoModel1.getCategory() + pseudoModel1.getDesc()
                                + pseudoModel1.getPrice();

        String expectedResult2 = pseudoModel3.getName() + pseudoModel3.getCategory() + pseudoModel3.getDesc()
                                + pseudoModel3.getPrice();

        String actualResult1 = actual.get(0).getName() + actual.get(0).getCategory() + actual.get(0).getDesc()
                                + actual.get(0).getPrice();

        String actualResult2 = actual.get(1).getName() + actual.get(1).getCategory() + actual.get(1).getDesc()
                                + actual.get(1).getPrice();

        assertThat(actualResult1, is(equalToIgnoringWhiteSpace(expectedResult1)));
        assertThat(actualResult2, is(equalToIgnoringWhiteSpace(expectedResult2)));
    }

}
