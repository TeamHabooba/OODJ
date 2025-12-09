package group.habooba.core.repository;

import java.util.List;

/**
 * Data storage interface
 * @param <T> Type to be stored
 */
public interface Repository<T> {
    List<T> load();
    void save(List<T> objects);
}
