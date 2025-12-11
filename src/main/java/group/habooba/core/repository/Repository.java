package group.habooba.core.repository;

import java.util.List;

/**
 * Data storage interface
 */
public interface Repository {
    boolean load();
    void save();
    boolean fileExists();
    boolean loaded();
    void reset();
}
