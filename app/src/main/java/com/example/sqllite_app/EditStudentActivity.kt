package com.example.sqllite_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
class EditStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_student)

        val saveButton: Button = findViewById(R.id.Validerbutton)
        saveButton.setOnClickListener {
            saveEditedStudent()
        }
        val student = intent.getSerializableExtra("student") as? Student

        if (student == null) {
            Toast.makeText(this, "Invalid or missing student data", Toast.LENGTH_SHORT).show()
            finish() // Close the activity if student data is not valid
        } else {
            // Continue with populating fields
            populateFields(student)
        }

        val receivedIntent = intent
        if (receivedIntent != null && receivedIntent.hasExtra("student")) {

            if (student != null) {
                populateFields(student)
            } else {
                // Handle the case where "student" extra is not a valid Student object
                Toast.makeText(this, "Invalid student data found", Toast.LENGTH_SHORT).show()
            }
        } else {
            // Handle the case where "student" extra is not found
            Toast.makeText(this, "No student data found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun populateFields(student: Student) {
        val nomEditText = findViewById<EditText>(R.id.nom)
        val prenomEditText = findViewById<EditText>(R.id.prenom)
        val telEditText = findViewById<EditText>(R.id.tel)
        val emailEditText = findViewById<EditText>(R.id.email)
        val loginEditText = findViewById<EditText>(R.id.login)
        val passwordEditText = findViewById<EditText>(R.id.password)

        nomEditText.setText(student.nom)
        prenomEditText.setText(student.prenom)
        telEditText.setText(student.tel)
        emailEditText.setText(student.email)
        loginEditText.setText(student.login)
        passwordEditText.setText(student.password)
    }

    private fun saveEditedStudent() {
        val nom = findViewById<EditText>(R.id.nom).text.toString()
        val prenom = findViewById<EditText>(R.id.prenom).text.toString()
        val tel = findViewById<EditText>(R.id.tel).text.toString()
        val email = findViewById<EditText>(R.id.email).text.toString()
        val login = findViewById<EditText>(R.id.login).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()

        val dbHelper = EtudiantDBHelper(this)

        // Retrieve the student from the Intent extra
        val student = intent.getSerializableExtra("student") as? Student

        if (student != null) {
            // Create a new Student object with the updated information
            val updatedStudent = Student(nom, prenom, tel, email, login, password)

            // Update the student in the database
            dbHelper.updateStudent(student, updatedStudent)
            dbHelper.close()

            // Optionally, you can navigate back to the main activity after saving
            val intent = Intent(this@EditStudentActivity, MainActivity::class.java)
            startActivity(intent)
        } else {
            Toast.makeText(this, "Invalid or missing student data", Toast.LENGTH_SHORT).show()
        }
    }



}
