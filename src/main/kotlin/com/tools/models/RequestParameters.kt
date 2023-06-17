package com.tools.models

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class RequestParameters(
    @JsonProperty("first_number")
    val firstNumber: BigDecimal?,
    @JsonProperty("second_number")
    val secondNumber: BigDecimal?,
    @JsonProperty("operator")
    val operator: String?
) {

    fun calculate(): Response {

        if (operator.isNullOrBlank()) return Response(result = null, error = "Operation failed: Operator can't be null")

        if (firstNumber == null || secondNumber == null) return Response(
            result = null,
            error = "Operation failed: Numbers can't be null or blank"
        )

        val result: BigDecimal = when (this.operator) {
            "ADD" -> this.firstNumber.plus(this.secondNumber)
            "SUBTRACT" -> this.firstNumber.minus(this.secondNumber)
            "MULTIPLY" -> this.firstNumber.multiply(this.secondNumber)
            "DIVIDE" -> if (secondNumber.compareTo(BigDecimal.ZERO) == 0) this.firstNumber.divide(this.secondNumber) else return Response(
                result = null,
                error = "Operation failed: Denominator can't be zero"
            )

            else -> {
                return Response(result = null, error = "Operator $operator is not supported")
            }
        }
        return Response(result, null)
    }
}