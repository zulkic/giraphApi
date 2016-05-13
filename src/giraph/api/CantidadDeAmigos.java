package giraph.api;


import org.apache.hadoop.io.Text;

public class CantidadDeAmigos extends NodeComputation {

	@Override
	public void compute(PNode pNode, Iterable<Text> messages) {
		System.out.println("holamundo");
		//pNode.setNodePropertyValue("Friends", Integer.toString(pNode.getNumPEdges()));
		pNode.voteToHalt();
	}

}
