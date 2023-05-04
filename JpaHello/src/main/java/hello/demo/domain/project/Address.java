package hello.demo.domain.project;

import lombok.Getter;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Getter
public class Address {
    public Address(){}
    public Address(String street,String city,String state){
        this.street=street;
        this.city=city;
        this.state=state;
    }
    public String getFullAddress(){
        return this.getState()+" "+this.getCity()+" "+this.getStreet();
    }
    @Column
    private String street;
    @Column
    private String city;
    @Column
    private String state;

    @Override
    public int hashCode(){
        return Objects.hash(this.getCity(),this.getState(),this.getStreet());
    }
    @Override
    public boolean equals(Object a){
        if (a==null) return false;
        if (!(a instanceof Address)) return  false;
        Address comp = (Address)a;
        if(comp.street==this.getStreet() && comp.city==this.getCity() && comp.state==this.getState()) return true;
        else return false;
    }

}
