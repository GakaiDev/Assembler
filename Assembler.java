import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class Assembler {

    private static String toBinary(int n){
        String binary = Integer.toBinaryString(n);
        return String.format("%16s", binary).replace(" ", "0");
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Uso: java Assembler <arquivo.asm>");
            return;
        }

        File inputFile = new File(args[0]);
        String outputFileName = inputFile.getAbsolutePath().replace(".asm", ".hack");

        System.out.println("iniciando a primeira passagem");
        SymbolTable symbolTable = new SymbolTable();
        int romAddress = 0;

        Parser firstParser = new Parser(inputFile);

        while (firstParser.hasMoreLines()) {
            firstParser.advance();
            InstructionType type = firstParser.instructionType();

            if (type == InstructionType.L_INSTRUCTION){
                String symbol = firstParser.symbol();
                symbolTable.addEntry(symbol, romAddress);
            }else{
                romAddress++;
            }

        }

        System.out.println("passagem concluida");
        
        System.out.println("iniciando a segunda passagem");

        Parser secondParser = new Parser(inputFile);
        Code code = new Code();
        int nextRamAddress = 16;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
            while (secondParser.hasMoreLines()) {
                secondParser.advance();
                InstructionType type = secondParser.instructionType();
                String binaryLine = null;

                if (type == InstructionType.A_INSTRUCTION) {
                    String symbol = secondParser.symbol();
                    int address;

                    if (symbolTable.contains(symbol)) {
                        address = symbolTable.getAddress(symbol);
                    } else {
                        try {
                            address = Integer.parseInt(symbol);
                        } catch (NumberFormatException e) {
                            address = nextRamAddress;
                            symbolTable.addEntry(symbol, address);
                            nextRamAddress++;
                        }
                    }
                    binaryLine = toBinary(address);

                } else if (type == InstructionType.C_INSTRUCTION) {
                    String comp = code.comp(secondParser.comp());
                    String dest = code.dest(secondParser.dest());
                    String jump = code.jump(secondParser.jump());
                    binaryLine = "111" + comp + dest + jump;
                } else {
                    continue; 
                }
                writer.write(binaryLine + "\n");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("arquivo " + outputFileName + " gerado com sucesso!");

    }

}