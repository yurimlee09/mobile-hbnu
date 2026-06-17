import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static final PokedexService service = new PokedexService();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        initSampleData();

        while (true) {
            System.out.println("\n==========  포켓몬 도감 관리 시스템 ==========");
            System.out.println("1. 포켓몬 추가          2. 포켓몬 삭제");
            System.out.println("3. 전체 도감 조회       4. 이름으로 포켓몬 검색");
            System.out.println("5. 타입별 포켓몬 출력    6. 포획 여부 설정");
            System.out.println("7. 세대별 포켓몬 출력    8. 최강 포켓몬 TOP 6 (명예의 전당)");
            System.out.println("9. 포획한 포켓몬만 보기  10. 타입별 평균 전투력 통계");
            System.out.println("11. [보너스] 도감 달성률  0. 시스템 종료");
            System.out.print("▶ 메뉴를 선택하세요: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> addPokemon();
                case 2 -> deletePokemon();
                case 3 -> printList(service.getAllPokemons(), "전체 포켓몬 목록");
                case 4 -> searchByName();
                case 5 -> printByType();
                case 6 -> updateCaught();
                case 7 -> printByGeneration();
                case 8 -> printList(service.getTop6Pokemons(), " 전투력 상위 TOP 6 포켓몬 ");
                case 9 -> printList(service.getCaughtPokemons(), "내가 포획한 포켓몬 목록");
                case 10 -> printTypeAverage();
                case 11 -> printCompletionRate();
                case 0 -> {
                    System.out.println("정상적으로 종료되었습니다. 도감이 안전하게 저장되었습니다.");
                    return;
                }
                default -> System.out.println(" 잘못된 선택입니다. 다시 입력해주세요.");
            }
        }
    }

    private static void initSampleData() {
        if (service.getAllPokemons().isEmpty()) {
            service.addPokemon(new Pokemon(1, "이상해씨", "풀", 1, 318, 1, true));
            service.addPokemon(new Pokemon(4, "파이리", "불꽃", 1, 309, 1, true));
            service.addPokemon(new Pokemon(6, "리자몽", "불꽃", 1, 534, 3, false));
            service.addPokemon(new Pokemon(7, "꼬부기", "물", 1, 314, 1, false));
            service.addPokemon(new Pokemon(25, "피카츄", "전기", 1, 320, 2, true));
            service.addPokemon(new Pokemon(152, "치코리타", "풀", 2, 318, 1, false));
            service.addPokemon(new Pokemon(248, "마기라스", "바위", 2, 600, 3, true));
        }
    }

    private static void addPokemon() {
        System.out.print("도감 번호(ID) 입력: "); int id = scanner.nextInt(); scanner.nextLine();
        System.out.print("이름 입력: "); String name = scanner.nextLine();
        System.out.print("타입 입력 (ex. 불꽃/물/풀): "); String type = scanner.nextLine();
        System.out.print("세대 입력 (정수형태 숫자만 ex. 1): "); int gen = scanner.nextInt();
        System.out.print("전투력(CP) 입력: "); int cp = scanner.nextInt();
        System.out.print("진화 단계 입력 (1~3): "); int stage = scanner.nextInt();
        System.out.print("포획 여부 (true/false): "); boolean caught = scanner.nextBoolean();

        Pokemon p = new Pokemon(id, name, type, gen, cp, stage, caught);
        if (service.addPokemon(p)) {
            System.out.println( name + "이(가) 도감에 추가되었습니다.");
        } else {
            System.out.println(" 이미 존재하는 도감 번호입니다.");
        }
    }

    private static void deletePokemon() {
        System.out.print("삭제할 포켓몬의 도감 번호 입력: ");
        int id = scanner.nextInt();
        if (service.deletePokemon(id)) {
            System.out.println("🗑성공적으로 삭제되었습니다.");
        } else {
            System.out.println("해당 번호의 포켓몬을 찾을 수 없습니다.");
        }
    }

    private static void searchByName() {
        System.out.print("검색할 포켓몬 이름(일부 가능): ");
        String name = scanner.nextLine();
        printList(service.searchByName(name), "'" + name + "' 검색 결과");
    }

    private static void printByType() {
        System.out.print("조회할 타입 입력 (ex. 불꽃): ");
        String type = scanner.nextLine();
        printList(service.getPokemonsByType(type), "[" + type + "] 타입 포켓몬 목록");
    }

    private static void updateCaught() {
        System.out.print("포획 상태를 변경할 도감 번호 입력: "); int id = scanner.nextInt();
        System.out.print("포획 하셨나요? (true/false): "); boolean caught = scanner.nextBoolean();
        if (service.updateCaughtStatus(id, caught)) {
            System.out.println("🔄 포획 상태가 성공적으로 변경되었습니다.");
        } else {
            System.out.println("해당 번호의 포켓몬이 존재하지 않습니다.");
        }
    }

    private static void printByGeneration() {
        System.out.print("조회할 세대 입력 (숫자): ");
        int gen = scanner.nextInt();
        printList(service.getPokemonsByGeneration(gen), "[" + gen + "세대] 포켓몬 목록");
    }

    private static void printTypeAverage() {
        System.out.println("\n --- 타입별 평균 전투력(CP) ---");
        Map<String, Double> avgMap = service.getTypeAverageCp();
        if (avgMap.isEmpty()) {
            System.out.println("데이터가 없습니다.");
            return;
        }
        avgMap.forEach((type, avg) -> System.out.printf("• [%s 타입]: %.2f CP\n", type, avg));
    }

    private static void printCompletionRate() {
        System.out.printf("\n현재 도감 전체 달성률: %.2f%%\n", service.getCompletionRate());
    }

    private static void printList(List<Pokemon> list, String title) {
        System.out.println("\n --- " + title + " ---");
        if (list.isEmpty()) {
            System.out.println("조회된 포켓몬이 없습니다.");
        } else {
            list.forEach(System.out::println);
        }
    }
}