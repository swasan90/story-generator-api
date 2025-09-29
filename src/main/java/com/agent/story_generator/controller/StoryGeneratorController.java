package com.agent.story_generator.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.agent.story_generator.model.Feature;
import com.agent.story_generator.model.FeatureRequest;
import com.agent.story_generator.service.StoryGeneratorService; 

@RestController
@RequestMapping(("/api/v1"))
@CrossOrigin(origins = "https://story-generator-ui.s3.us-east-2.amazonaws.com", methods = {RequestMethod.POST, RequestMethod.OPTIONS})
public class StoryGeneratorController { 
	
	@Autowired
	private StoryGeneratorService storyGeneratorService;

	@PostMapping("/generate-story")
	public Feature generateStoriesWithEstimatesAndTestCases(@RequestBody FeatureRequest featureReq) {
		return storyGeneratorService.generateStories(featureReq.getFeatureDescription());
	} 
}
