package com.changejar.Items.config;

import com.mongodb.ConnectionString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@ConditionalOnProperty(value = "spring.data.mongo.enabled")
@EnableMongoAuditing
@EnableMongoRepositories(basePackages = {"com.changejar.Items"})
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Value("${spring.data.mongodb.uri}")
    private String mongoUI;

    @Override
    protected String getDatabaseName() {
        ConnectionString connectionString = new ConnectionString(mongoUI);
        return connectionString.getDatabase();
    }

    @Bean
    @Override
    public MongoTemplate mongoTemplate(MongoDatabaseFactory dbFactory, MappingMongoConverter mongoConverter) {
        return new MongoTemplate(dbFactory, mongoConverter);
    }


}
