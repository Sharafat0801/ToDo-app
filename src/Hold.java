import java.util.ArrayList;
public class Hold {

    private static ArrayList<Task> tasks = new ArrayList<>();
    


    public ArrayList<Task> ShowAll(){
        return tasks;
    }

    public Task Show(int i){
        return tasks.get(i);
    }

    public void AddTask(Task t){
        tasks.add(t);
    }

    public void Remove(int i){
        tasks.remove(i);
    }

    public void RemoveAll(){
        tasks.clear();
    }
    
}