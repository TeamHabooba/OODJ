package group.habooba.core.repository;

import group.habooba.core.domain.TextSerializable;

import java.util.List;
import java.util.Map;

/**
 * Data storage interface
 */
public interface IRepository<T extends TextSerializable> {
    boolean load();
    void save();
    boolean fileExists();
    boolean loaded();
    void reset();
    boolean documentValid();
    Map<String, Object> meta();
    List<Map<String, Object>> data();
    List<T> dataAsList();
    String path();
    Map<String, Object> documentObjectModel();
}
