package com.example.graphsql.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "subject")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Subject {
    @Id
    @GeneratedValue()
    public Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "subject")
    private List<Student> students;
}
