package com.agent.story_generator.model;

import java.util.List;

import lombok.Data;

@Data
public class Story {
	
	private String title;
	private String description;
	private String id;
	private List<String> acceptanceCriteria;
	private List<String> testCases;
	private String category;
	private int storyPoint;

}
