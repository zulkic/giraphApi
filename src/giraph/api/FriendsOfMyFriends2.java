package giraph.api;

import org.apache.hadoop.io.Text;

public class FriendsOfMyFriends2 extends NodeComputation{

	@Override
	public void compute(PNode pNode, Iterable<Text> messages) {
		//consulto si el nodo e
		if(pNode.getNodeLabel().equals("people")){
			if(getSuperstep()==0){
				pNode.setNodePropertyValue("friendsOfMyFriends","");

				//enviar a todos los friends
				for (PEdge pedge : pNode.getPEdges()) {
					if(pedge.getEdgeLabel().equals("friend")){
						String lista = pNode.getNodePropertyValue("friendsOfMyFriends") + "," + pedge.getTargetVertexId().toString();
						if(pNode.getNodePropertyValue("friendsOfMyFriends").equals("") || pNode.getNodePropertyValue("friendsOfMyFriends").equals("ERROR"))
							lista = pedge.getTargetVertexId().toString();
						pNode.setNodePropertyValue("friendsOfMyFriends", lista);
						sendMessageToAllEdges(pNode.getNode(), pedge.getTargetVertexId());
					}
				}
			}
			//recibir mensaje
			//si es 2, comparar con los vecinos y guardar
			//si es uno envíar a todos los nodos menos al del mismo id
			
		    for (Text message : messages) {
		    	if(getSuperstep() < 2)
		    	{
			    	boolean noEsta = true;
					for (PEdge pedge : pNode.getPEdges()) {
						if(message.toString().equals(pNode.getId().toString()) || message.toString().equals(pedge.getTargetVertexId().toString()))
							noEsta=false;
					}
					if(noEsta)
					{
						String[] list= pNode.getNodePropertyValue("friendsOfMyFriends").split(",");
						for (String friend : list) {
							if(!friend.equals("") && !friend.equals("ERROR")){
								if(friend.equals(message.toString()))
									noEsta=false;		
							}
						}
						if(noEsta)
						{
							//envío y guardo
							String lista = pNode.getNodePropertyValue("friendsOfMyFriends") + "," + message.toString();
							pNode.setNodePropertyValue("friendsOfMyFriends", lista);
							sendMessageToAllEdges(pNode.getNode(), message);
						}
					}
		    	}
			}
		}
		pNode.voteToHalt();
	}
	

}
