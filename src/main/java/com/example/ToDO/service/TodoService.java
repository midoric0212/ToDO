package com.example.ToDO.service;

import com.example.ToDO.model.Todo;
import com.example.ToDO.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    @Autowired
    private TodoRepository repo;

    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

    // 增刪改查方法
    public List<Todo> findAll() {
        return repo.findAll();
    }

    public Todo save(Todo todo) {
        return repo.save(todo);
    }

    public Todo update(Long id, Todo newTodo) {
        return repo.findById(id).map(todo -> {
            todo.setTitle(newTodo.getTitle());
            todo.setDone(newTodo.isDone());
            return repo.save(todo);
        }).orElseThrow(() -> new RuntimeException("Not Found"));
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }
}
