package orgltmaxcloud.sample.msa.book.common.models;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Payment {
    private Long id;
    private Long orderId;

    public Payment() {}

    public Payment(Long orderId) {
        this.orderId = orderId;
    }

    @Override
    public String toString() {
        return String.format(
                "Payment[id=%d, orderId='%d']",
                id, orderId);
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
