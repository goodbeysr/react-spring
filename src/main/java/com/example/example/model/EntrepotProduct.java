package com.example.example.model;
        
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.FetchType;

@Entity
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
@JsonSerialize(typing = JsonSerialize.Typing.STATIC)
public class EntrepotProduct implements Serializable {
    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_id")
    private @NonNull Product product;

    @Id
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "entrepot_id")
    private @NonNull Entrepot entrepot;

    private @NonNull Integer capacite;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntrepotProduct)) return false;
        EntrepotProduct that = (EntrepotProduct) o;
        return Objects.equals(product.getName(), that.product.getName()) &&
                Objects.equals(entrepot.getName(), that.entrepot.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(product.getName(), entrepot.getName(), capacite);
    }
}
