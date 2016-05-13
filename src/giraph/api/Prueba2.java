package giraph.api;

import org.apache.hadoop.io.Text;

public class Prueba2 extends NodeComputation{

	@Override
	public void compute(PNode pNode, Iterable<Text> messages) {
		if(pNode.getNodeLabel().equals("people")){
			int friends = 0;
			int likes =0;
			for(PEdge pedge : pNode.getPEdges())		
			{
				if(pedge.getEdgeLabel().equals("friend"))
					friends++;
				else
					likes++;
			}
			pNode.setNodePropertyValue("friends", Integer.toString(friends));
			pNode.setNodePropertyValue("likes", Integer.toString(likes));
		}
		pNode.voteToHalt();
	}

}
