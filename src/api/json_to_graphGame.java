package api;

import com.google.gson.*;
import gameClient.util.Point3D;

import java.lang.reflect.Type;


public class json_to_graphGame implements JsonDeserializer<directed_weighted_graph> {
    @Override
    public directed_weighted_graph deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        JsonObject jsongraph =jsonElement.getAsJsonObject();
        directed_weighted_graph g=new DWGraph_DS();
        JsonArray nodesjson =jsongraph.get("Nodes").getAsJsonArray();
        JsonArray edgesjson =jsongraph.get("Edges").getAsJsonArray();
        for(int i=0;i<nodesjson.size();i++)
        {
            JsonObject nodej=nodesjson.get(i).getAsJsonObject();
            int key = nodej.get("id").getAsInt();

            String pos = nodej.getAsJsonObject().get("pos").getAsString();
           String[] p =pos.split(",");
           double x=Double.parseDouble(p[0]);
           double y=Double.parseDouble(p[1]);
           double z=Double.parseDouble(p[2]);
            Point3D poi=new Point3D(x,y,z);
            node_data n=new DWGraph_DS.NodeData(key,poi);
            g.addNode(n);

        }
        for(int i=0;i<edgesjson.size();i++) {
            JsonObject edgej = edgesjson.get(i).getAsJsonObject();
            int src = edgej.getAsJsonObject().get("src").getAsInt();
            int dest = edgej.getAsJsonObject().get("dest").getAsInt();
            double w = edgej.getAsJsonObject().get("w").getAsDouble();
            g.connect(src, dest, w);
        }

        return g;
    }
}
