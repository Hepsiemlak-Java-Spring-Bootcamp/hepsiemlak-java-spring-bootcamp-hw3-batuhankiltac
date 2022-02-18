package emlakburada.dto.response;

import emlakburada.model.User;
import lombok.Data;

import java.util.Date;

@Data
public class MessageResponse {
    private String title;
    private String description;
    private Date sentDate;
    private Date readDate;
    private boolean isSeen;
    private User sender;
    private User receiver;
}