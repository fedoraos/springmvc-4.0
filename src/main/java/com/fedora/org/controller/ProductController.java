package com.fedora.org.controller;

import java.io.File;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fedora.org.domain.Product;
import com.fedora.org.exception.NoProductsFoundUnderCategoryException;
import com.fedora.org.exception.ProductNotFoundException;
import com.fedora.org.service.ProductService;
import com.fedora.org.validator.ProductValidator;


@Controller
@RequestMapping("/products")
public class ProductController {
	
	@Autowired
	private ProductService productService;

	@Autowired
	private ProductValidator productValidator;
	
	@RequestMapping
	public String list(Model model) {
		model.addAttribute("products", productService.getAllProducts());
		return "products";
	}
	
	@RequestMapping("/all")
	public ModelAndView allProducts() {
		ModelAndView modelAndView = new ModelAndView();
		
		modelAndView.addObject("products", productService.getAllProducts());
		modelAndView.setViewName("products");
		return modelAndView;
	}
	
	@RequestMapping("/{category}")
	public String getProductsByCategory(Model model, @PathVariable("category") String category) {
		List<Product> products = productService.getProductsByCategory(category);

		if (products == null || products.isEmpty()) {
			throw new NoProductsFoundUnderCategoryException();
		}

		model.addAttribute("products", products);
		return "products";
	}
	
	
	@RequestMapping("/filter/{ByCriteria}")
	public String getProductsByFilter(@MatrixVariable(pathVar="ByCriteria") Map<String,List<String>> filterParams, Model model) {
		model.addAttribute("products", productService.getProductsByFilter(filterParams));
		return "products";
	}
	
	@RequestMapping("/product")
	public String getProductById(Model model, @RequestParam("id") String productId) {
		Product product = productService.getProductById(productId);
		model.addAttribute("product", product);
		return "product";
	}

	
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String getAddNewProductForm(@ModelAttribute("newProduct") Product newProduct) {
	   return "addProduct";
	}
	   
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String processAddNewProductForm(@ModelAttribute("newProduct") Product productToBeAdded, ModelMap map, BindingResult result, HttpServletRequest request) {
		String[] suppressedFields = result.getSuppressedFields();
		
		if (suppressedFields.length > 0) {
			throw new RuntimeException("Attempting to bind disallowed fields: " + StringUtils.arrayToCommaDelimitedString(suppressedFields));
		}
		
		MultipartFile productImage = productToBeAdded.getProductImage();
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
				
			if (productImage!=null && !productImage.isEmpty()) {
		       try {
		    	   String filePath =rootDirectory+"resources"+File.separator+"images"+File.separator+productToBeAdded.getProductId() + ".png";
		    	   System.out.println(filePath);
		    	   productImage.transferTo(new File(filePath));
		       } catch (Exception e) {
				throw new RuntimeException("Product Image saving failed", e);
		   }
		   }

		
	   	productService.addProduct(productToBeAdded);
		return "redirect:/products";
	}
	
	/**
	 * 用于控制 表单提交的的可允许的字段；
	 * 有什么用  什么时候用的？
	 * @param binder
	 */
	
	@InitBinder
	public void initialiseBinder(WebDataBinder binder) {
		binder.setValidator(productValidator);
		//binder.setAllowedFields("productId","name","unitPrice","description","manufacturer","category","unitsInStock", "condition","productImage");
		binder.setAllowedFields("productId","name","unitPrice","description","manufacturer","category","unitsInStock", "condition","productImage","language");
	}
	
	@ExceptionHandler(ProductNotFoundException.class)
	public ModelAndView handleError(HttpServletRequest req, ProductNotFoundException exception) {
		 ModelAndView mav = new ModelAndView();
		 mav.addObject("invalidProductId", exception.getProductId());
		 mav.addObject("exception", exception);
		 mav.addObject("url", req.getRequestURL()+"?"+req.getQueryString());
		 mav.setViewName("productNotFound");
		 return mav;
	}
	
	
	@RequestMapping("/invalidPromoCode")
	public String invalidPromoCode() {
			return "invalidPromoCode";
	}

}
