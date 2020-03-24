package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Visit;
import guru.springframework.sfgpetclinic.repositories.VisitRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class VisitSDJpaServiceTest {


    @Mock
    VisitRepository visitRepository;
    @InjectMocks
    VisitSDJpaService service;

    @BeforeEach
    void setUp() {

    }

    @Test
    void findAll() {
        Set<Visit> visits = new HashSet<>();
        Visit visit = new Visit();
        visits.add(visit);
        when(visitRepository.findAll()).thenReturn(visits);

        Set<Visit> visitSet = service.findAll();
      //  assertThat(visitSet).isNotNull();
        assertThat(visitSet).hasSize(1);

        verify(visitRepository).findAll();
    }

    @Test
    void findAllBDD() {
        //given
        Set<Visit> visits = new HashSet<>();
        Visit visit = new Visit();
        visits.add(visit);
        given(visitRepository.findAll()).willReturn(visits);

        //when
        Set<Visit> visitSet = service.findAll();

        //then
        assertThat(visitSet).hasSize(1);
        then(visitRepository).should().findAll();
    }

    @Test
    void findById() {

        Visit v = new Visit();
        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(v));
        Visit foundVisit =   service.findById(1L);
        assertThat(foundVisit).isNotNull();

        verify(visitRepository).findById(anyLong());
    }

    @Test
    void findByIdBDD() {

        //given
        Visit v = new Visit();
        when(visitRepository.findById(anyLong())).thenReturn(Optional.of(v));
        //when
        Visit foundVisit = service.findById(1L);

        //then
        assertThat(foundVisit).isNotNull();
        then(visitRepository).should().findById(anyLong());
    }

    @Test
    void save() {

        Object o = new Object();
        Visit v = new Visit();
        when(visitRepository.save(any(Visit.class))).thenReturn(v);
        Visit vi=service.save(v);
        verify(visitRepository).save(any(Visit.class));
        assertThat(vi).isNotNull();
    }

    @Test
    void saveBDD() {

        //given
        Object o = new Object();
        Visit v = new Visit();
        given(visitRepository.save(any(Visit.class))).willReturn(v);

        //when
        Visit vi=service.save(v);

        //then
        assertThat(vi).isNotNull();
        then(visitRepository).should().save(any(Visit.class));
    }

    @Test
    void delete() {

        Visit v = new Visit();
        service.delete(v);
        verify(visitRepository).delete(any(Visit.class));
    }

    @Test
    void deleteBDD() {

        //given
        Visit v = new Visit();
        //when
        service.delete(v);
        //then
        verify(visitRepository).delete(any(Visit.class));
        then(visitRepository).should().delete(any(Visit.class));
    }
    @Test
    void deleteById() {

        service.deleteById(1L);
        verify(visitRepository).deleteById(anyLong());
    }

    @Test
    void deleteByIdBDD() {

        //given--none

        //when
        service.deleteById(1L);
        //then
        then(visitRepository).should().deleteById(anyLong());
    }
}