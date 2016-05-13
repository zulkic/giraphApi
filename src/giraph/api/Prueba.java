package giraph.api;

import org.apache.giraph.conf.StrConfOption;
import org.apache.hadoop.io.Text;

public class Prueba extends NodeComputation {
	
	public static final StrConfOption SOURCE_ID = new StrConfOption("Prueba.sourceId", "1", "description");


	private boolean isSource(PNode pNode) {
		return SOURCE_ID.getDefaultValueStr().equals(pNode.getId().toString());
	}
	
	@Override
	public void compute(PNode pNode, Iterable<Text> messages) {
		
    if (getSuperstep() == 0) {
    	pNode.setNodePropertyValue("distance", String.valueOf(Double.MAX_VALUE));
    }
    double minDist = isSource(pNode) ? 0d : Double.MAX_VALUE;
    
    for (Text message : messages) {
        minDist = Math.min(minDist, Double.parseDouble(message.toString()));
      } 

    if (minDist < Double.parseDouble(pNode.getNodePropertyValue("distance"))) {
    	pNode.setNodePropertyValue("distance", String.valueOf(minDist));

      for (PEdge edge : pNode.getPEdges()) {
        double distance = minDist + Double.parseDouble(edge.getEdgePropertyValue("distance").toString());
        sendMessage(edge.getTargetVertexId(), new Text(Double.toString(distance)));
      }
    }
    pNode.voteToHalt();
	}


}
