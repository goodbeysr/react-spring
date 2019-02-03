package com.example.example.model;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.*;
import java.util.*;
import lombok.*;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@ToString(exclude = "entrepotProduct")
@EqualsAndHashCode(exclude = "entrepotProduct")
@RequiredArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(
  generator = ObjectIdGenerators.PropertyGenerator.class, 
  property = "id")
@JsonSerialize(typing = JsonSerialize.Typing.STATIC)
public class Entrepot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private @NonNull String name;
  
    private @NonNull String address;
    
    private @NonNull Integer capacite;
    
    private Integer pris =0;
    
    private @NonNull Boolean etat;

    @OneToMany(mappedBy = "entrepot", cascade = CascadeType.ALL, orphanRemoval = true)
    
    private List<EntrepotProduct> entrepotProduct = new ArrayList<>();
    
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;
}