package org.wiki.producer;

import java.util.Properties;

import org.apache.kafka.common.serialization.StringSerializer;
import org.wiki.properties.KafkaProperties;

public class WikimediaChangesProducer {

	public static Properties setKafkaProperties() {
		Properties properties = new Properties();

		/* 부트스트랩 서버 보안 연결을 위한 값 */
		properties.setProperty("security.protocol", "SASL_SSL");
		properties.setProperty("sasl.jaas.config",
			"org.apache.kafka.common.security.plain.PlainLoginModule required username=\"your-username\" password=\"your-password\";");
		properties.setProperty("sasl.mechanism", "PLAIN");

		/* 부트스트랩 서버 */
		properties.setProperty("bootstrap.servers", KafkaProperties.BOOTSTRAP_SERVER);

		/* 프로듀서 직렬화 설정 */
		properties.setProperty("key.serializer", StringSerializer.class.getName());
		properties.setProperty("value.serializer", StringSerializer.class.getName());

		return properties;
	}
}
