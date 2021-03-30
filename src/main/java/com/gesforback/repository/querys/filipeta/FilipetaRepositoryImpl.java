package com.gesforback.repository.querys.filipeta;

import com.gesforback.entity.DataTable;
import com.gesforback.entity.Filipeta;
import com.gesforback.entity.Portaria;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import org.apache.commons.lang3.StringUtils;

public class FilipetaRepositoryImpl implements FilipetaRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public DataTable<Filipeta> filtrar(String numero, String nomePortaria, int draw, int start, int length) {

        DataTable<Filipeta> dataTable = new DataTable<>();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Filipeta> query = criteriaBuilder.createQuery(Filipeta.class);
        Root<Filipeta> filipeta = query.from(Filipeta.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Filipeta, Portaria> portaria = (Join) filipeta.fetch("portaria");

        if (!StringUtils.isBlank(numero)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(filipeta.get("numero")), "%" + numero.toUpperCase() + "%"));
        }
        if (!StringUtils.isBlank(nomePortaria)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(portaria.get("nome")), "%" + nomePortaria.toUpperCase() + "%"));
        }

        query.select(filipeta);
        query.where(predicates.toArray(new Predicate[]{}));
        Order order = criteriaBuilder.desc(filipeta.get("numero"));
        query.orderBy(order);

        TypedQuery<Filipeta> typedQuery = em.createQuery(query);

        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(10);

        dataTable.setData(typedQuery.getResultList());
        Long count = dataTable.countRows(criteriaBuilder, query, filipeta, em);

        dataTable.setRecordsTotal(count);
        dataTable.setRecordsFiltered(count);
        dataTable.setDraw(draw);
        dataTable.setStart(start);
        
        return dataTable;
    }

    @Override
    public List<Filipeta> existeFilipetaParaPortaria(String numero, UUID portariaId) {

        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Filipeta> criteriaQuery = criteriaBuilder.createQuery(Filipeta.class);
        Root<Filipeta> filipeta = criteriaQuery.from(Filipeta.class);

        Subquery<UUID> subquery = criteriaQuery.subquery(UUID.class);
        Root<Portaria> portaria = subquery.from(Portaria.class);
        //Join<Filipeta, Portaria> join = filipeta.join("portaria");

        subquery.select(filipeta.get("id")).where(criteriaBuilder.equal(portaria.get("id"), criteriaBuilder.parameter(UUID.class, "id")));
        criteriaQuery.select(filipeta).where(criteriaBuilder.in(filipeta.get("id")).value(subquery));
        
        //criteriaQuery.select(filipeta).where(criteriaBuilder.equal(filipeta.get("id"),numero));
        //criteriaBuilder.and(criteriaBuilder.equal(portaria.get("id"), portariaId));

        TypedQuery<Filipeta> q = em.createQuery(criteriaQuery);
        q.setParameter("id", portariaId);
        List<Filipeta> employees = q.getResultList();

        return employees;
    }

}
