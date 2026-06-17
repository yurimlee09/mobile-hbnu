import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PokedexRepository {
    private Map<Integer, Pokemon> pokedexMap = new HashMap<>();
    private final String FILE_PATH = "pokedex.txt";

    public PokedexRepository() {
        loadFromFile();
    }

    public void addPokemon(Pokemon pokemon) {
        pokedexMap.put(pokemon.getId(), pokemon);
        saveToFile();
    }

    public boolean deletePokemon(int id) {
        if (pokedexMap.containsKey(id)) {
            pokedexMap.remove(id);
            saveToFile();
            return true;
        }
        return false;
    }

    public Pokemon findById(int id) {
        return pokedexMap.get(id);
    }

    public List<Pokemon> getAllPokemons() {
        return new ArrayList<>(pokedexMap.values());
    }

    public void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Pokemon p : pokedexMap.values()) {
                writer.write(p.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("❌ 파일 저장 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                Pokemon p = Pokemon.fromFileString(line);
                pokedexMap.put(p.getId(), p);
            }
        } catch (IOException e) {
            System.out.println("❌ 파일 로드 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
