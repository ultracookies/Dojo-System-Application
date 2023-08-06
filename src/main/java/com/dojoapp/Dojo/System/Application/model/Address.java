package com.dojoapp.Dojo.System.Application.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Address")
@Data
public class Address {

    public Address() {}

    public Address(String street, String city, String zipcode, String state) {
        this.street = street;
        this.city = city;
        this.zipcode = zipcode;
        this.state = state;
    }

    @Id
    @Column("AddressID")
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column("Street")
    private String street;

    @Column("City")
    private String city;

    @Column("Zipcode")
    private String zipcode;

    @Column("State")
    private String state;
}
