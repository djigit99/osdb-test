package dev.andriip.testosdb.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/companies")
public class CompanyController {

    private final CompanyService companyService;

    @Autowired
    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }


    @GetMapping
    List<Company> getAllCompanies(
            @RequestParam(defaultValue = "id,desc") String sortBy,
            @RequestParam(defaultValue = "10") int page,
            @RequestParam(defaultValue = "10") int size) {
        return companyService.getAllCompanies(sortBy, page, size);

    }

    @GetMapping("{companyId}")
    public Optional<Company> getCompany(@PathVariable("companyId") int id) {
        return companyService.getCompany(id);
    }

    @PostMapping
    public void addCompany(@RequestBody Company company) {
        companyService.addCompany(company);
    }

    @PutMapping("{companyId}")
    public void updateCompany(
            @PathVariable("companyId") int id,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) Date founded) {
            companyService.updateCompany(id, title, founded);
    }

    @DeleteMapping("{companyId}")
    public void deleteCompany(@PathVariable("companyId") int id) {
        companyService.deleteCompany(id);
    }
}
