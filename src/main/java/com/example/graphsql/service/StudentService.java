package com.example.graphsql.service;

import com.example.graphsql.entity.Student;
import com.example.graphsql.entity.Subject;
import com.example.graphsql.repository.StudentRepository;
import com.example.graphsql.repository.SubjectRepository;
import graphql.GraphQL;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.util.List;

@Service
public class StudentService implements BaseFetcher<Student>{
    @Autowired
    StudentRepository studentRepository;
    @Autowired
    SubjectRepository subjectRepository;

    @Value("classpath:student.graphql")
    Resource resource;

    private GraphQL graphQL;

    @Override
    public Student get(DataFetchingEnvironment dataFetchingEnvironment) {
      return null;
    }

    @Override
    public Boolean delete(DataFetchingEnvironment dataFetchingEnvironment) {
        Long id = dataFetchingEnvironment.getArgument("id");
        Student student = studentRepository.getById(id);
        if(student != null){
            studentRepository.delete(student);
            return true;
        }
        return false;
    }

    @Override
    public Student findById(DataFetchingEnvironment dataFetchingEnvironment) {
        Long id = dataFetchingEnvironment.getArgument("id");
        return studentRepository.findById(id).get();
    }

    @Override
    public Student update(DataFetchingEnvironment dataFetchingEnvironment) {
        Long id = dataFetchingEnvironment.getArgument("id");
        Long idSubject = dataFetchingEnvironment.getArgument("subject_id");
        Subject subject = null;
        if(idSubject != null){
            subject = subjectRepository.findById(idSubject).get();
        }
        Student student = studentRepository.getById(id);
        student.setName(dataFetchingEnvironment.getArgument("name"));
        student.setAddress(dataFetchingEnvironment.getArgument("address"));
        student.setSubject(subject);
        studentRepository.save(student);
        return student;
    }

    @Override
    public Student insert(DataFetchingEnvironment dataFetchingEnvironment) {
        Long idSubject = dataFetchingEnvironment.getArgument("subject_id");
        Subject subject = null;
        if(idSubject != null){
            subject = subjectRepository.findById(idSubject).get();
        }
        Student student = new Student();
        student.setName(dataFetchingEnvironment.getArgument("name"));
        student.setAddress(dataFetchingEnvironment.getArgument("address"));
        student.setSubject(subject);

        studentRepository.save(student);
        return student;
    }

    @Override
    public List<Student> findAll(DataFetchingEnvironment dataFetchingEnvironment) {
        return studentRepository.findAll();
    }

    @Override
    public Page<Student> findByPage(DataFetchingEnvironment dataFetchingEnvironment) {
        Integer pageIndex = dataFetchingEnvironment.getArgument("page_index");
        Integer pageSize = dataFetchingEnvironment.getArgument("page_size");
        Pageable page = PageRequest.of(pageIndex,pageSize);
        Page<Student> pageStudent = studentRepository.findAll(page);
        return pageStudent;
    }

    @PostConstruct
    private void loadSchema() throws IOException {
        File schemaFile = resource.getFile();
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(schemaFile);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("delete", this::delete)
                        .dataFetcher("findById", this::findById)
                        .dataFetcher("insert", this::insert)
                        .dataFetcher("update", this::update)
                        .dataFetcher("findAll", this::findAll)
                        .dataFetcher("findByPage",this::findByPage)
                )
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }


}
