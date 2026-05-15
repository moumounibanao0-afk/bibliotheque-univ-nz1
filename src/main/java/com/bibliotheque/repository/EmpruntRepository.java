package com.bibliotheque.repository;

import com.bibliotheque.model.Emprunt;
import com.bibliotheque.model.StatutEmprunt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface EmpruntRepository extends JpaRepository<Emprunt, Long> {
    List<Emprunt> findByEtudiantId(Long etudiantId);
    List<Emprunt> findByStatut(StatutEmprunt statut);
    boolean existsByEtudiantIdAndOuvrageIdAndStatut(Long etudiantId, Long ouvrageId, StatutEmprunt statut);
}
