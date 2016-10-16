import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class Paint
{
    public void drawTreeFile(Tree tree, int index) throws FileNotFoundException
    {
        OutputStream os = new FileOutputStream(String.format("in%s.txt", index));
    }
}