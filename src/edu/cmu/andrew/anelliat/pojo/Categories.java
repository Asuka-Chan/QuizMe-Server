/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 *
 * This POJO is used to perform the
 * conversion of JSON to Java Object
 * using the Jackson library
 */
package edu.cmu.andrew.anelliat.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class Categories {

    //This annotation denotes the name of the item in the JSON
    @JsonProperty("trivia_categories")
    private List<Category> categories;

    /**
     * Default constructor that initializes the
     * categories list
     */
    public Categories(){
        this.categories = new ArrayList<>();
    }

    /**
     * Setter method for the categories variable
     * @param categories
     */
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * Getter to retrieve the categories value
     * @return List<Category>
     */
    public List<Category> getCategories() {
        return this.categories;
    }

    /**
     * The overridden toString method
     * of the class
     * @return String
     */
    @Override
    public String toString() {
        return "Categories{" +
                "categories=" + categories +
                '}';
    }
}
