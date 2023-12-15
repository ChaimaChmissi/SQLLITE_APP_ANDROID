package com.example.sqllite_app

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONException
import java.io.Serializable

class MainActivity : AppCompatActivity(), UsersAdapter.OnItemClickListener {
    private lateinit var recyclerView: RecyclerView
    private lateinit var usersAdapter: UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize RecyclerView
        recyclerView = findViewById(R.id.recyclerView)

        // Create an empty ArrayList to hold your data (you can populate it later)
        val dataSet = ArrayList<Student>()

        // Initialize UsersAdapter with the data and the listener
        usersAdapter = UsersAdapter(dataSet, this)

        // Set layout manager and adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = usersAdapter

        // Load the list of students when the activity is created
        loadStudents()

        val addButton: Button = findViewById(R.id.AddButton)
        addButton.setOnClickListener {
            // Navigate to AddStudentActivity when the "Add New Student" button is clicked
            val intent = Intent(this@MainActivity, AddStudentActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loadStudents() {
        val dbHelper = EtudiantDBHelper(this)
        val students = dbHelper.getAllStudents()
        dbHelper.close()

        // Update the dataset in the adapter and refresh the RecyclerView
        usersAdapter.updateData(students)
    }


    override fun longClickItem(position: Int) {
        val options = arrayOf("Edit", "Delete")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose an option")
            .setItems(options) { _, which ->
                when (which) {
                    0 -> editStudent(position)
                    1 -> deleteStudent(position)
                }
            }
        builder.create().show()
    }
    private fun editStudent(position: Int) {
        val dbHelper = EtudiantDBHelper(this)
        val students = dbHelper.getAllStudents()

        if (position >= 0 && position < students.size) {
            val studentToEdit = students[position]
            val intent = Intent(this@MainActivity, EditStudentActivity::class.java)
            intent.putExtra("student", studentToEdit)
            startActivity(intent)

        } else {
            // Handle the case where position is out of bounds
            Toast.makeText(this, "Invalid position", Toast.LENGTH_SHORT).show()
        }
    }



    private fun deleteStudent(position: Int) {
        val dbHelper = EtudiantDBHelper(this)
        val students = dbHelper.getAllStudents()
        val studentToDelete = students[position]

        // Show a confirmation dialog before deleting
        AlertDialog.Builder(this)
            .setTitle("Delete Student")
            .setMessage("Are you sure you want to delete this student?")
            .setPositiveButton("Yes") { _, _ ->
                dbHelper.deleteStudent(studentToDelete)
                dbHelper.close()

                // Reload the students and update the RecyclerView
                loadStudents()
            }
            .setNegativeButton("No", null)
            .show()
    }


    override fun selectItem(position: Int) {
        // Handle item selection if needed
    }
}
