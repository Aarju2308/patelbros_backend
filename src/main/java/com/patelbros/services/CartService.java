package com.patelbros.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.patelbros.dtos.CartRequest;
import com.patelbros.dtos.CartResponse;
import com.patelbros.entities.Cart;
import com.patelbros.entities.User;
import com.patelbros.exceptions.OperationNotPermittedException;
import com.patelbros.mappers.CartMapper;
import com.patelbros.repositories.CartRepository;
import com.patelbros.repositories.ProductRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {
	
	private final CartRepository cartRepository;
	private final ProductRepository productRepository;
	private final CartMapper cartMapper;
	
	public Integer addToCart(CartRequest request, Authentication authentication) {
		
		User user = null;
		
		if (authentication!=null) {
			user = (User)authentication.getPrincipal();
		}
		
		int quantity = request.getQuantity();
		Integer cartId = null;
		
		var product = productRepository.findById(request.getProductId()).orElseThrow(
					()->new OperationNotPermittedException("No Product found with id : "+ request.getProductId())
				);
		
		if (user == null && request.getUniqueId() == 0) {
			throw new OperationNotPermittedException("Either user id or unique id is needed to add product to cart");
		}
		
		
		
		Optional<Cart> oldCart = cartRepository.findByUserOrUniqueIdAndProduct(user, request.getUniqueId(), product);
		
		if (oldCart.isPresent()) {
			cartId = oldCart.get().getId();
			quantity += oldCart.get().getQuantity();
		}
		
		
		var total = product.getPrice() * quantity;
		
		Cart cart = Cart.builder()
				.id(cartId)
				.user(user)
				.product(product)
				.price(product.getPrice())
				.quantity(quantity)
				.total(total)
				.uniqueId(request.getUniqueId())
				.build();
		
		
		return cartRepository.save(cart).getId();
	}
	
	public Integer removeFromCart(CartRequest request, Authentication authentication) {
		User user = null;
		
		if(authentication != null) {
			user = (User)authentication.getPrincipal();
		}
		
		int quantity = request.getQuantity();
		
		
		var product = productRepository.findById(request.getProductId()).orElseThrow(
				()->new OperationNotPermittedException("No Product found with id : "+ request.getProductId())
			);
		
		if (user == null && request.getUniqueId() == 0) {
			throw new OperationNotPermittedException("Either user id or unique id is needed to remove product quantity from cart");
		}
		
		Cart oldCart = cartRepository.findByUserOrUniqueIdAndProduct(user, request.getUniqueId(), product).orElseThrow(
				()->new OperationNotPermittedException("No Cart found with The Provided Credientials : "+ request.getProductId())
			);
		
		
		Integer cartId = oldCart.getId();
		quantity = oldCart.getQuantity() - quantity; 
		
		
		var total = product.getPrice() * quantity;
		
		Cart cart = Cart.builder()
				.id(cartId)
				.user(user)
				.product(product)
				.price(product.getPrice())
				.quantity(quantity)
				.total(total)
				.uniqueId(request.getUniqueId())
				.build();
		
		
		return cartRepository.save(cart).getId();
		
	}
	
	

	public void updateUser(int uniqueId, Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		
		List<Cart> carts = cartRepository.findByUniqueId(uniqueId);
		for (Cart cart : carts) {
			cart.setUser(user);
			cartRepository.save(cart);
		}
	}

	public Long getCartCountByUser(Authentication authentication) {
		User user = (User)authentication.getPrincipal();
		return cartRepository.countByUser(user);
	}
	
	public Long getCartCountByUniqueId(int uniqueId) {
		return cartRepository.countByUniqueId(uniqueId);
	}

	public List<CartResponse> getCartByUser(Authentication authentication) {
		
		User user = (User)authentication.getPrincipal();
		
		List<Cart> carts = cartRepository.findByUser(user);
		
		return carts.stream()
				.map(c->cartMapper.toResponse(c))
				.toList();
		
		
	}

	public List<CartResponse> getCartByUniqueId(int uniqueId) {
	
		List<Cart> carts = cartRepository.findByUniqueId(uniqueId);
		
		return carts.stream()
				.map(c->cartMapper.toResponse(c))
				.toList();
		
		
	}

	public void deleteCart(Integer cartId) {
		
		cartRepository.deleteById(cartId);
	}

	public int getProductsCount(User user) {
		int totalProducts = 0;
		List<Cart> carts = cartRepository.findByUser(user);
		for (Cart cart : carts) {
			totalProducts += cart.getQuantity();
		}
		return totalProducts;
	}
	
	public double getCartNetAmount(User user) {
		double netAmount = 0;
		List<Cart> carts = cartRepository.findByUser(user);
		for (Cart cart : carts) {
			netAmount += cart.getTotal();
		}
		return netAmount;
	}
	
}
