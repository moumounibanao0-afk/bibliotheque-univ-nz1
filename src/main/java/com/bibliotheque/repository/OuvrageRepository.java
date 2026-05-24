package com.bibliotheque.repository;

import com.bibliotheque.model.Ouvrage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface OuvrageRepository extends JpaRepository<Ouvrage, Long> {

    // Recherche par titre
    List<Ouvrage> findByTitreContainingIgnoreCase(String titre);

    // Recherche par auteur
    List<Ouvrage> findByAuteurContainingIgnoreCase(String auteur);

    // Recherche par ISBN
    Optional<Ouvrage> findByIsbn(String isbn);

    // Ouvrages disponibles
    List<Ouvrage> findByExemplairesDisponiblesGreaterThan(int nombre);

    // Recherche par catégorie
    List<Ouvrage> findByCategorieId(Long categorieId);

    // Recherche par rayon (via catégorie)
    List<Ouvrage> findByCategorieRayonIgnoreCase(String rayon);

    // ✅ Recherche avancée multi-critères (titre OU auteur OU ISBN OU description)
    @Query("SELECT o FROM Ouvrage o WHERE " +
           "LOWER(o.titre) LIKE LOWER(CONCAT('%', :motCle, '%')) OR " +
           "LOWER(o.auteur) LIKE LOWER(CONCAT('%', :motCle, '%')) OR " +
           "LOWER(o.isbn) LIKE LOWER(CONCAT('%', :motCle, '%')) OR " +
           "LOWER(o.description) LIKE LOWER(CONCAT('%', :motCle, '%'))")
    List<Ouvrage> rechercheAvancee(@Param("motCle") String motCle);

    // ✅ Recherche par catégorie nom
    @Query("SELECT o FROM Ouvrage o WHERE LOWER(o.categorie.nom) LIKE LOWER(CONCAT('%', :nom, '%'))")
    List<Ouvrage> findByCategorieNomContaining(@Param("nom") String nom);
}