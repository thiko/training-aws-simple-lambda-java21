package com.tekkminds.awscourse;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;

/**
 * As we configure the API Gateway as a simple Proxy, we use the APIGatewayProxyRequestEvent as input and
 * APIGatewayProxyResponseEvent as output type.
 * API Gateway is not the only possible trigger for Lambda we have to make it explicit.
 */
public class SimpleJavaLambdaProxyHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    // Initialization code - put code here you want to initialize during cold start (and not get charged for it)

    @Override
    public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent apiGatewayProxyRequestEvent, Context context) {
        // APIGatewayProxyRequestEvent contains a lot of information about the request
        // Context contains all available information about the context this function is executed (like the function name, Version, ...)

        return null;
    }

    static void expensiveOperation() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
