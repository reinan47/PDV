package com.gm2.pvd.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.gm2.pvd.Exception.InvalidOperationException;
import com.gm2.pvd.Exception.NoItemException;
import com.gm2.pvd.dto.ProductSaleDTO;
import com.gm2.pvd.dto.ProductInfoDTO;
import com.gm2.pvd.dto.SaleDTO;
import com.gm2.pvd.dto.SaleInfoDTO;
import com.gm2.pvd.entity.ItemSale;
import com.gm2.pvd.entity.Product;
import com.gm2.pvd.entity.Sale;
import com.gm2.pvd.entity.User;
import com.gm2.pvd.repository.ItemSaleRepository;
import com.gm2.pvd.repository.ProductRepository;
import com.gm2.pvd.repository.SaleRepository;
import com.gm2.pvd.repository.UserRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SaleService {

	private final UserRepository userRepository;

	private final ProductRepository productRepository;

	private final SaleRepository saleRepository;

	private final ItemSaleRepository itemSaleRepository;
	
	public List<SaleInfoDTO> findAll(){
		return saleRepository.findAll().stream().map(sale -> getSaleInfo(sale)).collect(Collectors.toList());
		
	}

	private SaleInfoDTO getSaleInfo(Sale sale) {
		var products = getProductInfo(sale.getItems());
		BigDecimal total = getTotal(products);
		
		SaleInfoDTO saleInfoDTO = new SaleInfoDTO();
		saleInfoDTO.setId(sale.getId());
		saleInfoDTO.setUser(sale.getUser().getName());
		saleInfoDTO.setDate(sale.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
		saleInfoDTO.setProducts(products);
		saleInfoDTO.setTotal(total);
		
		return saleInfoDTO;
	}
	
	private BigDecimal getTotal(List<ProductInfoDTO> products) {
		BigDecimal total = new BigDecimal(0);
		
		for(int i=0 ; i < products.size() ; i++) {
			total = total.add(products.get(i).getPrice().multiply(new BigDecimal(products.get(i).getQuantity())));
		}
		return total;
	}

	//usando builder() do lombok
	private List<ProductInfoDTO> getProductInfo(List<ItemSale> items) {
		return items.stream().map(
				item -> ProductInfoDTO.builder()
				.description(item.getProduct().getDescription())
				.quantity(item.getQuantity())
				.quantity(item.getQuantity())
				.price(item.getProduct().getPrice()).build()
				
		).collect(Collectors.toList());
	}
		

	@Transactional
	public long save(SaleDTO sale) {


		
		
		User user = userRepository.findById(sale.getUserid())
				.orElseThrow(() -> new InvalidOperationException("Usuário não encontrado com o ID: " + sale.getUserid()));
		
		Sale newSale = new Sale();
		newSale.setUser(user);
		newSale.setDate(LocalDate.now());

		List<ItemSale> itemSales = getItemSale(sale.getItems());

		newSale = saleRepository.save(newSale);

		saveItemSale(itemSales, newSale);

		return newSale.getId();
	}

	private void saveItemSale(List<ItemSale> itemSales, Sale newSale) {
		for(ItemSale item: itemSales) {
			item.setSale(newSale);
			itemSaleRepository.save(item);
		}

	}

	private List<ItemSale> getItemSale(List<ProductSaleDTO> products){
		if(products.isEmpty()) {
			throw new InvalidOperationException("Produto vazio");
		}
		
		return products.stream().map(item -> {
			Product product = productRepository.findById(item.getProductid())
					.orElseThrow(() -> new NoItemException("Produto não encontrado"));

			ItemSale itemSale =  new ItemSale();
			itemSale.setProduct(product);
			itemSale.setQuantity(item.getQuantity());
			
			int total = product.getQuantity() - item.getQuantity();
			product.setQuantity(total);
			if(product.getQuantity() == 0) {
				throw new NoItemException("Esgotou o estoque");
			}else if(product.getQuantity() < item.getQuantity()) {
				throw new InvalidOperationException("Itens insuficiente");
			}
			productRepository.save(product);
			
			return itemSale;
		}).collect(Collectors.toList());
	}

	public SaleInfoDTO getById(long id) {
		Sale sale = saleRepository.findById(id)
				.orElseThrow(() -> new InvalidOperationException("Venda não encontrada"));
		return getSaleInfo(sale);
	}
}
