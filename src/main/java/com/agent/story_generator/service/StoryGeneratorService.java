package com.agent.story_generator.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.agent.story_generator.agent.StoryGeneratorAgent;
import com.agent.story_generator.exception.ValidateException;
import com.agent.story_generator.model.Feature;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class StoryGeneratorService {
	
	@Autowired
	private StoryGeneratorAgent storyGeneratorAgent;
	
	@Autowired
	private StoryParser storyParser;
	
	public Feature generateStories(String featureDescription) { 
		String storiesJson = getAgentOutput(featureDescription);
		log.info("Agent1 returned rawJson: {}", storiesJson); 
		return parseStories(storiesJson);
	}
	
	private String getAgentOutput(String featureDescription) {
		String storiesJson = storyGeneratorAgent.generateStories(featureDescription); 
		return storiesJson;
	}
 
	
	private Feature parseStories(String rawJson) {
	    try {
	        return storyParser.parse(rawJson);
	    } catch (Exception e) {
	        log.error("Parsing failed", e);
	        throw new ValidateException("Failed to parse Agent1 output.");
	    }
	}

	
	

}
