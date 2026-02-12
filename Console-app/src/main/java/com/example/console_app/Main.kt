package com.example.console_app

val students = mutableListOf<Student>()
val courses = mutableListOf<Course>()
val enrollments = mutableListOf<Enrollment>()

fun main() {
    while (true) {
        showMenu()

        when (val choice = readLine()) {
            "1" -> registerStudent()
            "2" -> createCourse()
            "3" -> enrollStudent()
            "4" -> viewCourseStudents()
            "5" -> viewAllStudents()
            "6" -> viewAllCourses()
            "7" -> {
                println("\nGoodbye! Thank you for using the system.")
                return
            }
            else -> println("Invalid choice. Please enter a number between 1-7.")
        }
    }
}

fun showMenu() {
    println("\n" + "=".repeat(50))
    println("        STUDENT COURSE REGISTRATION SYSTEM")
    println("=".repeat(50))
    println("  1. Register Student")
    println("  2. Create Course")
    println("  3. Enroll Student in Course")
    println("  4. View Course Students")
    println("  5. View All Students")
    println("  6. View All Courses")
    println("  7. Exit")
    println("=".repeat(50))
    print("Choose option (1-7): ")
}

fun registerStudent() {
    println("\n--- Register New Student ---")

    // Student ID input with validation
    print("Enter Student ID: ")
    val id = readLine()!!

    if (id.isBlank()) {
        println("ERROR: Student ID cannot be empty!")
        return
    }

    if (students.any { it.id.equals(id, ignoreCase = true) }) {
        println("ERROR: Student ID already exists!")
        return
    }

    // Name input with validation
    print("Enter Full Name: ")
    val name = readLine()!!
    if (name.isBlank()) {
        println("ERROR: Student name cannot be empty!")
        return
    }

    // Email input (optional)
    print("Enter Email (optional, press Enter to skip): ")
    val email = readLine()
    val processedEmail = if (email.isNullOrBlank()) null else email

    // Major input with validation
    print("Enter Major: ")
    val major = readLine()!!
    if (major.isBlank()) {
        println("ERROR: Major cannot be empty!")
        return
    }

    students.add(Student(id, name, processedEmail, major))
    println("SUCCESS: Student registered successfully!")
    println("   ID: $id | Name: $name | Major: $major")
}

fun createCourse() {
    println("\n--- Create New Course ---")

    // Course ID input with validation
    print("Enter Course ID: ")
    val id = readLine()!!

    if (id.isBlank()) {
        println("ERROR: Course ID cannot be empty!")
        return
    }

    if (courses.any { it.courseId.equals(id, ignoreCase = true) }) {
        println("ERROR: Course ID already exists!")
        return
    }

    // Course name input with validation
    print("Enter Course Name: ")
    val name = readLine()!!
    if (name.isBlank()) {
        println("ERROR: Course name cannot be empty!")
        return
    }

    // Credits input with enhanced validation
    print("Enter Credits (positive number): ")
    val creditsInput = readLine()!!

    val credits = try {
        val value = creditsInput.toInt()
        if (value <= 0) {
            println("WARNING: Credits must be positive. Using default 3.")
            3
        } else {
            value
        }
    } catch (e: NumberFormatException) {
        println("WARNING: Invalid credit amount. Using default 3.")
        3
    }

    courses.add(Course(id, name, credits))
    println("SUCCESS: Course created successfully!")
    println("   ID: $id | Name: $name | Credits: $credits")
}

fun enrollStudent() {
    println("\n--- Enroll Student in Course ---")

    // Student ID input with validation
    print("Enter Student ID: ")
    val studentId = readLine()!!
    if (studentId.isBlank()) {
        println("ERROR: Student ID cannot be empty!")
        return
    }

    // Course ID input with validation
    print("Enter Course ID: ")
    val courseId = readLine()!!
    if (courseId.isBlank()) {
        println("ERROR: Course ID cannot be empty!")
        return
    }

    val student = students.find { it.id.equals(studentId, ignoreCase = true) }
    val course = courses.find { it.courseId.equals(courseId, ignoreCase = true) }

    when {
        student == null -> println("ERROR: Student not found with ID: $studentId")
        course == null -> println("ERROR: Course not found with ID: $courseId")
        enrollments.any {
            it.studentId.equals(studentId, ignoreCase = true) &&
                    it.courseId.equals(courseId, ignoreCase = true)
        } -> println("ERROR: Student already enrolled in this course!")
        else -> {
            enrollments.add(Enrollment(student.id, course.courseId))
            println("SUCCESS: Enrollment successful!")
            println("   Student: ${student.name} (${student.id})")
            println("   Course: ${course.courseName} (${course.courseId})")
        }
    }
}

fun viewCourseStudents() {
    println("\n--- View Course Students ---")

    // Course ID input with validation
    print("Enter Course ID: ")
    val courseId = readLine()!!

    if (courseId.isBlank()) {
        println("ERROR: Course ID cannot be empty!")
        return
    }

    val course = courses.find { it.courseId.equals(courseId, ignoreCase = true) }

    if (course == null) {
        println("ERROR: Course not found with ID: $courseId")
        return
    }

    val courseEnrollments = enrollments.filter {
        it.courseId.equals(course.courseId, ignoreCase = true)
    }

    println("\n" + "=".repeat(50))
    println("COURSE: ${course.courseName} (${course.courseId})")
    println("Credits: ${course.credits}")
    println("Total Enrolled: ${courseEnrollments.size} student(s)")
    println("-".repeat(50))

    if (courseEnrollments.isEmpty()) {
        println("No students currently enrolled in this course.")
    } else {
        println("ENROLLED STUDENTS:")
        courseEnrollments.forEachIndexed { index, enrollment ->
            val student = students.find { it.id.equals(enrollment.studentId, ignoreCase = true) }
            if (student != null) {
                println("  ${index + 1}. ${student.name} (${student.id})")
                println("     Major: ${student.major} | Email: ${student.email ?: "Not provided"}")
            }
        }
    }
    println("=".repeat(50))
}

fun viewAllStudents() {
    println("\n" + "=".repeat(50))
    println("ALL REGISTERED STUDENTS")
    println("=".repeat(50))

    if (students.isEmpty()) {
        println("No students registered yet.")
    } else {
        println("Total Students: ${students.size}")
        println("-".repeat(50))

        students.forEachIndexed { index, student ->
            // Count how many courses this student is enrolled in
            val enrolledCourses = enrollments.count {
                it.studentId.equals(student.id, ignoreCase = true)
            }

            println("${index + 1}. ${student.name}")
            println("   ID: ${student.id}")
            println("   Major: ${student.major}")
            println("   Email: ${student.email ?: "Not provided"}")
            println("   Enrolled Courses: $enrolledCourses")
            println()
        }
    }
    println("=".repeat(50))
}

fun viewAllCourses() {
    println("\n" + "=".repeat(50))
    println("ALL COURSES")
    println("=".repeat(50))

    if (courses.isEmpty()) {
        println("No courses available yet.")
    } else {
        println("Total Courses: ${courses.size}")
        println("-".repeat(50))

        courses.forEachIndexed { index, course ->
            val enrolledCount = enrollments.count {
                it.courseId.equals(course.courseId, ignoreCase = true)
            }

            println("${index + 1}. ${course.courseName}")
            println("   ID: ${course.courseId}")
            println("   Credits: ${course.credits}")
            println("   Enrolled Students: $enrolledCount")
            println()
        }
    }
    println("=".repeat(50))
}