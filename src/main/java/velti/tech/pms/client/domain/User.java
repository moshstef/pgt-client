package velti.tech.pms.client.domain;


public class User {
    
    Long customerId;
    String msisdn;

    public User() {
    }

    public User(Long memberId, String msisdn) {
        this.customerId = memberId;
        this.msisdn = msisdn;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
