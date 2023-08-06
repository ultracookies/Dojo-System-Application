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

    @Id
    @Setter(AccessLevel.NONE)
    private Long id;

    @Column("CustomID")
    @Setter(AccessLevel.NONE)
    private String customID;

    @Column("FirstName")
    private String firstName;

    @Column("MiddleName")
    private String middleName;

    @Column("LastName")
    private String lastName;

    @Column("Sex")
    @Setter(AccessLevel.NONE)
    private Character sex;

    @Column("Rank")
    private Rank rank;

    @Column("BirthDate")
    @Setter(AccessLevel.NONE)
    private String birthDate;

    @Column("Age")
    @Setter(AccessLevel.NONE)
    private int age;

    @Column("Height")
    private String height;

    @Column("Weight")
    private String weight;

    @Column("DateBegan")
    @Setter(AccessLevel.NONE)
    private String dateBegan;

    @Column("AddressID")
    @Setter(AccessLevel.NONE)
    private Long addressID;

    @Column("PhoneNumber")
    private String phoneNumber;

    @Column("ImageURL")
    private String imageURL;

    @Column("OtherFiles")
    private String otherFiles;

    @Column("NotesDirectory")
    private String notesDirectory;

    public Student() {}

    public Student(String customID, String firstName, String lastName, String birthDate, Rank rank, Long addressID, Character sex) {
        this.customID = customID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rank = rank;
        this.birthDate = birthDate;
        this.addressID = addressID;
        this.sex = sex;
    }

    public Student(String customID, String firstName, String lastName, String birthDate, Rank rank) {
        this.customID = customID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.rank = rank;
        this.birthDate = birthDate;
    }

    @Override
    public String toString() {
        return "{ID=" + id + ", " +
                "FirstName=" + firstName + ", " +
                "LastName=" + lastName + ", " +
                "BirthDate=" + birthDate + ", " +
                "Rank=" + rank + "}";
    }
}
