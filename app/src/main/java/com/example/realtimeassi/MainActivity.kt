package com.example.realtimeassi

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    // Write a message to the database
    val database = Firebase.database
    val myRef = database.getReference("person")

    private lateinit var saveDataBtn: Button
    private lateinit var getDataBtn: Button
    private lateinit var edName: EditText
    private lateinit var edId: EditText
    private lateinit var edAge: EditText
    private lateinit var savedData: TextView

    var count: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        saveDataBtn = findViewById(R.id.saveData)
        getDataBtn = findViewById(R.id.getData)

        edName = findViewById(R.id.age)
        edId = findViewById(R.id.name)
        edAge = findViewById(R.id.id)
        savedData = findViewById(R.id.tvData)


        saveDataBtn.setOnClickListener {
            val name = edName.text.toString()
            val id = edId.text.toString()
            val age = edAge.text.toString()

            val person = hashMapOf(
                "name" to name,
                "id" to id,
                "age" to age,
            )
            myRef.child("$count").setValue(person)
            count++
            Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
        }

        getDataBtn.setOnClickListener {
            // Read from the database
            myRef.addValueEventListener(object : ValueEventListener {

                override fun onDataChange(snapshot: DataSnapshot) {
                    // This method is called once with the initial value and again
                    // whenever data at this location is updated.
                    val value = snapshot.value
                    savedData.text = value.toString()
                    Log.d("TAG", "Value is: $value")
                    Toast.makeText(applicationContext, "Success", Toast.LENGTH_SHORT).show()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("TAG", "Failed to read value.", error.toException())
                    Toast.makeText(applicationContext, "Exception", Toast.LENGTH_SHORT).show()
                }

            })
        }

    }
}