package com.talkdesk.todolist.entities

import org.hibernate.annotations.CreationTimestamp
import java.util.*
import javax.persistence.*

@Entity
data class Task (
        @Id
        val id : String = UUID.randomUUID().toString(),
        val timestamp : Long,
        val name : String,
        val status : Boolean
)