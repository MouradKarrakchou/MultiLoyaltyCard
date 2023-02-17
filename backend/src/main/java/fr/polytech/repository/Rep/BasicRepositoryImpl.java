package fr.polytech.repository.Rep;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public class BasicRepositoryImpl<T, ID> implements Repository<T, ID> {

    protected HashMap<ID, T> storage = new HashMap<>();

    @Override
    public long count() {
        return storage.size();
    }

    @Override
    public void deleteAll() {
        storage.clear();
    }

    @Override
    public void deleteById(ID id) {
        storage.remove(id);
    }

    @Override
    public boolean existsById(ID id) {
        return storage.containsKey(id);
    }

    @Override
    public Iterable<T> findAll() {
        return storage.values();
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(storage.get(id));
    }

    @Override
    public <S extends T> void save(ID id, S entity) {
        storage.put(id, entity);
    }
}
