package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

/**
 * **Main Activity** for the calculator app.
 * - Prevents division by zero crashes.
 * - Ensures valid number inputs (e.g., no multiple decimal points).
 */
class MainActivity : AppCompatActivity() {

    /** Display field for showing user input and results */
    private lateinit var screenDisplay: TextView

    /** Stores the first operand for calculations */
    private var storedValue: Double = 0.0

    /** Stores the currently selected operation */
    private var selectedOperation: String? = null

    /** Tracks whether a new number is being entered */
    private var isFreshOperation: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screenDisplay = findViewById(R.id.screenDisplay)

        // Numeric and decimal point buttons
        val btnNum0: Button = findViewById(R.id.btnNum0)
        val btnNum1: Button = findViewById(R.id.btnNum1)
        val btnNum2: Button = findViewById(R.id.btnNum2)
        val btnNum3: Button = findViewById(R.id.btnNum3)
        val btnNum4: Button = findViewById(R.id.btnNum4)
        val btnNum5: Button = findViewById(R.id.btnNum5)
        val btnNum6: Button = findViewById(R.id.btnNum6)
        val btnNum7: Button = findViewById(R.id.btnNum7)
        val btnNum8: Button = findViewById(R.id.btnNum8)
        val btnNum9: Button = findViewById(R.id.btnNum9)
        val btnDecimal: Button = findViewById(R.id.btnDecimal)

        // Operation buttons
        val btnReset: Button = findViewById(R.id.btnReset)
        val btnToggleSign: Button = findViewById(R.id.btnToggleSign)
        val btnModulus: Button = findViewById(R.id.btnModulus)
        val btnDivideOp: Button = findViewById(R.id.btnDivideOp)
        val btnMultiplyOp: Button = findViewById(R.id.btnMultiplyOp)
        val btnSubtractOp: Button = findViewById(R.id.btnSubtractOp)
        val btnAddOp: Button = findViewById(R.id.btnAddOp)
        val btnEqualOp: Button = findViewById(R.id.btnEqualOp)

        // Assign listener to numeric buttons
        val numberClickListener = { view: android.view.View ->
            val button = view as Button
            appendNumber(button.text.toString())
        }

        // Apply click listeners to numbers and decimal button
        btnNum0.setOnClickListener(numberClickListener)
        btnNum1.setOnClickListener(numberClickListener)
        btnNum2.setOnClickListener(numberClickListener)
        btnNum3.setOnClickListener(numberClickListener)
        btnNum4.setOnClickListener(numberClickListener)
        btnNum5.setOnClickListener(numberClickListener)
        btnNum6.setOnClickListener(numberClickListener)
        btnNum7.setOnClickListener(numberClickListener)
        btnNum8.setOnClickListener(numberClickListener)
        btnNum9.setOnClickListener(numberClickListener)
        btnDecimal.setOnClickListener(numberClickListener)

        // Apply click listeners to operation buttons
        btnAddOp.setOnClickListener { operationPressed("+") }
        btnSubtractOp.setOnClickListener { operationPressed("-") }
        btnMultiplyOp.setOnClickListener { operationPressed("*") }
        btnDivideOp.setOnClickListener { operationPressed("/") }
        btnEqualOp.setOnClickListener { computeResult() }
        btnReset.setOnClickListener { resetCalculator() }
        btnToggleSign.setOnClickListener { changeSign() }
        btnModulus.setOnClickListener { convertToPercentage() }
    }

    /**
     * Handles number and decimal button presses.
     * - Prevents multiple decimal points in the same number.
     * - Clears the screen if starting a new input or if "Error" is displayed.
     *
     * @param digit The number or decimal point entered.
     */
    private fun appendNumber(digit: String) {
        val currentText = screenDisplay.text.toString()

        // Prevent multiple decimals in the same number
        if (digit == "." && currentText.contains(".")) {
            return
        }

        // Clear display if it's a new number or an error message
        if (isFreshOperation || currentText == "0" || currentText == "Error") {
            screenDisplay.text = ""
            isFreshOperation = false
        }

        // Append the digit to the display
        screenDisplay.append(digit)
    }

    /**
     * Handles arithmetic operation button presses.
     * - If there's an existing operation, compute intermediate result.
     * - Stores the current operation for the next step.
     *
     * @param op The operation (+, -, *, /) pressed.
     */
    private fun operationPressed(op: String) {
        if (!isFreshOperation) {
            computeResult()  // Compute pending operations before proceeding
        }

        selectedOperation = op
        storedValue = screenDisplay.text.toString().toDoubleOrNull() ?: 0.0
        isFreshOperation = true
    }

    /**
     * Executes the stored operation and updates the display.
     * - Handles division by zero properly by displaying "Error".
     */
    private fun computeResult() {
        if (selectedOperation != null) {
            val currentText = screenDisplay.text.toString()

            // If the display shows "Error", reset it and stop further computation
            if (currentText == "Error") {
                return
            }

            val secondOperand = currentText.toDoubleOrNull() ?: 0.0
            val result = when (selectedOperation) {
                "+" -> storedValue + secondOperand
                "-" -> storedValue - secondOperand
                "*" -> storedValue * secondOperand
                "/" -> if (secondOperand == 0.0) {
                    screenDisplay.text = "Error"  // Set display to "Error" on divide by zero
                    return  // Stop execution here
                } else {
                    storedValue / secondOperand
                }
                else -> secondOperand
            }

            // Ensure we don't assign "Error" to storedValue
            screenDisplay.text = result.toString()
            storedValue = result
            isFreshOperation = true
            selectedOperation = null
        }
    }

    /**
     * Resets the calculator to its default state.
     */
    private fun resetCalculator() {
        screenDisplay.text = "0"
        storedValue = 0.0
        selectedOperation = null
        isFreshOperation = true
    }

    /**
     * Toggles the sign of the displayed number (positive to negative).
     */
    private fun changeSign() {
        if (screenDisplay.text.toString() == "Error") {
            return  // Can't toggle sign of "Error"
        }
        val currentValue = screenDisplay.text.toString().toDoubleOrNull() ?: 0.0
        screenDisplay.text = (currentValue * -1).toString()
    }

    /**
     * Converts the displayed number into a percentage.
     */
    private fun convertToPercentage() {
        if (screenDisplay.text.toString() == "Error") {
            return  // Can't perform percentage on "Error"
        }
        val currentValue = screenDisplay.text.toString().toDoubleOrNull() ?: 0.0
        screenDisplay.text = (currentValue / 100).toString()
    }
}