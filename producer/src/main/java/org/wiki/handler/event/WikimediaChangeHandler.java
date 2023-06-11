package org.wiki.handler.event;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wiki.enums.StreamTopic;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.MessageEvent;

public class WikimediaChangeHandler implements EventHandler {

	private final Logger log = LoggerFactory.getLogger(WikimediaChangeHandler.class.getSimpleName());
	private KafkaProducer<String, String> kafkaProducer;
	private String topic;

	public WikimediaChangeHandler(KafkaProducer<String, String> kafkaProducer, StreamTopic topic) {
		this.kafkaProducer = kafkaProducer;
		this.topic = topic.getTopic();
	}

	public WikimediaChangeHandler(KafkaProducer<String, String> kafkaProducer, String topic) {
		this.kafkaProducer = kafkaProducer;
		this.topic = topic;
	}


	@Override
	public void onOpen() throws Exception {

	}

	@Override
	public void onClosed() throws Exception {
		this.kafkaProducer.close();
	}

	/**
	 * [비동기] Http 스트림 메시지를 수신.
	 */
	@Override
	public void onMessage(String event, MessageEvent messageEvent) throws Exception {
		this.kafkaProducer.send(
			new ProducerRecord<>(this.topic, messageEvent.getData())
		);
	}

	@Override
	public void onComment(String comment) throws Exception {

	}

	@Override
	public void onError(Throwable t) {
		log.error("[스트림 읽기 오류] - {}", t.getMessage());
	}
}
