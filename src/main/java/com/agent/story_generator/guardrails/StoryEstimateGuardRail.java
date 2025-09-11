package com.agent.story_generator.guardrails;

import java.io.InputStream;
import java.util.Set;

import org.springframework.stereotype.Component;

import com.agent.story_generator.exception.ValidateException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.networknt.schema.JsonSchema;
import com.networknt.schema.JsonSchemaFactory;
import com.networknt.schema.SpecVersionDetector;
import com.networknt.schema.ValidationMessage;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.guardrail.OutputGuardrail;
import dev.langchain4j.guardrail.OutputGuardrailResult;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StoryEstimateGuardRail implements OutputGuardrail {

	private final ObjectMapper mapper = new ObjectMapper();

	@Override
	public OutputGuardrailResult validate(AiMessage responseFromLLM) {
		String rawJson = responseFromLLM.text();
		if (rawJson == null || rawJson.trim().isEmpty()) {
			throw new ValidateException("Agent1 returned empty output");
		} else {
			try {
				log.info("processing output guard rail");
				InputStream schemaStream = getClass().getResourceAsStream("/schemas/StoryGenerateSchema.json");
				if (schemaStream == null) {
					throw new RuntimeException("Schema file not found in classpath");
				}

				JsonNode schemaNode = mapper.readTree(schemaStream);
				JsonSchemaFactory factory = JsonSchemaFactory.getInstance(SpecVersionDetector.detect(schemaNode));
				JsonSchema schema = factory.getSchema(schemaNode);

				JsonNode jsonNode = mapper.readTree(rawJson); 
				Set<ValidationMessage> errors = schema.validate(jsonNode);
				log.info("printing errors {}", errors);
				if (!errors.isEmpty()) {
					throw new IllegalArgumentException("JSON validation failed: " + errors);
				}
				return success();
			} catch (Exception e) {
				log.error("Guard rail failed on validating", e);
				throw new ValidateException("Failed to validate Agent1 output");
			}
		}
	}
}
