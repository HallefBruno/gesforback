
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
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;

public class MoradorRepositoryImpl implements MoradorRepositoryCustom {
    
    @PersistenceContext
    private EntityManager em;

    @Override
    @SuppressWarnings("UnusedAssignment")
    public DataTable<Morador> filtrar(FiltrosMorador filtrosMorador, int draw, int start) {
        Optional<FiltrosMorador> filtros = Optional.ofNullable(filtrosMorador);
        
        DataTable<Morador> dataTable = new DataTable<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Morador> query = cb.createQuery(Morador.class);
        Root<Morador> morador = query.from(Morador.class);
        Join<Morador, Telefone> telefone = (Join) morador.fetch("telefones");
        Join<Morador, MoradorSecundario> moradorSecundario = (Join) morador.<Morador, MoradorSecundario>fetch("moradorSecundarios", JoinType.LEFT);
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate = cb.disjunction();
        
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getNome).orElse(""))) {
            predicate = cb.or(cb.like(cb.upper(morador.get("nome")), "%" + filtrosMorador.getNome().toUpperCase() + "%"), cb.like(cb.upper(moradorSecundario.get("nome")), "%" + filtrosMorador.getNome().toUpperCase()+ "%"));
            predicates.add(predicate);
        }
        
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getCpf).orElse(""))) {
            predicate = cb.or(cb.like(morador.get("cpf"), "%" + StringUtils.getDigits(filtrosMorador.getCpf()) + "%"), cb.like(moradorSecundario.get("cpf"), "%" + StringUtils.getDigits(filtrosMorador.getCpf()) + "%"));
            predicates.add(predicate);
        }
        
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getResidencia).orElse(""))) {
            predicate = cb.or(cb.like(cb.upper(morador.get("residencia")), "%" + filtrosMorador.getResidencia().toUpperCase() + "%"),cb.like(cb.upper(moradorSecundario.get("residencia")), "%" + filtrosMorador.getResidencia().toUpperCase() + "%"));
            predicates.add(predicate);
        }
        
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getTelefone).orElse(""))) {
            predicate = cb.or(cb.like(telefone.get("numero"), "%" + StringUtils.getDigits(filtrosMorador.getTelefone()) + "%"), cb.like(moradorSecundario.get("telefone"), "%" + StringUtils.getDigits(filtrosMorador.getTelefone()) + "%"));
            predicates.add(predicate);
        }
                
        query.select(morador);
        query.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Morador> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(10);
        dataTable.setData(typedQuery.getResultList());
        Long count = dataTable.countRows(cb, query, morador, em);
        dataTable.setRecordsTotal(count);
        dataTable.setRecordsFiltered(count);
        dataTable.setDraw(draw);
        dataTable.setStart(start);
        
        return dataTable;
    }

}

//CriteriaBuilder qb = entityManager.getCriteriaBuilder();
//CriteriaQuery<Long> cq = qb.createQuery(Long.class);
//cq.select(qb.count(cq.from(MyEntity.class)));
//cq.where(/*your stuff*/);
//return entityManager.createQuery(cq).getSingleResult();


