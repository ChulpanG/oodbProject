
package lab1;


public class ProductDish{

    Product products;
    int quantity;
    String unit;

    ProductDish(Product products, int quantity, String unit){
        this.products = products;
        this.quantity = quantity;
        this.unit = unit;
    }
    public Product getProducts(){
        return products;
    }
    public int getQuantity(){
        return quantity;
    }
    public String getUnit(){
        return unit;
    }
}