package br.com.backendapi.repository;

import br.com.backendapi.model.Filtro;
import br.com.backendapi.model.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PessoaRepositoryImpl implements PessoaRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Pessoa> pesquisar(Filtro filtro, Pageable pageable) {

        CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        CriteriaQuery<Pessoa> criteriaQuery = criteriaBuilder.createQuery(Pessoa.class);
        Root<Pessoa> root = criteriaQuery.from(Pessoa.class);

        List<Predicate> predicates = filtros(filtro, criteriaBuilder, root);
        Predicate[] filtros = predicates.toArray(new Predicate[predicates.size()]);

        criteriaQuery.where(filtros).orderBy(criteriaBuilder.asc(root.get("nome")));

        TypedQuery<Pessoa> typedQuery = this.entityManager.createQuery(criteriaQuery);

        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        return new PageImpl<>( typedQuery.getResultList(), pageable, getTotalRows(criteriaBuilder, root, filtros));

    }

    public List<Predicate> filtros(Filtro filtro, CriteriaBuilder criteriaBuilder, Root<Pessoa> root) {
        List<Predicate> predicates = new ArrayList<>();

        if (filtro.getId() != null)
            predicates.add(criteriaBuilder.equal(root.get("id"), filtro.getId()));

        if (!filtro.getNome().isEmpty())
            predicates.add(criteriaBuilder.like(criteriaBuilder.upper(root.get("nome")), "%" + filtro.getNome().toUpperCase() + "%"));

        if (!filtro.getCpf().isEmpty())
            predicates.add(criteriaBuilder.like(root.get("cpf"), filtro.getCpf() + "%"));

        if (!filtro.getDataNascimento().isEmpty())
            predicates.add(criteriaBuilder.equal(root.get("dataNascimento").as(LocalDate.class).as(String.class), filtro.getDataNascimento()));

        return predicates;
    }

    protected Long getTotalRows(CriteriaBuilder criteriaBuilder, Root root, Predicate[] predicates) {
        CriteriaQuery<Long> criteriaQuery = criteriaBuilder.createQuery(Long.class);
        root = criteriaQuery.from(Pessoa.class);
        criteriaQuery.select(criteriaBuilder.count(root)).where(predicates);
        return this.entityManager.createQuery(criteriaQuery).getSingleResult();
    }

}
