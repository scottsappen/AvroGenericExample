package com.github.scotts.avro;

import org.apache.avro.Schema;
import org.apache.avro.SchemaBuilder;
import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.generic.*;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;

import java.io.File;
import java.io.IOException;

public class GenericRecordClimber {

    public static void main(String[] args) {

        /**
         * Simple steps for a simple example:
         * - Write the schema in the code (not recommended, but easy to read)
         * - Create an object using that schema
         * - Write out a file using that schema (this will create a .avro file)
         * - Read the .avro file
         */

        //let's define our schema in the code here
        Schema.Parser parser = new Schema.Parser();
        Schema schema = parser.parse("{\n" +
                "     \"type\": \"record\",\n" +
                "     \"namespace\": \"com.github.scotts.avro\",\n" +
                "     \"name\": \"ClimbingGym\",\n" +
                "     \"doc\": \"Avro Schema for our Climbing Gym\",\n" +
                "     \"fields\": [\n" +
                "       { \"name\": \"gym_name\", \"type\": \"string\", \"doc\": \"Name of Climbing Gym\" },\n" +
                "       { \"name\": \"gym_nickname\", \"type\": \"string\", \"doc\": \"Nick Name of Climbing Gym\" },\n" +
                "       { \"name\": \"location\", \"type\": \"string\", \"doc\": \"Location of the Climbing Gym\" },\n" +
                "       { \"name\": \"hastopropeclimbing\", \"type\": \"boolean\", \"default\": true, \"doc\": \"Field indicating if the climbing gym offers top rope climbing\" },\n" +
                "       { \"name\": \"hasleadclimbing\", \"type\": \"boolean\", \"default\": false, \"doc\": \"Field indicating if the climbing gym offers lead climbing\" },\n" +
                "       { \"name\": \"hasbouldering\", \"type\": \"boolean\", \"default\": true, \"doc\": \"Field indicating if the climbing gym offers bouldering\" },\n" +
                "       { \"name\": \"hasspeedclimbing\", \"type\": \"boolean\", \"default\": false, \"doc\": \"Field indicating if the climbing gym offers speed climbing\" }\n" +
                "     ]\n" +
                "}");

        //let's create a generic record from our schema
        GenericRecordBuilder climbingGymBuilder = new GenericRecordBuilder(schema);
        climbingGymBuilder.set("gym_name", "Inner Peaks");
        climbingGymBuilder.set("gym_nickname", "New Gym or South End");
        climbingGymBuilder.set("location", "Charlotte, NC");
        climbingGymBuilder.set("hasleadclimbing", true);
        climbingGymBuilder.set("hasspeedclimbing", true);
        GenericData.Record climbingGym = climbingGymBuilder.build();

        //let's create a file for that record
        final DatumWriter<GenericRecord> datumWriter = new GenericDatumWriter<>(schema);
        try (DataFileWriter<GenericRecord> dataFileWriter = new DataFileWriter<>(datumWriter)) {
            dataFileWriter.create(climbingGym.getSchema(), new File("climbinggym.avro"));
            dataFileWriter.append(climbingGym);
            System.out.println("Created file climbinggym.avro");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //let's read back that newly generated avro file, create a generic record and thumb through some fields
        final File file = new File("climbinggym.avro");
        final DatumReader<GenericRecord> datumReader = new GenericDatumReader<>();
        GenericRecord customerRead;
        try (DataFileReader<GenericRecord> dataFileReader = new DataFileReader<>(file, datumReader)){
            customerRead = dataFileReader.next();
            System.out.println("Name of Climbing Gym: " + customerRead.get("gym_name"));
            System.out.println("Nick Name of Climbing Gym: " + customerRead.get("gym_nickname"));
            System.out.println("Location of the Climbing Gym: " + customerRead.get("location"));
            System.out.println("Gym offers top rope climbing?: " + customerRead.get("hastopropeclimbing"));
            System.out.println("Gym offers lead climbing: " + customerRead.get("hasleadclimbing"));
            System.out.println("Gym offers bouldering: " + customerRead.get("hasbouldering"));
            System.out.println("Gym offers speed climbing: " + customerRead.get("hasspeedclimbing"));
        }
        catch(IOException e) {
            e.printStackTrace();
        }

    }
}
