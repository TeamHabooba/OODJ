package group.habooba.core.repository;

import group.habooba.core.exceptions.EmptyParserTextException;
import group.habooba.core.exceptions.InvalidValueException;
import group.habooba.core.exceptions.NullParserTextException;
import group.habooba.core.exceptions.ValueException;

import java.util.*;

public class TextParser {
    /**
     * Text to parse.
     */
    private final String text;
    /**
     * Main cursor. Used to go through the file.
     */
    private int current;
    /**
     * Additional cursors. Used in complex scenarios.
     */
    private int pos1, pos2;


    public TextParser(String text, int current, int from, int to) {
        this.text = text;
        this.current = current;
        this.pos1 = from;
        this.pos2 = to;
    }

    public TextParser(String text, int pos) {
        this(text, pos, 0, 0);
    }

    public TextParser(String text) {
        this(text, 0, 0, 0);
    }

    public TextParser(){
        this("");
    }

    /**
     * Sets all cursors to 0.
     */
    private void resetCursors(){
        current = 0;
        pos1 = 0;
        pos2 = 0;
    }

    /**
     * Resets pos1 and pos2 cursors to 0.
     */
    private void resetSecondaryCursors(){
        pos1 = 0;
        pos2 = 0;
    }


    /**
     * Peek character.
     * @return character at "current" cursor index.
     * @throws IndexOutOfBoundsException if "current" is more than "text" length.
     */
    private char peek(){
        if(current >= text.length())
            throw new IndexOutOfBoundsException("Index out of parser text bounds.");
        return text.charAt(current);
    }

    /**
     * Peek character at position "p".
     * @param p position to peek.
     * @return character at "p" index.
     */
    private char at(int p){
        if(p >= text.length())
            throw new IndexOutOfBoundsException("Index out of parser text bounds.");
        return text.charAt(p);
    }

    /**
     * Peek current character, then increment "current" index.
     * @return peeked character.
     */
    private char peekAndMove(){
        if(current == text.length())
            throw new IndexOutOfBoundsException("Index out of parser text bounds.");
        return text.charAt(current++);
    }

    /**
     * Check current character and move character.
     * @param expected expected character.
     * @throws InvalidValueException if gets unexpected character.
     */
    private void consume(char expected){
        if(expected != peekAndMove())
            throw new InvalidValueException("Parser error. Expected: " + expected + ", Got: " + peekAndMove());
    }

    /**
     * Skips all the whitespaces (including tabs, new lines, etc.).
     */
    private void skipSpaces(){
        if(!Character.isWhitespace(peek()))
            return;
        while(Character.isWhitespace(peek())){
            try{
                peekAndMove();
            } catch(IndexOutOfBoundsException e){
                return;
            }
        }
    }


    /**
     * Checks if text contains any characters.
     */
    private void checkTextExistence(){
        if (text==null)
            throw new NullParserTextException("Text was null.");
        if (text.isBlank())
            throw new EmptyParserTextException("Text didn't contain any characters except for spaces.");
    }

    /**
     * Verifies that all brackets have their closing pairs.
     * @return validation result.
     */
    private boolean bracketsAreValid(){
        resetSecondaryCursors();
        ArrayDeque<Character> stack = new ArrayDeque<>();
        while(pos1<text.length()){
            switch (at(pos1)){
                case '[':
                    if(stack.isEmpty()){
                        stack.push('[');
                        break;
                    }
                    if(!stack.peek().equals('"'))
                        stack.push('[');
                    break;
                case ']':
                    if(stack.isEmpty())
                        return false;
                    if(stack.peek().equals('"'))
                        break;
                    if(stack.peek()=='[')
                        stack.pop();
                    else return false;
                    break;
                case '{':
                    if(stack.isEmpty()){
                        stack.push('{');
                        break;
                    }
                    if(!stack.peek().equals('"'))
                        stack.push('{');
                    break;
                case '}':
                    if(stack.isEmpty())
                        return false;
                    if(stack.peek().equals('"'))
                        break;
                    if(stack.peek()=='{')
                        stack.pop();
                    else return false;
                    break;
                case '"':
                    if(stack.isEmpty()){
                        stack.push('"');
                        break;
                    }
                    if(stack.peek().equals('"'))
                        stack.pop();
                    else
                        stack.push('"');
                    break;
                default:
                    break;
            }
            pos1++;
        }
        return stack.isEmpty();
    }


    // Parsers
    /**
     * Parses boolean value.
     * @return true or false.
     */
    private Boolean parseBoolean(){
        if(peek() == 't'){
            consume('t'); consume('r'); consume('u');  consume('e');
            return true;
        } else {
            consume('f');  consume('a'); consume('l');   consume('s');   consume('e');
            return false;
        }

    }

