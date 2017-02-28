package com.app.grocerylist;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.offers.DrinkOffer;
import com.app.offers.DrinkOfferRepository;
import com.app.offers.FoodStuffOffer;
import com.app.offers.FoodStuffOfferRepository;
import com.app.offers.Offer;
import com.app.offers.OfferRepository;
import com.app.offers.OfferStatus;
import com.app.restaurant.Restaurant;

@Service
@Transactional
public class GroceryListServiceImpl implements GroceryListService{
	
	private final GroceryListRepository groceryListRepository;
	private final FoodStuffOfferRepository foodStuffOfferRepository;
	private final DrinkOfferRepository drinkOfferRepository;
	private final OfferRepository offerRepository;
	
	@Autowired
	public GroceryListServiceImpl(final GroceryListRepository groceryListRepository,final FoodStuffOfferRepository foodStuffOfferRepository, 
			final DrinkOfferRepository drinkOfferRepository,final OfferRepository offerRepository){
		this.groceryListRepository = groceryListRepository;
		this.foodStuffOfferRepository = foodStuffOfferRepository;
		this.drinkOfferRepository = drinkOfferRepository;
		this.offerRepository = offerRepository;
	}

	@Override
	public GroceryList findByRestaurantActive(Restaurant restaurant) {
		
		List<GroceryList> list = groceryListRepository.findByRestaurant(restaurant);
		GroceryList retVal = null;
		SimpleDateFormat dateFormatOfStringInDB = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date now = new Date();
		Date now1 = setTimeToMidnight(now);
		String date = dateFormatOfStringInDB.format(now1);
		Date today = null;
		try {
			today = dateFormatOfStringInDB.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		for (GroceryList groceryList : list) {
			String start = dateFormatOfStringInDB.format(groceryList.getStartDate());
			String end = dateFormatOfStringInDB.format(groceryList.getEndDate());
			Date startDate = null;
			Date endDate = null;
			try {
				startDate = dateFormatOfStringInDB.parse(start);
				endDate = dateFormatOfStringInDB.parse(end);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if((startDate.before(today) && endDate.after(today)) ||(startDate.after(today) && endDate.after(today)) 
					&& groceryList.isAccepted() == false){
				retVal = groceryList;
			}
		}
		return retVal;
	}

	@Override
	public GroceryList findOne(Long id) {
		return groceryListRepository.findOne(id);
	}

	@Override
	public GroceryList save(GroceryList groceryList) {
		
		List<Offer> offers = offerRepository.findByGroceryList(groceryList);
		
		for (FoodStuffOffer foodS : groceryList.getFood()) {
			foodStuffOfferRepository.save(foodS);
		}
		for (DrinkOffer drink: groceryList.getDrinks()) {
			drinkOfferRepository.save(drink);
		}
		for (Offer offer : offers) {
			if(offer.getAccepted().equals(OfferStatus.WAITING)){
				offer.setAccepted(OfferStatus.REJECTED);
				offerRepository.save(offer);
			}
		}
		groceryList.setAccepted(false);
		return groceryListRepository.save(groceryList);
	}

	@Override
	public void delete(Long id) {
		GroceryList list = groceryListRepository.findOne(id);
		groceryListRepository.delete(id);
		List<Offer> offers = offerRepository.findByGroceryList(list);
		for (Offer offer : offers) {
			offer.setAccepted(OfferStatus.REJECTED);
			offerRepository.save(offer);
		}
	}
	public Date setTimeToMidnight(Date date) {
	    Calendar calendar = Calendar.getInstance();

	    calendar.setTime( date );
	    calendar.set(Calendar.HOUR_OF_DAY, 0);
	    calendar.set(Calendar.MINUTE, 0);
	    calendar.set(Calendar.SECOND, 0);
	    calendar.set(Calendar.MILLISECOND, 0);

	    return calendar.getTime();
	}

	@Override
	public List<GroceryList> findByRestaurant(Restaurant restaurant) {
		return groceryListRepository.findByRestaurant(restaurant);
	}

}
