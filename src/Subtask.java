public class Subtask extends Task{
    private int subTaskId;                          // поле для помещения его в ArrayList

    public Subtask(String taskTitle, String taskDescription) {
        super(taskTitle, taskDescription);

    }

    public int getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
    }
}
