package com.dojoapp.Dojo.System.Application.model;


import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    @Query("SELECT * FROM \"Student\" WHERE \"Rank\" = :rank")
    Student findByRank(@Param("rank") Rank rank);

    @Query("SELECT * FROM \"Student\" WHERE \"LastName\" = :lName")
    Student findByLastName(@Param("lName") String lastName);

    @Query("SELECT * FROM \"Student\" WHERE \"FirstName\" = :fName")
    Student findByFirstName(@Param("fName") String firstName);
}
