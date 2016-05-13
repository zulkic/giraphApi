package giraph.api;



import java.io.IOException;

import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.Text;

public abstract class NodeComputation extends BasicComputation<Text, Text, Text, Text>{
	public PNode pNode;
	

	public abstract void  compute(PNode pNode, Iterable<Text> messages);
	
	@Override
	public void compute(
			Vertex<Text, Text, Text> vertex,
			Iterable<Text> messages) throws IOException {
		
		pNode = new PNode(vertex);
		compute(pNode, messages);
	}
}
