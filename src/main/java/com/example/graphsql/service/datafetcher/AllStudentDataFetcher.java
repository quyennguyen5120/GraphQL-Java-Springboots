package com.example.graphsql.service.datafetcher;

import com.example.graphsql.entity.Student;
import com.example.graphsql.repository.StudentRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AllStudentDataFetcher implements DataFetcher<List<Student>> {
    @Autowired
    StudentRepository studentRepository;

    @Override
    public List<Student> get(DataFetchingEnvironment dataFetchingEnvironment) {
        return studentRepository.findAll();
    }
}
