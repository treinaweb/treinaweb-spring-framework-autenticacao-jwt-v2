package br.com.treinaweb.twjobs.core.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.treinaweb.twjobs.core.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
}
