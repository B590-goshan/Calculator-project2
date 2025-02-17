# Calculator
# Project 1

This is a basic calculator application designed for Android using Kotlin and XML.
It emulates the functionality of a typical smartphone calculator, enabling users to effortlessly perform fundamental arithmetic calculations.

## **Features**  

The following **core functionalities** have been implemented:  

* A fully functional calculator layout with buttons for digits, arithmetic operations, and special functions.  
* Users can perform addition, subtraction, multiplication, and division.  
* Users can reset the calculator using the `C` button.  
* Users can toggle the sign of a number with the `+/-` button.  
* Users can compute percentages using the `%` button.  
* Users see real-time updates of their input in the top `TextView`.  
* Decimal numbers are supported via the `.` button.  
* Users can enter continuous calculations without needing to press `=` after each operation.  

### **Additional Enhancements:**  

* Division by zero is managed gracefully by displaying `"Error"` instead of crashing the app.  
* Prevents unnecessary leading zeros (e.g., entering `0005` is automatically formatted as `5`).  
* Real-time calculations are updated dynamically as the user enters numbers and operators.  

## **Demo Walkthrough**  

Below is a walkthrough demonstrating the app’s functionality:  

<img src='calculator.gif' title='Calculator Demo Walkthrough' width='50%' alt='Demo Walkthrough' />  

## **Challenges & Fixes**  

### **Layout Issues**  
GridLayout wasn’t properly expanding, causing some buttons to be misaligned.  

### **Handling Edge Cases**  
- **ZeroDivisionError**: It will displays `"Error"` and doesnt crash.  
- **Decimal Point Validation**: Multiple decimal points in a single number input.  

## **License**
© 2025 Gokul Kaarthick Shanmugam

Licensed under the Apache License, Version 2.0 (the "License");  
you may not use this file except in compliance with the License.  
You may obtain a copy of the License at:  

    http://www.apache.org/licenses/LICENSE-2.0  

Unless required by applicable law or agreed to in writing, software  
distributed under the License is provided "AS IS", WITHOUT WARRANTIES  
OR CONDITIONS OF ANY KIND, either express or implied.  
See the License for the specific language governing permissions  
and limitations under the License.  
