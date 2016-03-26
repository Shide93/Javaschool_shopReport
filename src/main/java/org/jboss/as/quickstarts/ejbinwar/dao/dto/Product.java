package org.jboss.as.quickstarts.ejbinwar.dao.dto;


/**
 * The type Product dto.
 */
public class Product {
    /**
     * The Name.
     */
    private String name;
    /**
     * The Description.
     */
    private String description;
    /**
     * The Price.
     */
    private int price;
    /**
     * The Total sales.
     */
    private int totalSales;
    /**
     * The Stock.
     */
    private int stock;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(final String description) {
        this.description = description;
    }

    /**
     * Gets price.
     *
     * @return the price
     */
    public int getPrice() {
        return price;
    }

    /**
     * Sets price.
     *
     * @param price the price
     */
    public void setPrice(final int price) {
        this.price = price;
    }

    /**
     * Gets total sales.
     *
     * @return the total sales
     */
    public int getTotalSales() {
        return totalSales;
    }

    /**
     * Sets total sales.
     *
     * @param totalSales the total sales
     */
    public void setTotalSales(final int totalSales) {
        this.totalSales = totalSales;
    }

    /**
     * Gets stock.
     *
     * @return the stock
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets stock.
     *
     * @param stock the stock
     */
    public void setStock(final int stock) {
        this.stock = stock;
    }
}
