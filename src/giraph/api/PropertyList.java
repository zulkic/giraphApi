package giraph.api;

import org.json.JSONException;
import org.json.JSONObject;

public class PropertyList {
	private JSONObject json = new JSONObject();
	
	public void addProperty(String key, String value)
	{
		try {
			json.put(key, value);
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}
	
	public String getJSONasString(){
		return json.toString();
	}

}
