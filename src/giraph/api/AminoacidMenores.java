package giraph.api;

import org.apache.hadoop.io.Text;

public class AminoacidMenores extends NodeComputation {

	@Override
	public void compute(PNode pNode, Iterable<Text> messages) {
		if(pNode.getNodeLabel().equals("het")){
			if(pNode.getNodePropertyValue("symbol").equals("FE2"))
			{
				for (PEdge pedge : pNode.getPEdges()) {
					if(pedge.getEdgeLabel().equals("distanciaAminoHet") && Float.parseFloat(pedge.getEdgePropertyValue("distance")) <= 3.5)
					{
						sendMessage(pedge.getTargetVertexId(),new Text("esMenor"));
					}
				}
			}
		}
	    for (Text message : messages) {
	        pNode.setNodePropertyValue(message.toString(), "Si");
	      } 
		
		pNode.voteToHalt();
	}

}
