package giraph.api;

import java.util.ArrayList;

import org.apache.hadoop.io.Text;

public class LikeFriends extends NodeComputation {

	@Override
	public void compute(PNode pNode, Iterable<Text> messages) {
		if(pNode.getNodeLabel().equals("people")){
			ArrayList<Text> friends = new ArrayList<Text>();
			int likes =0;
			for(PEdge pedge : pNode.getPEdges())		
			{
				if(pedge.getEdgeLabel().equals("friend"))
					friends.add(new Text(pedge.getTargetVertexId()));
				else
					likes++;
			}
			pNode.setNodePropertyValue("myLikes", Integer.toString(likes));
			
		    for (Text message : messages) {
		    	int suma = Integer.parseInt(pNode.getNodePropertyValue("friendsLikes").toString()) + Integer.parseInt(message.toString());
		    	pNode.setNodePropertyValue("friendsLikes", Integer.toString(suma));
		    }
		    
		    if(getSuperstep()==0)
		    {
		    	pNode.setNodePropertyValue("friendsLikes","0");
		    	sendMessageToMultipleEdges(friends.iterator(), new Text(Integer.toString(likes)));
		    }
		}
		pNode.voteToHalt();
	}

}
