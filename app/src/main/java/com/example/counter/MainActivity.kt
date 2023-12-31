package com.example.counter

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    //Use lateinit to initialize our views to be used throughout the class/activity
    //Will define them in onCreate
    private lateinit var numberText: TextView
    private lateinit var numberInput: EditText
    private lateinit var interval: EditText
    private lateinit var summary: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Get a reference to our buttons
        val submitButton: Button = findViewById(R.id.main_activity_bt_submit)
        val randomButton: Button = findViewById(R.id.main_activity_bt_random_number)
        val incrementButton: Button = findViewById(R.id.main_activity_bt_increment)
        val decrementButton: Button = findViewById(R.id.main_activity_bt_decrement)

        //Set a click listener for each button
        submitButton.setOnClickListener {submitNumber()}
        randomButton.setOnClickListener {generateRandomNumber()}
        incrementButton.setOnClickListener {changeNumber("+")}
        decrementButton.setOnClickListener {changeNumber("-")}

        //Set the values to our views initialized with late init
        numberText = findViewById(R.id.main_activity_tv_number)
        numberInput = findViewById(R.id.main_activity_et_number_input)
        interval = findViewById(R.id.main_activity_et_interval)
        summary = findViewById(R.id.main_activity_tv_summary)

        //Check if a bundle is present, if it is not savedInstanceState will be null
        if (savedInstanceState != null){
            //Get the message from the bundle and set the text
            numberText.text = savedInstanceState.getString("myNumber")
            interval.setText(savedInstanceState.getString("myInterval"))
            summary.text = savedInstanceState.getString("mySummary")


        }

    }
    //Override the lifecycle method onSaveInstanceState to save important data
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        //Save the current message
        outState.putString("myNumber", numberText.text.toString())
        outState.putString("myInterval", interval.text.toString())
        outState.putString("mySummary", summary.text.toString())
    }

    //Get a number from an editText and display
    private fun submitNumber(){
        //Get the number from the editText that the user entered
        var startingNumber = numberInput.text.toString()

        //Check to see if the user entered a number, if not default to 10
        if (startingNumber == ""){
            numberText.text = "10"
        }

        //Set the starting number to the textView
        numberText.text = startingNumber

        //Clear the editText after submitting the number
        numberInput.setText("")

        //Hide the keyboard
        hideKeyboard()

    }

    //Generate a random number and display
    private fun generateRandomNumber(){
        //Generate a random number between -100 and 100
        val randomNumber = (-100..100).random()
        numberText.text = randomNumber.toString()
    }

    //Increment or Decrement the number based on the button pressed
    private fun changeNumber(operation: String){
        //Get the current number and increment the number
        val currentNumber = numberText.text.toString().toInt()
        var incrementValue = interval.text.toString()

        //Check to see if the user entered a number, if not default to 1
        if (incrementValue == ""){
            incrementValue = "1"
        }

        //Either increment or decrement the number based on the operation
        if (operation == "+"){
            val newNumber = currentNumber + incrementValue.toInt()
            numberText.text = newNumber.toString()

            //Update the summary message
            summary.text = "$currentNumber + $incrementValue = $newNumber"
        } else {
            val newNumber = currentNumber - incrementValue.toInt()
            numberText.text = newNumber.toString()

            //Update the summary message
            summary.text = "$currentNumber - $incrementValue = $newNumber"
        }

        //Hide the keyboard
        hideKeyboard()
    }

    //Hide the keyboard
    private fun hideKeyboard(){
       val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        //Alternative way to hide the keyboard:
        //imm.hideSoftInputFromWindow(numberText.windowToken, 0)
    }

}