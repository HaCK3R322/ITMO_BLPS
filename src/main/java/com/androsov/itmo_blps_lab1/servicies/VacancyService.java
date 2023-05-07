package com.androsov.itmo_blps_lab1.servicies;

import com.androsov.itmo_blps_lab1.dto.VacancySearchParams;
import com.androsov.itmo_blps_lab1.entities.Resume;
import com.androsov.itmo_blps_lab1.entities.ResumeVacancyLink;
import com.androsov.itmo_blps_lab1.entities.Vacancy;
import com.androsov.itmo_blps_lab1.repositories.ResumeVacancyLinkRepository;
import com.androsov.itmo_blps_lab1.repositories.VacancyRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class VacancyService {
    private VacancyRepository vacancyRepository;
    private ResumeVacancyLinkRepository resumeVacancyLinkRepository;
    private EntityManager entityManager;

    public Vacancy createVacancy(Vacancy vacancy) {
        if (vacancyRepository.existsByUserAndName(vacancy.getUser(), vacancy.getName()))
            throw new IllegalArgumentException("Vacancy with user '" + vacancy.getUser() +
                    "' and name '" + vacancy.getName() + "' already exists.");

        return vacancyRepository.save(vacancy);
    }

    public Vacancy getById(Long id) throws EntityNotFoundException {
        return vacancyRepository.findById(id).orElseThrow(EntityNotFoundException::new);
    }

    public boolean exists(Vacancy vacancy) {
        if(vacancy == null || vacancy.getId() == null) return false;

        return vacancyRepository.existsById(vacancy.getId());
    }
    public boolean existsById(Long id) {
        return vacancyRepository.existsById(id);
    }

    public List<Vacancy> searchByParams(VacancySearchParams params, Pageable pageable) {
        // create a criteria builder to construct the query
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

        // create a criteria query for the Vacancy entity
        CriteriaQuery<Vacancy> query = criteriaBuilder.createQuery(Vacancy.class);
        Root<Vacancy> root = query.from(Vacancy.class);

        // create a list of predicates based on the search parameters
        List<Predicate> predicates = new ArrayList<>();

        // search for vacancies containing all the specified keywords
        if (params.getKeyWords() != null && !params.getKeyWords().isEmpty()) {
            List<String> keywords = params.getKeyWords();
            Predicate[] keywordPredicates = keywords.stream()
                    .map(keyword -> criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + keyword.toLowerCase() + "%")
                    )).toArray(Predicate[]::new);
            predicates.add(criteriaBuilder.or(keywordPredicates));
        }

        // search for vacancies in the specified cities
        if (params.getCities() != null && !params.getCities().isEmpty()) {
            predicates.add(root.get("city").in(params.getCities()));
        }

        // search for vacancies with a salary within the specified range
        if (params.getSalaryFrom() != null && params.getSalaryTo() != null) {
            predicates.add(criteriaBuilder.between(
                    root.get("salaryFrom"), params.getSalaryFrom(), params.getSalaryTo()));
        } else if (params.getSalaryFrom() != null) {
            predicates.add(criteriaBuilder.greaterThanOrEqualTo(
                    root.get("salaryFrom"), params.getSalaryFrom()));
        } else if (params.getSalaryTo() != null) {
            predicates.add(criteriaBuilder.lessThanOrEqualTo(
                    root.get("salaryTo"), params.getSalaryTo()));
        }

        // combine all the predicates with an "AND" operator
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        // execute the query and return the results
        List<Vacancy> results = entityManager
                .createQuery(query)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize()) // offset
                .setMaxResults(pageable.getPageSize())
                .getResultList();
        return results;
    }

}
