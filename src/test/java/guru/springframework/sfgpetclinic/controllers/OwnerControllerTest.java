package guru.springframework.sfgpetclinic.controllers;

import guru.springframework.sfgpetclinic.fauxspring.BindingResult;
import guru.springframework.sfgpetclinic.fauxspring.Model;
import guru.springframework.sfgpetclinic.model.Owner;
import guru.springframework.sfgpetclinic.services.OwnerService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith({MockitoExtension.class})
class OwnerControllerTest {

    public static final String OWNERS_CREATE_OR_UPDATE_OWNER_FORM = "owners/createOrUpdateOwnerForm";
    public static final String REDIRECT_OWNERS_5 = "redirect:/owners/5";
    @Mock
    OwnerService ownerService;

    @Mock
    BindingResult bindingResult;
    @Mock
    Model model;


    @InjectMocks
    OwnerController ownerController;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @BeforeEach
    void setUp() {
        given(ownerService.findAllByLastNameLike(stringArgumentCaptor.capture()))
                .willAnswer(invocationOnMock -> {
                    List<Owner> ownerList = new ArrayList<>();
                    String name = invocationOnMock.getArgument(0);
                    if(name.equals("%Kalaki%")){
                        ownerList.add(new Owner(1l,"atena","%Kalaki%"));
                        return ownerList;
                    }
                    else if(name.equals("%DontFindMe%")){
                        return  ownerList;
                    }
                    else if(name.equals("%findMe%")){
                        ownerList.add(new Owner(1l,"atena","%Kalaki%"));

                        ownerList.add(new Owner(2l,"atena","%Kalaki2%"));

                        return  ownerList;
                    }
                    throw  new RuntimeException("Invalid Argument");
                });
    }

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

        Owner owner = new Owner(1L,"atena","Kalaki");

        String viewName =  ownerController.processFindForm(owner,bindingResult,model);

        assertEquals(stringArgumentCaptor.getValue(),"%Kalaki%");
        assertEquals("redirect:/owners/1",viewName);

        verifyZeroInteractions(model);
    }

    @Test
    void processFindFormWildcardNotFound(){

        Owner owner = new Owner(1L,"atena","DontFindMe");

        String viewName =  ownerController.processFindForm(owner,bindingResult,null);

        verifyNoMoreInteractions(ownerService);
        assertEquals(stringArgumentCaptor.getValue(),"%DontFindMe%");
        assertEquals("owners/findOwners",viewName);
        verifyZeroInteractions(model);
    }


    @Test
    void processFindFormWildcardFound(){

        Owner owner = new Owner(1L,"atena","findMe");

        InOrder inOrder = Mockito.inOrder(ownerService,model);
        String viewName =  ownerController.processFindForm(owner,bindingResult,model);

        assertEquals(stringArgumentCaptor.getValue(),"%findMe%");
        assertEquals("owners/ownersList",viewName);

        inOrder.verify(ownerService).findAllByLastNameLike(anyString());
        inOrder.verify(model,times(1)).addAttribute(anyString(),anyList());
        verifyNoMoreInteractions(model);

    }
}