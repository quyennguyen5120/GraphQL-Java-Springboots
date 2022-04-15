package com.example.graphsql.resource;

import com.example.graphsql.service.SubjectService;
import graphql.ExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/rest/subject")
@RestController
public class SubjectResource {
    @Autowired
    SubjectService subjectService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Object> getAllBooks(@RequestBody String query) {
        ExecutionResult execute = subjectService.getGraphQL().execute(query);
        return new ResponseEntity<>(execute, HttpStatus.OK);
    }
}
