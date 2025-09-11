package com.agent.story_generator.agent;

import com.agent.story_generator.guardrails.FeatureInputGuardRail;
import com.agent.story_generator.guardrails.StoryEstimateGuardRail;

import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.guardrail.InputGuardrails;
import dev.langchain4j.service.guardrail.OutputGuardrails;
import dev.langchain4j.service.spring.AiService;

@AiService
@InputGuardrails({ FeatureInputGuardRail.class })
@OutputGuardrails({ StoryEstimateGuardRail.class })
public interface StoryGeneratorAgent {
	
	@SystemMessage("You are a story generation assistant that produces agile stories with structured estimates.")
	@UserMessage("""
			Respond only with a valid JSON object. Do not include any introductory text or explanation.
			Restrict output to approximately 1200 tokens.

			Generate user stories for the following feature: {{featureDescription}}

			Instructions:
			- Return a top-level object with the following fields:
			  - "id": prefix "F00" followed by incrementing number (e.g., "F001")
			  - "title": feature title (max 100 characters)
			  - "description": short summary (max 150 characters)
			  - "stories": array of story objects

			- For each story object inside "stories", include:
			  - "id": prefix "US" followed by incrementing number (e.g., "US001")
			  - "title": business value
			  - "description": implementation detail with steps or approach (e.g: create new API endpoint in service name, call jpa methods)
			  - "acceptanceCriteria": list of business-driven criteria.
			  - "testCases": list of basic validations and edge case validations in detail.
			  - "category": one of ["UI", "API", "DB"]
			  - "storyPoint": integer between 1 and 8, estimated based on complexity and manual testing effort across two environments

			Additional context:
			- The application uses SQL Server as the database.
			- Estimate story points based on developer and tester effort, including estimated days to complete.
			- Do not reference any external classes or code. Generate all fields directly.
			""")
	String generateStories(@V("featureDescription") String featureDescription);

}
