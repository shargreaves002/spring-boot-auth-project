package com.sarah.web.services;

import com.sarah.web.models.Todo;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class TodoHardcodedService {

    private static List<Todo> todos = new ArrayList<>();
    private static int idCounter = 0;

    static {
        todos.add(new Todo(++idCounter, "sarah", "Learn to Dance", new Date(), false));
        todos.add(new Todo(++idCounter, "sarah", "Learn about Microservices", new Date(), false));
        todos.add(new Todo(++idCounter, "sarah", "Learn about Angular", new Date(), false));
    }

    public List<Todo> findAll() {
        return todos;
    }

    public Todo deleteById(long id) {
        Todo todo = findById(id);
        if (todo == null) return null;
        if (todos.remove(todo)) {
            return todo;
        }
        return null;
    }

    public Todo save(Todo todo) {
        if (todo.getId() == -1 || todo.getId() == 0) {
            todo.setId(++idCounter);
        } else {
            deleteById(todo.getId());
        }
        if (todo.getTargetDate() == null) {
            todo.setTargetDate(new Date());
        }
        todos.add(todo);
        return todo;
    }

    public Todo findById(long id) {
        for (Todo todo : todos) {
            if (todo.getId() == id) {
                return todo;
            }
        }

        return null;
    }
}
