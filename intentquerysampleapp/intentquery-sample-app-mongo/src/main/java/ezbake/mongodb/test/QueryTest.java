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


import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;


public class QueryTest
{
    public static int   	TOTAL_RECORDS = 20;
    public static String   	HOST = "192.168.50.104";
    public static int   	PORT = 27017;

    public static String   	DBNAME = "testapp";
    public static String   	COLLECTIONNAME = "impalaTest_users";

    MongoClient client;
    DB db;
    DBCollection collection;

    public QueryTest() throws UnknownHostException {

        client = new MongoClient(HOST, PORT);
        db = client.getDB(DBNAME);  // create DB "testapp" if not exists yet
        collection = db.getCollection(COLLECTIONNAME);
    }

    public void queryEzBakeTestDB_All() throws UnknownHostException {

        DBObject query = new BasicDBObject("gender", "F").append("employer", "Cloudera").append("age", new BasicDBObject("$gt", 30).append("$lt", 50));

        DBCursor cursor = collection.find(query);   // all

        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            cursor.close();
        }

        client.close();
    }

    public void queryEzBakeTestDB() throws UnknownHostException {

        System.out.println("Test 1: Print all users: ");
        DBCursor cursor = collection.find();
        try {
          while (cursor.hasNext()) {
              DBObject cur = cursor.next();
              System.out.println(cur);
          }
        } finally {
            cursor.close();
        }
        System.out.println("Test 1 Done");

        System.out.println("\nTest 2:");
        System.out.println("Print users: select * from users where gender = F");
        //DBObject query = new BasicDBObject("gender", "F"); //.append("employer", "Cloudera").append("age", new BasicDBObject("$gt", 30).append("$lt", 50));
        BasicDBObject query = new BasicDBObject();
        query.append("gender", "F");
        cursor = collection.find(query);    // all
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            cursor.close();
        }
        System.out.println("Test 2 Done");


        System.out.println("\nTest 3:");
        System.out.println("Print users: select * from users where gender = F and employer = Cloudera");
        //query = new BasicDBObject("gender", "F").append("employer", "Cloudera"); //.append("age", new BasicDBObject("$gt", 30).append("$lt", 50));
        
        BasicDBObject andQuery2 = new BasicDBObject();
        List<BasicDBObject> obj99 = new ArrayList<BasicDBObject>();
        obj99.add(new BasicDBObject("gender", "F"));
        obj99.add(new BasicDBObject("employer", "Cloudera"));
        andQuery2.put("$and", obj99);
        
        cursor = collection.find(andQuery2);    // all
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            cursor.close();
        }
        System.out.println("Test 3 Done");

        System.out.println("\nTest 3-1:");
        System.out.println("Print users: select * from users where gender = F and employer = Cloudera");
        //query = new BasicDBObject("gender", "F").append("employer", "Cloudera"); //.append("age", new BasicDBObject("$gt", 30).append("$lt", 50));
        
        BasicDBObject andQuery31 = new BasicDBObject();
        List<BasicDBObject> obj31 = new ArrayList<BasicDBObject>();
        obj31.add(new BasicDBObject("age", new BasicDBObject("$gt", 30)));
        obj31.add(new BasicDBObject("age", new BasicDBObject("$lt", 50)));
        
        // or
        //obj31.add(new BasicDBObject("age", new BasicDBObject("$lt", 50).append("$gt", 40)));

        andQuery31.put("$and", obj31);
        cursor = collection.find(andQuery31);    // all
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            cursor.close();
        }
        System.out.println("Test 3-1 Done");

        
        
        System.out.println("\nTest 4:");
        System.out.println("Print users: select * from users where gender = F and employer = Cloudera and age > 30 and age < 50");
        query = new BasicDBObject("gender", "F").append("employer", "Cloudera").append("age", new BasicDBObject("$gt", 30).append("$lt", 50));
        cursor = collection.find(query);    // all
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            cursor.close();
        }
        System.out.println("Test 4 Done");


        System.out.println("\nTest 5:");
        System.out.println("Print users with select fields: select last_name, gender, age, employer from users where gender = F and employer = Cloudera and age > 30 and age < 50");
        BasicDBObject keys = new BasicDBObject();
        keys.put("_id", 0);
        keys.put("last_name", 1);
        keys.put("gender", 1);
        keys.put("age", 1);
        keys.put("employer", 1);

        cursor = collection.find(query, keys);  // all
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            cursor.close();
        }
        System.out.println("Test 5 Done");

        System.out.println("\nTest 6:");
        System.out.println("Print users: select user_id, gender,age, employer from users where gender = F or employer = Cloudera");
        BasicDBObject orQuery = new BasicDBObject();
        List<BasicDBObject> obj= new ArrayList<BasicDBObject>();
        obj.add(new BasicDBObject("gender", "F"));
        obj.add(new BasicDBObject("employer", "Cloudera"));
        orQuery.put("$or", obj);


        //"gender", "F").append("employer", "Cloudera").append("age", new BasicDBObject("$gt", 30).append("$lt", 50));

        keys = new BasicDBObject();
        keys.put("_id", 0);
        keys.put("user_id", 1);
        keys.put("gender", 1);
        keys.put("age", 1);
        keys.put("employer", 1);

        cursor = collection.find(orQuery, keys);    // all
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            cursor.close();
        }
        System.out.println("Test 6 Done");

        //////////////////////////////////////////////////////////////////////
        System.out.println("\nTest 7:");
        System.out.println("Print users: select user_id, gender,age, employer from users where gender = F or employer = Cloudera and age > 40 and age < 52");
        BasicDBObject andQuery = new BasicDBObject();
        List<BasicDBObject> obj2= new ArrayList<BasicDBObject>();
        obj2.add(new BasicDBObject("gender", "F"));
        obj2.add(new BasicDBObject("employer", "Cloudera"));
        andQuery.put("$or", obj2);
        andQuery.append("age", new BasicDBObject("$gt", 40).append("$lt", 52));
        //andQuery.append("age", new BasicDBObject("$lt", 55));

        //"gender", "F").append("employer", "Cloudera").append("age", new BasicDBObject("$gt", 30).append("$lt", 50));
        /*
        keys = new BasicDBObject();
        keys.put("_id", 0);
        keys.put("user_id", 1);
        keys.put("gender", 1);
        keys.put("age", 1);
        keys.put("employer", 1);
        */
        cursor = collection.find(andQuery, keys);    // all
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            cursor.close();
        }
        System.out.println("Test 7 Done");


        //////////////////////////////////////////////////////////////////////
        System.out.println("\nTest 8:");
        System.out.println("Print users: select user_id, gender,age, employer from users");
        cursor = collection.find(null, keys);    // all
        try {
            while (cursor.hasNext()) {
                DBObject cur = cursor.next();
                System.out.println(cur);
            }
        } finally {
            cursor.close();
        }
        System.out.println("Test 8 Done");

    }


    public static void main(String[] args) throws UnknownHostException {
        QueryTest instance = new QueryTest();

        instance.queryEzBakeTestDB();
        System.out.println("Query Done");
    }
}
