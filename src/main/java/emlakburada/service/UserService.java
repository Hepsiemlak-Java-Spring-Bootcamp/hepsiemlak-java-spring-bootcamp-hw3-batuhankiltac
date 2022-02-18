package emlakburada.service;

import emlakburada.client.BannerClient;
import emlakburada.config.EmailConfig;
import emlakburada.dto.request.UserRequest;
import emlakburada.dto.response.UserResponse;
import emlakburada.model.User;
import emlakburada.queue.QueueService;
import emlakburada.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
	private final UserRepository userRepository;
	private final BannerClient bannerClient;
	private final QueueService queueService;

	@Autowired
	public UserService(UserRepository userRepository, BannerClient bannerClient, QueueService queueService) {
		this.userRepository = userRepository;
		this.bannerClient = bannerClient;
		this.queueService = queueService;
	}

	private UserResponse convertToUserResponse(User savedUser) {
		UserResponse response = new UserResponse();
		response.setUserId(savedUser.getUserId());
		response.setName(savedUser.getName());
		response.setEmail(savedUser.getEmail());
		return response;
	}

	private User convertToUser(UserRequest request) {
		User user = new User();
		user.setUserId(request.getUserId());
		user.setName(request.getName());
		user.setEmail(request.getEmail());
		return user;
	}

	public UserResponse createUser(UserRequest userRequest) {
		User savedUser = userRepository.saveUser(convertToUser(userRequest));
		EmailService emailService = new EmailService(new EmailConfig());
		queueService.sendMessage(emailService);
		bannerClient.saveBanner();
		return convertToUserResponse(savedUser);
	}

	public List<UserResponse> getAllUsers() {
		List<UserResponse> userList = new ArrayList<>();
		for (User user : userRepository.findAllUsers()) {
			userList.add(convertToUserResponse(user));
		}
		return userList;
	}

	public UserResponse getUserById(int userId) {
		User user = userRepository.findUserById(userId);
		return convertToUserResponse(user);
	}
}