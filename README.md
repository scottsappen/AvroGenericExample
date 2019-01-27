# AvroGenericExample
Simple example that show how you can create an Avro object from a schema

Example will create a generic record as an example using a rock climbing facility as the entity.

This is a Java/Maven project using JDK1.8.

**Simple steps for a simple example:**
- Write the schema in the code (not recommended, but easy to read)
- Create an object using that schema
- Write out a file using that schema (this will create a .avro file)
- Read the .avro file

<br/>

*Common compile time errors*

Exception in thread "main" org.apache.avro.AvroRuntimeException: Field location type:STRING pos:2 not set and has no default value
- means you probably did not set a required field

Exception in thread "main" java.lang.NullPointerException
- means you probably did something else wrong, maybe setting a field that does not exist in the schema

