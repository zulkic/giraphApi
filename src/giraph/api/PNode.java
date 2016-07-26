package giraph.api;



import java.util.ArrayList;
import java.util.List;

import org.apache.giraph.edge.DefaultEdge;
import org.apache.giraph.edge.Edge;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.Text;
import org.json.JSONException;
import org.json.JSONObject;




/**
 * Clase que sirve para interactuar con los valores de property graph adecuadamente.
 * @author Francisco Moya González
 *
 */
public class PNode {
	
	JSONObject jsonObject;
	Vertex<Text,Text,Text> vertex;
	ArrayList<PEdge> pEdges = new ArrayList<PEdge>();
	ArrayList<Edge<Text,Text>> edges = new ArrayList<Edge<Text,Text>>();
	
	/**
	 * El constructor recibe el parametro codificado en json y lo decodifica,
	 * obteniendo todas las propiedades del nodo.
	 * @param json Se entrega como parametro el json que contiene el property graph.
	 */
	public PNode(Vertex<Text,Text,Text> vertex){
		try {
			this.vertex = vertex;
			this.jsonObject = new JSONObject(this.vertex.getValue().toString());


			/*for (int i = 0; i < edges.size(); i++) {
				PEdge pEdge = new PEdge(edges.get(i));
				pEdges.add(pEdge);
			}*/

			
			
			
			for (Edge<Text, Text> edge : this.vertex.getEdges()) {
				PEdge pEdge = new PEdge(edge);
				pEdges.add(pEdge);
			}
			//decoder();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}


	
	/**
	 * Vuelve a codificar el valor del nodo para pasarlo a vertex.setvalue().
	 * @return Un tipo Text listo para pasarlo a vertex.setvalue(), sólo si se ha modificado una propiedad.
	 */
	private Text encoder(){
		return new Text(jsonObject.toString());
	}
	/**
	 * Obtiene el valor de una propiedad en el vertice.
	 * @param key La propiedad a la que se quiere obtener el valor.
	 * @return El valor de la propiedad consultada. Si la propiedad no existe indica
	 * una excepción y retorna ERROR.
	 */
	public String getNodePropertyValue(String key) {
		try {
			if(!key.equals("_label"))
				return jsonObject.getString(key);
			else
				return "ERROR";
		} catch (JSONException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
	/**
	 *Modifica el valor de una propiedad en el vertice, si la propiedad no existe la agrega.
	 * @param key El nombre de la propiedad a modificar.
	 * @param value El valor necesario para modificar la propiedad.
	 * @return Verdadero si se realiza correctamente, falso en caso contrario.
	 */
	public Boolean setNodePropertyValue(String key, String value)
	{
		try {
			if(!key.equals("_label")){
				jsonObject.put(key,value);
				vertex.setValue(this.encoder());
				return true;
			}
			else{
				return false;
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}
	/**
	 * El metodo tiene por objetivo retornar el valor de una etiqueta
	 * @return El valor de la etiqueta, en caso contrario retorna "ERROR"
	 */
	public String getNodeLabel(){
		try {
			return jsonObject.getString("_label");
		}catch (JSONException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
	
	/**
	 * Es utilizado para modificar el valor de una etiqueta
	 * @param value Es el valor a modificar en la etiqueta
	 * @return Verdadero en caso de realizarse el cambio, falso en cualquier otro caso.
	 */
	public Boolean setNodeLabel(String value)
	{
		try {
			jsonObject.put("_label", value);
			vertex.setValue(this.encoder());
			return true;
		} catch (JSONException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * Entrega una lista con los vertices vecinos.
	 * @return Una lista de ids de nodos vecinos.
	 */
	public Iterable<String> getNeighbors() {
		List<String> edges = new ArrayList<String>();
		for (PEdge edge : pEdges) {
			edges.add(edge.getTargetVertexId().toString());
		}
	    return edges;
	}
	
	/**
	 * Entrega una lista con los vertices vecinos y que contienen la misma etiqueta de la arista.
	 * @param edgeLabel Es la etiqueta de la arista que se debe cumplir.
	 * @return Una lista de ids de vertices vecinos que contienen la etiqueta de la arista.
	 */
	public Iterable<String> getNeighborsByEdgeLabel(String edgeLabel) {
		List<String> edges = new ArrayList<String>();
		for (PEdge edge : this.getPEdges()) {
			if(edge.getEdgeLabel().equals(edgeLabel))
				edges.add(edge.getTargetVertexId().toString());
		}
	    return edges;
	}
	
	/*private Iterable<Edge<Text, Text>> getEdgess(){
		return vertex.getEdges();
	}*/
	
	public Iterable<PEdge> getPEdges(){
		return pEdges;
	}
	/**
	 * Devuelve la cantidad de aristas que contiene el nodo
	 * @return un entero que representa la cantidad de aristas
	 */
	public int getNumPEdges(){
		return vertex.getNumEdges();
	}
	
	/**
	 * Agrega una relación artista vertice al nodo actual.
	 * @param nodeId Hacia el vertice que se dirige
	 * @param edgeValue El valor de la arista.
	 */
	public void addPEdge(String nodeId, PropertyList propertyList){
		DefaultEdge<Text, Text> edge = new DefaultEdge<Text,Text>();
		edge.setTargetVertexId(new Text(nodeId));
		edge.setValue(new Text(propertyList.getJSONasString()));
		vertex.addEdge(edge);
		PEdge pEdge = new PEdge(edge);
		pEdges.add(pEdge);
	}
	
	
	/**
	 * Elimina todas las relaciones que existen entre el nodo actual y el nodo final. 
	 * @param vertexId El vertice hacia el que se dirige la o las aristas.
	 */
	public void removePEdges(String vertexId){
		for (PEdge pedge : pEdges) {
			if(pedge.getTargetVertexId().toString().equals(vertexId)){
				pEdges.remove(pedge);				
			}
		}
		vertex.removeEdges(new Text(vertexId));		
	}

	/**
	 * Apaga el vertice
	 */
	public void voteToHalt(){
		vertex.voteToHalt();		
	}
	
	/**
	 * Obtiene el id del vertice actual
	 * @return El id del vertice actual
	 */
	public Text getId(){
		return vertex.getId();
	}
	
	public Vertex<Text, Text, Text> getNode(){
		return this.vertex;
	}

}
