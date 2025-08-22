package com.example.ToDO.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.ToDO.model.Todo;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {
}


