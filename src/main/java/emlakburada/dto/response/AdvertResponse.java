package emlakburada.dto.response;

import emlakburada.model.RealEstate;
import emlakburada.model.User;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AdvertResponse {
	private RealEstate realEstate;
	private User user;
	private int advertNo;
	private String title;
	private long price;
	private List<String> pictures;
	private int duration;
	private boolean isPromotion = false;
	private boolean isChecked = false;
	private boolean isActive = false;
	private Date creationDate;
}