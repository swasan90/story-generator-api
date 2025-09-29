package com.agent.story_generator.guardrails;

import java.util.Map;

import org.springframework.stereotype.Component;

import dev.langchain4j.guardrail.InputGuardrail;
import dev.langchain4j.guardrail.InputGuardrailRequest;
import dev.langchain4j.guardrail.InputGuardrailResult;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FeatureInputGuardRail implements InputGuardrail  { 	
	@Override
    public InputGuardrailResult validate(InputGuardrailRequest  request) {
		Map<String, Object> variables = request.requestParams().variables();
		String input =  variables.get("featureDescription").toString();
		log.info("printing input {}",input);
	    if (input == null || input.trim().isEmpty()) {
	        return fatal("Feature description is empty");
	    }
	    if (input.length() < 200) {
	        return failure("Feature description is too short");
	    }
	    return success();		
	}

}
