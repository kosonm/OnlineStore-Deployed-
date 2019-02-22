package com.eshop.kosonm.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.eshop.kosonm.cart.CartInfo;
import com.eshop.kosonm.cart.CartUtils;
import com.eshop.kosonm.pagination.PaginationResult;
import com.eshop.kosonm.product.Product;
import com.eshop.kosonm.product.ProductDao;
import com.eshop.kosonm.product.ProductInfo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Transactional
public class ProductController {

   private ProductDao productDAO;

   @Autowired
   public ProductController(ProductDao productDao) {
      this.productDAO = productDao;
   }

   @RequestMapping({ "/productList" })
   public String listProductHandler(Model model, //
         @RequestParam(value = "name", defaultValue = "") String likeName,
         @RequestParam(value = "page", defaultValue = "1") int page) {
      final int maxResult = 5;
      final int maxNavigationPage = 10;

      PaginationResult<ProductInfo> result = productDAO.queryProducts(page, //
            maxResult, maxNavigationPage, likeName);

      model.addAttribute("paginationProducts", result);
      return "productList";
   }

   @RequestMapping({ "/buyProduct" })
   public String listProductHandler(HttpServletRequest request, Model model, //
         @RequestParam(value = "code", defaultValue = "") String code) {

      Product product = null;

      if (StringUtils.isNotEmpty(code)) {
         product = productDAO.findProduct(code);
      }
      if (product != null) {

         //
         CartInfo cartInfo = CartUtils.getCartInSession(request);

         ProductInfo productInfo = new ProductInfo(product);

         cartInfo.addProduct(productInfo, 1);
      }

      return "redirect:/shoppingCart";
   }

   @GetMapping("/productImage")
   public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
         @RequestParam("code") String code) throws IOException {
      Product product = null;
      if (code != null) {
         product = this.productDAO.findProduct(code);
      }
      if (product != null && product.getImage() != null) {
         response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
         response.getOutputStream().write(product.getImage());
      }
      response.getOutputStream().close();
   }

}