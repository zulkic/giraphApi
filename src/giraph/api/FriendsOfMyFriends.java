package giraph.api;

import org.apache.hadoop.io.Text;

public class FriendsOfMyFriends extends NodeComputation{

	@Override
	public void compute(PNode pNode, Iterable<Text> messages) {
		if(pNode.getNodeLabel().equals("people")){
			if(getSuperstep()==0){
				pNode.setNodePropertyValue("friendsOfMyFriends","");

				//enviar a todos los friends
				for (PEdge pedge : pNode.getPEdges()) {
					if(pedge.getEdgeLabel().equals("friend")){
						String send = "1:" + pedge.getTargetVertexId().toString();
						sendMessageToAllEdges(pNode.getNode(), new Text(send));
					}
				}
			}
			
			//recibir mensaje
			//si es 2, comparar con los vecinos y guardar
			//si es uno envíar a todos los nodos menos al del mismo id
			
		    for (Text message : messages) {
		    	boolean noEsta = true;
				String[] receive = message.toString().split(":");
				for (PEdge pedge : pNode.getPEdges()) {
					if(receive[1].equals(pNode.getId().toString()) || receive[1].equals(pedge.getTargetVertexId().toString()))
						noEsta=false;
					
				}
				if(noEsta)
				{
					String[] list= pNode.getNodePropertyValue("friendsOfMyFriends").split(",");
					for (String friend : list) {
						if(!friend.equals("") && !friend.equals("ERROR")){
							String[] aux = friend.split(":");
							if(aux[1].equals(receive[1]))
								noEsta=false;		
						}
					}
					if(noEsta)
					{
						//envio y guardo
						int suma= Integer.parseInt(receive[0])+1;
						String valor= Integer.toString(suma) + ":" +receive[1];
						String lista = pNode.getNodePropertyValue("friendsOfMyFriends") + "," + valor;
						pNode.setNodePropertyValue("friendsOfMyFriends", lista);
						sendMessageToAllEdges(pNode.getNode(), new Text(valor));
					}
				}
			}
		}
		pNode.voteToHalt();
	}
	

}
