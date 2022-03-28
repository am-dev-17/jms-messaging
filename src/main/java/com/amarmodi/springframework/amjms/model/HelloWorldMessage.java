package com.amarmodi.springframework.amjms.model;

import lombok.*;

import java.io.Serializable;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HelloWorldMessage implements Serializable {

    static final long serialVersionUID = 2345678776543432L;

    private UUID id;
    private String message;
}
