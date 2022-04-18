package com.example.graphsql.entity;

import com.example.graphsql.entity.BaseEntity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "subject")
public class Subject extends BaseEntity {
    @Id
    @GeneratedValue()
    public Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "subject")
    private List<Student> students;

    public Subject() {
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
