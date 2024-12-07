package backend.academy.flame.cli;

import backend.academy.flame.model.Configs;
import backend.academy.flame.model.TransformationFunction;
import java.util.Scanner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ConsoleCommunicatorTest {

    private ConsoleCommunicator consoleCommunicator;

    @BeforeEach
    void setUp() {
        consoleCommunicator = new ConsoleCommunicator();
    }

    @Test
    void testCommunicate_ValidInput() {
        Scanner mockScanner = mock(Scanner.class);
        consoleCommunicator.scanner(mockScanner);

        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt())
            .thenReturn(500)  // height
            .thenReturn(800)  // width
            .thenReturn(1000) // iterations
            .thenReturn(3);   // transformation choice (SPHERE)

        var configs = consoleCommunicator.communicate();

        assertNotNull(configs);
        assertEquals(500, configs.height());
        assertEquals(800, configs.width());
        assertEquals(1000, configs.iterationCount());
        assertEquals(TransformationFunction.SPHERE, configs.transform());
    }



    @Test
    void testCommunicate_InvalidTransformationChoice() {
        Scanner mockScanner = mock(Scanner.class);
        consoleCommunicator.scanner(mockScanner);

        when(mockScanner.hasNextInt()).thenReturn(true);
        when(mockScanner.nextInt())
            .thenReturn(500)
            .thenReturn(800)
            .thenReturn(1000)
            .thenReturn(6, 2);

        Configs configs = consoleCommunicator.communicate();

        assertNotNull(configs);
        assertEquals(500, configs.height());
        assertEquals(800, configs.width());
        assertEquals(1000, configs.iterationCount());
        assertEquals(TransformationFunction.HEART, configs.transform());
    }

}
