package com.dojoapp.Dojo.System.Application;

import com.dojoapp.Dojo.System.Application.model.Student;

public class CurrentStudent {

    private static Student student;

    private CurrentStudent() {}

    public static Student getStudent() {
        return student;
    }

    public static void setStudent(Student student) {
        CurrentStudent.student = student;
    }

}
