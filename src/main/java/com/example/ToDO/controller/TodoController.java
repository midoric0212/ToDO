package com.example.ToDO.controller;

import com.example.ToDO.model.Todo;
import com.example.ToDO.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {
    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return service.findAll();
    }

    @PostMapping
    public Todo createTodo(@RequestBody Map<String, Object> request) {
        Todo todo = new Todo();
        // 前端傳送 {"content": "內容"}，後端存為title
        String content = (String) request.get("content");
        todo.setTitle(content);
        todo.setDone(false);
        return service.save(todo);
    }

    @PutMapping("/{id}")
    public Todo updateTodo(@PathVariable Long id, @RequestBody Map<String, Object> request) {
        Todo existingTodo = service.findAll().stream()
            .filter(t -> t.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found with id: " + id));

        if (!request.containsKey("title") && !request.containsKey("done")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Update request must contain either 'title' or 'done' field");
        }

        if (request.containsKey("title")) {
            String title = (String) request.get("title");
            if (title == null || title.trim().isEmpty()) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Title cannot be empty");
            }
            existingTodo.setTitle(title);
        }

        if (request.containsKey("done")) {
            Boolean done = (Boolean) request.get("done");
            if (done == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Done status cannot be null");
            }
            existingTodo.setDone(done);
        }
        
        return service.update(id, existingTodo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        service.delete(id);
    }
}
