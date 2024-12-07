import org.junit.jupiter.api.Test;
import xyz.alphaline.Equation;

import java.util.List;

public class Tests {

    @Test
    void generateOpsListsTest() {
        List<Integer> integers = List.of(1, 2, 3);
        Equation.recursiveGenerateOpsList(null, integers.size() - 1).forEach(System.out::println);
    }
}
