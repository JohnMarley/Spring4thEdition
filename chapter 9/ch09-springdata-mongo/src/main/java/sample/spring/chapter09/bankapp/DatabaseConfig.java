package sample.spring.chapter09.bankapp;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableMongoRepositories(basePackages = "sample.spring")
@EnableAsync
public class DatabaseConfig {

	public MongoDatabaseFactory mongoDbFactory() {
		String connectionString = "mongodb://localhost:27017/myMongoDB";
//		if there would be pwd protected access to mongodb:
//		spring.data.mongodb.uri=mongodb://username:password@localhost:27017/mydatabase
		return new SimpleMongoClientDatabaseFactory(connectionString);
	}

	@Bean
	public MongoTemplate mongoTemplate() {
		return new MongoTemplate(mongoDbFactory());
	}
}
