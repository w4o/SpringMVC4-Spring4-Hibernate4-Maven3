package com.ueedit.common.hibernate.dao;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.hibernate.Criteria;
import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ueedit.common.entity.BaseEntity;

/**
 * 通用 DAO
 * 
 * @author xiaowu
 * @date 2015年8月25日 上午11:02:06
 * @param <M>
 * @param <PK>
 */
@Repository("commonDao")
@SuppressWarnings({ "unchecked"})
public class CommonDao<M extends BaseEntity<PK>, PK extends java.io.Serializable> {

    @Autowired
    @Qualifier("sessionFactory")
    private SessionFactory sessionFactory;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Session getSession() {

        return sessionFactory.getCurrentSession();
    }

    public M save(M model) throws Exception {

        getSession().save(model);
        return model;
    }

    public List<M> saveAll(List<M> list) throws Exception {

        List<M> rel = new ArrayList<>();
        for (M m : list) {
            getSession().save(m);
            rel.add(m);
        }
        return rel;
    }

    public M saveOrUpdate(M model) throws Exception {

        getSession().saveOrUpdate(model);
        return model;
    }

    public M update(M model) throws Exception {

        this.getSession().update(model);
        return model;
    }

    public void delete(M model) throws Exception {

        this.getSession().delete(model);
    }

    public M getObj(PK id) {

        return (M) getSession().get(this.getEntityClass(), id);
    }

    public Integer countAll() {

        Criteria criteria = this.getSession().createCriteria(this.getEntityClass());
        criteria.setProjection(Projections.rowCount());
        return ((Long) criteria.uniqueResult()).intValue();
    }

    /**
     * 查询指定类的满足条件的持久化对象 缓存查询
     * 
     * @param <T>
     */
    public <T> List<T> queryByHql(String hql, Map<String, Object> map, String region) {

        Query query = getSession().createQuery(hql).setCacheable(true);
        if (region != null && !"".equals(region)) {
            query.setCacheRegion(region);
        }
        if (map != null) {
            query.setProperties(map);
        }
        List<T> result = query.list();
        if (!Hibernate.isInitialized(result))
            Hibernate.initialize(result);
        return result;
    }

    /**
     * 查询指定类的满足条件的持久化对象
     * 
     * @param <T>
     */
    public <T> List<T> queryByHql(String hql, Map<String, Object> map) {

        Query query = getSession().createQuery(hql);
        if (map != null) {
            query.setProperties(map);
        }
        List<T> result = query.list();
        if (!Hibernate.isInitialized(result))
            Hibernate.initialize(result);
        return result;
    }

    /**
     * @param <T>
     * @param hql
     * @param page当前页数
     * @param limit每页行数
     * @param map查询参数
     * @return
     */
    public <T> List<T> queryPageByHql(String hql, int page, int limit, Map<String, Object> map) {

        Query query = getSession().createQuery(hql);
        if (map != null) {
            query.setProperties(map);
        }
        query.setMaxResults(limit);
        query.setFirstResult((page - 1) * limit);
        List<T> result = query.list();
        if (!Hibernate.isInitialized(result))
            Hibernate.initialize(result);
        return result;
    }

    /**
     * 统计指定类的查询结果
     */
    public Integer countQuery(String hql, Map<String, Object> map) {

        final String counthql = hqlgo(hql).toString();
        final String t = removeOrders(counthql);
        Long count = null;
        Query query = getSession().createQuery(t);
        if (map != null) {
            query.setProperties(map);
        }
        count = (Long) query.uniqueResult();
        return count == null ? 0 : count.intValue();
    }

    /**
     * 根据sql语句获取结果
     * @param sql
     * @param map
     * @return
     */
    public List<Map<String, Object>> queryBySql(String sql, Object args[]) {

        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql, args);
        return list;
    }

    /**
     * 根据sql语句获取分页结果
     * 
     * @param sql
     * @param page
     *            当前页数
     * @param rows
     *            每页行数
     * @param args
     * @return
     */
    public List<Map<String, Object>> queryPageSql(String sql, Integer page, Integer rows, Object args[]) {

        StringBuilder sb = new StringBuilder();
        sb.append(sql);
        sb.append("  LIMIT ");
        sb.append((page - 1) * rows);
        sb.append(" , ");
        sb.append(rows);
        List<Map<String, Object>> list = this.queryBySql(sb.toString(), args);
        return list;
    }

    /**
     * 统计指定类的查询结果
     */
    public Integer countQuerySql(String sql, Object args[]) {

        final String counthql = hqlgo(sql).toString();
        final String t = removeOrders(counthql);
        Integer count = null;
        count = jdbcTemplate.queryForObject(t, args, Integer.class);
        return count == null ? 0 : count;
    }

    /**
     * 获取总数(不截取sql)
     * 
     * @param sql
     * @param args
     * @return
     */
    public Integer countbysql(String sql, Object args[]) {

        Integer count = jdbcTemplate.queryForObject(sql, args, Integer.class);
        return count;
    }

    /**
     * 去除hql的orderby子句
     */
    private static String removeOrders(String hql) {

        Pattern p = Pattern.compile("order\\s*by[\\w|\\W|\\s|\\S]*", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(hql);
        StringBuffer sb = new StringBuffer();
        while (m.find()) {
            m.appendReplacement(sb, "");
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private static StringBuilder hqlgo(String hql) {

        final StringBuilder counthql = new StringBuilder();
        counthql.append(" SELECT COUNT(*)  ");
        counthql.append(hql.substring(hql.toUpperCase().lastIndexOf("FROM")));
        return counthql;
    }

    public Integer executeHql(String hql) {

        return this.getSession().createQuery(hql).executeUpdate();
    }

    public Integer executeUpdate(String hql, Map<String, Object> map) throws Exception {

        Query q = this.getSession().createQuery(hql);
        if (map != null) {
            q.setProperties(map);
        }
        return q.executeUpdate();
    }

    private Class<M> entityClass;
    
    public CommonDao() {
		this.entityClass = null;
		Class<?> c = getClass();
		Type t = c.getGenericSuperclass();
		if (t instanceof ParameterizedType) {
			Type[] p = ((ParameterizedType) t).getActualTypeArguments();
			this.entityClass = (Class<M>) p[0];
		}

	}

    public Class<M> getEntityClass() {

        return entityClass;
    }

    public void setEntityClass(Class<M> entityClass) {

        this.entityClass = entityClass;
    }

}
