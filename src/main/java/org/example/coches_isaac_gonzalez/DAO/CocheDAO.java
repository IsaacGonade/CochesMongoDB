package org.example.coches_isaac_gonzalez.DAO;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.example.coches_isaac_gonzalez.domain.Coche;
import org.example.coches_isaac_gonzalez.util.ConexionBBDD;

import com.google.gson.Gson;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class CocheDAO {

    MongoClient con;

    Coche coche = new Coche();

    MongoCollection<Document> collection=null;

    public void insertarCoche(Coche coche){
        try{
            con = ConexionBBDD.conectar();
            MongoDatabase database = con.getDatabase("Coches");
            database.createCollection("Coches");
            database.getCollection("Coches");
        } catch (Exception e){
            System.out.println(e.getMessage());
        }

        //Document documento = new Document()
//                    .append("titulo", libro.getTitulo())
//                    .append("descripcion", libro.getDescripcion())
//                    .append("autor", libro.getAutor())
//                    .append("fecha", libro.getFecha())
//                    .append("disponible", libro.getDisponible()));
//            db.getCollection("libros").insertOne(documento);


            /*Gson gson = new Gson();
            String json = gson.toJson(coche);

            Document doc = Document.parse(json);
            collection.insertOne(doc);*/


            /*Document hondaCivic = new Document();
            Document renaultMegane = new Document();
            Document citroenC4 = new Document();*/

            /*hondaCivic.append("_id", 1)
                    .append("matrcula", "4852KHL")
                    .append("marca", "Honda")
                    .append("modelo", "Honda Civic")
                    .append("tipo", "Sedan");

            renaultMegane.append("_id", 2)
                    .append("matrcula", "5769LOP")
                    .append("marca", "Renault")
                    .append("modelo", "Renault Megane")
                    .append("tipo", "Familiar");

            citroenC4.append("_id", 3)
                    .append("matrcula", "6523HYJ")
                    .append("marca", "Citroen")
                    .append("modelo", "Citroen C4")
                    .append("tipo", "Familiar");*/
    }

    public List<Coche> obtenerCoche(){
        try {
            con = ConexionBBDD.conectar();
            MongoDatabase database = con.getDatabase("Coches");
//        database.createCollection("Coches");
            collection = database.getCollection("Coches");
        } catch (Exception e){
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        Document documento = new Document();
        FindIterable findIterable = database.getCollection("libros")
                .find(documento)
                .limit(10);

        List<Coche> coches = new ArrayList<Coche>();
        Coche coche = null;
        Iterator<Document> iter = findIterable.iterator();
        while (iter.hasNext()) {
            documento = iter.next();
            coche = new Coche();
            coche.setMatricula(documento.getString("matricula"));
            coche.setMarca(documento.getString("marca"));
            coche.setModelo(documento.getString("modelo"));
            coche.setTipo(documento.getString("tipo"));
            coches.add(coche);
        }
        return coches;
    }
}
