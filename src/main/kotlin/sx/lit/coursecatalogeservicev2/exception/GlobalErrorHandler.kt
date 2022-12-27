package sx.lit.coursecatalogeservicev2.exception

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.ObjectMapper
import io.swagger.v3.core.util.Json
import mu.KLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Component
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception

@Component
@ControllerAdvice
class GlobalErrorHandler : ResponseEntityExceptionHandler() {

    companion object : KLogging();

    override fun handleMethodArgumentNotValid(
        ex: MethodArgumentNotValidException,
        headers: HttpHeaders,
        status: HttpStatus,
        request: WebRequest
    ): ResponseEntity<Any> {

        logger.error("MethodArgumentNotValid thrown: ${ex.message}", ex)
        val errors = ex.bindingResult.allErrors
            .map { err -> err.defaultMessage }
            .sortedBy { it }
        logger.info("errors: $errors")

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(errors.joinToString( ", " ))
    }

    @ExceptionHandler(Exception::class)
    fun handleAllExceptions(exception: Exception, request: WebRequest) : ResponseEntity<Any> {
        logger.error("Exception thrown: ${exception.message}", exception)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ObjectMapper().writeValueAsString(exception.message))
    }

    @ExceptionHandler(InstructorNotValidException::class)
    fun handleInstructorNotValidException(exception: Exception, request: WebRequest) : ResponseEntity<Any>  {
        logger.error("InstructorNotValidException thrown: ${exception.message}", exception)
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body((ObjectMapper().writeValueAsString(exception.message)))
    }
}
