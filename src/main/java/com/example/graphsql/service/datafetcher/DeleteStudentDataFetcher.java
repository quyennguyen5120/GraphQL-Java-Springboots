package com.example.graphsql.service.datafetcher;

import com.example.graphsql.entity.Student;
import com.example.graphsql.repository.StudentRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeleteStudentDataFetcher implements DataFetcher {
    @Autowired
    StudentRepository studentRepository;

    @Override
    public Boolean get(DataFetchingEnvironment environment) {
        int id = environment.getArgument("id");
        try {
            studentRepository.deleteById(id);
            return true;
        } catch (Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
