import exceptions.WorldException;
import org.junit.Assert;
import org.junit.Test;

public class DataMoveCreatureTest extends Assert {

    @Test(expected = WorldException.class)
    public void testErrorWorldJSON0GetResult(){
        Solution.dataMoveCreature = new DataMoveCreature("TestWorldWrong0.json");
        Solution.getResult("STWSWTPPTPTTPWPP", "Swamper");
    }

    @Test(expected = WorldException.class)
    public void testErrorWorldJSON1GetResult(){
        Solution.dataMoveCreature = new DataMoveCreature("TestWorldWrong1.json");
        Solution.getResult("STWSWTPPTPTTPWPP", "Swamper");
    }

    @Test(expected = NullPointerException.class)
    public void testErrorWorldJSON2GetResult(){
        Solution.dataMoveCreature = new DataMoveCreature(null);
        Solution.getResult("STWSWTPPTPTTPWPP", "Swamper");
    }

    @Test(expected = NullPointerException.class)
    public void testErrorWorldJSON3GetResult(){
        Solution.dataMoveCreature = null;
        Solution.getResult("STWSWTPPTPTTPWPP", "Swamper");
    }

    @Test(expected = NullPointerException.class)
    public void testErrorPathJSON0GetResult(){
        Solution.dataMoveCreature = new DataMoveCreature("TestWldWrong0.json");
        Solution.getResult("STWSWTPPTPTTPWPP", "Swamper");
    }
}
