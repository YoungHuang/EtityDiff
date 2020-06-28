package com.cmt.diff;

import java.util.HashMap;
import java.util.Map;

/**
 * 保存Entity的snapshot
 *
 * @author yonghuang
 * @param <T>
 * @param <ID>
 */
public class EntitySnapshotContext<T extends Entity<ID>, ID> {
    private Map<ID, T> entityMap = new HashMap<>();

    public void attach(T entity) {
        if (entity.getId() != null) {
            entityMap.put(entity.getId(), entity);
        }
    }

    public void detach(T entity) {
        if (entity.getId() != null) {
            entityMap.remove(entity.getId());
        }
    }

    public T find(ID id) {
        return entityMap.get(id);
    }
}
