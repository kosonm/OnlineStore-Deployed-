package com.eshop.kosonm.Controller;

import java.util.List;

import com.eshop.kosonm.DAO.OrderDao;
import com.eshop.kosonm.DAO.ProductDao;
import com.eshop.kosonm.Entity.Product;
import com.eshop.kosonm.Model.OrderDetailInfo;
import com.eshop.kosonm.Model.OrderInfo;
import com.eshop.kosonm.Pagination.PaginationResult;
import com.eshop.kosonm.Validator.ProductForm;
import com.eshop.kosonm.Validator.ProductFormValidator;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
 
@Controller
@Transactional
public class AdminController {

   /*
   Control class for admin pages
   */
 
   @Autowired
   private OrderDao orderDao;
 
   @Autowired
   private ProductDao productDao;
 
   @Autowired
   private ProductFormValidator productFormValidator;
 
   @InitBinder
   public void myInitBinder(WebDataBinder dataBinder) {
      Object target = dataBinder.getTarget();
      if (target == null) {
         return;
      }
      System.out.println("Target=" + target);
 
      if (target.getClass() == ProductForm.class) {
         dataBinder.setValidator(productFormValidator);
      }
   }
 
   @GetMapping("/admin/login")
   public String login(Model model) {
 
      return "login";
   }
 
   @GetMapping("/admin/accountInfo")
   public String accountInfo(Model model) {
 
      UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      System.out.println(userDetails.getPassword());
      System.out.println(userDetails.getUsername());
      System.out.println(userDetails.isEnabled());
 
      model.addAttribute("userDetails", userDetails);
      return "accountInfo";
   }
 
   @GetMapping("/admin/orderList")
   public String orderList(Model model, 
         @RequestParam(value = "page", defaultValue = "1") String pageStr) {
      int page = 1;
      try {
         page = Integer.parseInt(pageStr);
      } catch (Exception e) {
      }
      final int MAX_RESULT = 5;
      final int MAX_NAVIGATION_PAGE = 10;
 
      PaginationResult<OrderInfo> paginationResult 
            = orderDao.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);
 
      model.addAttribute("paginationResult", paginationResult);
      return "orderList";
   }
 
   @GetMapping("/admin/product")
   public String product(Model model, @RequestParam(value = "code", defaultValue = "") String code) {
      ProductForm productForm = null;
 
      if (code != null && code.length() > 0) {
         Product product = productDao.findProduct(code);
         if (product != null) {
            productForm = new ProductForm(product);
         }
      }
      if (productForm == null) {
         productForm = new ProductForm();
         productForm.setNewProduct(true);
      }
      model.addAttribute("productForm", productForm);
      return "product";
   }
 
   @PostMapping("/admin/product")
   public String productSave(Model model, 
         @ModelAttribute("productForm") @Validated ProductForm productForm, 
         BindingResult result, 
         final RedirectAttributes redirectAttributes) {
 
      if (result.hasErrors()) {
         return "product";
      }
      try {
         productDao.save(productForm);
      } catch (Exception e) {
         Throwable rootCause = ExceptionUtils.getRootCause(e);
         String message = rootCause.getMessage();
         model.addAttribute("errorMessage", message);
         return "product";
      }
 
      return "redirect:/productList";
   }
 
   @GetMapping("/admin/order")
   public String orderView(Model model, @RequestParam("orderId") String orderId) {
      OrderInfo orderInfo = null;
      if (orderId != null) {
         orderInfo = this.orderDao.getOrderInfo(orderId);
      }
      if (orderInfo == null) {
         return "redirect:/admin/orderList";
      }
      List<OrderDetailInfo> details = this.orderDao.listOrderDetailInfos(orderId);
      orderInfo.setDetails(details);
 
      model.addAttribute("orderInfo", orderInfo);
 
      return "order";
   }
 
}