FROM public.ecr.aws/lambda/java:21

# Copy your built JAR into the Lambda image
#COPY target/story-generator-1.0.0.jar ${LAMBDA_TASK_ROOT}
COPY target/classes ${LAMBDA_TASK_ROOT}
COPY target/dependency/* ${LAMBDA_TASK_ROOT}/lib/

# Specify your Lambda handler class
CMD ["com.agent.story_generator.StreamLambdaHandler::handleRequest"]