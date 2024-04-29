import java.io.File;
public class lol {
    public static void main(String[] args) {
        File dir = new File("Data");
        File[] files = dir.listFiles();

        for(File f : files){
            System.out.println(f.getName());
        }
    }
}
