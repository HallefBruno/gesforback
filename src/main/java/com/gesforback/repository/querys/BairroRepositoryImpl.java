
package com.gesforback.repository.querys;

import com.gesforback.entity.Bairro;
import com.gesforback.entity.Cidade;
import com.gesforback.entity.DataTable;
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


public class BairroRepositoryImpl implements BairroRepositoryCustom {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public DataTable<Bairro> filtrar(String nomeBairro, String nomeCidade, int draw, int start, int length) {
        DataTable<Bairro> dataTable = new DataTable<>();
        
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Bairro> query = criteriaBuilder.createQuery(Bairro.class);
        Root<Bairro> bairro = query.from(Bairro.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Bairro, Cidade> cidade = (Join) bairro.fetch("cidade");

        if (!StringUtils.isBlank(nomeBairro)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(bairro.get("nome")), "%" + nomeBairro.toUpperCase() + "%"));
        }
        if (!StringUtils.isBlank(nomeCidade)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(cidade.get("nome")), "%" + nomeCidade.toUpperCase() + "%"));
        }
        
        query.select(bairro);
        query.where(predicates.toArray(new Predicate[]{}));
        Order order = criteriaBuilder.desc(cidade.get("nome"));
        query.orderBy(order);

        TypedQuery<Bairro> typedQuery = em.createQuery(query);

        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(10);

        dataTable.setData(typedQuery.getResultList());
        Long count = dataTable.countRows(criteriaBuilder, query, bairro, em);

        dataTable.setRecordsTotal(count);
        dataTable.setRecordsFiltered(count);
        dataTable.setDraw(draw);
        dataTable.setStart(start);
        
        return dataTable;
    }
    
    
}
