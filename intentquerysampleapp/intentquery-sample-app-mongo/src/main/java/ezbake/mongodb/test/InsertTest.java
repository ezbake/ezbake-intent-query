/*   Copyright (C) 2013-2014 Computer Sciences Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. */

package ezbake.mongodb.test;


import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import org.bson.types.ObjectId;

import java.net.UnknownHostException;
import java.util.Random;


public class InsertTest 
{
    public static int   	TOTAL_RECORDS = 20;
    public static String   	HOST = "192.168.50.104";
    public static int   	PORT = 27017;

    public static String   	DBNAME = "testapp";
    public static String   	COLLECTIONNAME = "impalaTest_users";

    
    MongoClient client;
    DB db;
    DBCollection collection;
    
    
    public InsertTest() throws UnknownHostException {
        
        client = new MongoClient(HOST, PORT);
        db = client.getDB(DBNAME);  				// create DB "testapp" if not exists yet
        collection = db.getCollection(COLLECTIONNAME);
    }

    
    public void insert() {
        
        collection.drop();
        
        
        int     age_min = 20;
        int     age_max = 60;
        
        Random randomGenerator = new Random();
        for (int i = 0; i < TOTAL_RECORDS; i++) {
            String  index = String.format("%04d", i);
            BasicDBObject user = new BasicDBObject("user_id", i);
            user.append("last_name", "last_" +  index);
            user.append("first_name", "first_" + index);
            
            int age = randomGenerator.nextInt(age_max - age_min) + age_min;
            user.append("age", age);
            user.append("gender", (age % 2 == 0) ? "M" : "F");
            user.append("employer", (i % 2 == 0) ? "Cloudera" : "42Six");

            collection.insert(user);
        }
        
    }


    public static void main(String[] args) throws UnknownHostException {
        InsertTest instance = new InsertTest();
        
        instance.insert();
        System.out.println("Creation Done");


    }
}
