package org.introspection.backend;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        // Replace this string with your actual Atlas Connection String
        return MongoClients.create("mongodb+srv://solankisrishti611_db_user:SrishtiCluster!@cluster1.s9v9ubn.mongodb.net/IntrospectionDB?appName=Cluster1");
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "IntrospectionDB");
    }
}