package emlakburada.repository;

import emlakburada.model.Advert;
import emlakburada.model.RealEstate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class AdvertRepository {
	private static final List<Advert> advertList = new ArrayList<>();
	private static final Set<Advert> favorites = new HashSet<>();

	private static Advert prepareAdverts(int advertNo, String title) {
		Advert advert = new Advert();
		advert.setRealEstate(prepareRealEstate());
		advert.setAdvertNo(advertNo);
		advert.setTitle(title);
		advert.setPictures(pictureList());
		advert.setActive(true);
		return advert;
	}

	private static Advert prepareFavorites(int advertNo, String title) {
		Advert advert = new Advert();
		advert.setRealEstate(prepareRealEstate());
		advert.setAdvertNo(advertNo);
		advert.setTitle(title);
		advert.setPictures(pictureList());
		advert.setActive(true);
		return advert;
	}

	static {
		advertList.add(prepareAdverts(38164780, "Sahibinden Acil Satılık"));
		advertList.add(prepareAdverts(38164781, "Dosta GİDERRR ACİLLL!!!"));
		advertList.add(prepareAdverts(38164782, "Metroya Koşarak 5 dk"));
		advertList.add(prepareAdverts(38164783, "Öğrenciye ve Bekar uygun"));
	}

	static {
		favorites.add(prepareFavorites(38164780, "Sahibinden Acil Satılık"));
		favorites.add(prepareFavorites(38164782, "Metroya Koşarak 5 dk"));
	}

	private static List<String> pictureList() {
		List<String> picture = new ArrayList<>();
		picture.add("https://hecdnw01.hemlak.com/ds01/4/4/9/0/2/3/8/3/81d2e088-a551-485d-b2e9-664cc9200cdc.jpg");
		picture.add("https://hecdnw01.hemlak.com/ds01/4/4/9/0/2/3/8/3/81d2e088-a551-485d-b2e9-664cc9200cdc.jpg");
		return picture;
	}

	private static RealEstate prepareRealEstate() {
		return new RealEstate();
	}

	public Advert saveAdvert(Advert advert) {
		advertList.add(advert);
		return advert;
	}

	public List<Advert> findAllAdverts() {
		return advertList;
	}

	public Set<Advert> findAllFavorites() {
		return favorites;
	}

	public Advert findAdvertByAdvertNo(int advertNo) {
		return advertList.stream().filter(advert -> advert.getAdvertNo() == advertNo).findAny().orElse(new Advert());
	}

	public Set<Advert> findAdvertByUserId(int userId) {
		return Collections.singleton(favorites.stream().filter(advert -> advert.getUser().getUserId() == userId).findAny().orElse(new Advert()));
	}
}