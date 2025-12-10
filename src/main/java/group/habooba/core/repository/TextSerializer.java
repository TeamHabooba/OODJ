package group.habooba.core.repository;

import group.habooba.core.domain.TextSerializable;
import group.habooba.core.exceptions.InvalidValueException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class TextSerializer {

    /**
     * Mutable string container
     */
    private final StringBuilder sb;
    /**
     * Current indentation level
     */
    private int indentLevel;
    /**
     * Use pretty print (with tabs and new lines) or casual
     */
    private final boolean pretty;

    public TextSerializer(boolean pretty) {
        this.sb = new StringBuilder();
        this.indentLevel = 0;
        this.pretty = pretty;
    }

    /**
     * Main serialization method
     * @param obj object to serialize
     * @return serialized object as a string
     */
    public String serialize(Object obj) {
        sb.setLength(0);
        indentLevel = 0;
        writeValue(obj);
        return sb.toString();
    }

    private void writeValue(Object obj) {
        switch (obj) {
            case null -> sb.append("null");
            case String s -> writeString(s);
            case Number number -> writeNumber(number);
            case Boolean b -> sb.append(obj.toString());
            case Enum<?> e -> writeEnum(e);
            case Map<?, ?> map -> writeObject(map);
            case List<?> list -> writeArray(list);
            case Object[] objects -> writeArray(Arrays.asList(objects));
            case TextSerializable ser -> writeObject(ser.toMap());
            default -> throw new InvalidValueException("Unsupported parser type: " + obj.getClass());
        }
    }

    /**
     * Object serialization
     * @param map object to serialize as a Map
     */
    private void writeObject(Map<?, ?> map) {
        sb.append('{');
        if (map.isEmpty()) {
            sb.append('}');
            return;
        }
        indentLevel++;
        boolean first = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!first) {
                sb.append(',');
            }
            first = false;

            if (pretty) {
                sb.append('\n');
                indent();
            }
            // Key is always a String
            writeString(entry.getKey().toString());
            sb.append(':');
            if (pretty) sb.append(' ');
            // Value
            writeValue(entry.getValue());
        }
        indentLevel--;
        if (pretty) {
            sb.append('\n');
            indent();
        }
        sb.append('}');
    }

    /**
     * List/array serialization
     * @param list List to serialize
     */
    private void writeArray(List<?> list) {
        sb.append('[');
        if (list.isEmpty()) {
            sb.append(']');
            return;
        }
        indentLevel++;
        boolean first = true;
        for (Object item : list) {
            if (!first) {
                sb.append(',');
            }
            first = false;
            if (pretty) {
                sb.append('\n');
                indent();
            }
            writeValue(item);
        }
        indentLevel--;
        if (pretty) {
            sb.append('\n');
            indent();
        }
        sb.append(']');
    }

    /**
     * String serialization with ANSI escape-sequences used
     * @param str string to serialize
     */
    private void writeString(String str) {
        sb.append('"');
        for (char c : str.toCharArray()) {
            switch (c) {
                case '"':  sb.append("\\\""); break;
                case '\\': sb.append("\\\\"); break;
                case '\b': sb.append("\\b"); break;
                case '\f': sb.append("\\f"); break;
                case '\n': sb.append("\\n"); break;
                case '\r': sb.append("\\r"); break;
                case '\t': sb.append("\\t"); break;
                default:
                    if (c < 0x20 || c == 0x7F) {
                        // Unicode non-printable characters
                        sb.append(String.format("\\u%04x", (int) c));
                    } else {
                        sb.append(c);
                    }
            }
        }
        sb.append('"');
    }

    /**
     * Enum serialization (serializes as string with enum name)
     * @param e enum to serialize
     */
    private void writeEnum(Enum<?> e) {
        writeString(e.name());
    }

    /**
     * Number serialization (Integer, Double and Float)
     * @param num number as an object
     */
    private void writeNumber(Number num) {
        if (num instanceof Double || num instanceof Float) {
            double d = num.doubleValue();
            if (Double.isNaN(d) || Double.isInfinite(d)) {
                throw new RuntimeException("JSON does not support NaN or Infinity");
            }

            // Remove .0 for whole numbers
            if (d == Math.floor(d) && !Double.isInfinite(d)) {
                sb.append((long) d);
            } else {
                sb.append(d);
            }
        } else {
            sb.append(num.toString());
        }
    }

    /**
     * Pretty print indentation
     */
    private void indent() {
        for (int i = 0; i < indentLevel; i++) {
            sb.append("  "); // 2 spaces per level
        }
    }

    // Static utils

    /**
     * Parses object to String
     * @param obj
     * @return
     */
    public static String toText(Object obj) {
        return new TextSerializer(false).serialize(obj);
    }

    public static String toTextPretty(Object obj) {
        return new TextSerializer(true).serialize(obj);
    }
}