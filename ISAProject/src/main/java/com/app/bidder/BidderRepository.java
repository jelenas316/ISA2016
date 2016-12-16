package com.app.bidder;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface BidderRepository extends CrudRepository<Bidder, String>{

}
