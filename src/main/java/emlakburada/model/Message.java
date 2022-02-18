package emlakburada.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {
	private String title;
	private String description;
	private Date sentDate;
	private Date readDate;
	private boolean isSeen;
	private User sender;
	private User receiver;

	public Message(String title, String description) {
		this.title = title;
		this.description = description;
	}
}