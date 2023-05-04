package hello.demo.domain.project;

import lombok.Getter;

@Getter
public enum Type {
    USER("User"), Admin("admin");
    Type(String description){

    }
}
