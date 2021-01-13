package com.gesforback.service;

import com.gesforback.entity.Cidade;
import com.gesforback.entity.Estado;
import com.gesforback.repository.CidadeRepositoryCustom;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 *
 * @author sud
 */
@Component
public class CidadeRepositoryCustomImpl implements CidadeRepositoryCustom {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Cidade> findByCidade(String nomeEstado,String nomeCidade, Pageable pageable) {
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Cidade> query = criteriaBuilder.createQuery(Cidade.class);
        Root<Cidade> cidade = query.from(Cidade.class);
        List<Predicate> predicates = new ArrayList<>();
        Join<Cidade, Estado> estado = (Join) cidade.fetch("estado");
        
        if(!StringUtils.isBlank(nomeEstado)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(estado.get("nome")),"%"+nomeEstado.toUpperCase()+"%"));
        }
        if(!StringUtils.isBlank(nomeCidade)) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(cidade.get("nome")),"%"+nomeCidade.toUpperCase()+"%"));
        }
        
        Order order = criteriaBuilder.desc(cidade.get("nome"));
        
        query.select(cidade);
        query.where(predicates.toArray(new Predicate[predicates.size()]));
        query.orderBy(order);
        
        List<Cidade> resultList = em.createQuery(query).getResultList();
        Page page = new PageImpl(resultList, pageable, resultList.size());
        return page;
    }

}
