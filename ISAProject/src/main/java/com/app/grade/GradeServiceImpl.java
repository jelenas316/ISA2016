package com.app.grade;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.guest.Guest;
import com.app.restaurant.Restaurant;

@Service
@Transactional
public class GradeServiceImpl implements GradeService{

	private final GradeRepository gradeRepo;
	
	@Autowired
	public GradeServiceImpl(final GradeRepository gradeRepo) {
		this.gradeRepo=gradeRepo;
	}
	
	@Override
	public Iterable<Grade> findAll() {
		return gradeRepo.findAll();
	}

	@Override
	public Grade findOne(Long id) {
		return gradeRepo.findOne(id);
	}

	@Override
	public Grade save(Grade grade) {
		return gradeRepo.save(grade);
	}

	@Override
	public void delete(Long id) {
		gradeRepo.delete(id);
	}
	
	public Iterable<Grade> findByGuest(String email){
		return gradeRepo.findByGuestEmail(email);
	}

	@Override
	public List<Grade> findByRestaurant(Long id) {
		return gradeRepo.findByRestaurantId(id);
	}
	
	public List<RestaurantDTO> getRestaurantsDTO(Iterable<Restaurant> allRestaurants,Guest guest){
		Iterable<Grade> grades = gradeRepo.findAll();
		List<RestaurantDTO> restaurantsDTO=new ArrayList<>();
		RestaurantDTO dto=new RestaurantDTO();
		for (Restaurant r : allRestaurants) {
			dto=restaurantToDTO(r);
			List<Grade> gradesForRestaurant=getGradesForRestaurant(grades, r.getId());
			dto.setReiting(calculateGradeForAllUsers(gradesForRestaurant));
			dto.setFriendsReiting(calculateGradeForFriends(guest,gradesForRestaurant));
			restaurantsDTO.add(dto);
		}
		return restaurantsDTO;
	}

	/**
	 * Returns all grades for passed restaurant.
	 */
	private List<Grade> getGradesForRestaurant(Iterable<Grade> grades, Long restaurantId){
		List<Grade> returnValue=new ArrayList<>();
		
		for(Grade grade : grades){
			if(grade.getRestaurant()!=null && grade.getRestaurant().getId().equals(restaurantId))
				returnValue.add(grade);
		}
		
		return returnValue;
	}
	
	/**
	 * Method that converts {@link Restaurant} to {@link RestaurantDTO}.
	 */
	private RestaurantDTO restaurantToDTO(Restaurant restaurant){
		RestaurantDTO dto=new RestaurantDTO();
		dto.setDescription(restaurant.getDescription());
		dto.setId(restaurant.getId());
		dto.setName(restaurant.getName());
		dto.setAddress(restaurant.getAddress());
		dto.setFriendsReiting(0);
		dto.setReiting(0);
		return dto;
	}
	
	/**
	 * Calculates grade for restaurant for all users.
	 * 
	 * @param grades
	 * @return reiting for all users
	 */
	private Integer calculateGradeForAllUsers(List<Grade> grades){
		Integer value = 0;
		if(grades.size()>0){
			for(Grade grade : grades){
				value+=grade.getValue();
			}
			BigDecimal average=new BigDecimal(value/grades.size());
			value=average.intValue();
		}
		return value;
	}
	
	/**
	 * Calculates grade for restaurant for friends of the guest and guest.
	 * 
	 * @param email - email of the guest
	 * @return 
	 */
	private Integer calculateGradeForFriends(Guest guest, List<Grade> grades){
		Integer value = 0;
		Integer counter=0;
		if(grades.size()>0){
			for(Grade grade : grades){
				if(grade.getGuest().getEmail().equals(guest.getEmail()) ||
						guest.getFriends().contains(grade.getGuest())){
					value+=grade.getValue();
					counter++;
				}
			}
			if(counter>0){
				BigDecimal average=new BigDecimal(value/counter);
				value=average.intValue();
			}
		}
		return value;
	}

	@Override
	public GradeDTO findByWaiter(String email) {
		int totalGrade = 0;
		int numberOfGrades = 0;
		List<Grade> grades = gradeRepo.findByWaiterEmail(email);
		for (Grade grade : grades) {
			totalGrade+=grade.getValue();
			numberOfGrades++;
		}
		GradeDTO retVal = new GradeDTO();
		double total = (double)totalGrade/(double)numberOfGrades;				
		retVal.setGrade(Math.round(total*100)/100.00);
		retVal.setNumberOfGrades(numberOfGrades);
		return retVal;
	}

	@Override
	public GradeDTO findByFood(Long id) {
		int totalGrade = 0;
		int numberOfGrades = 0;
		List<Grade> grades = gradeRepo.findByFoodId(id);
		for (Grade grade : grades) {
			totalGrade+=grade.getValue();
			numberOfGrades++;
		}
		GradeDTO retVal = new GradeDTO();
		double total = (double)totalGrade/(double)numberOfGrades;					
		retVal.setGrade(Math.round(total*100)/100.00);
		retVal.setNumberOfGrades(numberOfGrades);
		return retVal;
	}

	@Override
	public GradeDTO findRestaurantGrade(Long id) {
		int totalGrade = 0;
		int numberOfGrades = 0;
		List<Grade> grades = gradeRepo.findByRestaurantId(id);
		for (Grade grade : grades) {
			totalGrade+=grade.getValue();
			numberOfGrades++;
		}
		GradeDTO retVal = new GradeDTO();
		double total = (double)totalGrade/(double)numberOfGrades;					
		retVal.setGrade(Math.round(total*100)/100.00);
		retVal.setNumberOfGrades(numberOfGrades);
		return retVal;
	}
	
}
