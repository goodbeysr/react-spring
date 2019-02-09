package com.example.example.model;


import java.util.*;
import lombok.*;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

@Entity
@Data
@ToString(exclude = "entrepotProduct")
@EqualsAndHashCode(exclude = "entrepotProduct")
@NoArgsConstructor
@RequiredArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private @NonNull String name;
    
    private @NonNull Float volume;
    
    private @NonNull Integer quantite;
    
    private @NonNull Float price;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntrepotProduct> entrepotProduct = new ArrayList<>();
    
    @Column(nullable = false, updatable = false)
    @CreationTimestamp
    private Date createdAt;
}