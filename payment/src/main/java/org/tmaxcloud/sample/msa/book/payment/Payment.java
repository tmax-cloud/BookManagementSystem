package org.tmaxcloud.sample.msa.book.payment;

import javax.persistence.*;

@Entity
@Table(name = "Payment")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long orderId;

    Payment(Long orderId) {
        this.orderId = orderId;
    }

    public Long getId() {
        return this.id;
    }

    public Long getOrderId() {
        return this.orderId;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOrderId(Long id) {
        this.orderId = id;
    }
}
