package com.androsov.itmo_blps.servicies;

import com.androsov.itmo_blps.dto.requests.VacancySearchParams;
import com.androsov.itmo_blps.dto.requests.VacancyCreateRequest;
import com.androsov.itmo_blps.model.User;
import com.androsov.itmo_blps.model.entities.Vacancy;
import com.androsov.itmo_blps.repositories.VacancyRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
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

        if(!user.getRole().getName().equals("ROLE_HR"))
            throw new AccessDeniedException("User with role " + user.getRole().getName() + " cant create resume");

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
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Vacancy> query = criteriaBuilder.createQuery(Vacancy.class);

        Root<Vacancy> root = query.from(Vacancy.class);
        List<Predicate> predicates = new ArrayList<>();

        // Keywords search in name, description
        if (params.getKeyWords() != null && !params.getKeyWords().isEmpty()) {
            List<String> keywords = params.getKeyWords();
            Predicate[] keywordPredicates = keywords.stream()
                    // WHERE (NAME LIKE %keywords%) OR (DESCRIPTION LIKE %keywords%)
                    .map(keyword -> criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("name")), "%" + keyword.toLowerCase() + "%"),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("description")), "%" + keyword.toLowerCase() + "%")
                    ))
                    .toArray(Predicate[]::new);

            predicates.add(criteriaBuilder.or(keywordPredicates));
        }

        // If specified cities
        if (params.getCities() != null && !params.getCities().isEmpty()) {
            predicates.add(root.get("city").in(params.getCities()));
        }

        // Salary in specified range
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

        // All this combined by AND
        query.where(criteriaBuilder.and(predicates.toArray(new Predicate[0])));

        List<Vacancy> results = entityManager
                .createQuery(query)
                .setFirstResult(pageable.getPageNumber() * pageable.getPageSize()) // offset
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        return results;
    }

}
