package com.example.graphsql.service.datafetcher;

import com.example.graphsql.entity.Student;
import com.example.graphsql.repository.StudentRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UpdateStudentDataFetcher implements DataFetcher<Student> {
    @Autowired
    StudentRepository studentRepository;

    @Override
    public Student get(DataFetchingEnvironment environment) {
        Student student = Student.builder()
                .id(environment.getArgument("id"))
                .name(environment.getArgument("name"))
                .address((environment.getArgument("address")))
                .build();
        return studentRepository.save(student);
    }
}
