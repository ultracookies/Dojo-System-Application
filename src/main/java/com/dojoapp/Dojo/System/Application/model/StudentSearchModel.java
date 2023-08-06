package com.dojoapp.Dojo.System.Application.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;

@Data
public class StudentSearchModel {

    @Setter(AccessLevel.NONE)
    private Long id;

    @Setter(AccessLevel.NONE)
    private String firstName;

    @Setter(AccessLevel.NONE)
    private String lastName;

    @Setter(AccessLevel.NONE)
    private String rank;

    public StudentSearchModel(Student student) {
        this.id = student.getId();
        this.firstName = student.getFirstName();
        this.lastName = student.getLastName();
        this.rank = student.getRank().toString();
    }

//    public StudentSearchModel(String firstName, String lastName, String rank) {
//        this.firstName = new SimpleStringProperty(firstName);
//        this.lastName = new SimpleStringProperty(lastName);
//        this.rank = new SimpleStringProperty(rank);
//    }

//    public StudentSearchModel(Student student) {
//        this.firstName = new SimpleStringProperty(student.getFirstName());
//        this.lastName = new SimpleStringProperty(student.getLastName());
//        this.rank = new SimpleStringProperty(student.getRank().toString());
//    }
//
//    public StudentSearchModel(String firstName, String lastName, String rank) {
//        this.firstName = new SimpleStringProperty(firstName);
//        this.lastName = new SimpleStringProperty(lastName);
//        this.rank = new SimpleStringProperty(rank);
//    }
}
