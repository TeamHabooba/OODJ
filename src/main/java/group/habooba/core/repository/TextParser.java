package group.habooba.core.repository;

import group.habooba.core.exceptions.EmptyParserTextException;
import group.habooba.core.exceptions.NullParserTextException;
import group.habooba.core.exceptions.ValueException;

import java.util.ArrayDeque;

public class TextParser {
    /**
     * Text to parse
     */
    private String text;
    /**
     * Main cursor. Used to go through the file
     */
    private int pos;
    /**
     * Additional cursors. Used in complex scenarios
     */
    private int pos1, pos2;

    public TextParser(String text, int pos, int pos1, int pos2) {
        this.text = text;
        this.pos = pos;
        this.pos1 = pos1;
        this.pos2 = pos2;
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
     * Checks if text contains any characters
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
    public boolean bracketsAreValid(){
        ArrayDeque<Character> stack = new ArrayDeque<>();
        while(pos<text.length()){
            switch (text.charAt(pos)){
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
            pos++;
        }
        return stack.isEmpty();
    }

    public Object parse(){
        try{
            checkTextExistence();
        } catch (ValueException e){
            return null;
        }
        if(!bracketsAreValid())
            return null;
        return null;
    }
}
