package Assessemnt1;

// Clothing.java
// Clothing product class
class Clothing extends Product {
    private String sizes;
    private String material;
    private String colors;

    public Clothing(String id, String name, double price,
                    String sizes, String material, String colors) {
        super(id, name, price, "Clothing");
        this.sizes = sizes;
        this.material = material;
        this.colors = colors;
    }

    // Getters and setters
    public String getSizes() { return sizes; }
    public void setSizes(String sizes) { this.sizes = sizes; }

    public String getMaterial() { return material; }
    public void setMaterial(String material) { this.material = material; }

    public String getColors() { return colors; }
    public void setColors(String colors) { this.colors = colors; }

    @Override
    public String getProductDetails() {
        return toString() +
                "\nSizes: " + sizes +
                "\nMaterial: " + material +
                "\nColors: " + colors;
    }
}