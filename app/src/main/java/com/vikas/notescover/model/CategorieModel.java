package com.vikas.notescover.model;

public class CategorieModel {

    private int categoriesLogo;
    private String categoriesName;

      //constucters
    public CategorieModel(int categoriesLogo, String categoriesName) {
        this.categoriesLogo = categoriesLogo;
        this.categoriesName = categoriesName;
    }


  // getters
    public int getCategoriesLogo() {
        return categoriesLogo;
    }

    public String getCategoriesName() {
        return categoriesName;
    }
}
