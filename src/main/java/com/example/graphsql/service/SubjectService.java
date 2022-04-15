package com.example.graphsql.service;

import com.example.graphsql.entity.Subject;
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
public class SubjectService implements BaseFetcher<Subject>{

    @Value("classpath:subject.graphql")
    Resource resource;
    private GraphQL graphQL;
    @Autowired
    SubjectRepository subjectRepository;

    @Override
    public Subject get(DataFetchingEnvironment dataFetchingEnvironment) {
        return null;
    }

    @Override
    public Boolean delete(DataFetchingEnvironment dataFetchingEnvironment) {
        Long id = dataFetchingEnvironment.getArgument("id");
        Subject subject = subjectRepository.getById(id);
        if(subject != null){
            subjectRepository.delete(subject);
            return true;
        }
        return false;
    }

    @Override
    public Subject findById(DataFetchingEnvironment dataFetchingEnvironment) {
        Long id = dataFetchingEnvironment.getArgument("id");
        return subjectRepository.findById(id).get();
    }

    @Override
    public Subject update(DataFetchingEnvironment dataFetchingEnvironment) {
        Long id = dataFetchingEnvironment.getArgument("id");
        Subject subject = subjectRepository.getById(id);
        subject = Subject.builder()
                .name( dataFetchingEnvironment.getArgument("name"))
                .build();
        subjectRepository.save(subject);
        return subject;
    }

    @Override
    public Subject insert(DataFetchingEnvironment dataFetchingEnvironment) {
        Subject subject = Subject.builder()
                .name( dataFetchingEnvironment.getArgument("name"))
                .build();
        subjectRepository.save(subject);
        return subject;
    }

    @Override
    public List<Subject> findAll(DataFetchingEnvironment dataFetchingEnvironment) {
        return subjectRepository.findAll();
    }

    @Override
    public Page<Subject> findByPage(DataFetchingEnvironment dataFetchingEnvironment) {
        Integer pageIndex = dataFetchingEnvironment.getArgument("page_index");
        Integer pageSize = dataFetchingEnvironment.getArgument("page_size");
        Pageable page = PageRequest.of(pageIndex,pageSize);
        Page<Subject> pageSubject = subjectRepository.findAll(page);
        return pageSubject;
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
                        .dataFetcher("findByPage", this::findByPage)
                )
                .build();
    }

    public GraphQL getGraphQL() {
        return graphQL;
    }
}
