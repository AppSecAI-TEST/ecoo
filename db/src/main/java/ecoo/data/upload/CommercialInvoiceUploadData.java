package ecoo.data.upload;


import ecoo.data.upload.annotation.UploadField;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author Justin Rundle
 * @since July 2017
 */
@Entity
@Table(name = "upload_commercial_invoice_data")
public final class CommercialInvoiceUploadData extends UploadData {

    private static final long serialVersionUID = -3540534799082789005L;

    @Column(name = "marks")
    @UploadField(fieldId = 2)
    private String marks;

    @Column(name = "product_code")
    @UploadField(fieldId = 3)
    private String productCode;

    @Column(name = "descr")
    @UploadField(fieldId = 4)
    private String description;

    @Column(name = "qty")
    @UploadField(fieldId = 5)
    private String quantity;

    @Column(name = "price")
    @UploadField(fieldId = 6)
    private String price;

    @Column(name = "amount")
    @UploadField(fieldId = 7)
    private String amount;

    /**
     * Constructs a new {@link CommercialInvoiceUploadData} model object.
     */
    public CommercialInvoiceUploadData() {
    }

    public String getMarks() {
        return marks;
    }

    public void setMarks(String marks) {
        this.marks = marks;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CommercialInvoiceUploadData{" +
                "marks='" + marks + '\'' +
                ", productCode='" + productCode + '\'' +
                ", description='" + description + '\'' +
                ", quantity='" + quantity + '\'' +
                ", price='" + price + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
