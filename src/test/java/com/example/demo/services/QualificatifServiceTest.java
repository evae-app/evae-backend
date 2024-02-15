package com.example.demo.services;
import com.example.demo.exception.QualificatifAlreadyExistsException;
import com.example.demo.exception.QualificatifNotFoundException;
import com.example.demo.models.Qualificatif;
import com.example.demo.repositories.QualificatifRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class QualificatifServiceTest {

    @Mock
    private QualificatifRepository qualificatifRepository;



    @InjectMocks
    private QualificatifService qualificatifService;

    @Test
    void saveQualificatif_Success() {
        // Given
        Qualificatif qualificatif = new Qualificatif();
        qualificatif.setMinimal("Minimal Value");
        qualificatif.setMaximal("Maximal Value");

        when(qualificatifRepository.existsByMaximalOrMinimal(anyString(), anyString())).thenReturn(false);
        when(qualificatifRepository.save(any(Qualificatif.class))).thenReturn(qualificatif);

        // When
        Qualificatif savedQualificatif = qualificatifService.saveQualificatif(qualificatif);

        // Then
        assertNotNull(savedQualificatif);
        assertEquals("Minimal Value", savedQualificatif.getMinimal());
        assertEquals("Maximal Value", savedQualificatif.getMaximal());
    }

    @Test
    void saveQualificatif_AlreadyExists() {
        // Given
        Qualificatif qualificatif = new Qualificatif();
        qualificatif.setMinimal("Minimal Value");
        qualificatif.setMaximal("Maximal Value");

        when(qualificatifRepository.existsByMaximalOrMinimal(anyString(), anyString())).thenReturn(true);

        // When, Then
        assertThrows(QualificatifAlreadyExistsException.class, () -> {
            qualificatifService.saveQualificatif(qualificatif);
        });
    }

    @Test
    void getQualificatifs_Success() {
        // Given
        List<Qualificatif> qualificatifs = new ArrayList<>();
        Qualificatif qualificatif1 = new Qualificatif();
        qualificatif1.setMinimal("Minimal 1");
        qualificatif1.setMaximal("Maximal 1");
        qualificatifs.add(qualificatif1);

        Qualificatif qualificatif2 = new Qualificatif();
        qualificatif2.setMinimal("Minimal 2");
        qualificatif2.setMaximal("Maximal 2");
        qualificatifs.add(qualificatif2);

        when(qualificatifRepository.findAll()).thenReturn(qualificatifs);

        // When
        List<Qualificatif> retrievedQualificatifs = qualificatifService.getQualificatifs();

        // Then
        assertEquals(2, retrievedQualificatifs.size());
        assertEquals("Minimal 1", retrievedQualificatifs.get(0).getMinimal());
        assertEquals("Maximal 2", retrievedQualificatifs.get(1).getMaximal());
    }

    @Test
    void getQualificatifById_Success() {
        // Given
        Qualificatif qualificatif = new Qualificatif();
        qualificatif.setId(1);
        qualificatif.setMinimal("Minimal Value");
        qualificatif.setMaximal("Maximal Value");

        when(qualificatifRepository.findById(1)).thenReturn(Optional.of(qualificatif));

        // When
        Qualificatif retrievedQualificatif = qualificatifService.getQualificatifById(1);

        // Then
        assertNotNull(retrievedQualificatif);
        assertEquals(1, retrievedQualificatif.getId());
        assertEquals("Minimal Value", retrievedQualificatif.getMinimal());
        assertEquals("Maximal Value", retrievedQualificatif.getMaximal());
    }

    @Test
    void getQualificatifById_NotFound() {
        // Given
        when(qualificatifRepository.findById(1)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(QualificatifNotFoundException.class, () -> {
            qualificatifService.getQualificatifById(1);
        });
    }

    /*@Test
    void deleteQualificatif_Success() {
        // Given
        int id = 1;
        when(qualificatifRepository.existsById(id)).thenReturn(true);

        // When
        String result = qualificatifService.deleteQualificatif(id);

        // Then
        assertEquals("Qualificatif supprime || 1", result);
        verify(qualificatifRepository, times(1)).deleteById(id);
    }*/

    /*@Test
    void deleteQualificatif_NotFound() {
        // Given
        int id = 1;
        when(qualificatifRepository.existsById(id)).thenReturn(false);

        // When, Then
        assertThrows(QualificatifNotFoundException.class, () -> {
            qualificatifService.deleteQualificatif(id);
        });
        verify(qualificatifRepository, never()).deleteById(id);
    }*/

    @Test
    void updateQualificatif_Success() {
        // Given
        Qualificatif existingQualificatif = new Qualificatif();
        existingQualificatif.setId(1);
        existingQualificatif.setMinimal("Minimal Value");
        existingQualificatif.setMaximal("Maximal Value");

        Qualificatif updatedQualificatif = new Qualificatif();
        updatedQualificatif.setId(1);
        updatedQualificatif.setMinimal("Updated Minimal Value");
        updatedQualificatif.setMaximal("Updated Maximal Value");

        when(qualificatifRepository.findById(1)).thenReturn(Optional.of(existingQualificatif));
        when(qualificatifRepository.existsByMaximalOrMinimal(anyString(), anyString())).thenReturn(false);
        when(qualificatifRepository.existsByMaximal(anyString())).thenReturn(false);
        when(qualificatifRepository.existsByMinimal(anyString())).thenReturn(false);
        when(qualificatifRepository.save(existingQualificatif)).thenReturn(existingQualificatif);

        // When
        Qualificatif result = qualificatifService.updateQualificatif(updatedQualificatif);

        // Then
        assertNotNull(result);
        assertEquals(updatedQualificatif.getId(), result.getId());
        assertEquals(updatedQualificatif.getMinimal(), result.getMinimal());
        assertEquals(updatedQualificatif.getMaximal(), result.getMaximal());
    }

    @Test
    void updateQualificatif_NotFound() {
        // Given
        Qualificatif updatedQualificatif = new Qualificatif();
        updatedQualificatif.setId(1);

        when(qualificatifRepository.findById(1)).thenReturn(Optional.empty());

        // When, Then
        assertThrows(QualificatifNotFoundException.class, () -> {
            qualificatifService.updateQualificatif(updatedQualificatif);
        });
    }

    @Test
    void updateQualificatif_AlreadyExists() {
        // Given
        Qualificatif existingQualificatif = new Qualificatif();
        existingQualificatif.setId(1);
        existingQualificatif.setMinimal("Minimal Value");
        existingQualificatif.setMaximal("Maximal Value");

        Qualificatif updatedQualificatif = new Qualificatif();
        updatedQualificatif.setId(1);
        updatedQualificatif.setMinimal("Updated Minimal Value");
        updatedQualificatif.setMaximal("Updated Maximal Value");

        when(qualificatifRepository.findById(1)).thenReturn(Optional.of(existingQualificatif));
        when(qualificatifRepository.existsByMaximalOrMinimal(anyString(), anyString())).thenReturn(true);

        // When, Then
        assertThrows(QualificatifAlreadyExistsException.class, () -> {
            qualificatifService.updateQualificatif(updatedQualificatif);
        });
    }


}