package tests.algorithms.eulerian;

import de.hawh.ld.GKA01.algorithms.eulerian_circuits.Fleury;
import org.junit.jupiter.api.Test;
import tests.algorithms.eulerian.superclasses.EulerianCircuitTest;

public class FleuryTest extends EulerianCircuitTest {

    @Test
    void testCorrectness() {
        super.testEulerianCircuit(new Fleury());
    }
}
