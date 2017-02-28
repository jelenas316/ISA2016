package com.app.offers;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bidder.Bidder;
import com.app.drinkgrocery.DrinkGrocery;
import com.app.drinkgrocery.DrinkGroceryRepo;
import com.app.foodStuff.FoodStuff;
import com.app.foodStuff.FoodStuffRepo;
import com.app.grocerylist.GroceryList;
import com.app.grocerylist.GroceryListRepository;
@Service
@Transactional
public class OfferServiceImpl implements OfferService
{
	private final GroceryListRepository groceryListRepository;
	private final OfferRepository offerRepository;
	private final FoodStuffOfferRepository foodStuffOfferRepository;
	private final DrinkOfferRepository drinkOfferRepository;
	private final FoodStuffRepo foodStuffRepository;
	private final DrinkGroceryRepo drinkGroceryRepository;

	@Autowired
	public OfferServiceImpl(final GroceryListRepository groceryListRepository, final OfferRepository offerRepository,
			final FoodStuffOfferRepository foodStuffOfferRepository, final DrinkOfferRepository drinkOfferRepository,
			final FoodStuffRepo foodStuffRepository, final DrinkGroceryRepo drinkGroceryRepository){
		this.groceryListRepository = groceryListRepository;
		this.offerRepository = offerRepository;
		this.foodStuffOfferRepository = foodStuffOfferRepository;
		this.drinkOfferRepository = drinkOfferRepository;
		this.drinkGroceryRepository = drinkGroceryRepository;
		this.foodStuffRepository = foodStuffRepository;
	}
	
	/*@Override
	public Iterable<Offer> findByRestaurant(Restaurant restaurant) {
		return offerRepository.findByRestaurant(restaurant);
	}
*/
	@Override
	public Offer findOne(Long id) {
		return offerRepository.findOne(id);
	}

	@Override
	public Offer save(Offer offer) {
		for (int i = 0; i < offer.getFood().size(); i++) {
			offer.getFood().get(i).setId(null);
			offer.getFood().set(i, foodStuffOfferRepository.save(offer.getFood().get(i)));
		}
		for (int i = 0; i < offer.getDrinks().size(); i++) {
			offer.getDrinks().get(i).setId(null);
			offer.getDrinks().set(i, drinkOfferRepository.save(offer.getDrinks().get(i)));
		}
		return offerRepository.save(offer);
	}

	@Override
	public void delete(Long id) {
		offerRepository.delete(id);		
	}

	@Override
	public Offer findByBidder(Bidder bidder) {
		List<Offer> offers = offerRepository.findByBidder(bidder);
		for (Offer offer : offers) {
			if(offer.getAccepted().equals(OfferStatus.WAITING)){
				return offer;
			}
		}
		return null;
	}

	@Override
	public List<Offer> findByGroceries(GroceryList grocerylist) {
		List<Offer> offers = offerRepository.findByGroceryList(grocerylist);
		List<Offer> retVal = new ArrayList<Offer>();
		
		for (Offer offer : offers) {
			if(offer.getAccepted().equals(OfferStatus.WAITING)){
				retVal.add(offer);
			}
		}
		return retVal;
	}

	@Override
	public boolean acceptOffer(Long id) {
		Offer o = offerRepository.findOne(id);
		o.setAccepted(OfferStatus.ACCEPTED);
		offerRepository.save(o);
		GroceryList list = o.getGroceryList();
		List<Offer> offers = offerRepository.findByGroceryList(list);
		list.setAccepted(true);
		groceryListRepository.save(list);
		for (FoodStuffOffer foodStuffOffer : o.getFood()) {
			if(foodStuffOffer.getFoodStuff()!=null){
				FoodStuff food = foodStuffRepository.findOne(foodStuffOffer.getFoodStuff());
				int quantity = food.getQuantity()+foodStuffOffer.getQuantity();
				food.setQuantity(quantity);
				foodStuffRepository.save(food);
			}else{
				FoodStuff food = new FoodStuff();
				food.setDescription(foodStuffOffer.getDescription());
				food.setName(foodStuffOffer.getName());
				food.setQuantity(foodStuffOffer.getQuantity());
				food.setRestaurant(list.getRestaurant());
				foodStuffRepository.save(food);
			}			
		}
		for (DrinkOffer drinkOffer : o.getDrinks()) {
			if(drinkOffer.getDrinkGrocery()!=null){
				DrinkGrocery drink = drinkGroceryRepository.findOne(drinkOffer.getDrinkGrocery());
				int quantity = drink.getQuantity()+drinkOffer.getQuantity();
				drink.setQuantity(quantity);
				drinkGroceryRepository.save(drink);
			}else{
				DrinkGrocery drink= new DrinkGrocery();
				drink.setDescription(drinkOffer.getDescription());
				drink.setName(drinkOffer.getName());
				drink.setQuantity(drinkOffer.getQuantity());
				drink.setRestaurant(list.getRestaurant());
				drinkGroceryRepository.save(drink);
			}			
		}
		for (Offer offer : offers) {
			if(offer.getAccepted().equals(OfferStatus.WAITING) && offer.getId()!=o.getId()){
				offer.setAccepted(OfferStatus.REJECTED);
				offerRepository.save(offer);
			}
		}
		return true;
	}

	@Override
	public boolean rejectOffer(Long id) {
		Offer o = offerRepository.findOne(id);
		o.setAccepted(OfferStatus.REJECTED);
		offerRepository.save(o);
		return true;
	}

	@Override
	public List<Offer> getPastOffers(Bidder b) {
		List<Offer> offers = offerRepository.findByBidder(b);
		List<Offer> retVal = new ArrayList<Offer>();
		for (Offer offer : offers) {
			if(offer.getAccepted().equals(OfferStatus.ACCEPTED)||offer.getAccepted().equals(OfferStatus.REJECTED)){
				retVal.add(offer);
			}
		}
		return retVal;
	}

	public Offer getPastForGroceryList(GroceryList id) {
		List<Offer> offers = offerRepository.findByGroceryList(id);
		for (Offer offer : offers) {
			if(offer.getAccepted().equals(OfferStatus.ACCEPTED)){
				return offer;
			}
		}
		return null;
	}

}
