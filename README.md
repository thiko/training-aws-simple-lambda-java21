# Exercise: Creating a Simple Java Lambda Function with API Gateway

## Objective
Learn how to create, configure, and test a Java-based AWS Lambda function that responds to API Gateway proxy events, while implementing Lambda best practices like initialization optimization and proper error handling.

## Overview
In this exercise, you'll work with a pre-configured Java project to create a Lambda function that returns information about itself. You'll learn how to optimize performance by executing expensive operations during initialization and how to properly format responses for API Gateway integration.

## Steps

### Part 1: Prepare the Java Project

1. **Review the provided Java code**
   - Open the project in your preferred IDE
   - Examine the `SimpleJavaLambdaProxyHandler` class:
     - Note the implementation of `RequestHandler` interface
     - Observe how it handles API Gateway proxy events
     - See how expensive operations are handled in the static initializer

2. **Build the project**
   - Open a terminal in the project directory
   - Run the Maven build command:
     ```bash
     ./mvnw clean package
     ```
   - Verify that `target/SimpleJavaLambdaProxy-1.0-SNAPSHOT.jar` is created

### Part 2: Create and Configure the Lambda Function

1. **Navigate to the Lambda service**
   - Sign in to the AWS Management Console
   - Go to the Lambda service

2. **Create a new Lambda function**
   - Click "Create function"
   - Select "Author from scratch"
   - Enter a name for your function: `SimpleJavaLambdaProxy`
   - Runtime: Select "Java 21"
   - Architecture: arm64
   - Execution role: Create a new role with basic Lambda permissions
   - Advanced settings:
     - Memory: 512 MB
     - Timeout: 30 seconds
   - Click "Create function"

3. **Configure the Lambda function**
   - In the "Code" tab, click "Upload from" and select ".zip or .jar file"
   - Upload the `target/SimpleJavaLambdaProxy-1.0-SNAPSHOT.jar` file
   - After upload completes, scroll down to "Runtime settings"
   - Click "Edit" and set the handler to:
     ```
     com.tekkminds.awscourse.SimpleJavaLambdaProxyHandler::handleRequest
     ```
   - Click "Save"

### Part 3: Test the Lambda Function

1. **Create a test event**
   - Click the "Test" tab
   - Click "Create new event" if no test is configured
   - Event template: Select "API Gateway AWS Proxy"
   - Event name: `TestAPIGatewayEvent`
   - Event body: You can use the default `API Gateway AWS Proxy` template.
   - Click "Save"

2. **Execute the test**
   - Click "Test" with your new test event selected
   - Wait for the execution to complete
   - Review the execution results:
     - You should see a status code of 200
     - The response body should contain a JSON object with:
       - message: "Hello World"
       - functionName: The name of your function
       - functionVersion: The version of your function (likely "$LATEST")

3. **Review the logs**
   - Expand the "Execution results" details
   - Check the function logs
   - Verify that the log message includes the function name and version

4. **Test error handling** (optional)
   - Modify the code to force an error (e.g., add a line that throws an exception)
   - Upload the modified JAR
   - Run the test again
   - Verify that the function returns a 500 status code with an error message

### Part 4: Understanding Cold Starts

1. **Observe initialization behavior**
   - Note the execution time for your first test (it includes the expensive operation)
   - Run the test again immediately
   - Compare the execution time (it should be faster as the expensive operation runs only during cold starts)
   
   *Optional if you were too fast:*
   - Wait about 15 minutes without invoking the function
   - Run the test again and observe if a cold start occurs

2. **Monitor function activity**
   - Navigate to the "Monitor" tab
   - Review the CloudWatch metrics for your function
   - Check the "Duration" metric to see the difference between cold starts and warm invocations

## Verification

You have successfully completed this exercise when:
- Your Lambda function executes without errors
- The function returns a properly formatted JSON response with status code 200
- The response contains the function name and version
- You understand how static initializers can be used to optimize expensive operations during cold starts
- You've observed the difference between cold and warm invocations

## Understanding the Code

Key aspects of the provided Java code:

1. **Static Initializer Block**
   ```java
   static {
       expensiveOperation();
   }
   ```
   This code runs once during the Lambda container initialization (cold start) and not on each invocation.

2. **Handler Method**
   ```java
   public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context)
   ```
   This is the entry point for each Lambda invocation.

3. **Logging**
   ```java
   context.getLogger().log("Function " + context.getFunctionName() + ":" + context.getFunctionVersion() + " has been called", LogLevel.INFO);
   ```
   This logs information about each invocation using the Lambda context.

4. **Response Formatting**
   ```java
   var result = """
           {
               "message": "Hello World",
               "functionName": "%s",
               "functionVersion": "%s"
           }
           """.formatted(context.getFunctionName(), context.getFunctionVersion());
   ```
   This formats a JSON response using Java text blocks and string formatting.

5. **Error Handling**
   ```java
   catch (Exception e) {
       context.getLogger().log("Failed to convert request body to JSON", LogLevel.ERROR);
       var errorMessage = """
               {
                   "errorMessage": "%s",
                   "errorCode": 500
               }
               """.formatted(e.getMessage());
       return new APIGatewayProxyResponseEvent().withStatusCode(500).withBody(errorMessage);
   }
   ```
   This catches exceptions and returns a formatted error response.

## Extended Learning

- Try deploying a more complex Lambda function that processes input from the API Gateway event
- Experiment with different memory allocations to see how it affects cold start times
