/**
 * Author: Anita Nelliat
 * Last Modified: April 3rd, 2020
 *
 * This POJO is used to perform the
 * conversion of JSON to Java Object
 * using the Jackson library
 */
package edu.cmu.andrew.anelliat.pojo;

public class Category {

    private int id;
    private String name;

    /**
     * Default constructor
     */
    public Category() {}

    /**
     * Parameterized constructor for Category class
     * @param id - the id of the category
     * @param name - the name of the category
     */
    public Category(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Getter of the id variable
     * @return int
     */
    public int getId() {
        return id;
    }

    /**
     * Setter of the id variable
     * @param id - the id of the category
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Getter of the name variable
     * @return String
     */
    public String getName() {
        return name;
    }

    /**
     * Setter of the name variable
     * @param name - the name of the category
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * The overridden toString method of the
     * Category class
     * @return String
     */
    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
