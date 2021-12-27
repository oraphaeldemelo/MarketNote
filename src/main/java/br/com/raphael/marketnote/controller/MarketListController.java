package br.com.raphael.marketnote.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.raphael.marketnote.model.MarketList;
import br.com.raphael.marketnote.repository.MarketListRepository;

@RestController
@RequestMapping(path = "/marketHistory")
public class MarketListController {
	
	@Autowired
	private MarketListRepository repository;
	
	@GetMapping
	public List<MarketList> list() {
		
		List<MarketList> allList = repository.findAll();

		return allList;
	}
	
	@PostMapping("/newList")
	public ResponseEntity<MarketList> newList(@RequestBody MarketList list) {
		
		  MarketList newList = new MarketList(); 
		  newList.setNome(list.getNome());
		  newList.setDataCriacao(LocalDateTime.now()); 
		  
		  MarketList responseList = repository.save(newList);
		 
		  return ResponseEntity.ok(responseList);
	}
	
	@PostMapping("/editList")
	public ResponseEntity<HttpStatus> editList(@RequestBody MarketList list) {
		Optional<MarketList> findList = repository.findById(list.getId());
		
		if(findList.isPresent()) {
			MarketList updateList = findList.get();
			
			updateList.setNome(list.getNome());
			
			repository.save(updateList);
			return ResponseEntity.ok(HttpStatus.OK);
		} else {
			return ResponseEntity.notFound().build();
		}		
	}
	
	
}
