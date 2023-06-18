package com.tools.models

import com.fasterxml.jackson.annotation.JsonInclude
import java.math.BigDecimal

data class Response(
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val result: BigDecimal?,
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val error: String?
)