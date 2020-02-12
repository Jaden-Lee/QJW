import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class interpreter {
    private static Scanner readInput;
    private static ArrayList<String> doc;
    private static String className = "input";
    private static final String INIT = "c:".concat(className);
    private static int line;
    
    public static void main(String[] args) throws Exception {
        initialize();
    }
    private static void initialize() throws Exception {
        line = 1;
        doc = new ArrayList();
        readInput = new Scanner(new File("input.qj"));
        checkClass(readInput.nextLine());
        checkMain(readInput.nextLine());
        while (readInput.hasNextLine()) {
            interpretLine(readInput.nextLine());
        }
    }

    private static void interpretLine(String str) throws Exception {
        // str = str.replaceAll("\\s+","");
        if (str.indexOf("(") == -1 || str.indexOf(")") == -1) throw specifyException("Missing one or two parenthesis, ( and/or ).");
        String cmd = str.substring(0,str.indexOf("(")).replaceAll("\\s+","");
        String in = str.substring(str.indexOf("("),str.indexOf(")")+1);
        if (cmd.equals("pln")) {
            if (in.indexOf("\"") != -1) {
                in = in.substring(in.indexOf("\"")+1);
                if (in.indexOf("\"") == -1) {
                    throw specifyException("Missing a matching quotation mark, \".");
                }
                in = in.substring(0,in.indexOf("\""));
                System.out.println(in);
            }
            else {
                String operator = locateOperator(in);
                if (operator.equals("z")) System.out.println(in);
                else {
                    in = in.replaceAll("\\s+","");
                    in = in.substring(1,in.length()-1);
                    int value = Integer.parseInt(in.substring(0,in.indexOf(operator)));
                    String after = in.substring(in.indexOf(operator)+1,in.length());
                    in = in.substring(in.indexOf(operator)+1,in.length());
                    while (! operator.equals("z")) {
                        if (operator.equals("+")) {
                            String temp = after;
                            if (after.indexOf("+") != -1) temp = after.substring(0, after.indexOf("+"));
                            if (after.indexOf("-") != -1) temp = after.substring(0, after.indexOf("-"));
                            if (after.indexOf("*") != -1) temp = after.substring(0, after.indexOf("*"));
                            if (after.indexOf("/") != -1) temp = after.substring(0, after.indexOf("/"));
                            value += Integer.parseInt(temp);
                        }
                        else if (operator.equals("-")) {
                            String temp = after;
                            if (after.indexOf("+") != -1) temp = after.substring(0, after.indexOf("+"));
                            if (after.indexOf("-") != -1) temp = after.substring(0, after.indexOf("-"));
                            if (after.indexOf("*") != -1) temp = after.substring(0, after.indexOf("*"));
                            if (after.indexOf("/") != -1) temp = after.substring(0, after.indexOf("/"));
                            value -= Integer.parseInt(temp);
                        }
                        else if (operator.equals("*")) {
                            String temp = after;
                            if (after.indexOf("+") != -1) temp = after.substring(0, after.indexOf("+"));
                            if (after.indexOf("-") != -1) temp = after.substring(0, after.indexOf("-"));
                            if (after.indexOf("*") != -1) temp = after.substring(0, after.indexOf("*"));
                            if (after.indexOf("/") != -1) temp = after.substring(0, after.indexOf("/"));
                            value *= Integer.parseInt(temp);
                        }
                        else if (operator.equals("/")) {
                            String temp = after;
                            if (after.indexOf("+") != -1) temp = after.substring(0, after.indexOf("+"));
                            if (after.indexOf("-") != -1) temp = after.substring(0, after.indexOf("-"));
                            if (after.indexOf("*") != -1) temp = after.substring(0, after.indexOf("*"));
                            if (after.indexOf("/") != -1) temp = after.substring(0, after.indexOf("/"));
                            value /= Integer.parseInt(temp);
                        }
                        operator = locateOperator(in);
                        in = in.substring(in.indexOf(operator)+1,in.length());
                        if (in.indexOf(operator) == -1) {
                            if (operator.equals("+")) value += Integer.parseInt(in);
                            else if (operator.equals("-")) value -= Integer.parseInt(in);
                            else if (operator.equals("*")) value *= Integer.parseInt(in);
                            else if (operator.equals("/")) value /= Integer.parseInt(in);
                            break;
                        }
                        after = in.substring(0,in.indexOf(operator));
                    }
                    System.out.println(value);
                }
            }
        }
        else if (cmd.equals("p")){
            if (in.indexOf("\"") != -1) {
                in = in.substring(in.indexOf("\"")+1);
                if (in.indexOf("\"") == -1) {
                    throw specifyException("Missing a matching quotation mark, \".");
                }
                in = in.substring(0,in.indexOf("\""));
                System.out.print(in);
            }
            else {
                String operator = locateOperator(in);
                if (operator.equals("z")) System.out.print(in);
                else {
                    in = in.replaceAll("\\s+","");
                    in = in.substring(1,in.length()-1);
                    int value = Integer.parseInt(in.substring(0,in.indexOf(operator)));
                    String after = in.substring(in.indexOf(operator)+1,in.length());
                    in = in.substring(in.indexOf(operator)+1,in.length());
                    while (! operator.equals("z")) {
                        if (operator.equals("+")) {
                            String temp = after;
                            if (after.indexOf("+") != -1) temp = after.substring(0, after.indexOf("+"));
                            if (after.indexOf("-") != -1) temp = after.substring(0, after.indexOf("-"));
                            if (after.indexOf("*") != -1) temp = after.substring(0, after.indexOf("*"));
                            if (after.indexOf("/") != -1) temp = after.substring(0, after.indexOf("/"));
                            value += Integer.parseInt(temp);
                        }
                        else if (operator.equals("-")) {
                            String temp = after;
                            if (after.indexOf("+") != -1) temp = after.substring(0, after.indexOf("+"));
                            if (after.indexOf("-") != -1) temp = after.substring(0, after.indexOf("-"));
                            if (after.indexOf("*") != -1) temp = after.substring(0, after.indexOf("*"));
                            if (after.indexOf("/") != -1) temp = after.substring(0, after.indexOf("/"));
                            value -= Integer.parseInt(temp);
                        }
                        else if (operator.equals("*")) {
                            String temp = after;
                            if (after.indexOf("+") != -1) temp = after.substring(0, after.indexOf("+"));
                            if (after.indexOf("-") != -1) temp = after.substring(0, after.indexOf("-"));
                            if (after.indexOf("*") != -1) temp = after.substring(0, after.indexOf("*"));
                            if (after.indexOf("/") != -1) temp = after.substring(0, after.indexOf("/"));
                            value *= Integer.parseInt(temp);
                        }
                        else if (operator.equals("/")) {
                            String temp = after;
                            if (after.indexOf("+") != -1) temp = after.substring(0, after.indexOf("+"));
                            if (after.indexOf("-") != -1) temp = after.substring(0, after.indexOf("-"));
                            if (after.indexOf("*") != -1) temp = after.substring(0, after.indexOf("*"));
                            if (after.indexOf("/") != -1) temp = after.substring(0, after.indexOf("/"));
                            value /= Integer.parseInt(temp);
                        }
                        operator = locateOperator(in);
                        in = in.substring(in.indexOf(operator)+1,in.length());
                        if (in.indexOf(operator) == -1) {
                            if (operator.equals("+")) value += Integer.parseInt(in);
                            else if (operator.equals("-")) value -= Integer.parseInt(in);
                            else if (operator.equals("*")) value *= Integer.parseInt(in);
                            else if (operator.equals("/")) value /= Integer.parseInt(in);
                            break;
                        }
                        after = in.substring(0,in.indexOf(operator));
                    }
                    System.out.print(value);
                }
            }
        }
    }

    private static String locateOperator(String input) {
        int pval = input.indexOf("+");
        int sval = input.indexOf("-");
        int mval = input.indexOf("*");
        int dval = input.indexOf("/");
        if (pval == -1) pval = Integer.MAX_VALUE;
        if (sval == -1) sval = Integer.MAX_VALUE;
        if (mval == -1) mval = Integer.MAX_VALUE;
        if (dval == -1) dval = Integer.MAX_VALUE;

        if (pval < sval && (pval < mval && pval < dval)) return "+";
        else if (sval < pval && (sval < mval && sval < dval)) return "-";
        else if (mval < pval && (mval < sval && mval < dval)) return "*";
        else if (dval < pval && (dval < sval && dval < mval)) return "/";
        return "z";
    }

    private static void checkClass(String str) throws Exception {
        str = str.replaceAll("\\s+","");
        if (! str.equals(INIT)) throw specifyException("No main method to run.");
        line++;
    }

    private static void checkMain(String str) throws Exception {
        str = str.replaceAll("\\s+","");
        if (! str.equals("m{}")) throw specifyException("No class specifier.");
        line++;
    }

    private static Exception specifyException(String reason) {
        return new Exception("Exception at line " + line + ": " + reason);
    }
}
