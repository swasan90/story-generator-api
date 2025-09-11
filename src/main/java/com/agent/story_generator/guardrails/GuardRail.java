package com.agent.story_generator.guardrails;

public interface GuardRail<T> {
	
	void validate(T input);
}
