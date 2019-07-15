package cashregister;


import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CashRegisterTest {

    private ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

    private String systemOut() {
        return outContent.toString();
    }

    @Test
    public void should_print_the_real_purchase_when_call_process() {
        Item[] items = new Item[]{new Item("item1", 10.0)};
        Purchase purchase = new Purchase(items);
        CashRegister cashRegister = new CashRegister(new Printer());

        cashRegister.process(purchase);

        assertEquals(systemOut(), "item1\t10.0\n");
    }

    @Test
    public void should_print_the_stub_purchase_when_call_process() {
        CashRegister cashRegister = new CashRegister(new Printer());
        Purchase purchase = mock(Purchase.class);

        when(purchase.asString()).thenReturn("Some Items infos");
        cashRegister.process(purchase);

        assertEquals(systemOut(), "Some Items infos");
    }

    @Test
    public void should_verify_with_process_call_with_mockito() {
        CashRegister cashRegister = new CashRegister(new Printer());
        Purchase purchase = mock(Purchase.class);

        when(purchase.asString()).thenReturn("Some Items infos");
        cashRegister.process(purchase);

        verify(purchase).asString();
    }

}
