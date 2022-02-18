package emlakburada.dto.request;

import lombok.Data;

@Data
public class AdvertRequest {
	private String title;
	private long price;
	private int duration;
	private boolean isPromotion = false;
	private boolean isChecked = false;
	private boolean isActive = false;
}