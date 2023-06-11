package org.wiki.enums;

public enum StreamTopic {

	WIKIMEDIA_STREAM_TOPIC("wikimedia.recentchange");

	private String topic;

	StreamTopic(String topic) {
		this.topic = topic;
	}

	public String getTopic() {
		return topic;
	}
}
