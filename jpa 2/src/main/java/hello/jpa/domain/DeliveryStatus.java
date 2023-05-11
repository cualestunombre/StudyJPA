package hello.jpa.domain;

public enum DeliveryStatus {
    READY("배송준비"),COMP("배송완료");
    private String description;
    DeliveryStatus(String description){
        this.description=description;
    }
    public String getDescription(){
       return  this.description;
    }
}
