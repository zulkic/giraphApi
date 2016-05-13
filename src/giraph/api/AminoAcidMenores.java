package giraph.api;

import org.apache.hadoop.io.Text;
import org.apache.zookeeper.server.quorum.Vote;

public class AminoAcidMenores extends NodeComputation {

	@Override
	public void compute(PNode pNode, Iterable<Text> messages) {
		if(pNode.getNodeLabel().equals("aminoacid")){
			for (PEdge pedge : pNode.getPEdges()) {
				if(pedge.getTargetVertexId().toString().equals("1E08_A_10"))
				{
					Float dist = Float.parseFloat(pedge.getEdgePropertyValue("distance"));
					if(dist <= 3.5)
					{
						pNode.setNodePropertyValue("EsMenor", "Si");
					}
				}
			}
		}
		pNode.voteToHalt();
	}

}
