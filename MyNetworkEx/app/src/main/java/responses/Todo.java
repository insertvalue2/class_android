package responses;

import com.google.gson.annotations.SerializedName;

public class Todo {
    @SerializedName("userId")
    public int userId;
    @SerializedName("id")
    public int id;
    @SerializedName("completed")
    public String completed;
}
