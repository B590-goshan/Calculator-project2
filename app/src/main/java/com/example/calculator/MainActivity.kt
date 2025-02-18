package com.example.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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

        if (savedInstanceState != null) {
            screenDisplay.text = savedInstanceState.getString("displayText", "0")
            storedValue = savedInstanceState.getDouble("storedValue", 0.0)
            selectedOperation = savedInstanceState.getString("selectedOperation")
            isFreshOperation = savedInstanceState.getBoolean("isFreshOperation", true)
        }

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

        // Scientific Calculation
        val btnSin: Button? = findViewById(R.id.btnSin)
        val btnCos: Button? = findViewById(R.id.btnCos)
        val btnTan: Button? = findViewById(R.id.btnTan)
        val btnLog10: Button? = findViewById(R.id.btnLog10)
        val btnLn: Button? = findViewById(R.id.btnLn)

        // Assign listener to numeric buttons
        val numberClickListener: (android.view.View) -> Unit = { view: android.view.View ->
            val button = view as Button
            appendNumber(button.text.toString())
            Log.d("Calculator", "Button clicked: ${button.text}")
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

        // Apply click listeners for Scientific Operations (only in landscape mode)
        btnSin?.setOnClickListener { computeScientificFunction("sin") }
        btnCos?.setOnClickListener { computeScientificFunction("cos") }
        btnTan?.setOnClickListener { computeScientificFunction("tan") }
        btnLog10?.setOnClickListener { computeScientificFunction("Log 10") }
        btnLn?.setOnClickListener { computeScientificFunction("ln") }

        // Numeric Buttons
        logAndSetListener(findViewById(R.id.btnNum0)) { appendNumber("0") }
        logAndSetListener(findViewById(R.id.btnNum1)) { appendNumber("1") }
        logAndSetListener(findViewById(R.id.btnNum2)) { appendNumber("2") }
        logAndSetListener(findViewById(R.id.btnNum3)) { appendNumber("3") }
        logAndSetListener(findViewById(R.id.btnNum4)) { appendNumber("4") }
        logAndSetListener(findViewById(R.id.btnNum5)) { appendNumber("5") }
        logAndSetListener(findViewById(R.id.btnNum6)) { appendNumber("6") }
        logAndSetListener(findViewById(R.id.btnNum7)) { appendNumber("7") }
        logAndSetListener(findViewById(R.id.btnNum8)) { appendNumber("8") }
        logAndSetListener(findViewById(R.id.btnNum9)) { appendNumber("9") }
        logAndSetListener(findViewById(R.id.btnDecimal)) { appendNumber(".") }

        // Operation Buttons
        logAndSetListener(findViewById(R.id.btnAddOp)) { operationPressed("+") }
        logAndSetListener(findViewById(R.id.btnSubtractOp)) { operationPressed("-") }
        logAndSetListener(findViewById(R.id.btnMultiplyOp)) { operationPressed("*") }
        logAndSetListener(findViewById(R.id.btnDivideOp)) { operationPressed("/") }
        logAndSetListener(findViewById(R.id.btnEqualOp)) { computeResult() }
        logAndSetListener(findViewById(R.id.btnReset)) { resetCalculator() }
        logAndSetListener(findViewById(R.id.btnToggleSign)) { changeSign() }
        logAndSetListener(findViewById(R.id.btnModulus)) { convertToPercentage() }

        // Scientific Functions (Only in Landscape Mode)
        logAndSetListener(findViewById(R.id.btnSin)) { computeScientificFunction("sin") }
        logAndSetListener(findViewById(R.id.btnCos)) { computeScientificFunction("cos") }
        logAndSetListener(findViewById(R.id.btnTan)) { computeScientificFunction("tan") }
        logAndSetListener(findViewById(R.id.btnLog10)) { computeScientificFunction("log 10") }
        logAndSetListener(findViewById(R.id.btnLn)) { computeScientificFunction("ln") }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("displayText", screenDisplay.text.toString())
        outState.putDouble("storedValue", storedValue)
        outState.putString("selectedOperation", selectedOperation)
        outState.putBoolean("isFreshOperation", isFreshOperation)
    }

    private fun logAndSetListener(button: Button?, action: () -> Unit) {
        button?.setOnClickListener {
            Log.d("Calculator", "Button clicked: ${button.text}")
            action()
        }
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

        // If a number is pressed after an operation result, clear the screen
        if (isFreshOperation) {
            screenDisplay.text = ""
            isFreshOperation = false
        }

        // Prevent multiple decimal points in the same number
        if (digit == "." && currentText.contains(".")) return

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
        // If an operation is pressed right after a result, continue with the last result
        if (selectedOperation != null && !isFreshOperation) {
            computeResult()  // Compute previous operation before applying the new one
        }

        selectedOperation = op
        storedValue = screenDisplay.text.toString().toDoubleOrNull() ?: 0.0
        isFreshOperation = true  // Mark as a fresh operation so next number clears the display
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
                    screenDisplay.text = getString(R.string.error_text)  // Set display to "Error" on divide by zero
                    return  // Stop execution here
                } else {
                    storedValue / secondOperand
                }
                else -> secondOperand
            }

            // Ensure we don't assign "Error" to storedValue
            screenDisplay.text = screenDisplay.context.getString(R.string.formatted_number, result)
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
    @SuppressLint("DefaultLocale")
    private fun changeSign() {
        if (screenDisplay.text.toString() == "Error") {
            return  // Can't toggle sign of "Error"
        }
        val currentValue = screenDisplay.text.toString().toDoubleOrNull() ?: 0.0
        screenDisplay.text = String.format("%.6f", currentValue * -1)
    }

    /**
     * Converts the displayed number into a percentage.
     */
    @SuppressLint("DefaultLocale")
    private fun convertToPercentage() {
        if (screenDisplay.text.toString() == "Error") {
            return  // Can't perform percentage on "Error"
        }
        val currentValue = screenDisplay.text.toString().toDoubleOrNull() ?: 0.0
        screenDisplay.text = String.format("%.6f", currentValue / 100)
    }

    /**
     * Handles scientific function button presses.
     * - Computes trigonometric and logarithmic values based on the current input.
     */
    @SuppressLint("DefaultLocale")
    private fun computeScientificFunction(func: String) {
        // If a scientific function is used after an arithmetic result, continue using the result
        if (isFreshOperation) {
            isFreshOperation = false  // Reset flag
        }

        val value = screenDisplay.text.toString().toDoubleOrNull() ?: run {
            screenDisplay.text = getString(R.string.error_text)
            return
        }

        if (value <= 0 && (func == "log 10" || func == "ln")) {
            screenDisplay.text = getString(R.string.error_text)
            return
        }

        val result = when (func.lowercase()) {
            "sin" -> kotlin.math.sin(Math.toRadians(value))
            "cos" -> kotlin.math.cos(Math.toRadians(value))
            "tan" -> kotlin.math.tan(Math.toRadians(value))
            "Log 10" -> kotlin.math.log10(value)
            "ln" -> kotlin.math.ln(value)
            else -> {
                Log.e("Calculator", "Unknown function: $func")
                return
            }
        }

        // Ensure fresh operation state so the next number clears the screen
        isFreshOperation = true
        screenDisplay.text = String.format(getString(R.string.formatted_number), result)

        Log.d("Calculator", "Function executed: $func, Result: $result")
    }
}