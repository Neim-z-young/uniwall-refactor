package com.oyoungy.domain.repository;

import java.util.Optional;

public interface BaseRepository<T, ID> {
    /**
     * 保存实体
     *
     * @param t 实体
     * @return 数据字典
     */
    default T save(T t) {
        return t;
    }

    /**
     * 查询实体
     *
     * @param keyId 查询主键
     * @return 返回实体
     */
    default Optional<T> findOne(ID keyId) {
        return Optional.empty();
    }

    /**
     * 删除实体
     *
     * @param keyId 查询主键
     */
    default void remove(ID keyId) {
    }
}
