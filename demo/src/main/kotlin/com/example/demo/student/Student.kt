package com.example.demo.student

import java.time.LocalDate

data class Student(val id: Long,
                           val name: String,
                           val age: Int,
                           val email: String,
                           val dob: LocalDate) {

}

