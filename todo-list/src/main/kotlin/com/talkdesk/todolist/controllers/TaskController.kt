package com.talkdesk.todolist.controllers

import com.talkdesk.todolist.entities.Task
import com.talkdesk.todolist.repositories.TaskRepository
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/todos")
class TaskController(
        private val taskRepository: TaskRepository
) {

    @GetMapping
    fun listTasks() : ResponseEntity<List<Task>> {
        val tasks = this.taskRepository.findAll()
        return ResponseEntity.ok(tasks.toList())
    }

    @PostMapping
    fun newTask(@RequestBody task : Task) : ResponseEntity<Task> {
        val taskWithCurrentTimestamp = task.copy(timestamp = System.currentTimeMillis());
        return ResponseEntity.ok(this.taskRepository.save(taskWithCurrentTimestamp))
    }

    @PatchMapping("/{id}/state")
    fun changeStatus(@PathVariable id : String, @RequestBody params : Map<String, Any>) : ResponseEntity<Task> {
        val task = this.taskRepository.findById(id)
        if (task.isPresent){
            val status = (params["status"] ?: task.get().status) as Boolean
            val updatedTask = task.get().copy(status = status)
            return ResponseEntity.ok(this.taskRepository.save(updatedTask))
        }
        return ResponseEntity.notFound().build()
    }

    @PatchMapping("/{id}")
    fun updateTask(
            @RequestBody params : Map<String, Any>,
            @PathVariable("id") id : String
    ) : ResponseEntity<Task>{
        val task = this.taskRepository.findById(id)
        if (task.isPresent){
            val name = (params["name"] ?: task.get().name) as String
            val status = (params["status"] ?: task.get().status) as Boolean
            val taskUpdated = task.get().copy(name = name, status = status)
            return ResponseEntity.ok(taskRepository.save(taskUpdated))
        }
        return ResponseEntity.notFound().build()
    }

    @DeleteMapping("/{id}")
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