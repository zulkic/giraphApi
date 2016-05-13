package giraph.api;

import org.apache.giraph.edge.Edge;
import org.apache.giraph.edge.EdgeFactory;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.giraph.io.formats.AdjacencyListTextVertexInputFormat;



/**
* Class to read graphs stored as adjacency lists with ids represented by
* Strings and values as doubles.  This is a good inputformat for reading
* graphs where the id types do not matter and can be stashed in a String.
*/
public class TextTextTextAdjacencyListVertexInputFormat
   extends AdjacencyListTextVertexInputFormat<Text, Text,
           Text>  {

 @Override
 public AdjacencyListTextVertexReader createVertexReader(InputSplit split,
     TaskAttemptContext context) {
   return new TextTextTextAdjacencyListVertexReader(null);
 }

 /**
  * Vertex reader used with
  * {@link TextDoubleDoubleAdjacencyListVertexInputFormat}
  */
 protected class TextTextTextAdjacencyListVertexReader extends
     AdjacencyListTextVertexReader {

   /**
    * Constructor with
    * {@link AdjacencyListTextVertexInputFormat.LineSanitizer}.
    *
    * @param lineSanitizer the sanitizer to use for reading
    */
   public TextTextTextAdjacencyListVertexReader(LineSanitizer
       lineSanitizer) {
     super(lineSanitizer);
   }

   @Override
   public Text decodeId(String s) {
     return new Text(s);
   }

   @Override
   public Text decodeValue(String s) {
     return new Text(s);
   }

   @Override
   public Edge<Text, Text> decodeEdge(String s1, String s2) {
     return EdgeFactory.create(new Text(s1),
         new Text(s2));
   }
 }

}
