package com.example.sqllite_app

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddStudentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_student)

        val saveButton: Button = findViewById(R.id.Validerbutton)
        saveButton.setOnClickListener {
            saveStudent()
        }
    }

    private fun saveStudent() {
        val nom = findViewById<EditText>(R.id.nom).text.toString()
        val prenom = findViewById<EditText>(R.id.prenom).text.toString()
        val tel = findViewById<EditText>(R.id.tel).text.toString()
        val email = findViewById<EditText>(R.id.email).text.toString()
        val login = findViewById<EditText>(R.id.login).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()

        val dbHelper = EtudiantDBHelper(this)
        val student = Student(nom, prenom, tel, email, login, password)
        dbHelper.insertStudent(student)
        dbHelper.close()

        // Optionally, you can navigate back to the main activity after saving
        val intent = Intent(this@AddStudentActivity, MainActivity::class.java)
        startActivity(intent)
    }
}
