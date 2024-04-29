public class Task {

    private String title;
    private static String status;
    private String task;

    public Task(String title, String task){
        this.title = title;
        this.task = task;
        status = "pending";
    }

    public Task(String title, String task, String stat){
        this.title = title;
        this.task = task;
        status = stat;
    }

    public String ShowTask(){
        return task;
    }

    public String getStatus(){
        return status;
    }

    public void changeStatus(String stat){
        status = stat;
    }

    public String toString(){
        return title;
    }
}