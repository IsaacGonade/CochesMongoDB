package org.example.coches_isaac_gonzalez.util;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import java.util.Properties;

public class ConexionBBDD {

    public static MongoClient conectar()
    {
        try {
            //final MongoClient conexion = new MongoClient(new MongoClientURI("mongodb://admin:1234@localhost:27017/?authSource=admin"));
            Properties configuration = new Properties();
            configuration.load(R.getProperties("database.properties"));
            String host = configuration.getProperty("host");
            String port = configuration.getProperty("port");
            String name = configuration.getProperty("name");
            String username = configuration.getProperty("username");
            String password = configuration.getProperty("password");
            final MongoClient conexion = new MongoClient(new MongoClientURI("mongodb://" + username + ":" + password + "@" + host + ":" + port + "/?authSource=admin"));

            return conexion;
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static void desconectar(MongoClient con)
    {
        con.close();
    }
}
