import org.junit.jupiter.api.Test;
import xyz.alphaline.Main;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LinesSplitterTest {

    static String[][] parser(String in) {
        return Arrays.stream(in.split("\n")).map(s -> s.split("")).toList().toArray(String[][]::new);
    }

    static boolean arrayContainsString(String[] haystack, String needle) {
        for (String s : haystack) {
            if (s.equals(needle)) {
                return true;
            }
        }

        return false;
    }


    @Test
    void LtoR() {
        String[][] in = parser("""
                123
                456
                789""");

        String[] out = Main.mapLtoR(in);
        System.out.println(Arrays.toString(out));
        assertTrue(arrayContainsString(out, "123"));
        assertTrue(arrayContainsString(out, "456"));
        assertTrue(arrayContainsString(out, "789"));
    }

    @Test
    void RtoL() {
        String[][] in = parser("""
                123
                456
                789""");

        String[] out = Main.mapRtoL(in);
        System.out.println(Arrays.toString(out));
        assertTrue(arrayContainsString(out, "321"));
        assertTrue(arrayContainsString(out, "654"));
        assertTrue(arrayContainsString(out, "987"));
    }

    @Test
    void TtoB() {
        String[][] in = parser("""
                123
                456
                789""");

        String[] out = Main.mapTtoB(in);
        System.out.println(Arrays.toString(out));
        assertTrue(arrayContainsString(out, "147"));
        assertTrue(arrayContainsString(out, "258"));
        assertTrue(arrayContainsString(out, "369"));
    }

    @Test
    void BtoT() {
        String[][] in = parser("""
                123
                456
                789""");

        String[] out = Main.mapBtoT(in);
        System.out.println(Arrays.toString(out));
        assertTrue(arrayContainsString(out, "741"));
        assertTrue(arrayContainsString(out, "852"));
        assertTrue(arrayContainsString(out, "963"));
    }

    @Test
    void LTtoRB() {
        String[][] in = parser("""
                123
                456
                789""");

        String[] out = Main.mapLTtoRB(in);
        System.out.println(Arrays.toString(out));
        assertTrue(arrayContainsString(out, "1"));
        assertTrue(arrayContainsString(out, "42"));
        assertTrue(arrayContainsString(out, "753"));
        assertTrue(arrayContainsString(out, "86"));
        assertTrue(arrayContainsString(out, "9"));
    }

    @Test
    void RBtoLT() {
        String[][] in = parser("""
                123
                456
                789""");

        String[] out = Main.mapRBtoLT(in);
        System.out.println(Arrays.toString(out));
        assertTrue(arrayContainsString(out, "1"));
        assertTrue(arrayContainsString(out, "24"));
        assertTrue(arrayContainsString(out, "357"));
        assertTrue(arrayContainsString(out, "68"));
        assertTrue(arrayContainsString(out, "9"));
    }

    @Test
    void LBtoRT() {
        String[][] in = parser("""
                123
                456
                789""");

        String[] out = Main.mapLBtoRT(in);
        System.out.println(Arrays.toString(out));
        assertTrue(arrayContainsString(out, "7"));
        assertTrue(arrayContainsString(out, "48"));
        assertTrue(arrayContainsString(out, "159"));
        assertTrue(arrayContainsString(out, "26"));
        assertTrue(arrayContainsString(out, "3"));
    }

    @Test
    void RTtoLB() {
        String[][] in = parser("""
                123
                456
                789""");

        String[] out = Main.mapRTtoLB(in);
        System.out.println(Arrays.toString(out));
        assertTrue(arrayContainsString(out, "3"));
        assertTrue(arrayContainsString(out, "62"));
        assertTrue(arrayContainsString(out, "951"));
        assertTrue(arrayContainsString(out, "84"));
        assertTrue(arrayContainsString(out, "7"));
    }




}
