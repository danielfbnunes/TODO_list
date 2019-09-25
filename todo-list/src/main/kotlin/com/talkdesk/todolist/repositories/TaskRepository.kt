package com.talkdesk.todolist.repositories

import com.talkdesk.todolist.entities.Task
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface TaskRepository : CrudRepository<Task, String>