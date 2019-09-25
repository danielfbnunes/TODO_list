package com.talkdesk.todolist.entities

import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id

@Entity
data class Task (
        @Id
        val id : String = UUID.randomUUID().toString(),
        val name : String,
        @Column(nullable = true)
        val description : String?,
        val isDone : Boolean
)