package com.example.graphsql.service;

import com.example.graphsql.repository.StudentRepository;
import com.example.graphsql.service.datafetcher.*;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Service
public class StudentService {
    @Autowired
    StudentRepository studentRepository;

    @Value("classpath:student.graphql")
    Resource resource;

    private GraphQL graphQL;
    @Autowired
    private AllStudentDataFetcher allStudentDataFetcher;

    @Autowired
    private StudentDataFetcher studentDataFetcher;

    @Autowired
    private AddStudentDataFetcher addStudentDataFetcher;

    @Autowired
    private UpdateStudentDataFetcher updateStudentDataFetcher;

    @Autowired
    private DeleteStudentDataFetcher deleteStudentDataFetcher;

    // load schema at application start up
    @PostConstruct
    private void loadSchema() throws IOException {

        // get the schema
        File schemaFile = resource.getFile();
        // parse schema
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("allStudents", allStudentDataFetcher)
                        .dataFetcher("student", studentDataFetcher)
                        .dataFetcher("addStudent", addStudentDataFetcher)
                        .dataFetcher("updateStudent", updateStudentDataFetcher)
                        .dataFetcher("deleteStudent", deleteStudentDataFetcher))
                .build();
    }


    public GraphQL getGraphQL() {
        return graphQL;
    }
}
