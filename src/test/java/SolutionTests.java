import exceptions.ADijkException;
import exceptions.WorldException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SolutionTests extends Assert {

    @Before
    public void init(){
        Solution.dataMoveCreature = new DataMoveCreature("TestWorld.json");
    }

    @Test(timeout = 30)
    public void testOk0GetResult(){
        int result = Solution.getResult("STWSWTPPTPTTPWPP", "Swamper");
        assertEquals(15, result);
    }

    @Test(timeout = 30)
    public void testOk1GetResult(){
        int result = Solution.getResult("STWSWTPPTPTTPWPP", "Human");
        assertEquals(10, result);
    }

    @Test(timeout = 30)
    public void testOk2GetResult(){
        int result = Solution.getResult("STWSWTPPTPTTPWPP", "Woodman");
        assertEquals(12, result);
    }

    @Test(expected = ADijkException.class)
    public void testErrorWorldLength0GetResult(){
        Solution.getResult("STWSWTPPTPTTPWPPP", "Woodman");
    }

    @Test(expected = ADijkException.class)
    public void testErrorWorldLength1GetResult(){
        Solution.getResult("STWSWTPPTPTTPWP", "Woodman");
    }

    @Test(expected = WorldException.class)
    public void testErrorWorldCellName0GetResult(){
        Solution.getResult("STWSWTPPTPTLPWPP", "Woodman");
    }

    @Test(expected = WorldException.class)
    public void testErrorCreature0GetResult(){
        Solution.getResult("STWSWTPPTPTTPWPP", "Kikimora");
    }

}
