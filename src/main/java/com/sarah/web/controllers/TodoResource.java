package com.sarah.web.controllers;

import com.sarah.web.models.Todo;
import com.sarah.web.services.TodoHardcodedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class TodoResource {

    @Autowired
    private TodoHardcodedService todoService;

    @GetMapping("/users/{username}/todos")
    public List<Todo> getAllTodos(@PathVariable String username) {
        return todoService.findAll();
    }

    @GetMapping("/users/{username}/todos/{id}")
    public Todo getTodo(@PathVariable String username, @PathVariable long id) {
        return todoService.findById(id);
    }

    @DeleteMapping("/users/{username}/todos/{todo_id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable String username, @PathVariable long todo_id) {
        Todo todo = todoService.deleteById(todo_id);
        if (todo != null) {
            return ResponseEntity.noContent().build();
        }
        return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
    }

    @PutMapping("users/{username}/todos/{todo_id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable String username, @PathVariable long todo_id, @RequestBody Todo todo) {
        todo.setId(todo_id);
        Todo todoUpdated = todoService.save(todo);
        return new ResponseEntity<>(todo, HttpStatus.OK);
    }

    @PostMapping("/users/{username}/todos")
    public ResponseEntity<?> postTodo(@PathVariable String username, @RequestBody Todo todo) {
        Todo createdTodo = todoService.save(todo);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id").buildAndExpand(createdTodo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
}
