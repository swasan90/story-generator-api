package com.agent.story_generator.model;

import java.util.List;

import lombok.Data;

@Data
public class Feature {
	
	private List<Story> stories;
	private String id;
	private String title;
	private String description;

}
