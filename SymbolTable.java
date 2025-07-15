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

        public void addEntry(String symbol, int address){
            table.put(symbol, address);
        }

        public boolean contains(String symbol){
            return table.containsKey(symbol);
        }

        public int getAddress(String symbol){
            return table.get(symbol);
        }

    }

