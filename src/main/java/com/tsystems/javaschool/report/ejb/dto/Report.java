package com.tsystems.javaschool.report.ejb.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;

/**
 * DTO object for sending data by REST service.
 */
public class Report implements Serializable{

    /**
     * Shop total sales.
     */
    private long totalSales;

    /**
     * Sales for a specified dateFrom.
     */
    private long periodSales;

    /**
     * Shop total orders.
     */
    private long totalOrders;

    /**
     * Orders count per status.
     */
    private Map<String, Integer> ordersPerStatus;

    /**
     * The Top users.
     */
    private List<User> topUsers;

    /**
     * The Top products.
     */
    private List<Product> topProducts;

    /**
     * Gets total sales.
     *
     * @return the total sales
     */
    public long getTotalSales() {
        return totalSales;
    }

    /**
     * Sets total sales.
     *
     * @param totalSales the total sales
     */
    public void setTotalSales(final long totalSales) {
        this.totalSales = totalSales;
    }

    /**
     * Gets period sales.
     *
     * @return the period sales
     */
    public long getPeriodSales() {
        return periodSales;
    }

    /**
     * Sets period sales.
     *
     * @param periodSales the period sales
     */
    public void setPeriodSales(final long periodSales) {
        this.periodSales = periodSales;
    }

    /**
     * Gets total orders.
     *
     * @return the total orders
     */
    public long getTotalOrders() {
        return totalOrders;
    }

    /**
     * Sets total orders.
     *
     * @param totalOrders the total orders
     */
    public void setTotalOrders(final long totalOrders) {
        this.totalOrders = totalOrders;
    }

    /**
     * Gets orders per status.
     *
     * @return the orders per status
     */
    public Map<String, Integer> getOrdersPerStatus() {
        return ordersPerStatus;
    }

    /**
     * Sets orders per status.
     *
     * @param ordersPerStatus the orders per status
     */
    public void setOrdersPerStatus(final Map<String, Integer> ordersPerStatus) {
        this.ordersPerStatus = ordersPerStatus;
    }

    /**
     * Gets top users.
     *
     * @return the top users
     */
    public List<User> getTopUsers() {
        return topUsers;
    }

    /**
     * Sets top users.
     *
     * @param topUsers the top users
     */
    public void setTopUsers(final List<User> topUsers) {
        this.topUsers = topUsers;
    }

    /**
     * Gets top products.
     *
     * @return the top products
     */
    public List<Product> getTopProducts() {
        return topProducts;
    }

    /**
     * Sets top products.
     *
     * @param topProducts the top products
     */
    public void setTopProducts(final List<Product> topProducts) {
        this.topProducts = topProducts;
    }

    public long getPeriodOrders() {
        long ordersCount = 0L;
        for (Map.Entry<String, Integer> order : ordersPerStatus.entrySet()) {
            ordersCount += order.getValue();
        }
        return ordersCount;
    }
    public double getSalesPercent(double value) {
        return new BigDecimal(value / this.periodSales * 100)
                .setScale(2, RoundingMode.UP).doubleValue();
    }
}
