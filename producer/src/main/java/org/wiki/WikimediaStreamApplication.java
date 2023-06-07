package org.wiki;

import java.net.URI;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.wiki.eventhandler.WikimediaChangeHandler;
import org.wiki.producer.WikimediaChangesProducer;

import com.launchdarkly.eventsource.EventHandler;
import com.launchdarkly.eventsource.EventSource;

public class WikimediaStreamApplication {

	public static void main(String[] args) throws InterruptedException {

		Properties kafkaProperties = WikimediaChangesProducer.setKafkaProperties();

		/* 프로듀서 생성 */
		KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(kafkaProperties);

		EventHandler eventHandler = new WikimediaChangeHandler(kafkaProducer, WikimediaChangesProducer.getWikimediaStreamTopic());
		String url = "https://stream.wikimedia.org/v2/stream/recentchange";
		EventSource wikimediaEventSource = new EventSource.Builder(eventHandler, URI.create(url)).build();

		/* 별도 스레드로 동작 */
		wikimediaEventSource.start();

		TimeUnit.MINUTES.sleep(10);
	}
}
