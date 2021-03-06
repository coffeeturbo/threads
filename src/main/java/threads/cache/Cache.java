package threads.cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {
    private final Map<Integer, Base> memory = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return memory.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return memory.computeIfPresent(model.getId(), (integer, cache) -> {
            if (cache.getVersion() != model.getVersion()) {
                throw new OptimisticException("versions are not equals");
            }

            return Base.builder()
                    .id(model.id)
                    .version(model.version + 1)
                    .name(model.name)
                    .build();

        }) != null;
    }

    public void delete(Base model) {
        memory.remove(model.getId());
    }
}
