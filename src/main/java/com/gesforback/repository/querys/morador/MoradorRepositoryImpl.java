
package com.gesforback.repository.querys.morador;

import com.gesforback.entity.DataTable;
import com.gesforback.entity.Morador;
import com.gesforback.entity.MoradorSecundario;
import com.gesforback.entity.Telefone;
import com.gesforback.entity.filtros.FiltrosMorador;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;

public class MoradorRepositoryImpl implements MoradorRepositoryCustom {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    public DataTable<Morador> filtrar(FiltrosMorador filtrosMorador, int draw, int start, int length) {
        
        Optional<FiltrosMorador> filtros = Optional.ofNullable(filtrosMorador);
        
        DataTable<Morador> dataTable = new DataTable<>();
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery<Morador> query = criteriaBuilder.createQuery(Morador.class);
        Root<Morador> morador = query.from(Morador.class);
        Join<Morador, Telefone> telefone = (Join) morador.fetch("telefones");
        Join<Morador, MoradorSecundario> moradorSecundario = (Join) morador.fetch("moradorSecundarios");
        List<Predicate> predicates = new ArrayList<>();
        
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getNome).orElse(""))) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(morador.get("nome")), "%" + filtrosMorador.getNome().toUpperCase() + "%"));
        }
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getCpf).orElse(""))) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(morador.get("cpf")), "%" + filtrosMorador.getCpf() + "%"));
        }
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getResidencia).orElse(""))) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(morador.get("residencia")), "%" + filtrosMorador.getResidencia().toUpperCase() + "%"));
        }
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getTelefone).orElse(""))) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(telefone.get("numero")), "%" + filtrosMorador.getTelefone() + "%"));
        }
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getNome).orElse(""))) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(moradorSecundario.get("nome")), "%" + filtrosMorador.getNome().toUpperCase() + "%"));
        }
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getCpf).orElse(""))) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(moradorSecundario.get("cpf")), "%" + filtrosMorador.getCpf() + "%"));
        }
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getTelefone).orElse(""))) {
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(moradorSecundario.get("telefone")), "%" + filtrosMorador.getTelefone() + "%"));
        }

        query.select(morador).distinct(true);
        query.where(predicates.toArray(new Predicate[]{}));
        
        TypedQuery<Morador> typedQuery = em.createQuery(query);

        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(10);

        dataTable.setData(typedQuery.getResultList());
        Long count = dataTable.countRows(criteriaBuilder, query, morador, em);

        dataTable.setRecordsTotal(count);
        dataTable.setRecordsFiltered(count);
        dataTable.setDraw(draw);
        dataTable.setStart(start);
        
        return dataTable;
    }
    
}
