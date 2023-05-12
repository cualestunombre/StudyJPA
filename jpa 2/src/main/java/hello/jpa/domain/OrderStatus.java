package hello.jpa.domain;

public enum OrderStatus {
    ORDER("주문"), CANCEL("취소");
    private final String description;
    OrderStatus(String description){
        this.description=description;
    }
    public String getDescription(){
        return this.description;
    }



}

