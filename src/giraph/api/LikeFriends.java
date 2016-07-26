package giraph.api;

import java.util.ArrayList;

import org.apache.hadoop.io.Text;

public class LikeFriends extends NodeComputation {

	@Override
	public void compute(PNode pNode, Iterable<Text> messages) {
		//Filtro para actuar unicamente sobre nodos people
		if(pNode.getNodeLabel().equals("people")){
			//Creo un array de friends e inicializo los likes en 0
			ArrayList<Text> friends = new ArrayList<Text>();
			int likes =0;
			
			//para todas mis aristas, verifico si es de tipo friend y lo agrego al array
			// si no, la relacion es de tipo like y aumento el contador.
			for(PEdge pedge : pNode.getPEdges())		
			{
				if(pedge.getEdgeLabel().equals("friend"))
					friends.add(new Text(pedge.getTargetVertexId()));
				else
					likes++;
			}
			//Agrego a una propiedad del nodo la cantidad de likes que he realizado a paginas
			pNode.setNodePropertyValue("myLikes", Integer.toString(likes));
			
			//Si recibo mensajes, realizo la suma de likes de todos mis amigos y la guardo en una propiedad
			int suma=0;
		    for (Text message : messages) {
		    	suma += Integer.parseInt(message.toString());
		    }
		    
	    	pNode.setNodePropertyValue("friendsLikes", Integer.toString(suma));
		    //si mi superStep es 0 inicializo mi propiedad friendLikes a 0 y
		    // envío mensaje a todos mis vecinos del tipo friend con mi cantidad de likes
		    if(getSuperstep()==0)
		    {
		    	sendMessageToMultipleEdges(friends.iterator(), new Text(Integer.toString(likes)));
		    }
		}
		pNode.voteToHalt();
	}

}
