package one.xingyi.killingDragons2.nonFunctionals;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;

public class PutMetricsPrintlnTest {

    @Test
    public void testAddOneJustPrintsLine() throws UnsupportedEncodingException {
        PrintStream original = System.out;
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            System.setOut(new PrintStream(baos, true, "UTF-8"));
            PutMetrics.println().addOne("someMetric");
        } finally {
            System.setOut(original);
        }
        assertEquals("Metric [someMetric] has occured", baos.toString("UTF-8").trim());
    }
}
