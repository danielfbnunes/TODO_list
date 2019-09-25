package com.talkdesk.todolist.controllers

import com.talkdesk.todolist.entities.Task
import com.talkdesk.todolist.repositories.TaskRepository
import org.springframework.http.HttpStatus
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

    @DeleteMapping("/delete/{id}")
    fun deleteTask(
            @PathVariable id : String
    ) : ResponseEntity<Task>  {
        val task = this.taskRepository.findById(id)

        if (task.isPresent){
            this.taskRepository.deleteById(id)
            return ResponseEntity.status(HttpStatus.OK).build()
        }
        return ResponseEntity.notFound().build()
    }



}