import java.io.Serializable;

public class Pokemon implements Serializable {
    private int id;
    private String name;
    private String type;
    private int generation;
    private int cp;
    private int evolutionStage;
    private boolean isCaught;

    public Pokemon(int id, String name, String type, int generation, int cp, int evolutionStage, boolean isCaught) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.generation = generation;
        this.cp = cp;
        this.evolutionStage = evolutionStage;
        this.isCaught = isCaught;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public int getGeneration() { return generation; }
    public void setGeneration(int generation) { this.generation = generation; }
    public int getCp() { return cp; }
    public void setCp(int cp) { this.cp = cp; }
    public int getEvolutionStage() { return evolutionStage; }
    public void setEvolutionStage(int evolutionStage) { this.evolutionStage = evolutionStage; }
    public boolean isCaught() { return isCaught; }
    public void setCaught(boolean caught) { isCaught = caught; }

    public String toFileString() {
        return id + "," + name + "," + type + "," + generation + "," + cp + "," + evolutionStage + "," + isCaught;
    }

    public static Pokemon fromFileString(String str) {
        String[] parts = str.split(",");
        int id = Integer.parseInt(parts[0]);
        String name = parts[1];
        String type = parts[2];
        int generation = Integer.parseInt(parts[3]);
        int cp = Integer.parseInt(parts[4]);
        int evolutionStage = Integer.parseInt(parts[5]);
        boolean isCaught = Boolean.parseBoolean(parts[6]);
        return new Pokemon(id, name, type, generation, cp, evolutionStage, isCaught);
    }

    @Override
    public String toString() {
        return String.format("[No.%03d] %-8s | 타입: %-3s | %d세대 | CP: %-4d | 진화:%d단계 | 포획: %s",
                id, name, type, generation, cp, evolutionStage, isCaught ? "O" : "X");
    }
}