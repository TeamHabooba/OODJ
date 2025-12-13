package group.habooba.core.base;

public interface Copyable<T extends Copyable<T>> {
    T copy();
}
