package com.example.apispringrepository02.controllers;

import com.example.apispringrepository02.entities.ProgrammingLanguage;
import com.example.apispringrepository02.repositoris.ProgrammingLanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@RequestMapping("/p")
public class ProgrammingLanguageController {

    @Autowired
    ProgrammingLanguageRepository progrRepo;

    @GetMapping(path = "/get")
    public Page<ProgrammingLanguage> getAll(@RequestParam(required = false) Optional<Integer> page, @RequestParam(required = false) Optional<Integer> size) {
        Pageable pageable=null;
        if (page.isPresent() && size.isPresent()) {
            Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "name"));
            pageable = PageRequest.of(page.get(), size.get(),sort);
            Page<ProgrammingLanguage> getProgramm = progrRepo.findAll(pageable);
            return getProgramm;
        } else {
            Page<ProgrammingLanguage> pro = Page.empty();
            return pro;
        }
    }

    @PostMapping
    public ProgrammingLanguage createProgramming(@RequestBody ProgrammingLanguage programmingLanguage) {
        ProgrammingLanguage programmingLanguage1 = progrRepo.save(programmingLanguage);
        return programmingLanguage1;
    }

    @PutMapping("/{id}")
    public ProgrammingLanguage progUpdate(@PathVariable Long id, @RequestBody ProgrammingLanguage programmingLanguage) throws Exception {
        if (progrRepo.existsById(id)) {
            programmingLanguage.setId(id);
            ProgrammingLanguage newProgramm = progrRepo.save(programmingLanguage);
            return newProgramm;
        } else
            throw new Exception("Entity not found");
    }
}
