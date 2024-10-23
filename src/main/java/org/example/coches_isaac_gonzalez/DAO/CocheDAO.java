package org.example.coches_isaac_gonzalez.DAO;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.example.coches_isaac_gonzalez.domain.Coche;
import org.example.coches_isaac_gonzalez.util.ConexionBBDD;

import com.google.gson.Gson;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;


public class CocheDAO {
    //llamo a la conexion
    MongoClient con;

    //metodo para insertar coches
    public void insertarCoche(Coche coche){
        try{
            //creo la conexion
            con = ConexionBBDD.conectar();
            // Me conecto a la BD "Coches" si NO existe la crea.
            MongoDatabase database = con.getDatabase("Coches");
            // Creo la coleccion en la que guardare los documentos
            MongoCollection collection = database.getCollection("Coches");

            //nuevo documento
            Document doc = new Document("matricula", coche.getMatricula())
                    .append("marca", coche.getMarca())
                    .append("modelo", coche.getModelo())
                    .append("tipo", coche.getTipo());

            //inserto 1 documento
            collection.insertOne(doc);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    //metodo para modificar un coche
    public void modificarCoche(Coche cocheAntiguo, Coche cocheNuevo){
        con = ConexionBBDD.conectar();
        MongoDatabase database = con.getDatabase("Coches");
        MongoCollection collection = database.getCollection("Coches");

        //obtengo la matricula del coche seleccionado y modifico todos los campos
        collection.replaceOne(new Document("matricula", cocheAntiguo.getMatricula()),
                new Document()
                        .append("matricula", cocheNuevo.getMatricula())
                        .append("marca", cocheNuevo.getMarca())
                        .append("modelo", cocheNuevo.getModelo())
                        .append("tipo", cocheNuevo.getTipo()));
    }

    //metodo para eliminar el coche
    public void eliminarCoche(Coche coche){
        con = ConexionBBDD.conectar();
        MongoDatabase database = con.getDatabase("Coches");
        MongoCollection collection = database.getCollection("Coches");

        collection.deleteOne(new Document("matricula", coche.getMatricula()));
    }

    //metodo para cargar los coches existentes en la base de datos
    public List<Coche> obtenerCoche(){
            con = ConexionBBDD.conectar();
            MongoDatabase database = con.getDatabase("Coches");
            MongoCollection collection = database.getCollection("Coches");
            //creo un cursor para recorrer la base de datos
            MongoCursor<Document> cursor = collection.find().iterator();
            List<Coche> coches = new ArrayList<Coche>();
            Gson gson = new Gson();
            try{
                while (cursor.hasNext()) {
                    Coche coche = gson.fromJson(cursor.next().toJson(), Coche.class);
                    coches.add(coche);
                }
            } finally {
                cursor.close();
            }

            return coches;
    }
}
