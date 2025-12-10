package group.habooba.core.domain;

import java.util.Map;

public interface TextSerializable {
    String toText();
    Map<String, Object> toMap();
}