    /**
     * Parses null.
     * @return null.
     */
    private Object parseNull(){
        consume('n'); consume('u'); consume('l'); consume('l');
        return null;
    }

    /**
     * Parses integer and floating-point numbers as Integer and Double respectively.
     * @return Integer if int, Double if double.
     */
    public Object parseNumber(){
        StringBuilder sb = new StringBuilder();

        if (peek() == '-') sb.append(peekAndMove());

        if (peek() == '0') {
            sb.append(peekAndMove());
        } else {
            while (Character.isDigit(peek())) {
                sb.append(peekAndMove());
            }
        }
        boolean isDouble = false;
        if (peek() == '.') {
            isDouble = true;
            sb.append(peekAndMove());
            while (Character.isDigit(peek())) {
                sb.append(peekAndMove());
            }
        }
        if (peek() == 'e' || peek() == 'E') {
            isDouble = true;
            sb.append(peekAndMove());
            if (peek() == '+' || peek() == '-') sb.append(peekAndMove());
            while (Character.isDigit(peek())) {
                sb.append(peekAndMove());
            }
        }
        String numStr = sb.toString();
        if (isDouble) {
            return Double.parseDouble(numStr);
        } else {
            long l = Long.parseLong(numStr);
            if (l >= Integer.MIN_VALUE && l <= Integer.MAX_VALUE) {
                return (int) l;
            }
            return l;
        }
    }

    /**
     * Parses Unicode escape-sequence character (\u0000).
     * @return Unicode character.
     */
    private char parseUnicode() {
        String hex = "" + peekAndMove() + peekAndMove() + peekAndMove() + peekAndMove();
        return (char) Integer.parseInt(hex, 16);
    }

    /**
     * Parses one String including ANSI escape-sequences.
     * @return parsed String.
     */
    private String parseString() {
        consume('"');
        StringBuilder sb = new StringBuilder();

        while (peek() != '"') {
            char c = peekAndMove();
            if (c == '\\') {
                char escaped = peekAndMove();
                switch (escaped) {
                    case '"': sb.append('"'); break;
                    case '\\': sb.append('\\'); break;
                    case '/': sb.append('/'); break;
                    case 'b': sb.append('\b'); break;
                    case 'f': sb.append('\f'); break;
                    case 'n': sb.append('\n'); break;
                    case 'r': sb.append('\r'); break;
                    case 't': sb.append('\t'); break;
                    case 'u': sb.append(parseUnicode()); break;
                    default: throw new RuntimeException("Invalid escape: \\" + escaped);
                }
            } else {
                sb.append(c);
            }
        }

        consume('"');
        return sb.toString();
    }


    /**
     * Parses array of values.
     * @return parsed array.
     */
    private ArrayList<Object> parseArray() {
        ArrayList<Object> list = new ArrayList<>();
        consume('[');
        skipSpaces();

        if (peek() == ']') {
            consume(']');
            return list;
        }

        while (true) {
            skipSpaces();
            list.add(parseValue());
            skipSpaces();

            char next = peek();
            if (next == ',') {
                consume(',');
            } else if (next == ']') {
                consume(']');
                break;
            } else {
                throw new InvalidValueException("Expected ',' or ']' at position " + current);
            }
        }

        return list;
    }

    /**
     * Parses Object (HashMap)
     * @return parsed HashMap
     */
    private LinkedHashMap<String, Object> parseObject() {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();
        consume('{');
        skipSpaces();
        if(peek() == '}') {
            consume('}');
            return map;
        }
        while (true) {
            skipSpaces();
            String key = parseString();
            skipSpaces();
            consume(':');
            skipSpaces();
            Object value = parseValue();
            map.put(key, value);
            skipSpaces();
            if(peek() == '}') {
                consume('}');
                break;
            } else if(peek() == ',') {
                consume(',');
            } else {
                throw new InvalidValueException("Expected ',' or '}' at position " + current);
            }
        }
        return map;
    }

    private Object parseValue() {
        skipSpaces();
        char c = peek();

        if (c == '{') return parseObject();
        if (c == '[') return parseArray();
        if (c == '"') return parseString();
        if (c == 't' || c == 'f') return parseBoolean();
        if (c == 'n') return parseNull();
        if (c == '-' || Character.isDigit(c)) return parseNumber();

        throw new InvalidValueException("Unexpected character: " + c + " at position " + current);
    }


    /**
     * Main method
     * @return parsed object
     */
    public Object parse(){
        try{
            checkTextExistence();
        } catch (ValueException e){
            return null;
        }
        if(!bracketsAreValid())
            return null;
        skipSpaces();

        return parseValue();
    }

    public static Object fromText(String s){
        return new TextParser(s).parse();
    }
}
