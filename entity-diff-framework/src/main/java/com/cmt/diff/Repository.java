package com.cmt.diff;

/**
 * Repository接口
 *
 * @author yonghuang
 * @param <T>
 * @param <ID>
 */
public interface Repository<T, ID> {
    /**
     * 通过ID查找Entity
     * @param id
     * @return
     */
    T find(ID id);

    /**
     * 移除Entity
     * @param entity
     */
    void remove(T entity);

    /**
     * 保存entity
     * @param entity
     */
    void save(T entity);
}
