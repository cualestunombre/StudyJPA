package hello.demo.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto{
    public MemberDto(Long id, String name){
        this.id=id;
        this.name=name;
    }
    private final Long id;
    private final String name;
}