//Expression<String> cpfMoradorProprietario = morador.get("cpf");
//Expression<String> cpfMoradorSecundario = moradorSecundario.get("cpf");
//criteriaBuilder.function("REGEXP_REPLACE", String.class,cpfMoradorProprietario, criteriaBuilder.literal("[^a-zA-Z0-9]+"), criteriaBuilder.literal(""));
//criteriaBuilder.function("REGEXP_REPLACE", String.class,cpfMoradorSecundario, criteriaBuilder.literal("[^a-zA-Z0-9]+"), criteriaBuilder.literal(""));
/**
 * 
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
    @SuppressWarnings("UnusedAssignment")
    public DataTable<Morador> filtrarMoradorProprietario(FiltrosMorador filtrosMorador, int draw, int start) {
        Optional<FiltrosMorador> filtros = Optional.ofNullable(filtrosMorador);
        DataTable<Morador> dataTable = new DataTable<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Morador> query = cb.createQuery(Morador.class);
        Root<Morador> morador = query.from(Morador.class);
        Join<Morador, Telefone> telefone = (Join) morador.fetch("telefones");
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getNome).orElse(""))) {
            predicates.add(cb.or(cb.like(cb.upper(morador.get("nome")), "%" + filtrosMorador.getNome().toUpperCase() + "%")));
        }
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getCpf).orElse(""))) {
            predicates.add(cb.or(cb.like(morador.get("cpf"), "%" + StringUtils.getDigits(filtrosMorador.getCpf()) + "%")));
        }
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getResidencia).orElse(""))) {
            predicates.add(cb.or(cb.like(cb.upper(morador.get("residencia")), "%" + filtrosMorador.getResidencia().toUpperCase() + "%")));
        }
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getTelefone).orElse(""))) {
            predicates.add(cb.or(cb.like(telefone.get("numero"), "%" + StringUtils.getDigits(filtrosMorador.getTelefone()) + "%")));
        }
        
        query.select(morador).distinct(true);
        query.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<Morador> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(10);
        dataTable.setData(typedQuery.getResultList());
        Long count = dataTable.countRows(cb, query, morador, em);

        dataTable.setRecordsTotal(count);
        dataTable.setRecordsFiltered(count);
        dataTable.setDraw(draw);
        dataTable.setStart(start);
        
        return dataTable;
    }

    @Override
    @SuppressWarnings("UnusedAssignment")
    public DataTable<MoradorSecundario> filtrarMoradorSecundario(FiltrosMorador filtrosMorador, int draw, int start) {
        Optional<FiltrosMorador> filtros = Optional.ofNullable(filtrosMorador);
        DataTable<MoradorSecundario> dataTable = new DataTable<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MoradorSecundario> query = cb.createQuery(MoradorSecundario.class);
        Root<MoradorSecundario> moradorSecundario = query.from(MoradorSecundario.class);
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getNome).orElse(""))) {
            predicates.add(cb.or(cb.like(cb.upper(moradorSecundario.get("nome")), "%" + filtrosMorador.getNome().toUpperCase() + "%")));
        }
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getCpf).orElse(""))) {
            predicates.add(cb.or(cb.like(moradorSecundario.get("cpf"), "%" + StringUtils.getDigits(filtrosMorador.getCpf()) + "%")));
        }
        
        query.select(moradorSecundario).distinct(true);
        query.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<MoradorSecundario> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(10);
        dataTable.setData(typedQuery.getResultList());
        Long count = dataTable.countRows(cb, query, moradorSecundario, em);

        dataTable.setRecordsTotal(count);
        dataTable.setRecordsFiltered(count);
        dataTable.setDraw(draw);
        dataTable.setStart(start);
        
        return dataTable;
    }
    
}

//Expression<String> cpfMoradorProprietario = morador.get("cpf");
//Expression<String> cpfMoradorSecundario = moradorSecundario.get("cpf");
//criteriaBuilder.function("REGEXP_REPLACE", String.class,cpfMoradorProprietario, criteriaBuilder.literal("[^a-zA-Z0-9]+"), criteriaBuilder.literal(""));
//criteriaBuilder.function("REGEXP_REPLACE", String.class,cpfMoradorSecundario, criteriaBuilder.literal("[^a-zA-Z0-9]+"), criteriaBuilder.literal(""));
/**
 * Forma inicial que era feita a consulta
 * 
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
    @SuppressWarnings("UnusedAssignment")
    public DataTable<Morador> filtrar(FiltrosMorador filtrosMorador, int draw, int start) {
        
        Optional<FiltrosMorador> filtros = Optional.ofNullable(filtrosMorador);
        
        DataTable<Morador> dataTable = new DataTable<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Morador> query = cb.createQuery(Morador.class);
        Root<Morador> morador = query.from(Morador.class);
        Join<Morador, Telefone> telefone = (Join) morador.fetch("telefones");
        Join<Morador, MoradorSecundario> moradorSecundario = (Join) morador.fetch("moradorSecundarios");
        List<Predicate> predicates = new ArrayList<>();
        Predicate predicate = cb.disjunction();
        
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getNome).orElse(""))) {
            predicate = cb.or(cb.like(cb.upper(morador.get("nome")), "%" + filtrosMorador.getNome().toUpperCase() + "%"), cb.like(cb.upper(moradorSecundario.get("nome")), "%" + filtrosMorador.getNome().toUpperCase()+ "%"));
            predicates.add(predicate);
        }
        
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getCpf).orElse(""))) {
            predicate = cb.or(cb.like(morador.get("cpf"), "%" + StringUtils.getDigits(filtrosMorador.getCpf()) + "%"), cb.like(moradorSecundario.get("cpf"), "%" + StringUtils.getDigits(filtrosMorador.getCpf()) + "%"));
            predicates.add(predicate);
        }
        
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getResidencia).orElse(""))) {
            predicate = cb.or(cb.like(cb.upper(morador.get("residencia")), "%" + filtrosMorador.getResidencia().toUpperCase() + "%"));
            predicates.add(predicate);
        }
        
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getTelefone).orElse(""))) {
            predicate = cb.or(cb.like(telefone.get("numero"), "%" + StringUtils.getDigits(filtrosMorador.getTelefone()) + "%"), cb.like(moradorSecundario.get("telefone"), "%" + StringUtils.getDigits(filtrosMorador.getTelefone()) + "%"));
            predicates.add(predicate);
        }
                
        query.select(morador).distinct(true);
        query.where(predicates.toArray(new Predicate[]{}));
        
        TypedQuery<Morador> typedQuery = em.createQuery(query);

        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(10);

        dataTable.setData(typedQuery.getResultList());
        Long count = dataTable.countRows(cb, query, morador, em);

        dataTable.setRecordsTotal(count);
        dataTable.setRecordsFiltered(count);
        dataTable.setDraw(draw);
        dataTable.setStart(start);
        
        return dataTable;
    }
    
}
* 
* @Override
    @SuppressWarnings("UnusedAssignment")
    public DataTable<MoradorSecundario> filtrarMoradorSecundario(FiltrosMorador filtrosMorador, int draw, int start) {
        Optional<FiltrosMorador> filtros = Optional.ofNullable(filtrosMorador);
        DataTable<MoradorSecundario> dataTable = new DataTable<>();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<MoradorSecundario> query = cb.createQuery(MoradorSecundario.class);
        Root<MoradorSecundario> moradorSecundario = query.from(MoradorSecundario.class);
        List<Predicate> predicates = new ArrayList<>();

        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getNome).orElse(""))) {
            predicates.add(cb.or(cb.like(cb.upper(moradorSecundario.get("nome")), "%" + filtrosMorador.getNome().toUpperCase() + "%")));
        }
        if (!StringUtils.isBlank(filtros.map(FiltrosMorador::getCpf).orElse(""))) {
            predicates.add(cb.or(cb.like(moradorSecundario.get("cpf"), "%" + StringUtils.getDigits(filtrosMorador.getCpf()) + "%")));
        }
        
        query.select(moradorSecundario).distinct(true);
        query.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<MoradorSecundario> typedQuery = em.createQuery(query);
        typedQuery.setFirstResult(start);
        typedQuery.setMaxResults(10);
        dataTable.setData(typedQuery.getResultList());
        Long count = dataTable.countRows(cb, query, moradorSecundario, em);

        dataTable.setRecordsTotal(count);
        dataTable.setRecordsFiltered(count);
        dataTable.setDraw(draw);
        dataTable.setStart(start);
        
        return dataTable;
    }
 */