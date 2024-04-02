package sample.spring.chapter10.bankapp;

import java.util.ArrayList;
import java.util.List;

//import javax.annotation.PostConstruct;
//import javax.jms.ConnectionFactory;

import jakarta.jms.ConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;


//@ImportResource(locations = "classpath:META-INF/spring/applicationContext.xml")
@Configuration
@EnableJms //--corresponds to <jms:annotation-driven>
public class JmsConfig {

	//--corresponds to <amq:broker>
//	@PostConstruct
//	public void brokerService() throws Exception {
//		BrokerService broker = new BrokerService();
//		broker.addConnector("tcp://localhost:61616");
//		broker.start();
//	}
	
	
	//--corresponds to <amq:connectionFactory>
	@Bean
	public CachingConnectionFactory connectionFactory() {
		ActiveMQConnectionFactory activeMQConnectionFactory = new ActiveMQConnectionFactory();
		activeMQConnectionFactory.setBrokerURL("tcp://localhost:61616");
//		activeMQConnectionFactory.setBrokerURL("vm://localhost");
		List<String> trustedPackages = new ArrayList<>();
		trustedPackages.add("sample.spring.chapter10.bankapp.domain");
		trustedPackages.add("java.util");
		activeMQConnectionFactory.setTrustedPackages(trustedPackages);
		activeMQConnectionFactory.setConnectResponseTimeout(5000);
		return new CachingConnectionFactory(activeMQConnectionFactory);
	}

//	@Bean
//	public CachingConnectionFactory connectionFactory() {
//		CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
//		return cachingConnectionFactory;
//	}

	//--corresponds to <jms:listener-container>
	@Bean
	public DefaultJmsListenerContainerFactory jmsListenerContainerFactory(
			CachingConnectionFactory cachingConnectionFactory, JmsTransactionManager transactionManager) {
		DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
		factory.setConnectionFactory(cachingConnectionFactory);
		factory.setTransactionManager(transactionManager);
		return factory;
	}

	@Bean(name = "jmsTxManager")
	public JmsTransactionManager jmsTransactionManager(CachingConnectionFactory cachingConnectionFactory) {
		JmsTransactionManager transactionManager = new JmsTransactionManager();
		transactionManager.setConnectionFactory(cachingConnectionFactory);
		return transactionManager;
	}

	@Bean
	public JmsMessagingTemplate jmsMessagingTemplate(CachingConnectionFactory cachingConnectionFactory) {
		JmsMessagingTemplate jmsMessagingTemplate = new JmsMessagingTemplate(cachingConnectionFactory);
//		jmsMessagingTemplate.setDefaultDestinationName("fixedDepositDestination");
		return jmsMessagingTemplate;
	}

//	@Bean
//	public JmsTemplate jmsTemplate(CachingConnectionFactory cachingConnectionFactory) {
//		JmsTemplate jmsTemplate = new JmsTemplate(cachingConnectionFactory);
//		// Optionally configure default destination
//		// jmsTemplate.setDefaultDestinationName("yourQueueName");
//		return jmsTemplate;
//	}
}
