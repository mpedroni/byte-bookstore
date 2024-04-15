package com.mpedroni.bytebookstore.order;

import com.mpedroni.bytebookstore.localization.country.Country;
import com.mpedroni.bytebookstore.localization.state.State;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.Set;

@Table("orders")
public class Order {
    @Id
    private Long id;
    private String email;
    
    private String firstName;

    private String lastName;

    private String document;

    private String address;

    private String complement;

    private String city;

    @Column("country_id")
    private AggregateReference<Country, Long> country;

    @Column("state_id")
    private AggregateReference<State, Long> state;
    
    private String phone;

    private String cep;

    private BigDecimal total;

    @MappedCollection(idColumn = "order_id")
    private Set<OrderItem> items;

    @Deprecated
    public Order() {}

    private Order(
            Long id,
            String email,
            String firstName,
            String lastName,
            String document,
            String address,
            String complement,
            String city,
            AggregateReference<Country, Long> country,
            AggregateReference<State, Long> state,
            String phone,
            String cep,
            BigDecimal total,
            Set<OrderItem> items
    ) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.document = document;
        this.address = address;
        this.complement = complement;
        this.city = city;
        this.country = country;
        this.state = state;
        this.phone = phone;
        this.cep = cep;
        this.total = total;
        this.items = items;

        items.forEach(item -> item.withOrder(this));

        selfValidate();
    }

    public static Order newOrder(
            String email,
            String firstName,
            String lastName,
            String document,
            String address,
            String complement,
            String city,
            Long countryId,
            Long stateId,
            String phone,
            String cep,
            BigDecimal total,
            Set<OrderItem> items
    ) {
        return new Order(
                null,
                email,
                firstName,
                lastName,
                document,
                address,
                complement,
                city,
                AggregateReference.to(countryId),
                stateId != null ? AggregateReference.to(stateId) : null,
                phone,
                cep,
                total,
                items
        );
    }

    public BigDecimal total() {
        return total;
    }

    private void selfValidate() {
        var itemsTotal = items.stream()
                .map(item -> item.unitPrice().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (total().compareTo(itemsTotal) != 0) {
            throw new IllegalArgumentException("Cart total value does not match the sum of the items");
        }
    }
}
