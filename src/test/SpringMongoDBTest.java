package test;

/**
 * Created by luluteam on 2015/5/23.
 */

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.pmc.crackanalyse.DAO.CrackReportBasicDAO;
import com.pmc.crackanalyse.model.CrackReport;
import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")

public class SpringMongoDBTest {

//    @Test
//    public void testSayHello() {
//         System.out.println("hello,world");
//    }

    @Test
    public void testMongoConnect() {
        ApplicationContext ac = new AnnotationConfigApplicationContext(SpringMongoConfig.class);
        MongoOperations  mo = (MongoOperations)ac.getBean("mongoTemplate");
       // mo.remove(new Query(Criteria.where("_id").is("5559bfe6e039fb36082da356")),"ActivityInfo");
        DBCollection collection =  mo.getCollection("ActivityInfo");
        DBObject obj = new BasicDBObject();
        obj.put("appId","85d4a553-ee8d-4136-80ab-2469adcae44d");
        DBCursor cursor =  collection.find(obj);
        List<DBObject> list = cursor.toArray();
        for(DBObject dbObject : list){
            ObjectId id = (ObjectId)dbObject.get("_id");
            System.out.println(id.toString());


        }

        //mo.findAll()
    }

}
