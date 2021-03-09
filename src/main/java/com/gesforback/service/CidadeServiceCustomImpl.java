package com.gesforback.service;

import com.gesforback.entity.Cidade;
import com.gesforback.entity.DataTable;
import com.gesforback.entity.DataTableImpl;
import com.gesforback.entity.Estado;
import com.gesforback.repository.CidadeRepositoryCustom;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 *
 * @author sud
 */
@Component
public class CidadeServiceCustomImpl implements CidadeRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public DataTable<Cidade> findByCidade(String nomeEstado, String nomeCidade, int draw, int start,int length) {
        DataTable<Cidade> dataTable = new DataTableImpl<>();
        
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Cidade> query = criteriaBuilder.createQuery(Cidade.class);
        Root<Cidade> cidade = query.from(Cidade.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Cidade, Estado> estado = (Join) cidade.fetch("estado");

        if (!StringUtils.isBlank(nomeEstado)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(estado.get("nome")), "%" + nomeEstado.toUpperCase() + "%"));
        }
        if (!StringUtils.isBlank(nomeCidade)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(cidade.get("nome")), "%" + nomeCidade.toUpperCase() + "%"));
        }
        
        query.select(cidade);
        query.where(predicates.toArray(new Predicate[]{}));
        Order order = criteriaBuilder.desc(cidade.get("nome"));
        query.orderBy(order);

        TypedQuery<Cidade> typedQuery = em.createQuery(query);

        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(10);

        dataTable.setData(typedQuery.getResultList());
        Long count = dataTable.countRows(criteriaBuilder, query, cidade, em);

        dataTable.setRecordsTotal(count);
        dataTable.setRecordsFiltered(count);
        dataTable.setDraw(draw);
        dataTable.setStart(start);
        return dataTable;
    }
    
//    public Long count(String nomeEstado, String nomeCidade) {
//        
//        Query query = em.createQuery("SELECT COUNT(*) FROM Cidade c INNER JOIN c.estado WHERE c.estado.nome LIKE :nomeEstado AND UPPER(c.nome) LIKE UPPER(:nomeCidade) ");
//        query.setParameter("nomeEstado", "%" + nomeEstado + "%");
//        query.setParameter("nomeCidade", "%" + nomeCidade + "%");
//        return (Long) query.getResultList().get(0);
//
//    }
}