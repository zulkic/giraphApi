package giraph.api;


import org.apache.hadoop.io.Text;

public class CantidadDeAmigos extends NodeComputation {

	@Override
	public void compute(PNode pNode, Iterable<Text> messages) {
		pNode.setNodePropertyValue("Friends", Integer.toString(pNode.getNumPEdges()));
		for (PEdge pedge : pNode.getPEdges()) {
			System.out.println("la edad es:" + pedge.getEdgePropertyValue("distance") );
		}
		pNode.voteToHalt();
	}
}
