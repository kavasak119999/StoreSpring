package storespring.dto;

import lombok.*;
import storespring.enumeration.Status;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
    private Long id;
    private String created;
    private Status status;
}