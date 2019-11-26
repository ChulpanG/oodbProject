
package lab4;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = { "productID", "name", "allergens", "dishID"}, name = "product")
public class Product {
    int dishID;
    int productID;
    String name;
    String allergens;

    public Product(int productID, String name, String allergens, int dishID){
        this.productID=productID;
        this.name=name;
        this.allergens=allergens;
        this.dishID=dishID;
    }

    public int getDishID(){
        return dishID;
    }
    public String getDishName() {
        return name;
    }
    public void setName(String name) {
        this.name=name;
    }
    public String getAllergens() {
        return allergens;
    }
    public void setAllergens(String allergens) {
        this.allergens=allergens;
    }
}
