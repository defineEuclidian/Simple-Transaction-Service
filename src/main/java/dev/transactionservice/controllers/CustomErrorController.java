package dev.transactionservice.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import dev.transactionservice.models.Error;

import org.springframework.boot.web.servlet.error.ErrorController;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * CustomErrorController class that implements the ErrorController interface, utilizing the default /error path.
 * 
 * <p> This is used in tandem with the Error class to produce custom error messages that pertain to the Error class structure. </p>
 */
@RestController
public class CustomErrorController implements ErrorController {

    /**
     * Method for handling errors.
     * 
     * @param request HttpServletRequest
     * @return Error object storing the customized error message.
     */
    @RequestMapping("/error")
    public Error error(HttpServletRequest request) {
        String errorMessage = "";
        String errorCode = "";
        String errorPath = "";
        String errorReason = "";

        // HttpServletRequest attributes, for printing the status code, path of origin, and exception message
        Object errorCodeObject = request.getAttribute("jakarta.servlet.error.status_code");
        Object errorPathObject = request.getAttribute("jakarta.servlet.error.request_uri");
        Object errorReasonObject = request.getAttribute("org.springframework.web.servlet.DispatcherServlet.EXCEPTION");

        if (errorCodeObject != null) { // Check if the attribute exists, to ensure no exceptions are thrown if it does not exist
            errorCode = errorCodeObject.toString();
            errorMessage = HttpStatus.valueOf(Integer.valueOf(errorCode)).getReasonPhrase(); // Translate the error code into an integer, that can then be used to retrieve its reason phrase with HttpStatus
        }

        if (errorPathObject != null) {
            errorPath = errorPathObject.toString();
        }

        if (errorReasonObject != null) {
            Matcher errorReasonMatcher = Pattern.compile("Exception: (.*)").matcher(errorReasonObject.toString()); // Regex to extract the exception message, should the exception exist

            if (errorReasonMatcher.find())
            {
                errorReason = errorReasonMatcher.group(1); // The errorReasonMatcher splits the full exception into two parts: the exception library and exception message; we only need the message
            }
        }

        return new Error(errorMessage, errorCode, errorPath, errorReason);
    }
    
}
