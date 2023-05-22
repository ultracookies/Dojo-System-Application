package com.dojoapp.Dojo.System.Application.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("Student")
@Data
public class Student {

    public Student(String firstName, String lastName, Rank rank) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.rank = rank;
    }

    @Override
    public String toString() {
        return "{ID=" + id + ", " +
                "FirstName=" + firstName + ", " +
                "LastName=" + lastName + ", " +
                "Rank=" + rank + "}";
    }

    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column("FirstName")
    @Setter(AccessLevel.NONE)
    private final String firstName;

    @Column("LastName")
    @Setter(AccessLevel.NONE)
    private final String lastName;

    @Column("Rank")
    @Setter(AccessLevel.NONE)
    private final Rank rank;
}
