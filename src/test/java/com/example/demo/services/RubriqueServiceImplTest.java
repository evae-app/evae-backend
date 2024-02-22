package com.example.demo.services;

import com.example.demo.DTO.RubriqueDTO;
import com.example.demo.exception.DuplicateEntityException;
import com.example.demo.exception.NotFoundEntityException;
import com.example.demo.exception.UsedEntityException;
import com.example.demo.models.Enseignant;
import com.example.demo.models.Rubrique;
import com.example.demo.models.RubriqueEvaluation;
import com.example.demo.models.RubriqueQuestion;
import com.example.demo.repositories.RubriqueRepository;
import com.example.demo.services.RubriqueService;
import com.example.demo.services.RubriqueServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class RubriqueServiceImplTest {

    @Mock
    private RubriqueRepository rubriqueRepository;

    @InjectMocks
    private RubriqueService rubriqueService = new RubriqueServiceImpl();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllRubriques() {
        // Arrange
        Rubrique rubrique1 = new Rubrique();
        rubrique1.setId(1);
        rubrique1.setDesignation("Rubrique 1");

        Rubrique rubrique2 = new Rubrique();
        rubrique2.setId(2);
        rubrique2.setDesignation("Rubrique 2");

        List<Rubrique> expectedRubriques = Arrays.asList(rubrique1, rubrique2);
        when(rubriqueRepository.findAll()).thenReturn(expectedRubriques);

        // Act
        List<RubriqueDTO> actualRubriques = rubriqueService.getAllRubriques();

        // Assert
        assertEquals(expectedRubriques, actualRubriques);
        verify(rubriqueRepository, times(1)).findAll();
    }

    @Test
    public void testGetRubriqueById() {
        // Arrange
        Rubrique expectedRubrique = new Rubrique();
        expectedRubrique.setId(1);
        expectedRubrique.setDesignation("Rubrique 1");
        when(rubriqueRepository.findById(1)).thenReturn(Optional.of(expectedRubrique));

        // Act
        Rubrique actualRubrique = rubriqueService.getRubriqueById(1);

        // Assert
        assertEquals(expectedRubrique, actualRubrique);
        verify(rubriqueRepository, times(1)).findById(1);
    }


    @Test
    public void testCreateRubriqueNoEnseignant() {
        // Arrange
        Rubrique rubrique = new Rubrique();
        rubrique.setDesignation("Test Rubrique");
        rubrique.setNoEnseignant(null);

        when(rubriqueRepository.existsByDesignation(rubrique.getDesignation())).thenReturn(false);
        when(rubriqueRepository.save(rubrique)).thenReturn(rubrique);

        // Act
        Rubrique createdRubrique = rubriqueService.createRubrique(rubrique);

        // Assert
        assertEquals("RBS", createdRubrique.getType());
        verify(rubriqueRepository, times(1)).existsByDesignation(rubrique.getDesignation());
        verify(rubriqueRepository, times(1)).save(rubrique);
    }

    @Test
    public void testCreateRubriqueWithEnseignant() {
        // Arrange
        Rubrique rubrique = new Rubrique();
        rubrique.setDesignation("Test Rubrique");
        rubrique.setNoEnseignant(new Enseignant());

        when(rubriqueRepository.existsByDesignation(rubrique.getDesignation())).thenReturn(false);
        when(rubriqueRepository.save(rubrique)).thenReturn(rubrique);

        // Act
        Rubrique createdRubrique = rubriqueService.createRubrique(rubrique);

        // Assert
        assertEquals("RBP", createdRubrique.getType());
        verify(rubriqueRepository, times(1)).existsByDesignation(rubrique.getDesignation());
        verify(rubriqueRepository, times(1)).save(rubrique);
    }

    @Test
    public void testCreateRubriqueDuplicateDesignation() {
        // Arrange
        Rubrique rubrique = new Rubrique();
        rubrique.setDesignation("Test Rubrique");

        when(rubriqueRepository.existsByDesignation(rubrique.getDesignation())).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateEntityException.class, () -> rubriqueService.createRubrique(rubrique));
        verify(rubriqueRepository, times(1)).existsByDesignation(rubrique.getDesignation());
        verify(rubriqueRepository, never()).save(any(Rubrique.class));
    }

    @Test
    public void testUpdateExistingRubrique() {
        // Arrange
        Integer rubriqueId = 1;
        Rubrique existingRubrique = new Rubrique();
        existingRubrique.setId(rubriqueId);
        existingRubrique.setDesignation("Existing Rubrique");

        Rubrique updatedRubrique = new Rubrique();
        updatedRubrique.setId(rubriqueId);
        updatedRubrique.setDesignation("Updated Rubrique");

        when(rubriqueRepository.existsById(rubriqueId)).thenReturn(true);
        when(rubriqueRepository.findById(rubriqueId)).thenReturn(java.util.Optional.of(existingRubrique));
        when(rubriqueRepository.existsByDesignationAndIdNot(updatedRubrique.getDesignation(), rubriqueId)).thenReturn(false);
        when(rubriqueRepository.save(updatedRubrique)).thenReturn(updatedRubrique);

        // Act
        Rubrique result = rubriqueService.updateRubrique(rubriqueId, updatedRubrique);

        // Assert
        assertEquals(updatedRubrique, result);
        verify(rubriqueRepository, times(1)).existsById(rubriqueId);
        verify(rubriqueRepository, times(1)).findById(rubriqueId);
        verify(rubriqueRepository, times(1)).existsByDesignationAndIdNot(updatedRubrique.getDesignation(), rubriqueId);
        verify(rubriqueRepository, times(1)).save(updatedRubrique);
    }

    @Test
    public void testUpdateRubriqueDuplicateDesignation() {
        // Arrange
        Integer rubriqueId = 1;
        Rubrique existingRubrique = new Rubrique();
        existingRubrique.setId(rubriqueId);
        existingRubrique.setDesignation("Existing Rubrique");

        Rubrique updatedRubrique = new Rubrique();
        updatedRubrique.setId(rubriqueId);
        updatedRubrique.setDesignation("Existing Rubrique"); // Same designation as existing rubrique

        when(rubriqueRepository.existsById(rubriqueId)).thenReturn(true);
        when(rubriqueRepository.findById(rubriqueId)).thenReturn(java.util.Optional.of(existingRubrique));
        when(rubriqueRepository.existsByDesignationAndIdNot(updatedRubrique.getDesignation(), rubriqueId)).thenReturn(true);

        // Act & Assert
        assertThrows(DuplicateEntityException.class, () -> rubriqueService.updateRubrique(rubriqueId, updatedRubrique));
        verify(rubriqueRepository, times(1)).existsById(rubriqueId);
        verify(rubriqueRepository, times(1)).findById(rubriqueId);
        verify(rubriqueRepository, times(1)).existsByDesignationAndIdNot(updatedRubrique.getDesignation(), rubriqueId);
        verify(rubriqueRepository, never()).save(updatedRubrique);
    }


    @Test
    public void testUpdateRubriqueByDesignationSuccess() {
        // Arrange
        String designation = "Existing Rubrique";
        Rubrique existingRubrique = new Rubrique();
        existingRubrique.setId(1);
        existingRubrique.setDesignation(designation);

        Rubrique updatedRubrique = new Rubrique();
        updatedRubrique.setId(1);
        updatedRubrique.setDesignation("Updated Rubrique");

        // Simuler le comportement de la méthode findByDesignation
        when(rubriqueRepository.findByDesignation(designation)).thenReturn(Optional.of(existingRubrique));

        // Simuler le comportement de la méthode save
        when(rubriqueRepository.save(any(Rubrique.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Rubrique result = rubriqueService.updateRubriqueByDesignation(designation, updatedRubrique);

        // Assert
        assertNotNull(result);
        assertEquals(updatedRubrique.getId(), result.getId());
        assertEquals(updatedRubrique.getDesignation(), result.getDesignation());
        verify(rubriqueRepository, times(1)).findByDesignation(designation);
        verify(rubriqueRepository, times(1)).save(existingRubrique);
    }



    @Test
    public void testUpdateRubriqueByDesignationNotFound() {
        // Arrange
        String designation = "Non-existent Rubrique";
        when(rubriqueRepository.findByDesignation(designation)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundEntityException.class, () -> rubriqueService.updateRubriqueByDesignation(designation, new Rubrique()));
        verify(rubriqueRepository, times(1)).findByDesignation(designation);
        verify(rubriqueRepository, never()).save(any(Rubrique.class));
    }


    @Test
    void deleteRubrique_Success() {
        // Given
        Rubrique rubrique = new Rubrique();
        rubrique.setId(1);

        when(rubriqueRepository.existsById(1)).thenReturn(true);
        when(rubriqueRepository.findById(1)).thenReturn(Optional.of(rubrique));

        // When
        rubriqueService.deleteRubrique(1);

        // Then
        verify(rubriqueRepository, times(1)).deleteById(1);
    }

    @Test
    void deleteRubriqueByDesignation_Success() {
        // Given
        String designation = "designation";
        Rubrique rubrique = new Rubrique();
        rubrique.setId(1);
        rubrique.setDesignation(designation);
        // Initialisez les collections avec des ensembles vides
        rubrique.setRubriqueEvaluations(new HashSet<>());
        rubrique.setRubriqueQuestions(new HashSet<>());

        when(rubriqueRepository.findByDesignation(any())).thenReturn(Optional.of(rubrique));
        doNothing().when(rubriqueRepository).deleteById(any());

        // When
        rubriqueService.deleteRubriqueByDesignation(designation);

        // Then
        verify(rubriqueRepository, times(1)).findByDesignation(designation);
        verify(rubriqueRepository, times(1)).deleteById(rubrique.getId());
    }






    @Test
    void deleteRubriqueByDesignation_NotFound() {
        // Given
        String designation = "Test";

        when(rubriqueRepository.findByDesignation(designation)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(NotFoundEntityException.class, () -> {
            rubriqueService.deleteRubriqueByDesignation(designation);
        });
        verify(rubriqueRepository, never()).deleteById(anyInt());
    }






}