import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PokedexService {
    private final PokedexRepository repository = new PokedexRepository();

    public boolean addPokemon(Pokemon pokemon) {
        if (repository.findById(pokemon.getId()) != null) {
            return false;
        }
        repository.addPokemon(pokemon);
        return true;
    }

    public boolean deletePokemon(int id) {
        return repository.deletePokemon(id);
    }

    public List<Pokemon> getAllPokemons() {
        List<Pokemon> list = repository.getAllPokemons();
        list.sort(Comparator.comparingInt(Pokemon::getId));
        return list;
    }

    public List<Pokemon> searchByName(String name) {
        List<Pokemon> result = new ArrayList<>();
        for (Pokemon p : repository.getAllPokemons()) {
            if (p.getName().contains(name)) {
                result.add(p);
            }
        }
        return result;
    }

    public List<Pokemon> getPokemonsByType(String type) {
        return repository.getAllPokemons().stream()
                .filter(p -> p.getType().equals(type))
                .collect(Collectors.toList());
    }

    public boolean updateCaughtStatus(int id, boolean isCaught) {
        Pokemon p = repository.findById(id);
        if (p != null) {
            p.setCaught(isCaught);
            repository.saveToFile();
            return true;
        }
        return false;
    }

    public List<Pokemon> getPokemonsByGeneration(int gen) {
        return repository.getAllPokemons().stream()
                .filter(p -> p.getGeneration() == gen)
                .collect(Collectors.toList());
    }

    public List<Pokemon> getTop6Pokemons() {
        return repository.getAllPokemons().stream()
                .sorted(Comparator.comparingInt(Pokemon::getCp).reversed())
                .limit(6)
                .collect(Collectors.toList());
    }

    public List<Pokemon> getCaughtPokemons() {
        return repository.getAllPokemons().stream()
                .filter(Pokemon::isCaught)
                .collect(Collectors.toList());
    }

    public Map<String, Double> getTypeAverageCp() {
        return repository.getAllPokemons().stream()
                .collect(Collectors.groupingBy(
                        Pokemon::getType,
                        Collectors.averagingDouble(Pokemon::getCp)
                ));
    }

    public double getCompletionRate() {
        List<Pokemon> all = repository.getAllPokemons();
        if (all.isEmpty()) return 0.0;
        long caughtCount = all.stream().filter(Pokemon::isCaught).count();
        return ((double) caughtCount / all.size()) * 100;
    }
}