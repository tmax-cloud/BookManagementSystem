package org.tmaxcloud.sample.msa.book.order;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payment {
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
