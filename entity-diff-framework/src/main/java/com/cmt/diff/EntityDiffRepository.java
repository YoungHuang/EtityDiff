package com.cmt.diff;

import com.rits.cloning.Cloner;
import de.danielbechler.diff.ObjectDifferBuilder;
import de.danielbechler.diff.node.DiffNode;


/**
 * 可检测变化的Repository抽象类
 *
 * @author yonghuang
 * @param <T>
 * @param <ID>
 */
public abstract class EntityDiffRepository<T extends Entity<ID>, ID> implements Repository<T, ID> {
    private static final Cloner cloner = new Cloner();

    private ThreadLocal<EntitySnapshotContext<T, ID>> snapshot = ThreadLocal.withInitial(() -> new EntitySnapshotContext<>());

    @Override
    public T find(ID id) {
        T entity = this.onSelect(id);
        if (entity != null) {
            attach(entity);
        }

        return entity;
    }

    @Override
    public void remove(T entity) {
        this.onDelete(entity);
        this.detach(entity);
    }

    @Override
    public void save(T entity) {
        // 新创建entity
        if (entity.getId() == null) {
            this.onInsert(entity);
            this.attach(entity);
            return;
        }

        T oldeEntity = snapshot.get().find(entity.getId());
        //  比较变化
        DiffNode diff = ObjectDifferBuilder.buildDefault().compare(entity, oldeEntity);
        if (!diff.hasChanges()) {
            // 没有变化时，直接返回
            return;
        }

        // 调用UPDATE
        this.onUpdate(entity, oldeEntity, diff);

        // 更新snapshot
        refresh(entity);
    }

    protected abstract void onInsert(T entity);
    protected abstract T onSelect(ID id);
    protected abstract void onUpdate(T entity, T oldeEntity, DiffNode diff);
    protected abstract void onDelete(T entity);

    /**
     * 托管Entity
     */
    protected void attach(T entity) {
        try {
            snapshot.get().attach(cloner.deepClone(entity));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 解除托管Entity
     */
    protected void detach(T entity) {
        snapshot.get().detach(entity);
    }

    /**
     * 刷新snapshot
     */
    protected void refresh(T entity) {
        this.attach(entity);
    }
}
