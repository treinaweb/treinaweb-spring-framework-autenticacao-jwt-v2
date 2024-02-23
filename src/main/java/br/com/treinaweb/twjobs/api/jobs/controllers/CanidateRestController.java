package br.com.treinaweb.twjobs.api.jobs.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.treinaweb.twjobs.core.exceptions.JobNotFoundException;
import br.com.treinaweb.twjobs.core.repositories.JobRepository;
import br.com.treinaweb.twjobs.core.services.auth.AuthenticatedUser;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/jobs/{id}")
public class CanidateRestController {

    private final JobRepository jobRepository;

    @PostMapping("/apply")
    @PreAuthorize("hasAuthority('CANDIDATE')")
    public ResponseEntity<?> apply(@PathVariable Long id, Authentication authentication) {
        var job = jobRepository.findById(id)
            .orElseThrow(JobNotFoundException::new);
        var authenticateUser = (AuthenticatedUser) authentication.getPrincipal();
        job.getCandidates().add(authenticateUser.getUser());
        jobRepository.save(job);
        return ResponseEntity.noContent().build();
    }
    
}
