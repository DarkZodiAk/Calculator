package com.exampl.myapplication

import kotlin.math.round

class ZeroDivisionError: Error()

fun backspace(text: String) : String {
    return if(text.length > 1) text.dropLast(1)
    else "0"
}

fun percent(text: String) : String {
    return if(text.contains(Regex("[+×÷-]"))) {
        text
    } else if(!text.contains(Regex("[1-9]"))) {
        "0"
    } else {
        (text.toFloat() / 100).toString()
    }
}

fun putDigit(text: String, digit: Int) : String {
    return if(text == "0") digit.toString()
    else text + digit.toString()
}


fun putOperator(text: String, operator: Char) : String {
    return if(text.last() in listOf('+','×','-','÷')) {
        text.dropLast(1) + operator
    } else {
        text + operator
    }
}

fun putDot(text: String) : String {
    val revText = text.reversed()
    var i = 0
    while(revText[i] !in listOf('×','÷','-','+')) {
        if(revText[i] == '.') return text
        i++
    }
    return "$text."
}


fun String.findFirstEntry(symbol: Char): Int {
    for (index in this.indices) {
        if (this[index] == symbol) {
            return index
        }
    }
    return -1
}

fun processNum(number: String) : Float {
    return when (number.findFirstEntry('.')) {
        -1 -> number.toInt().toFloat()
        0 -> "0$number".toFloat()
        number.length - 1 -> (number + '0').toFloat()
        else -> number.toFloat()
    }
}

fun algOperation(num1: Float, num2: Float, operator: Char): Float {
    return when(operator) {
        '+' -> num1 + num2
        '-' -> num1 - num2
        else -> 0f
    }
}

fun multOperation(num1: Float, num2: Float, operator: Char): Float {
    if(operator == '÷') {
        if(num2 == 0f) throw ZeroDivisionError()
        return num1 / num2
    } else if(operator == '×') {
        return num1 * num2
    } else return 0f
}

fun calculate(text: String) : String {
    var numbers = mutableListOf<Float>()
    val operators = mutableListOf<Char>()
    var mutableText = text
    var i = 1

    //Parsing a string
    while(mutableText.contains(Regex("[+×÷-]"))) {
        if((mutableText[i] in listOf('×','÷')) || (mutableText[i] in listOf('+','-') && mutableText[i-1] != 'E')) {
            try {
                numbers.add(processNum(mutableText.take(i)))
            } catch (e:NumberFormatException) {
                return "Ошибка"
            }
            operators.add(mutableText[i])
            mutableText = mutableText.drop(i+1)
            i = 0
        }
        i++
    }
    try {
        numbers.add(processNum(mutableText))
    } catch(e:NumberFormatException) {
        return "Ошибка"
    }

    //Calculation starts here
    //Processing multiply and division operations

    i = 0
    while(true) {
        if(operators[i] in listOf('×','÷')) {
            try {
                numbers[i] = multOperation(numbers[i], numbers[i+1], operators[i])
            } catch(e:ZeroDivisionError) {
                return "На 0 делить нельзя"
            }
            numbers.removeAt(i+1)
            operators.removeAt(i)
        }
        else i++
        if(i == operators.size) break
    }

    //Next step
    var result = numbers[0]
    numbers = numbers.drop(1).toMutableList()

    for(i in numbers.indices) {
        result = algOperation(result, numbers[i], operators[i])
    }

    return when(result){
        round(result) -> result.toString().dropLast(2)
        else -> result.toString()
    }
}