package test;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * Created by luluteam on 2015/5/23.
 */
@Configuration
public class SpringMongoConfig extends AbstractMongoConfiguration {
    @Override
    public @Bean Mongo mongo() throws Exception {
        return new Mongo("localhost");
    }
    @Override
    public @Bean
    MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(),getDatabaseName());
    }

    @Override
    protected String getDatabaseName() {
        return "PMC";
    }
}
