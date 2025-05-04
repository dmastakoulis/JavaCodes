package Assessemnt1;

// Electronics.java
// Electronics product class
class Electronics extends Product {
    private String brandName;
    private int warrantyPeriod; // in months

    public Electronics(String id, String name, double price,
                       String brandName, int warrantyPeriod) {
        super(id, name, price, "Electronics");
        this.brandName = brandName;
        this.warrantyPeriod = warrantyPeriod;
    }

    // Getters and setters
    public String getBrandName() { return brandName; }
    public void setBrandName(String brandName) { this.brandName = brandName; }

    public int getWarrantyPeriod() { return warrantyPeriod; }
    public void setWarrantyPeriod(int warrantyPeriod) { this.warrantyPeriod = warrantyPeriod; }

    @Override
    public String getProductDetails() {
        return toString() +
                "\nBrand: " + brandName +
                "\nWarranty: " + warrantyPeriod + " months";
    }
}