package emlakburada.dto.response;

import emlakburada.model.Advert;
import emlakburada.model.Message;
import emlakburada.model.enums.UserType;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
public class UserResponse {
    private UserType userType;
    private int userId;
    private String name;
    private String email;
    private String photo;
    private String bio;
    private Set<Advert> favorites = new HashSet<>();
    private List<Advert> publishedAdverts = new ArrayList<>();
    private List<Message> messageBox;
}