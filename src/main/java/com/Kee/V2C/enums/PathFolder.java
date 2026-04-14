package com.Kee.V2C.enums;

public enum PathFolder {
    MODELS("Models/"),
    BRANDS("Brands/"),
    CATEGORIES("Categories/"),
    SUBCATEGORIES("Subcategories/"),
    PRODUCT_REQUESTS("ProductRequests/"),
    VENDORS("Vendors/"),
    PRODUCTS("Products/")
    ;


    private final String path;

    PathFolder(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }

}
