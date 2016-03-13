package spring.socket.progressbar.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.session.ExpiringSession;
import org.springframework.session.web.socket.config.annotation.AbstractSessionWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * configuration of Web Socket and Spring Session integration.
 * Created by hanwen on 16/3/11.
 */
@Configuration
@EnableWebSocketMessageBroker
public class ProgressbarSocketConfig
		extends AbstractSessionWebSocketMessageBrokerConfigurer<ExpiringSession> {

	protected void configureStompEndpoints(StompEndpointRegistry registry) {
		registry.addEndpoint("/progressbar").withSockJS();
	}

	public void configureMessageBroker(MessageBrokerRegistry registry) {
		registry.enableSimpleBroker("/queue/");
		registry.setApplicationDestinationPrefixes("/app");
	}
}