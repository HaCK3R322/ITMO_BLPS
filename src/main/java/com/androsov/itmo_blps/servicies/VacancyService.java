package com.androsov.itmo_blps.servicies;

import com.androsov.itmo_blps.dto.requests.VacancySearchParams;
import com.androsov.itmo_blps.dto.requests.VacancyCreateRequest;
import com.androsov.itmo_blps.model.User;
import com.androsov.itmo_blps.model.entities.Vacancy;
import com.androsov.itmo_blps.repositories.VacancyRepository;
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
import java.util.Optional;

@Service
@AllArgsConstructor
public class VacancyService {
    private VacancyRepository vacancyRepository;
    private EntityManager entityManager;
    private UserService userService;

    public Vacancy createFromRequest(VacancyCreateRequest request) throws IllegalArgumentException {
        User user = userService.getCurrentUser();

        if (vacancyRepository.existsByUserIdAndName(user.getId(), request.getName()))
            throw new IllegalArgumentException("Vacancy with user '" + user.getUsername() +
                    "' and name '" + request.getName() + "' already exists.");

        Vacancy vacancy = new Vacancy();

        vacancy.setUserId(user.getId());
        vacancy.setName(request.getName());
        vacancy.setDescription(request.getDescription());
        vacancy.setCity(request.getCity());
        vacancy.setAddress(request.getAddress());
        vacancy.setRequiredWorkExperience(request.getRequiredWorkExperience());
        vacancy.setResponsibilities(request.getResponsibilities());
        vacancy.setRequirements(request.getRequirements());
        vacancy.setConditions(request.getConditions());
        vacancy.setSalaryFrom(request.getSalaryFrom());
        vacancy.setSalaryTo(request.getSalaryTo());

        return vacancyRepository.save(vacancy);
    }

    public Vacancy getById(Long id) throws EntityNotFoundException {
        Optional<Vacancy> vacancyOptional = vacancyRepository.findById(id);

        if(vacancyOptional.isEmpty())
            throw new EntityNotFoundException("Vacancy with id " + id + " not found!");

        // TODO: if there will be admin, he cant do that shit

        return vacancyOptional.get();
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
