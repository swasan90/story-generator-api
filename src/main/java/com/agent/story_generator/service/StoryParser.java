package com.agent.story_generator.service;

import org.springframework.stereotype.Component;

import com.agent.story_generator.exception.ValidateException;
import com.agent.story_generator.model.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class StoryParser {
	
	private final ObjectMapper mapper = new ObjectMapper();
	
	public Feature parse(String json) {
		try {
			return mapper.readValue(json, new TypeReference<Feature>() {});
		}catch(Exception e) {
			log.error("Failed to parse feature and generated stories ",e);
			throw new ValidateException("Failed to parse feature and generated stories");
		}
	}

}
