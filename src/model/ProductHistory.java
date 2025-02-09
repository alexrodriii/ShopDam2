package model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "historical_inventory")  
@XmlRootElement(name = "productHistory")
@XmlType(propOrder = { "id", "productId", "name", "wholesalerPrice", "stock", "available", "date" })
public class ProductHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "product_id")
    private int productId;  

    @Column(name = "name")
    private String name;

    @Column(name = "wholesaler_price")
    private Amount wholesalerPrice;

    @Column(name = "available")
    private boolean available;

    @Column(name = "stock")
    private int stock;

    @Column(name = "date")
    private String date;  // Fecha del registro histórico

    public ProductHistory() {
        // Constructor por defecto
    }

    public ProductHistory(int productId, String name, Amount wholesalerPrice, boolean available, int stock, String date) {
        this.productId = productId;
        this.name = name;
        this.wholesalerPrice = wholesalerPrice;
        this.available = available;
        this.stock = stock;
        this.date = date;
    }

    // Getter y setter para id
    @XmlAttribute(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter y setter para productId
    @XmlElement(name = "productId")
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    // Getter y setter para name
    @XmlElement(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter y setter para wholesalerPrice
    @XmlElement(name = "wholesalerPrice")
    public Amount getWholesalerPrice() {
        return wholesalerPrice;
    }

    public void setWholesalerPrice(Amount wholesalerPrice) {
        this.wholesalerPrice = wholesalerPrice;
    }

    // Getter y setter para available
    @XmlElement(name = "available")
    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    // Getter y setter para stock
    @XmlElement(name = "stock")
    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    // Getter y setter para date
    @XmlElement(name = "date")
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "ProductHistory [productId=" + productId + ", name=" + name + ", wholesalerPrice=" + wholesalerPrice
                + ", available=" + available + ", stock=" + stock + ", date=" + date + "]";
    }
}
