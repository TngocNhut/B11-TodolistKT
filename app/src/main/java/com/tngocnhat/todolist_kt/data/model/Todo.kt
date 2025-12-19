package com.tngocnhat.todolist_kt.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "todos")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val description: String?,
    val createdDate: Long = System.currentTimeMillis(),
    val userId: Int // Foreign key reference to User
)
