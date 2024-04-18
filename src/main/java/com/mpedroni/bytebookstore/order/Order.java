package com.mpedroni.bytebookstore.order;

import com.mpedroni.bytebookstore.coupon.Coupon;
import com.mpedroni.bytebookstore.localization.country.Country;
import com.mpedroni.bytebookstore.localization.state.State;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.jdbc.core.mapping.AggregateReference;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Objects;
import java.util.Optional;
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

    @Column("coupon_id")
    private AggregateReference<Coupon, Long> couponRef;

    @Transient
    private Coupon coupon;

    @MappedCollection(idColumn = "order_id")
    private Set<OrderItem> items;

    @Deprecated
    protected Order() {
    }

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

    public Long id() {
        return id;
    }

    public String email() {
        return email;
    }

    public String firstName() {
        return firstName;
    }

    public String lastName() {
        return lastName;
    }

    public String document() {
        return document;
    }

    public String address() {
        return address;
    }

    public String complement() {
        return complement;
    }

    public String city() {
        return city;
    }

    public Long countryId() {
        return country.getId();
    }

    public Long stateId() {
        return state != null ? state.getId() : null;
    }

    public String phone() {
        return phone;
    }

    public String cep() {
        return cep;
    }

    public BigDecimal totalWithoutDiscount() {
        return total;
    }

    public Optional<Long> couponId() {
        return couponRef != null ? Optional.ofNullable(couponRef.getId()) : Optional.empty();
    }

    public Set<OrderItem> items() {
        return Collections.unmodifiableSet(items);
    }

    public BigDecimal total() {
        if (coupon == null)
            return total;

        var discount = total.multiply(BigDecimal.valueOf(coupon.discountInPercent() / 100.0));
        return total.subtract(discount);
    }

    public void apply(Coupon coupon) {
        Assert.notNull(coupon, "Coupon cannot be null");

        var isSameCoupon = couponRef != null && Objects.equals(couponRef.getId(), coupon.id());
        Assert.isTrue(id == null || isSameCoupon, "Coupon can only be applied to a new order");

        Assert.isNull(this.coupon, "Coupon already applied to this order");
        this.coupon = coupon;
        this.couponRef = AggregateReference.to(coupon.id());
        selfValidate();
    }

    private void selfValidate() {
        var itemsTotal = items.stream()
                .map(item -> item.unitPrice().multiply(BigDecimal.valueOf(item.quantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        if (coupon != null) {
            var discount = itemsTotal.multiply(BigDecimal.valueOf(coupon.discountInPercent() / 100.0));
            itemsTotal = itemsTotal.subtract(discount);
        }

        if (total().compareTo(itemsTotal) != 0) {
            throw new IllegalArgumentException("Cart total value does not match the sum of the items");
        }
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", document='" + document + '\'' +
                ", address='" + address + '\'' +
                ", complement='" + complement + '\'' +
                ", city='" + city + '\'' +
                ", country=" + country +
                ", state=" + state +
                ", phone='" + phone + '\'' +
                ", cep='" + cep + '\'' +
                ", total=" + total() +
                ", coupon=" + coupon +
                ", items=" + items +
                '}';
    }
}
