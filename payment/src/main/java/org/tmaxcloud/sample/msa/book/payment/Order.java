package org.tmaxcloud.sample.msa.book.payment;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Order {
    private Long id;
    private Type type;
    private Long bookId;
    private int quantity;
    private Long paymentId;

    enum Type {
        PURCHASE, SALE, RENT
    }

    public Order() {
    }

    public Order(Type type, Long bookId, int quantity) {
        this.type = type;
        this.bookId = bookId;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format(
                "Order[id=%d, title='%s', quantity='%s']",
                id, bookId, quantity);
    }

    public Long getId() {
        return this.id;
    }

    public Type getType() {
        return this.type;
    }

    public Long getBookId() {
        return this.bookId;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public Long getPaymentId() {
        return this.paymentId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
}
