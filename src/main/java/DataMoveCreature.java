import exceptions.WorldException;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

public class DataMoveCreature {
    private final Map<String, Map<String, Integer>> costMoveForCreature = new HashMap<>();
    private final List<String> listCells = new ArrayList<>();
    private final List<String> listCreatures = new ArrayList<>();

    public DataMoveCreature(String pathToFile) {
        String jsonString = null;

        try (InputStream in = getClass().getResourceAsStream(pathToFile);
             BufferedReader reader = new BufferedReader(new InputStreamReader(Objects.requireNonNull(in)))) {
            Optional<String> optional = reader.lines().reduce((s1, s2) -> s1 + s2);
            if(optional.isPresent()){
                jsonString = optional.get();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONObject object = new JSONObject(Objects.requireNonNull(jsonString));

        setCells(object);

        setCreatures(object);
    }

    private void setCreatures(JSONObject object) {
        JSONArray creatures = object.getJSONArray("creatures");

        for(int i = 0; i < creatures.length(); i++){
            JSONObject creature = creatures.getJSONObject(i);

            String name = creature.getString("name");
            listCreatures.add(name);

            JSONArray costMove = creature.getJSONArray("costMove");
            Map<String, Integer> costMap = new HashMap<>();
            for(int j = 0; j < costMove.length(); j++){
                JSONObject costEntity = costMove.getJSONObject(j);

                String nameCost = costEntity.getString("name");
                if(!listCells.contains(nameCost))throw new WorldException("worldCell don't contain this name of Cell: " + nameCost);

                int cost = costEntity.getInt("cost");

                costMap.put(nameCost, cost);
            }
            costMoveForCreature.put(name, costMap);
        }
    }

    private void setCells(JSONObject object) {
        JSONArray worldCell = object.getJSONArray("worldCell");
        for(int i = 0; i < worldCell.length(); i++){
            String name = worldCell.getString(i);
            if(name.length()!=1) throw new WorldException("Cells length greater or less then 1: " + name);

            listCells.add(name);
        }
    }

    public int getCostMoveFromCell(String cell, String creature) {
        if(!listCells.contains(cell))throw new WorldException("Not exists this cell: " + cell);
        if(!listCreatures.contains(creature))throw new WorldException("Not exists this creature: " + creature);

        return costMoveForCreature.get(creature).get(cell);
    }
}
