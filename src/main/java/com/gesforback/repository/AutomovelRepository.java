
package com.gesforback.repository;

import com.gesforback.entity.Automovel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutomovelRepository extends JpaRepository<Automovel, Long> {
    
    //@EntityGraph(attributePaths = {"fabricante"})
    //@Query("select auto from automovel auto inner join auto.fabricante where lower(auto.name) like lower(concat('%', ?1,'%'))  ")
    //Page<Automovel> findByNomeContainingIgnoreCase(String nome,Pageable pageable);
    
    //@Query(value = " select auto from Automovel auto join fetch auto.fabricante where lower(auto.nome) like lower(concat('%', :nome,'%')) ",
    //countQuery = " select count(auto) from Automovel auto join auto.fabricante where lower(auto.nome) like lower(concat('%', :nome,'%')) ")
    //Page<Automovel> findByNomeContainingIgnoreCase(@Param("nome") String nome,Pageable pageable);
    
    @EntityGraph(attributePaths = {"fabricante"})
    Page<Automovel> findByNomeContainingIgnoreCaseAndFabricanteId(String nome,Long fabricanteId, Pageable pageable);
    
}
