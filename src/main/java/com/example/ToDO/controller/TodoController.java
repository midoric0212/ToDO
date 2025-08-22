package com.example.ToDO.controller;

import com.example.ToDO.model.Todo;
import com.example.ToDO.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/todos")
@CrossOrigin(origins = "*")
public class TodoController {
    @Autowired
    private TodoService service;

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
            .orElseThrow(() -> new RuntimeException("Todo not found"));

        // 檢查是更新內容還是狀態
        if (request.containsKey("title")) {
            existingTodo.setTitle((String) request.get("title"));
        }
        if (request.containsKey("done")) {
            existingTodo.setDone((Boolean) request.get("done"));
        }
        
        return service.update(id, existingTodo);
    }

    @DeleteMapping("/{id}")
    public void deleteTodo(@PathVariable Long id) {
        service.delete(id);
    }
}
