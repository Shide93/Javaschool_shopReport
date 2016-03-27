package org.jboss.as.quickstarts.ejbinwar.ejb.dto;


import java.io.Serializable;

/**
 * The type User dto.
 */
public class User implements Serializable {
    /**
     * The Name.
     */
    private String name;
    /**
     * The Last name.
     */
    private String lastName;
    /**
     * The Email.
     */
    private String email;
    /**
     * The Orders count.
     */
    private long ordersCount;
    /**
     * The Order total.
     */
    private long orderTotal;


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
     * Gets last name.
     *
     * @return the last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name.
     *
     * @param lastName the last name
     */
    public void setLastName(final String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets orders count.
     *
     * @return the orders count
     */
    public long getOrdersCount() {
        return ordersCount;
    }

    /**
     * Sets orders count.
     *
     * @param ordersCount the orders count
     */
    public void setOrdersCount(final long ordersCount) {
        this.ordersCount = ordersCount;
    }

    /**
     * Gets order total.
     *
     * @return the order total
     */
    public long getOrderTotal() {
        return orderTotal;
    }

    /**
     * Sets order total.
     *
     * @param orderTotal the order total
     */
    public void setOrderTotal(final long orderTotal) {
        this.orderTotal = orderTotal;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(final String email) {
        this.email = email;
    }
}
