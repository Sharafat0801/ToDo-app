import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ToDo tasks = new ToDo();
    static Hold holdedTasks = new Hold();
    public static void main (String[] args) throws IOException{
        checkFiles();
        boolean state = true;
        
        while (state) {

            System.out.println("1. Add task \n2. See tasks \n3. Backup tasks \nEnter '0' to exit");
            int in = scanner.nextInt();
            scanner.nextLine();
            switch(in){
                case 0:
                    state = false;
                    break;
                case 1:
                    tasks.AddTask(addTask());
                    break;
                case 2:
                    showTask();
                    break;
                case 3:
                    backUpFiles();
                    break;
            }
        }
    }


    private static Task addTask() {
        System.out.println("Title: ");
        String title = scanner.nextLine(); 
    
        System.out.println("Task: ");
        String task = scanner.nextLine();   
    
        return new Task(title, task);
    }


    private static void showTask(){
        System.out.println("0. to go back.\n1. pending tasks \n2. hold on tasks");
        int in = scanner.nextInt();

        switch(in){
            case 0:
                break;
            case 1:
                showtask(1);
                break;
            case 2:
                showtask(2);
                break;
            default:
                break;
        }
    }

    private static void showtask(int x){
        int index = 1;
        ArrayList<Task> temp;

        if(x == 1){
            temp = tasks.ShowAll();
        } else{
            temp = holdedTasks.ShowAll();
        }
        System.out.println("\n\n"+temp.size()+" tasks available");
        for (Task task : temp) {
            System.out.println(index+" "+task.toString());
            index++;                    
        }

        System.out.println("\n\n\nUse number to access individual tasks\n-1 to delete all\n0 to quit: ");
        int ind = scanner.nextInt();
        if(ind == 0){}
        else if(ind == -1){
            tasks.RemoveAll();
            holdedTasks.RemoveAll();
        }
        else{
            
            System.out.println("\n\n\n"+temp.get(ind - 1).toString());
            System.out.println(temp.get(ind - 1).ShowTask());
            System.out.println("\n\n\n1 to go back.\n2 to delete the task.\n3 to change status.\n0 to quit:\n\n");
            int tempin = scanner.nextInt();
            
            switch(tempin){
                case 0:
                    break;
                case 1:
                    showTask();
                case 2:
                    if(x == 1){
                        tasks.Remove(ind - 1);
                    } else{
                        holdedTasks.Remove(ind - 1);
                    }
                    break;
                case 3:
                    changeStatus(ind - 1, x);
                    break;
                default:
                    break;
            }
        }
    }
    private static void changeStatus(int in, int x){

        if(x == 1){
            System.out.println("\n\nCurrent status - "+tasks.Show(in).getStatus());
            System.out.println("Change to \n1. hold\n2 done & delete\n0 to quit:");
            int ind = scanner.nextInt();
            switch(ind){
                case 0:
                    break;
                case 1:
                    tasks.Show(in).changeStatus("hold");
                    holdedTasks.AddTask(tasks.Show(in));
                    tasks.Remove(in);
                    break;
                case 2:
                    tasks.Remove(in);
                    break;
                default:
                    break;
            }
        } else{
            System.out.println("\n\nCurrent status - "+tasks.Show(in).getStatus());
            System.out.println("Change to \n1. pending\n2 done & delete\n0 to quit:");
            int ind = scanner.nextInt();
            switch(ind){
                case 0:
                    break;
                case 1:
                    holdedTasks.Show(in).changeStatus("hold");
                    tasks.AddTask(holdedTasks.Show(in));
                    holdedTasks.Remove(in);
                    break;
                case 2:
                    holdedTasks.Remove(in);
                    break;
                default:
                    break;
            }
        }
    }

    private static void checkFiles() throws IOException{
        File dir = new File("Data");
        File[] files = dir.listFiles();

        if(files != null && files.length > 0){
            for (File file : files) {
                String name = file.getName().substring(file.getName().length() - 8,file.getName().length() - 4);
                String path = "Data/"+file.getName();
                if(name.equals("ToDo")){
                    addPending(path);
                } else{
                    addHold(path);
                }
                
            }
        }
    }

    private static void addPending(String path) throws IOException{
        FileReader file = new FileReader(path);
        int i;
        String temp = "";

        while((i = file.read()) != -1){
            temp += (char) i;
        }
        if(temp.length() > 0){
            String[] strArr = temp.split("\\|\\|");
            for (String str : strArr) {
                tasks.AddTask(new Task(str.substring(0, str.indexOf('^')), str.substring(str.indexOf('^') + 1)));
            }
        }
        

        file.close();
    }

    private static void addHold(String path) throws IOException{
        FileReader file = new FileReader(path);
        int i;
        String temp = "";

        while((i = file.read()) != -1){
            temp += (char) i;
        }
        if(temp.length() > 0){
            String[] strArr = temp.split("\\|\\|");
            for (String str : strArr) {
                holdedTasks.AddTask(new Task(str.substring(0, str.indexOf('^')), str.substring(str.indexOf('^') + 1), "hold"));
            }
        }

        file.close();
    }

    private static void backUpFiles(){
        try {

            File dataDirectory = new File("Data");
            if (!dataDirectory.exists()) {
                dataDirectory.mkdir();
            }

            FileWriter writer = new FileWriter("Data/"+java.time.LocalDate.now()+"_ToDo.txt", true);
            ArrayList<Task> temp = tasks.ShowAll();

            for (Task task : temp) {
                writer.write(task.toString()+"^"+task.ShowTask());
            }
            FileWriter writer1 = new FileWriter("Data/"+java.time.LocalDate.now()+"_Hold.txt", true);
            
            ArrayList<Task> temp1 = holdedTasks.ShowAll();
            if(temp1.size() > 2){
                for (Task task : temp1) {
                    writer1.write(task.toString()+"^"+task.ShowTask()+"||");
                }
            }
        
            writer.close();
            writer1.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}