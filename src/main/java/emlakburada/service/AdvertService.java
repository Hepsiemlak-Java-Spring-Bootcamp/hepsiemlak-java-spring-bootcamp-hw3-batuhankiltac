package emlakburada.service;

import emlakburada.client.BannerClient;
import emlakburada.config.EmailConfig;
import emlakburada.dto.request.AdvertRequest;
import emlakburada.dto.response.AdvertResponse;
import emlakburada.model.Advert;
import emlakburada.model.RealEstate;
import emlakburada.model.User;
import emlakburada.queue.QueueService;
import emlakburada.repository.AdvertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class AdvertService {
	private final AdvertRepository advertRepository;
	private final BannerClient bannerClient;
	private final QueueService queueService;

	private static int advertNo = 38164784;

	@Autowired
	public AdvertService(AdvertRepository advertRepository, BannerClient bannerClient, QueueService queueService) {
		this.advertRepository = advertRepository;
		this.bannerClient = bannerClient;
		this.queueService = queueService;
	}

	private AdvertResponse convertToAdvertResponse(Advert savedAdvert) {
		AdvertResponse response = new AdvertResponse();
		response.setTitle(savedAdvert.getTitle());
		response.setPrice(savedAdvert.getPrice());
		response.setAdvertNo(savedAdvert.getAdvertNo());
		return response;
	}

	private Advert convertToAdvert(AdvertRequest request) {
		Advert advert = new Advert(new RealEstate(), new User(), advertNo, request.getTitle());
		advertNo++;
		advert.setAdvertNo(advertNo);
		advert.setTitle(request.getTitle());
		advert.setPrice(request.getPrice());
		return advert;
	}

	public AdvertResponse createAdvert(AdvertRequest advertRequest) {
		Advert savedAdvert = advertRepository.saveAdvert(convertToAdvert(advertRequest));
		EmailService emailService = new EmailService(new EmailConfig());
		queueService.sendMessage(emailService);
		bannerClient.saveBanner();
		return convertToAdvertResponse(savedAdvert);
	}

	public List<AdvertResponse> getAllAdverts() {
		List<AdvertResponse> advertList = new ArrayList<>();
		for (Advert advert : advertRepository.findAllAdverts()) {
			advertList.add(convertToAdvertResponse(advert));
		}
		return advertList;
	}

	public Set<AdvertResponse> getAllFavorites() {
		Set<AdvertResponse> favoriteList = new HashSet<>();
		for (Advert advert : advertRepository.findAllFavorites()) {
			favoriteList.add(convertToAdvertResponse(advert));
		}
		return favoriteList;
	}

	public AdvertResponse getAdvertByAdvertNo(int advertNo) {
		Advert advert = advertRepository.findAdvertByAdvertNo(advertNo);
		return convertToAdvertResponse(advert);
	}

	public Set<AdvertResponse> getAdvertByUserId(int userId) {
		Set<AdvertResponse> favorites = new HashSet<>();
		for (Advert advert : advertRepository.findAdvertByUserId(userId)) {
			favorites.add(convertToAdvertResponse(advert));
		}
		return favorites;
	}
}