public class Tarea{

    private String id;
    private String description;
    private boolean completed;

    public Tarea(String id, String description){
        this.id = id;
        this.description = description;
        this.completed = false;
    }

    public String getId(){
        return this.id;
    }

    public String getDescription(){
        return this.description;
    }

    public boolean isCompleted(){
        return this.completed;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }
}