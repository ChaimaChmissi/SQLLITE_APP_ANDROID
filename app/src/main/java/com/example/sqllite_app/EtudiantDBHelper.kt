package com.example.sqllite_app

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class EtudiantDBHelper(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(EtudiantBC.SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(EtudiantBC.SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "PFE.db"
    }
    fun insertStudent(student: Student) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_NOM, student.nom)
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_PRENOM, student.prenom)
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_TEL, student.tel)  // Added tel column
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_EMAIL, student.email)
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_LOGIN, student.login)
            put(EtudiantBC.EtudiantEntry.COLUMN_NAME_MDP, student.password)
        }

        db.insert(EtudiantBC.EtudiantEntry.TABLE_NAME, null, values)
        db.close()
    }


    fun getAllStudents(): ArrayList<Student> {
        val students = ArrayList<Student>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM ${EtudiantBC.EtudiantEntry.TABLE_NAME}", null)

        while (cursor.moveToNext()) {
            val nomIndex = cursor.getColumnIndex(EtudiantBC.EtudiantEntry.COLUMN_NAME_NOM)
            val prenomIndex = cursor.getColumnIndex(EtudiantBC.EtudiantEntry.COLUMN_NAME_PRENOM)
            val emailIndex = cursor.getColumnIndex(EtudiantBC.EtudiantEntry.COLUMN_NAME_EMAIL)
            val loginIndex = cursor.getColumnIndex(EtudiantBC.EtudiantEntry.COLUMN_NAME_LOGIN)
            val passwordIndex = cursor.getColumnIndex(EtudiantBC.EtudiantEntry.COLUMN_NAME_MDP)
            val telIndex = cursor.getColumnIndex(EtudiantBC.EtudiantEntry.COLUMN_NAME_TEL)

            // Check if the column index is valid
            if (nomIndex >= 0 && prenomIndex >= 0 && emailIndex >= 0 && loginIndex >= 0 && passwordIndex >= 0 && telIndex >= 0) {
                val nom = cursor.getString(nomIndex)
                val prenom = cursor.getString(prenomIndex)
                val email = cursor.getString(emailIndex)
                val login = cursor.getString(loginIndex)
                val password = cursor.getString(passwordIndex)
                val tel = cursor.getString(telIndex)

                students.add(Student(nom, prenom, tel, email, login, password))
            }
        }

        cursor.close()
        db.close()

        return students
    }

    fun updateStudent(oldStudent: Student, newStudent: Student) {
        writableDatabase.use { db ->
            val values = ContentValues().apply {
                put(EtudiantBC.EtudiantEntry.COLUMN_NAME_NOM, newStudent.nom)
                put(EtudiantBC.EtudiantEntry.COLUMN_NAME_PRENOM, newStudent.prenom)
                put(EtudiantBC.EtudiantEntry.COLUMN_NAME_TEL, newStudent.tel)
                put(EtudiantBC.EtudiantEntry.COLUMN_NAME_EMAIL, newStudent.email)
                put(EtudiantBC.EtudiantEntry.COLUMN_NAME_LOGIN, newStudent.login)
                put(EtudiantBC.EtudiantEntry.COLUMN_NAME_MDP, newStudent.password)
            }

            val whereClause = "${EtudiantBC.EtudiantEntry.COLUMN_NAME_NOM} = ? AND " +
                    "${EtudiantBC.EtudiantEntry.COLUMN_NAME_PRENOM} = ?"

            val whereArgs = arrayOf(oldStudent.nom, oldStudent.prenom)

            db.update(
                EtudiantBC.EtudiantEntry.TABLE_NAME,
                values,
                whereClause,
                whereArgs
            )
        }
    }



    // Inside EtudiantDBHelper class
    fun deleteStudent(student: Student) {
        val db = writableDatabase
        db.delete(
            EtudiantBC.EtudiantEntry.TABLE_NAME,
            "${EtudiantBC.EtudiantEntry.COLUMN_NAME_NOM} = ? AND ${EtudiantBC.EtudiantEntry.COLUMN_NAME_PRENOM} = ?",
            arrayOf(student.nom, student.prenom)
        )
        db.close()
    }



}
