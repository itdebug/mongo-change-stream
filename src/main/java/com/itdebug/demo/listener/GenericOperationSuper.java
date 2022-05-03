package com.itdebug.demo.listener;

import lombok.Data;

import java.lang.reflect.ParameterizedType;

/**
 * @author ：lurongkang
 * @date ：Created in 2022/5/2 20:08
 * @description：
 * @modified By：
 * @version:
 */
@Data
public class GenericOperationSuper<T> {

    /** 泛型的类型 */
    private Class<T> entityClass;

    public GenericOperationSuper() {
        BaseHibernateEntityDao();
    }

    @SuppressWarnings("unchecked")
    public void BaseHibernateEntityDao() {
        entityClass =(Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }
}