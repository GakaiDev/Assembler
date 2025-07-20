import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;





public class Parser {

    private BufferedReader reader;
    String currentLine; 

    public Parser(File inputFile) {
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            currentLine = null; 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean hasMoreLines() { 
        try {
            return reader.ready();
        } catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }


    public void advance() {
        try {
            String nextLine;
            while ((nextLine = reader.readLine()) != null) {
                int commentIndex = nextLine.indexOf("//");
                if (commentIndex != -1) {
                    nextLine = nextLine.substring(0, commentIndex);
                }

                nextLine = nextLine.trim();

                if (!nextLine.isEmpty()) {
                    currentLine = nextLine;
                    return; 
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public InstructionType instructionType() { 
        if (currentLine.startsWith("@")) {
            return InstructionType.A_INSTRUCTION; // Instrução A
        } else if (currentLine.startsWith("(") && currentLine.endsWith(")")) {
            return InstructionType.L_INSTRUCTION; // Instrução L
        } else {
            return InstructionType.C_INSTRUCTION; // Instrução C
        }

     }

    public String symbol() {
        if (instructionType() == InstructionType.A_INSTRUCTION) {
            return currentLine.substring(1); // Remove o '@'
        } else if (instructionType() == InstructionType.L_INSTRUCTION) {
            return currentLine.substring(1, currentLine.length() - 1); // Remove os parênteses
        }
        return null; 

    }

    public String dest() {
        if (instructionType() == InstructionType.C_INSTRUCTION){
            if (currentLine.contains("=")){
                return currentLine.split("=")[0];
            }
        }
        return null; // se nao possui "=" não é dest
    }

    public String comp() {
        if (instructionType() == InstructionType.C_INSTRUCTION){
            String temp = currentLine;
            if (temp.contains("=")) {
                temp = temp.split("=")[1]; // Remove a parte dest
            }
            if (temp.contains(";")) {
                temp = temp.split(";")[0]; // Remove a parte jump
            }
            return temp;
        }
        return null; // se nao possui "=" ou ";" não é comp
    }

    public String jump() {
        if (instructionType() == InstructionType.C_INSTRUCTION){
            if (currentLine.contains(";")) {
                return currentLine.split(";")[1]; // Retorna a parte jump
            }
        }
        return null; // se nao possui ";" não é jump
    }
}

enum InstructionType {
    A_INSTRUCTION,
    C_INSTRUCTION,
    L_INSTRUCTION
}