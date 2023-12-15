package com.example.sqllite_app

import java.io.Serializable

class Student(
    var nom: String,
    var prenom: String,
    var tel: String,
    var email: String,
    var login: String,
    var password: String
) : Serializable
