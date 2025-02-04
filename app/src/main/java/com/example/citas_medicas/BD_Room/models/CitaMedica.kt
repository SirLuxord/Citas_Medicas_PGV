package com.example.citas_medicas.BD_Room.models


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "citas")
data class CitaMedica(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int = 0,

    @ColumnInfo(name = "email")
    val email: String,

    @ColumnInfo(name = "titulo")
    val titulo: String,

    @ColumnInfo(name = "fecha")
    val fecha: String,

    @ColumnInfo(name = "hora")
    val hora: String
)