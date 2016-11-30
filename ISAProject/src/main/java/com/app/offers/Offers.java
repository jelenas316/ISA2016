package com.app.offers;

import java.util.List;

import lombok.Data;

@Data
//@Entity
//@Table(name = "offers")
public class Offers {

//	@Id
//	@GeneratedValue(strategy = GenerationType.AUTO)
//	@Column(name = "OFFERS_ID")
	private Long id;
	
	private List<Offer> offers;
	
}
