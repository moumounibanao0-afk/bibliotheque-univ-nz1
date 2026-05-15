package com.bibliotheque.repository;

import com.bibliotheque.model.Ouvrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OuvrageRepository extends JpaRepository<Ouvrage, Long> {
    List<Ouvrage> findByTitreContainingIgnoreCase(String titre);
    List<Ouvrage> findByAuteurContainingIgnoreCase(String auteur);
    Optional<Ouvrage> findByIsbn(String isbn);
    List<Ouvrage> findByExemplairesDisponiblesGreaterThan(int nombre);
}
