package idwall.desafio.string;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static idwall.desafio.string.UnitTestResourceUtils.readResource;
import static org.junit.Assert.*;

/**
 * Created by marcos.salomao.
 */
public class DesafioStringUnitTest {

    @Test
    public void testDesafioString_1() {

        try {

            String inputTest = readResource("input-1-2.txt");
            String outputTest = readResource("output-parte1.txt");

            assertNotNull("Input is null", inputTest);
            assertNotNull("Output test is null", outputTest);

            StringFormatter stringFormatter = new IdwallFormatter();
            String outputResult = stringFormatter.format(inputTest);

            assertNotNull("Result is null", outputResult);
            assertEquals("Result is not matching", outputTest, outputResult);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @Test
    public void testDesafioString_2() {

        try {

            String inputTest = readResource("input-1-2.txt");
            String outputTest = readResource("output-parte2.txt");

            assertNotNull("Input is null", inputTest);
            assertNotNull("Output test is null", outputTest);

            StringFormatter stringFormatter = new IdwallFormatter();
            String outputResult = stringFormatter.format(inputTest, 40, true);

            assertNotNull("Result is null", outputResult);
            assertEquals("Result is not matching", outputTest, outputResult);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @Test
    public void testDesafioString_3() {

        try {

            String inputTest = readResource("input-3.txt");
            String outputTest = readResource("output-parte3.txt");

            assertNotNull("Input is null", inputTest);
            assertNotNull("Output test is null", outputTest);

            StringFormatter stringFormatter = new IdwallFormatter();
            String outputResult = stringFormatter.format(inputTest, 10);

            assertNotNull("Result is null", outputResult);
            assertEquals("Result is not matching", outputTest, outputResult);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @Test
    public void testBigWords() {

        try {

            String inputTest = readResource("input-4.txt");
            String outputTest = readResource("output-parte4.txt");

            assertNotNull("Input is null", inputTest);
            assertNotNull("Output test is null", outputTest);

            StringFormatter stringFormatter = new IdwallFormatter();
            String outputResult = stringFormatter.format(inputTest, 10, true);

            assertNotNull("Result is null", outputResult);
            assertEquals("Result is not matching", outputTest, outputResult);

        } catch (Exception e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void testLimitLessThanOne() {

        try {

            String inputTest = readResource("input-1-2.txt");
            assertNotNull("Input is null", inputTest);

            StringFormatter stringFormatter = new IdwallFormatter();
            stringFormatter.format(inputTest, 0);

        } catch (IOException|URISyntaxException e) {
            e.printStackTrace();
            fail(e.getMessage());
        }

    }

    @Test(expected = IllegalArgumentException.class)
    public void testInputNull() {

        StringFormatter stringFormatter = new IdwallFormatter();
        stringFormatter.format(null);

    }

    @Test(expected = IllegalArgumentException.class)
    public void testInputEmpty() {

        StringFormatter stringFormatter = new IdwallFormatter();
        stringFormatter.format("");

    }

}
