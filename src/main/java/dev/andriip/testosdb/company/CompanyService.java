package dev.andriip.testosdb.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Stream;

@Service
public class CompanyService {
    private final CompanyRepository companyRepository;

    @Autowired
    public CompanyService(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    public Optional<Company> getCompany(int id) {
        return companyRepository.findById(id);
    }

    public void addCompany(Company company) {
        companyRepository.save(company);
    }

    @Transactional
    public void updateCompany(int id, String title, Date founded) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "company with id " + id + " does not exist."
                ));

        if (title != null &&
                title.length() > 0 && !Objects.equals(company.getTitle(), title)) {
            company.setTitle(title);
        }

        if (founded != null && !Objects.equals(company.getFounded(), founded)) {
            company.setFounded(founded);
        }
    }

    public void deleteCompany(int id) {
        Company company = companyRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException(
                        "company with id " + id + " does not exist."
                ));

        companyRepository.delete(company);
    }

    public List<Company> getAllCompanies(String sortBy, int page, int size) {
        String[] sortByParams = sortBy.split(",");
        String by = sortByParams[0], order = sortByParams[1];

        if (sortByParams.length > 2 ||
                Stream.of("id", "title", "founded").noneMatch(by::equalsIgnoreCase) ||
                Stream.of("asc", "desc").noneMatch(order::equalsIgnoreCase)) {
            throw new IllegalStateException("Incorrect sort parameter " + sortBy);
        }

        Page<Company> pageContent =  companyRepository.findAll(
                PageRequest.of(page, size, "asc".equalsIgnoreCase(order) ? Sort.by(by).ascending() : Sort.by(by).descending())
        );

        if (pageContent.hasContent()) {
            return pageContent.getContent();
        } else {
            return Collections.emptyList();
        }
    }
}
