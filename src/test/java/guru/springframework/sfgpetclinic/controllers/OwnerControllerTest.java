package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith({MockitoExtension.class})
class OwnerControllerTest {

    public static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    public static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    @Mock
    OwnerService ownerService;

    @Mock
    BindingResult bindingResult;

    @InjectMocks
    OwnerController ownerController;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;


    @Test
    void processCreationFormWithError() {

        Owner owner = new Owner(1L,"atena","Kalaki");
        given(bindingResult.hasErrors()).willReturn(true);

       assertEquals(OWNERS_CREATE_OR_UPDATE_OWNER_FORM,ownerController.processCreationForm(owner,bindingResult));
    }

    @Test
    void processCreationFormWithOutError() {

        Owner owner = new Owner(5L,"atena","Kalaki");
        given(bindingResult.hasErrors()).willReturn(false);
        given(ownerService.save(owner)).willReturn(owner);

       // ownerController.processCreationForm(owner,bindingResult)
       // Owner savedOwner = ownerService.save(owner);

        assertEquals(REDIRECT_OWNERS_5,ownerController.processCreationForm(owner,bindingResult));
        then(ownerService).should().save(owner);
    }

    @Test
    void processFindFormWildcardString(){

        Owner owner = new Owner(5L,"atena","Kalaki");
        List<Owner> ownerList = new ArrayList<>();
        final ArgumentCaptor<String> captor =  ArgumentCaptor.forClass(String.class);

        given(ownerService.findAllByLastNameLike(captor.capture())).willReturn(ownerList);

        String viewName =  ownerController.processFindForm(owner,bindingResult,null);

        assertEquals(captor.getValue(),"%Kalaki%");
    }

    @Test
    void processFindFormWildcardStringAnnotation(){

        Owner owner = new Owner(5L,"atena","Kalaki");
        List<Owner> ownerList = new ArrayList<>();
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture())).willReturn(ownerList);

        String viewName =  ownerController.processFindForm(owner,bindingResult,null);

        assertEquals(stringArgumentCaptor.getValue(),"%Kalaki%");
    }
}