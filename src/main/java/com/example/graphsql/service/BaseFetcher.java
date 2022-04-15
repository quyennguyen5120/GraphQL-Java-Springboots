package com.example.graphsql.service;

import graphql.GraphQL;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import org.springframework.data.domain.Page;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface BaseFetcher<T> extends DataFetcher<T> {
    Boolean delete(DataFetchingEnvironment dataFetchingEnvironment);
    T findById(DataFetchingEnvironment dataFetchingEnvironment);
    T update(DataFetchingEnvironment dataFetchingEnvironment);
    T insert(DataFetchingEnvironment dataFetchingEnvironment);
    List<T> findAll(DataFetchingEnvironment dataFetchingEnvironment);
    Page<T> findByPage(DataFetchingEnvironment dataFetchingEnvironment);
}
