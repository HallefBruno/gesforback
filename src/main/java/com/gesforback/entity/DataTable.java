package com.gesforback.entity;

import java.util.List;
import java.util.Set;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Root;

/**
 *
 * @author sud
 * @param <E>
 */

public class DataTable<E> {
    
    private int draw;
    private int start;
    private long recordsTotal;
    private long recordsFiltered;
    private List<E> data;

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public void setRecordsFiltered(long recordsFiltered) {
        this.recordsFiltered = recordsFiltered;
    }

    public List<E> getData() {
        return data;
    }

    public void setData(List<E> data) {
        this.data = data;
    }
    
    /**
     * @param <T>
     * @param criteriaBuilder
     * @param criteriaQuery
     * @param root
     * @param entityManager
     * @return Long
     */
    public <T> Long countRows(final CriteriaBuilder criteriaBuilder, final CriteriaQuery<T> criteriaQuery,Root<T> root, EntityManager entityManager) {
        CriteriaQuery<Long> query = createCountQuery(criteriaBuilder, criteriaQuery, root);
        return entityManager.createQuery(query).getSingleResult();
    }

    private <T> CriteriaQuery<Long> createCountQuery(final CriteriaBuilder cb, final CriteriaQuery<T> criteria, final Root<T> root) {
        final CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        final Root<T> countRoot = countQuery.from(criteria.getResultType());
        doJoins(root.getJoins(), countRoot);
        doJoinsOnFetches(root.getFetches(), countRoot);
        countQuery.select(cb.count(countRoot));
        countQuery.where(criteria.getRestriction());
        countRoot.alias(root.getAlias());
        return countQuery.distinct(criteria.isDistinct());
    }

    @SuppressWarnings("unchecked")
    private void doJoinsOnFetches(Set<? extends Fetch<?, ?>> joins, Root<?> root) {
        doJoins((Set<? extends Join<?, ?>>) joins, root);
    }

    private void doJoins(Set<? extends Join<?, ?>> joins, Root<?> root) {
        joins.forEach((join) -> {
            Join<?, ?> joined = root.join(join.getAttribute().getName(), join.getJoinType());
            joined.alias(join.getAlias());
            doJoins(join.getJoins(), joined);
        });
    }

    private void doJoins(Set<? extends Join<?, ?>> joins, Join<?, ?> root) {
        joins.forEach((join) -> {
            Join<?, ?> joined = root.join(join.getAttribute().getName(), join.getJoinType());
            joined.alias(join.getAlias());
            doJoins(join.getJoins(), joined);
        });
    }
}
