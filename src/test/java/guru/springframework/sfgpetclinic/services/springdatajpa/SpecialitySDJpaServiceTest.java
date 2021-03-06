package guru.springframework.sfgpetclinic.services.springdatajpa;

import guru.springframework.sfgpetclinic.model.Speciality;
import guru.springframework.sfgpetclinic.repositories.SpecialtyRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import static org.mockito.ArgumentMatchers.*;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialitySDJpaServiceTest {

    @Mock()
    SpecialtyRepository specialtyRepository;

    @InjectMocks
    SpecialitySDJpaService service;



    @Test
    void testDeleteByObject(){
        Speciality speciality = new Speciality();
        service.delete(speciality);
        verify(specialtyRepository,times(1)).delete(any(Speciality.class));
    }

    @Test
    void testDeleteByObjectBDD(){
        //given
        Speciality speciality = new Speciality();
        //when
        service.delete(speciality);
        //then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void findByIdTest(){

        Speciality speciality = new Speciality();

        when(specialtyRepository.findById(1L)).thenReturn(Optional.of(speciality));

        Speciality foundSpeciality = service.findById(1L);

        assertThat(foundSpeciality).isNotNull();
          verify(specialtyRepository,times(1)).findById(anyLong());
    }

    @Test
    void findByIdTestBDD(){
        //given
        Speciality speciality = new Speciality();
        given(specialtyRepository.findById(1L)).willReturn(Optional.of(speciality));

        //when
        Speciality foundSpeciality = service.findById(1L);

        //then
        assertThat(foundSpeciality).isNotNull();
        then(specialtyRepository).should(timeout(100)).findById(anyLong());
        then(specialtyRepository).shouldHaveNoMoreInteractions();
    }
    @Test
    void deleteById() {
        service.deleteById(1l);
        service.deleteById(1l);
        verify(specialtyRepository, times(2)).deleteById(1l);
    }

    @Test
    void deleteByIdBDD() {

        //given -none

        //when
        service.deleteById(1l);
        service.deleteById(1l);
        //then
        then(specialtyRepository).should(timeout(200).times(2)).deleteById(1l);
    }

    @Test
    void deleteObject(){

        //when
        service.delete(new Speciality());

        //then
        then(specialtyRepository).should().delete(any(Speciality.class));
    }

    @Test
    void testDoThrow(){

        doThrow(new RuntimeException("boooom")).when(specialtyRepository).delete(any());
        assertThrows(RuntimeException.class, () -> specialtyRepository.delete(new Speciality()));
        verify(specialtyRepository).delete(any());

    }

    @Test
    void testFindByIDThrowsBDD(){

        given(specialtyRepository.findById(1l)).willThrow(new RuntimeException("boooom"));

        assertThrows(RuntimeException.class, () -> service.findById(anyLong()));

        then(specialtyRepository).should().findById(anyLong());

    }
    @Test
    void testDeleteBDD(){

        willThrow(new RuntimeException("boooom")).given(specialtyRepository).delete(any());

        assertThrows(RuntimeException.class, () -> service.delete(new Speciality()));

        then(specialtyRepository).should().delete(any());

    }

    @Test
    void testSaveLambda(){
        //given
        final  String Match_Me = "Match_Me";
        Speciality speciality = new Speciality();
        speciality.setDescription(Match_Me);

        Speciality saveSpeciality = new Speciality();
        saveSpeciality.setId(1l);


        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(Match_Me)))).willReturn(saveSpeciality);

        //when
        Speciality returnSpeciality = service.save(speciality);

        //then
        assertThat(returnSpeciality.getId()).isEqualTo(1l);
    }

    @Test
    @MockitoSettings(strictness = Strictness.LENIENT)
    void testSaveLambdaNoMatch() {
        //given
        final String Match_Me = "Match_Me";
        Speciality speciality = new Speciality();
        speciality.setDescription("Not Match");

        Speciality saveSpeciality = new Speciality();
        saveSpeciality.setId(1l);


        given(specialtyRepository.save(argThat(argument -> argument.getDescription().equals(Match_Me)))).willReturn(saveSpeciality);

        //when
        Speciality returnSpeciality = service.save(speciality);

        //then
        assertNull(returnSpeciality);
    }
}