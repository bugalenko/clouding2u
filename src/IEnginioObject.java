import com.google.gson.JsonObject;


public interface IEnginioObject {
	JsonObject toJson() throws EnginioObjectIOException;
	 Object fromJson(JsonObject src) throws EnginioObjectIOException;
	 
}
