package br.com.raphael.marketnote.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.raphael.marketnote.model.MarketList;

@Repository
public interface MarketListRepository extends JpaRepository<MarketList, Long>{
	

}
