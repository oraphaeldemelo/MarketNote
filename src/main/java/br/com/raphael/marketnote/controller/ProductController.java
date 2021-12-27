package br.com.raphael.marketnote.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.raphael.marketnote.model.MarketList;
import br.com.raphael.marketnote.model.Product;
import br.com.raphael.marketnote.repository.MarketListRepository;
import br.com.raphael.marketnote.repository.ProductRepository;

@RestController
@RequestMapping("/product")
public class ProductController {
	
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private MarketListRepository listRepository; 
	
	@GetMapping("/list/{listId}")
	public List<Product> listByListId(@PathVariable String listId){
		
		List<Product> products = productRepository.findAllByMarketListId(Long.parseLong(listId));
		
		return products;
	}
	
	@PostMapping("/add")
	public ResponseEntity<Product> add(@RequestBody Product produto) {
		
		Optional<MarketList> findList = listRepository.findById(produto.getMarketList().getId());
		
		if(findList.isPresent()) {
			MarketList list = findList.get();
			BigDecimal valorProduto = produto.getQuantidade() != 1 ? produto.getValorUnd().multiply(new BigDecimal(produto.getQuantidade()))  : produto.getValorUnd();
			produto.setValorTotal(valorProduto);
			produto.setMarketList(list);
			
			Product response = productRepository.save(produto);
			
			return ResponseEntity.ok(response);
		} else {
			return ResponseEntity.notFound().build();
		}
		
		
		
	}
}
