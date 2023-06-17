package com.tools.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestHandler


class SimpleLambda : RequestHandler<String, String> {
    override fun handleRequest(input: String, context: Context): String {
        val message = System.getenv("MESSAGE")
        return "$input $message"
    }
}