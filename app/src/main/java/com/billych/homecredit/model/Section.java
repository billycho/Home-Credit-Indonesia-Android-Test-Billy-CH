package com.billych.homecredit.model;

import java.util.List;

public class Section {
    private String section;
    private String sectionTitle;
    private List<Product> productList;

    public Section(String section, String sectionTitle, List<Product> productList) {
        this.section = section;
        this.sectionTitle = sectionTitle;
        this.productList = productList;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSectionTitle() {
        return sectionTitle;
    }

    public void setSectionTitle(String sectionTitle) {
        this.sectionTitle = sectionTitle;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
