package com.example.graphsql.repository;

import com.example.graphsql.entity.Subject;
import com.google.errorprone.annotations.SuppressPackageLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
