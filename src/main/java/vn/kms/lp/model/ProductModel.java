package vn.kms.lp.model;

import java.util.Comparator;

public class ProductModel implements Comparable<ProductModel>, Comparator<ProductModel> {

    private int id;
    private String name;
    private String category;
    private String desc;
    private int price;

    public ProductModel(int id, String name, String category, String desc, int price) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.desc = desc;
        this.price = price;
    }

    public ProductModel() {

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDesc() {
        return desc;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public boolean equals(Object obj) {
        ProductModel model = (ProductModel) obj;
        return this.name.equals(model.getName());
    }

    @Override
    public int compareTo(ProductModel o) {
        return this.name.compareToIgnoreCase(o.getName());
    }

    @Override
    public int compare(ProductModel o1, ProductModel o2) {
        return o1.getPrice() - o2.getPrice();
    }

}
