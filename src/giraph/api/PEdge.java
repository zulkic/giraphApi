package giraph.api;


import org.apache.giraph.edge.Edge;
import org.apache.hadoop.io.Text;
import org.json.JSONException;
import org.json.JSONObject;

public class PEdge {
	JSONObject jsonObject;
	Edge<Text,Text> edge;
	private Text targetId;

	public PEdge(Edge<Text,Text> edge){
		try {
			this.edge = edge;
			this.jsonObject = new JSONObject(this.edge.getValue().toString());
			this.targetId = new Text(edge.getTargetVertexId());
			//decoder();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	/*private Text encoder(){
		return new Text(jsonObject.toString());
	}*/
	
	/**
	 * El método retorna el valor de una propiedad contenida en la arista
	 * @param key Es la llave de la propiedad
	 * @return El valor de la propiedad
	 */
	public String getEdgePropertyValue(String key) {
		try {
			return jsonObject.getString(key);
		} catch (JSONException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
	
	/**
	 * Retorna la etiqueta de la arista
	 * @return Un string con el valor de la etiqueta
	 */
	public String getEdgeLabel(){
		try {
			return jsonObject.getString("_label");
		} catch (JSONException e) {
			e.printStackTrace();
			return "ERROR";
		}
	}
	
	/**
	 * Retorna el id del nodo vecino
	 * @return El id en tipo text del nodo vecino
	 */
	public Text getTargetVertexId(){
		return this.targetId;
	}
	
	public Edge<Text, Text> getEdge(){
		return this.edge;
	}
	
}
