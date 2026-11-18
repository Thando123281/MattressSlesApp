import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ProductCalculationsTest {

  
    public void testVat() {
        ProductCalculationsImpl calc =
                new ProductCalculationsImpl(3500);

        assertEquals(525.0, calc.GetVat(), 0.01);
    }

    
    public void testCommission() {
        ProductCalculationsImpl calc =
                new ProductCalculationsImpl(3500);

        assertEquals(175.0, calc.GetCommission(), 0.01);
    }


    public void testTotal() {
        ProductCalculationsImpl calc =
                new ProductCalculationsImpl(3500);

        assertEquals(4200.0, calc.GetTotal(), 0.01);
    }
}

