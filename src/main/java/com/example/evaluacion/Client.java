package com.example.evaluacion;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Client implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String dni;
    private String name;
    private String lastName;
    private Boolean state;

    @PrePersist
    public void prePersist(){
        state = true;
    }
}
