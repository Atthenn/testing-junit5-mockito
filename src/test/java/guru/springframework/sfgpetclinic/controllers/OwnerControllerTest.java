package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
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
}