package com.example.ToDO.service;

import com.example.ToDO.model.Todo;
import com.example.ToDO.repository.TodoRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class TodoService {
    private final TodoRepository repo;

    public TodoService(TodoRepository repo) {
        this.repo = repo;
    }

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
        }).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found with id: " + id));
    }

    public void delete(Long id) {
        if (repo.findById(id).isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found with id: " + id);
        }
        repo.deleteById(id);
    }
}
