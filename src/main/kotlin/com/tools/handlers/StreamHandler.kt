package com.tools.handlers

import com.amazonaws.services.lambda.runtime.Context
import com.amazonaws.services.lambda.runtime.RequestStreamHandler
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.tools.models.RequestParameters
import com.tools.models.Response
import java.io.InputStream
import java.io.OutputStream
import java.util.logging.Logger

class StreamHandler : RequestStreamHandler {

    companion object {
        val logger: Logger = Logger.getLogger(StreamHandler::class.qualifiedName)
    }

    private val objectMapper: ObjectMapper = ObjectMapper()

    override fun handleRequest(input: InputStream, output: OutputStream, context: Context) {
        val request = objectMapper.readValue(input, RequestParameters::class.java)
        logger.info("Request Body : $request")
        val result = request.calculate()
        logger.info("Result of ${request.operator} operation is $result")
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.writeValue(output, result)
    }
}