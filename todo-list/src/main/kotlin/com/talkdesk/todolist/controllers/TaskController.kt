package com.talkdesk.todolist.controllers

import com.talkdesk.todolist.entities.Task
import com.talkdesk.todolist.repositories.TaskRepository
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/task")
class TaskController(
        private val taskRepository: TaskRepository
) {

    @GetMapping
    fun listTasks() : ResponseEntity<List<Task>> {
        val tasks = this.taskRepository.findAll()
        return ResponseEntity.ok(tasks.toList())
    }

    @PostMapping
    fun newTask(@RequestBody task : Task) : Task {
        return this.taskRepository.save(task)
    }

    @PatchMapping("/{id}/state")
    fun changeStatus(@PathVariable id : String, @RequestBody params : Map<String, Object>) : ResponseEntity<Task> {
        val task = this.taskRepository.findById(id)

        if (task.isPresent){
            val isDone = (params["isDone"] ?: task.get().isDone) as Boolean
            val updatedTask = task.get().copy(isDone = isDone)
            return ResponseEntity.ok(this.taskRepository.save(updatedTask))
        }
        return ResponseEntity.notFound().build()
    }
}