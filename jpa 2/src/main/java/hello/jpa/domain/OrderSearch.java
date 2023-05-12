package hello.jpa.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class OrderSearch {

    private final String memberName;
    private final OrderStatus orderStatus;
}
