import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class interpreter {
    private static Scanner readInput;
    private static ArrayList<String> loop;
    private static String className = "input";
    private static final String INIT = "c:".concat(className);
    private static int line;
    private static boolean isLoop;
    private static int startIndex;
    private static int endIndex;
    private static int curIndex;
    private static int interval;
    private static boolean greaterThan; //greaterThan if true; lessThan if false
    private static String repeat;
    private static boolean cont;
    private static HashMap<String, Integer> intVariables;
    private static HashMap<String, String> stringVariables;
    
    public static void main(String[] args) throws Exception {
        isLoop = false;
        initialize();
    }
    private static void initialize() throws Exception {
        line = 1;
        loop = new ArrayList();
        intVariables = new HashMap<>();
        stringVariables = new HashMap<>();
        readInput = new Scanner(new File("input.qj"));
        checkClass(readInput.nextLine());
        checkMain(readInput.nextLine());
        while (readInput.hasNextLine()) {
            interpretLine(readInput.nextLine());
        }
    }

    private static void interpretLine(String str) throws Exception {
        // str = str.replaceAll("\\s+","");
        if (str.indexOf("(") != -1 && str.indexOf(")") != -1) {
            hasParenthesis(str);
        }
        else if (str.indexOf("int") != -1 || str.toLowerCase().indexOf("string") != -1) {
            str = str.replaceAll("\\s+","");
            if (str.indexOf("int") != -1) {
                String name = str.substring(str.indexOf("int")+3, str.indexOf("="));
                String assignment = str.substring(str.indexOf("=")+1);
                intVariables.put(name, Integer.parseInt(assignment));
            } 
            else if (str.toLowerCase().indexOf("string") != -1) {
                String name = str.substring(str.toLowerCase().indexOf("string")+6, str.indexOf("="));
                String assignment = str.substring(str.indexOf("\"")+1,str.indexOf("\"")+1+str.substring(str.indexOf("\"")+1).indexOf("\""));
                stringVariables.put(name, assignment);
            }
        }
        else {
            throw specifyException("QJW does not understand the method, " + str);
        }
    }

    private static void hasParenthesis(String str) throws Exception {
        // str = str.replaceAll("\\s+","");
        String cmd = "";
        String in = "";
        if (str.indexOf("(") == -1 || str.indexOf(")") == -1) {
            if (!isLoop) throw specifyException("Missing one or two parenthesis, ( and/or ).");
        }
        else {
            cmd = str.substring(0,str.indexOf("(")).replaceAll("\\s+","");
            in = str.substring(str.indexOf("("),str.indexOf(")")+1);
        }
        if (isLoop) {
            if (! cont) {
                str = str.replaceAll("\\s+","");
                if (cmd.equals("\\f")) {
                    cont = true;
                    while (repeat.indexOf("|") != -1) {
                        loop.add(repeat.substring(0,repeat.indexOf("|")));
                        repeat = repeat.substring(repeat.indexOf("|")+1);
                    }
                    if (greaterThan) {
                        while (curIndex > endIndex) {
                            for (String string : loop) {
                                cmd = string.substring(0,string.indexOf("(")).replaceAll("\\s+","");
                                in = string.substring(string.indexOf("(")+1,string.indexOf(")"));
                                in = in.replaceAll("a",Integer.toString(curIndex));
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
                                            in = in.substring(in.indexOf(operator)+1,in.length());
                                            String after = in.substring(in.indexOf(operator)+1,in.length());
                                            while (! operator.equals("z")) {
                                                if (operator.equals("+")) {
                                                    int min = Integer.MAX_VALUE;
                                                    String temp = after;
                                                    if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                                    if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                                    if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                                    if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                                    if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                                    value += Integer.parseInt(temp);
                                                }
                                                else if (operator.equals("-")) {
                                                    int min = Integer.MAX_VALUE;
                                                    String temp = after;
                                                    if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                                    if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                                    if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                                    if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                                    if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                                    value -= Integer.parseInt(temp);
                                                }
                                                else if (operator.equals("*")) {
                                                    int min = Integer.MAX_VALUE;
                                                    String temp = after;
                                                    if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                                    if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                                    if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                                    if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                                    if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                                    value *= Integer.parseInt(temp);
                                                }
                                                else if (operator.equals("/")) {
                                                    int min = Integer.MAX_VALUE;
                                                    String temp = after;
                                                    if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                                    if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                                    if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                                    if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                                    if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                                    value /= Integer.parseInt(temp);
                                                }
                                                operator = locateOperator(in);
                                                in = in.substring(in.indexOf(operator)+1,in.length());
                                                if (in.indexOf(operator) == -1) {
                                                    if (locateOperator(in).equals("z")) {
                                                        if (operator.equals("+")) value += Integer.parseInt(in);
                                                        else if (operator.equals("-")) value -= Integer.parseInt(in);
                                                        else if (operator.equals("*")) value *= Integer.parseInt(in);
                                                        else if (operator.equals("/")) value /= Integer.parseInt(in);
                                                        break;
                                                    }
                                                }
                                                after = in;
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
                                            in = in.substring(in.indexOf(operator)+1,in.length());
                                            String after = in.substring(in.indexOf(operator)+1,in.length());
                                            while (! operator.equals("z")) {
                                                if (operator.equals("+")) {
                                                    int min = Integer.MAX_VALUE;
                                                    String temp = after;
                                                    if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                                    if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                                    if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                                    if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                                    if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                                    value += Integer.parseInt(temp);
                                                }
                                                else if (operator.equals("-")) {
                                                    int min = Integer.MAX_VALUE;
                                                    String temp = after;
                                                    if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                                    if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                                    if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                                    if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                                    if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                                    value -= Integer.parseInt(temp);
                                                }
                                                else if (operator.equals("*")) {
                                                    int min = Integer.MAX_VALUE;
                                                    String temp = after;
                                                    if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                                    if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                                    if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                                    if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                                    if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                                    value *= Integer.parseInt(temp);
                                                }
                                                else if (operator.equals("/")) {
                                                    int min = Integer.MAX_VALUE;
                                                    String temp = after;
                                                    if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                                    if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                                    if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                                    if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                                    if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                                    value /= Integer.parseInt(temp);
                                                }
                                                operator = locateOperator(in);
                                                in = in.substring(in.indexOf(operator)+1,in.length());
                                                if (in.indexOf(operator) == -1) {
                                                    if (locateOperator(in).equals("z")) {
                                                        if (operator.equals("+")) value += Integer.parseInt(in);
                                                        else if (operator.equals("-")) value -= Integer.parseInt(in);
                                                        else if (operator.equals("*")) value *= Integer.parseInt(in);
                                                        else if (operator.equals("/")) value /= Integer.parseInt(in);
                                                        break;
                                                    }
                                                }
                                                after = in;
                                            }
                                            System.out.print(value);
                                        }
                                    }
                                }
                                else if (cmd.equals("ln")) {
                                    System.out.println();
                                }
                            }
                            curIndex += interval;
                        }
                        isLoop = false;
                    }
                    else {
                        while (curIndex < endIndex) {
                            for (String string : loop) {
                                cmd = string.substring(0,string.indexOf("(")).replaceAll("\\s+","");
                                in = string.substring(string.indexOf("(")+1,string.indexOf(")"));
                                in = in.replaceAll("a",Integer.toString(curIndex));
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
                                            in = in.substring(in.indexOf(operator)+1,in.length());
                                            String after = in.substring(in.indexOf(operator)+1,in.length());
                                            while (! operator.equals("z")) {
                                                if (operator.equals("+")) {
                                                    int min = Integer.MAX_VALUE;
                                                    String temp = after;
                                                    if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                                    if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                                    if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                                    if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                                    if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                                    value += Integer.parseInt(temp);
                                                }
                                                else if (operator.equals("-")) {
                                                    int min = Integer.MAX_VALUE;
                                                    String temp = after;
                                                    if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                                    if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                                    if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                                    if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                                    if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                                    value -= Integer.parseInt(temp);
                                                }
                                                else if (operator.equals("*")) {
                                                    int min = Integer.MAX_VALUE;
                                                    String temp = after;
                                                    if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                                    if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                                    if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                                    if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                                    if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                                    value *= Integer.parseInt(temp);
                                                }
                                                else if (operator.equals("/")) {
                                                    int min = Integer.MAX_VALUE;
                                                    String temp = after;
                                                    if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                                    if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                                    if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                                    if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                                    if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                                    value /= Integer.parseInt(temp);
                                                }
                                                operator = locateOperator(in);
                                                in = in.substring(in.indexOf(operator)+1,in.length());
                                                if (in.indexOf(operator) == -1) {
                                                    if (locateOperator(in).equals("z")) {
                                                        if (operator.equals("+")) value += Integer.parseInt(in);
                                                        else if (operator.equals("-")) value -= Integer.parseInt(in);
                                                        else if (operator.equals("*")) value *= Integer.parseInt(in);
                                                        else if (operator.equals("/")) value /= Integer.parseInt(in);
                                                        break;
                                                    }
                                                }
                                                after = in;
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
                                            in = in.substring(in.indexOf(operator)+1,in.length());
                                            String after = in.substring(in.indexOf(operator)+1,in.length());
                                            while (! operator.equals("z")) {
                                                if (operator.equals("+")) {
                                                    int min = Integer.MAX_VALUE;
                                                    String temp = after;
                                                    if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                                    if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                                    if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                                    if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                                    if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                                    value += Integer.parseInt(temp);
                                                }
                                                else if (operator.equals("-")) {
                                                    int min = Integer.MAX_VALUE;
                                                    String temp = after;
                                                    if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                                    if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                                    if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                                    if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                                    if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                                    value -= Integer.parseInt(temp);
                                                }
                                                else if (operator.equals("*")) {
                                                    int min = Integer.MAX_VALUE;
                                                    String temp = after;
                                                    if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                                    if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                                    if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                                    if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                                    if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                                    value *= Integer.parseInt(temp);
                                                }
                                                else if (operator.equals("/")) {
                                                    int min = Integer.MAX_VALUE;
                                                    String temp = after;
                                                    if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                                    if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                                    if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                                    if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                                    if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                                    value /= Integer.parseInt(temp);
                                                }
                                                operator = locateOperator(in);
                                                in = in.substring(in.indexOf(operator)+1,in.length());
                                                if (in.indexOf(operator) == -1) {
                                                    if (locateOperator(in).equals("z")) {
                                                        if (operator.equals("+")) value += Integer.parseInt(in);
                                                        else if (operator.equals("-")) value -= Integer.parseInt(in);
                                                        else if (operator.equals("*")) value *= Integer.parseInt(in);
                                                        else if (operator.equals("/")) value /= Integer.parseInt(in);
                                                        break;
                                                    }
                                                }
                                                after = in;
                                            }
                                            System.out.print(value);
                                        }
                                    }
                                }
                                else if (cmd.equals("ln")) {
                                    System.out.println();
                                }
                            }
                            curIndex += interval;
                        }
                        isLoop = false;
                    }
                }
                else {
                    repeat += (str + "|");
                }
                repeat = repeat.replaceAll("null", "");
            }
        }
        else {
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
                    if (operator.equals("z")) {
                        int intRet = intVariables.getOrDefault(in.substring(in.indexOf("(")+1,in.indexOf(")")),-1);
                        String strRet = stringVariables.getOrDefault(in.substring(in.indexOf("(")+1,in.indexOf(")")),"z");
                        if (intRet != -1) {
                            System.out.println(intRet);
                        }
                        else if (! strRet.equals("z")) {
                            System.out.println(strRet);
                        }
                        else System.out.println(in);
                    }
                    else {
                        in = in.replaceAll("\\s+","");
                        in = in.substring(1,in.length()-1);
                        int value = Integer.parseInt(in.substring(0,in.indexOf(operator)));
                        in = in.substring(in.indexOf(operator)+1,in.length());
                        String after = in.substring(in.indexOf(operator)+1,in.length());
                        while (! operator.equals("z")) {
                            if (operator.equals("+")) {
                                int min = Integer.MAX_VALUE;
                                String temp = after;
                                if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                value += Integer.parseInt(temp);
                            }
                            else if (operator.equals("-")) {
                                int min = Integer.MAX_VALUE;
                                String temp = after;
                                if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                value -= Integer.parseInt(temp);
                            }
                            else if (operator.equals("*")) {
                                int min = Integer.MAX_VALUE;
                                String temp = after;
                                if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                value *= Integer.parseInt(temp);
                            }
                            else if (operator.equals("/")) {
                                int min = Integer.MAX_VALUE;
                                String temp = after;
                                if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                value /= Integer.parseInt(temp);
                            }
                            operator = locateOperator(in);
                            in = in.substring(in.indexOf(operator)+1,in.length());
                            if (in.indexOf(operator) == -1) {
                                if (locateOperator(in).equals("z")) {
                                    if (operator.equals("+")) value += Integer.parseInt(in);
                                    else if (operator.equals("-")) value -= Integer.parseInt(in);
                                    else if (operator.equals("*")) value *= Integer.parseInt(in);
                                    else if (operator.equals("/")) value /= Integer.parseInt(in);
                                    break;
                                }
                            }
                            after = in;
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
                    if (operator.equals("z")) {
                        int intRet = intVariables.getOrDefault(in.substring(in.indexOf("(")+1,in.indexOf(")")),-1);
                        String strRet = stringVariables.getOrDefault(in.substring(in.indexOf("(")+1,in.indexOf(")")),"z");
                        if (intRet != -1) {
                            System.out.print(intRet);
                        }
                        else if (! strRet.equals("z")) {
                            System.out.print(strRet);
                        }
                        else System.out.print(in);
                    }
                    else {
                        in = in.replaceAll("\\s+","");
                        in = in.substring(1,in.length()-1);
                        int value = Integer.parseInt(in.substring(0,in.indexOf(operator)));
                        in = in.substring(in.indexOf(operator)+1,in.length());
                        String after = in.substring(in.indexOf(operator)+1,in.length());
                        while (! operator.equals("z")) {
                            if (operator.equals("+")) {
                                int min = Integer.MAX_VALUE;
                                String temp = after;
                                if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                value += Integer.parseInt(temp);
                            }
                            else if (operator.equals("-")) {
                                int min = Integer.MAX_VALUE;
                                String temp = after;
                                if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                value -= Integer.parseInt(temp);
                            }
                            else if (operator.equals("*")) {
                                int min = Integer.MAX_VALUE;
                                String temp = after;
                                if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                value *= Integer.parseInt(temp);
                            }
                            else if (operator.equals("/")) {
                                int min = Integer.MAX_VALUE;
                                String temp = after;
                                if (after.indexOf("+") != -1) min = Math.min(min, after.indexOf("+"));
                                if (after.indexOf("-") != -1) min = Math.min(min, after.indexOf("-"));
                                if (after.indexOf("*") != -1) min = Math.min(min, after.indexOf("*"));
                                if (after.indexOf("/") != -1) min = Math.min(min, after.indexOf("/"));
                                if (min != Integer.MAX_VALUE) temp = after.substring(0, min);
                                value /= Integer.parseInt(temp);
                            }
                            operator = locateOperator(in);
                            in = in.substring(in.indexOf(operator)+1,in.length());
                            if (in.indexOf(operator) == -1) {
                                if (locateOperator(in).equals("z")) {
                                    if (operator.equals("+")) value += Integer.parseInt(in);
                                    else if (operator.equals("-")) value -= Integer.parseInt(in);
                                    else if (operator.equals("*")) value *= Integer.parseInt(in);
                                    else if (operator.equals("/")) value /= Integer.parseInt(in);
                                    break;
                                }
                            }
                            after = in;
                        }
                        System.out.print(value);
                    }
                }
            }
            else if (cmd.equals("ln")) {
                System.out.println();
            }
            else if (cmd.equals("f")) {
                isLoop = true;
                String start = in.substring(0,in.indexOf(";"));
                in = in.substring(in.indexOf(";")+1);
                String middle = in.substring(0,in.indexOf(";"));
                in = in.substring(in.indexOf(";")+1);
                String end = in;
                startIndex = Integer.parseInt(start.substring(start.indexOf("a=")+2));
                
                if (middle.indexOf("<") != -1) {
                    endIndex = Integer.parseInt(middle.substring(middle.indexOf("<")+1));
                    greaterThan = false;
                }
                else if (middle.indexOf(">") != -1) {
                    endIndex = Integer.parseInt(middle.substring(middle.indexOf(">")+1));
                    greaterThan = true;
                }
                else {
                    if (middle.indexOf("=") != -1) {
                        throw specifyException("The equal sign operator is not supported. Please use > or <");
                    }
                    throw specifyException("No boolean expression detected. Use > or <");
                }
    
                if (end.indexOf("++") != -1) {
                    interval = 1;
                }
                else if (end.indexOf("--") != -1) {
                    interval = -1;
                }
                else if (end.indexOf("+=") != -1) {
                    interval = Integer.parseInt(end.substring(end.indexOf("+=")+2));
                }
                else if (end.indexOf("-=") != -1) {
                    interval = -1*Integer.parseInt(end.substring(end.indexOf("-=")+2));
                }
                curIndex = startIndex;
                cont = false;
            }
            else if (cmd.equals("m")) {
                throw specifyException("EOF Reached.");
            }
            else {
                throw specifyException("QJW does not support the function, " + cmd  + "()");
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
