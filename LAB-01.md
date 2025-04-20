# Simple Java Lambda Proxy

Simple example of a Java-based AWS Lambda function.

- Create the JAR file using: `./mvnw clean package`
It generates the `target/SimpleJavaLambdaProxy-1.0-SNAPSHOT.jar` file containing the source code and all dependencies.

- Create the Lambda function on the webconsole 
- Configure the lambda function (Runtimeconfiguration) to specify the handler: `com.tekkminds.awscourse.SimpleJavaLambdaProxyHandler::handleRequest`


## Lab description

Work in the `SimpleJavaLambdaProxyHandler` class.
Requirements:
- It should invoke the `expensiveOperation` but in a way, that this code is executed during init-time. We don't want to pay for it.
- The handler should log (there is a logger!)
  - the function name
  - the function version
- It should return a valid `APIGatewayProxyResponseEvent` with status code 200 and following JSON formatted response body:
  ```
     {
        "message": "Hello World",
        "functionName": "<name of the function>",
        "functionVersion": "<version of the function>"
     }
    ```
  - You can either write the JSON manually or using Jackson (which is already part of the dependencies)

- In case of any Exception, it should return a `APIGatewayProxyResponseEvent` with status code 500 and a JSON formatted error object with following structure:
  ```
    {
        "errorMessage": "<the error message>",
        "errorCode": 500
    }
   ```
- Test the Lambda function using the webconsole by selecting your Lambda function -> Test -> Event: API Gateway API Proxy
