package giraph.api;

import java.util.ArrayList;

import org.apache.hadoop.io.Text;

public class MaxLikes extends NodeComputation {


@Override
public void compute(PNode pNode, Iterable<Text> messages) {
	if(pNode.getNodeLabel().equals("people"))
	{
	    boolean changed = false;
		ArrayList<Text> friends = new ArrayList<Text>();

	    for (Text message : messages) {
	      if (Integer.parseInt(pNode.getNodePropertyValue("maxLikes")) < Integer.parseInt(message.toString())) {
	        pNode.setNodePropertyValue("maxLikes", message.toString());
	        changed = true;
	      }
	    }
	    if (getSuperstep() == 0 || changed) {
			int likes =0;
			for(PEdge pedge : pNode.getPEdges())		
			{
				if(pedge.getEdgeLabel().equals("friend"))
					friends.add(new Text(pedge.getTargetVertexId()));
				else
					likes++;
			}
			if(getSuperstep()==0)
				pNode.setNodePropertyValue("maxLikes",Integer.toString(likes));
	      sendMessageToMultipleEdges(friends.iterator(), new Text(pNode.getNodePropertyValue("maxLikes")));
	    }
    
	}
    pNode.voteToHalt();
}

}
