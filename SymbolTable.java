import java.util.HashMap;
import java.util.Map;




public class SymbolTable {
    private Map<String, Integer> table;

    public SymbolTable(){
        table = new HashMap<>();

        for (int i = 0; i<16; i++){
            table.put("R" + i, i);      //preenchendo os registradores
        }

        table.put("SP", 0);
        table.put("LCL", 1);
        table.put("ARG", 2);    // simbolos pré-definidos
        table.put("THIS", 3);
        table.put("THAT", 4);
        table.put("SCREEN", 16384);
        table.put("KDB", 24576); 
        }   

        public void addEntry(String symbol, int address){  // Adiciona uma nova entrada à tabela de símbolos
            table.put(symbol, address);
        }

        public boolean contains(String symbol){  // Verifica se a tabela contém um símbolo
            return table.containsKey(symbol);
        }

        public int getAddress(String symbol){  // Retorna o endereço associado a um símbolo
            return table.get(symbol);
        }

    

    public void printTable() {
        System.out.println("---- Symbol Table ----");
        for (Map.Entry<String, Integer> entry : table.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());   // so mostra o conteudo da tabela para teste
        }
        System.out.println("----------------------");
    }
}