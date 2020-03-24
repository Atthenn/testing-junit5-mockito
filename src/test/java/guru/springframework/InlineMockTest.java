package guru.springframework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Map;

import static org.mockito.Mockito.mock;

public class InlineMockTest {


    @Test
    void testInlineMock(){

        Map mapMock = mock(Map.class);
        Assertions.assertEquals(mapMock.size(), 0);

    }
}
