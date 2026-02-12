package com.example.console_app
val students = mutableListOf<Student>()
val courses = mutableListOf<Course>()
val enrollments = mutableListOf<Enrollment>()

fun main() {
    while (true) {
        showMenu()

        when (readLine()) {
            "1" -> registerStudent()
            "2" -> createCourse()
            "3" -> enrollStudent()
            "4" -> viewCourseStudents()
            "5" -> viewAllStudents()
            "6" -> viewAllCourses()
            "7" -> {
                println("Goodbye!")
                return
            }
            else -> println("Invalid choice. Try again.")
        }
    }
}
fun viewAllCourses(){
    if (courses.isEmpty()) {
        println("No courses available.")
        return
    }

    println("\n--- Course List ---")
    courses.forEach { course ->
        val count = enrollments.count { it.courseId == course.courseId }
        println("ID: ${course.courseId} | Name: ${course.courseName} | Credits: ${course.credits} | Enrolled: $count")
    }
}

fun viewAllStudents(){
    if (students.isEmpty()) {
        println("No students registered.")
        return
    }

    println("\n--- Student List ---")
    students.forEach {
        println("ID: ${it.id} | Name: ${it.name} | Email: ${it.email ?: "N/A"} | Major: ${it.major}")
    }
}

fun viewCourseStudents(){
    print("Enter Course ID: ")
    val courseId = readLine()!!

    val course = courses.find { it.courseId == courseId }

    if (course == null) {
        println("Course not found.")
        return
    }

    val courseEnrollments = enrollments.filter { it.courseId == courseId }

    if (courseEnrollments.isEmpty()) {
        println("No students enrolled in this course.")
        return
    }

    println("\nStudents in ${course.courseName}:")
    courseEnrollments.forEach { enrollment ->
        val student = students.find { it.id == enrollment.studentId }
        if (student != null) {
            println("ID: ${student.id} | Name: ${student.name}")
        }
    }
}

fun enrollStudent(){
    print("Enter Student ID: ")
    val studentId = readLine()!!

    print("Enter Course ID: ")
    val courseId = readLine()!!

    val student = students.find { it.id == studentId }
    val course = courses.find { it.courseId == courseId }

    if (student == null) {
        println("Student not found.")
        return
    }

    if (course == null) {
        println("Course not found.")
        return
    }

    if (enrollments.any { it.studentId == studentId && it.courseId == courseId }) {
        println("Student already enrolled in this course.")
        return
    }

    enrollments.add(Enrollment(studentId, courseId))
    println("Enrollment successful!")
}

fun createCourse(){
    print("Enter Course ID: ")
    val id = readLine()!!

    if (courses.any { it.courseId == id }) {
        println("Course ID already exists!")
        return
    }

    print("Enter Course Name: ")
    val name = readLine()!!

    print("Enter Credits: ")
    val credits = readLine()!!.toInt()

    courses.add(Course(id, name, credits))

    println("Course created successfully!")
}
fun registerStudent(){
    print("Enter Student ID: ")
    val id = readLine()!!

    if (students.any { it.id == id }) {
        println("Student ID already exists!")
        return
    }

    print("Enter Name: ")
    val name = readLine()!!

    print("Enter Email (optional): ")
    val email = readLine()

    print("Enter Major: ")
    val major = readLine()!!

    students.add(Student(id, name, email, major))

    println("Student registered successfully!")
}

fun showMenu() {
    println("\n===== Student Course Registration System =====")
    println("1. Register Student")
    println("2. Create Course")
    println("3. Enroll Student in Course")
    println("4. View Course Students")
    println("5. View All Students")
    println("6. View All Courses")
    println("7. Exit")
    print("Choose option (1-7): ")
}
