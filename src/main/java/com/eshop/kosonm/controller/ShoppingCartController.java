package com.eshop.kosonm.controller;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import com.eshop.kosonm.order.OrderDao;
import com.eshop.kosonm.product.Product;
import com.eshop.kosonm.product.ProductDao;
import com.eshop.kosonm.product.ProductInfo;
import com.eshop.kosonm.cart.CartInfo;
import com.eshop.kosonm.cart.CartUtils;
import com.eshop.kosonm.customer.CustomerForm;
import com.eshop.kosonm.customer.CustomerFormValidator;
import com.eshop.kosonm.customer.CustomerInfo;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@Transactional
public class ShoppingCartController {

    private ProductDao productDAO;
    private OrderDao orderDAO;
    private CustomerFormValidator customerFormValidator;

    @Autowired
    public ShoppingCartController(ProductDao productDAO, OrderDao orderDAO,CustomerFormValidator customerFormValidator) {
        this.customerFormValidator = customerFormValidator;
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
    }

    @InitBinder
   public void myInitBinder(WebDataBinder dataBinder) {
      Object target = dataBinder.getTarget();
      if (target == null) {
         return;
      }
      System.out.println("Target=" + target);

      if (target.getClass() == CartInfo.class) {
      } else if (target.getClass() == CustomerForm.class) {
         dataBinder.setValidator(customerFormValidator);
      }

   }
    @RequestMapping({ "/shoppingCartRemoveProduct" })
    public String removeProductHandler(HttpServletRequest request, Model model, //
            @RequestParam(value = "code", defaultValue = "") String code) {
        Product product = null;

        if(StringUtils.isNotEmpty(code)){
            product = productDAO.findProduct(code);
        }
        if (product != null) {

            CartInfo cartInfo = CartUtils.getCartInSession(request);

            ProductInfo productInfo = new ProductInfo(product);

            cartInfo.removeProduct(productInfo);

        }

        return "redirect:/shoppingCart";
    }

    @PostMapping("/shoppingCart")
    public String shoppingCartUpdateQty(HttpServletRequest request, //
            Model model, //
            @ModelAttribute("cartForm") CartInfo cartForm) {

        CartInfo cartInfo = CartUtils.getCartInSession(request);
        cartInfo.updateQuantity(cartForm);

        return "redirect:/shoppingCart";
    }

    @GetMapping("/shoppingCart")
    public String shoppingCartHandler(HttpServletRequest request, Model model) {
        CartInfo myCart = CartUtils.getCartInSession(request);

        model.addAttribute("cartForm", myCart);
        return "shoppingCart";
    }

    @GetMapping("/shoppingCartCustomer")
    public String shoppingCartCustomerForm(HttpServletRequest request, Model model) {

        CartInfo cartInfo = CartUtils.getCartInSession(request);

        if (cartInfo.isEmpty()) {

            return "redirect:/shoppingCart";
        }
        CustomerInfo customerInfo = cartInfo.getCustomerInfo();
        CustomerForm customerForm = new CustomerForm(customerInfo);
        model.addAttribute("customerForm", customerForm);

        return "shoppingCartCustomer";
    }

    // Saves customer info
    @PostMapping("/shoppingCartCustomer")
    public String shoppingCartCustomerSave(HttpServletRequest request, Model model,
            @ModelAttribute("customerForm") @Validated CustomerForm customerForm, BindingResult result,
            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            customerForm.setValid(false);
            return "shoppingCartCustomer";
        }

        customerForm.setValid(true);
        CartInfo cartInfo = CartUtils.getCartInSession(request);
        CustomerInfo customerInfo = new CustomerInfo(customerForm);
        cartInfo.setCustomerInfo(customerInfo);

        return "redirect:/shoppingCartConfirmation";
    }

    // shows info for confirmation
    @GetMapping("/shoppingCartConfirmation")
    public String shoppingCartConfirmationReview(HttpServletRequest request, Model model) {
        CartInfo cartInfo = CartUtils.getCartInSession(request);

        if (cartInfo == null || cartInfo.isEmpty()) {

            return "redirect:/shoppingCart";
        } else if (!cartInfo.isValidCustomer()) {

            return "redirect:/shoppingCartCustomer";
        }
        model.addAttribute("myCart", cartInfo);

        return "shoppingCartConfirmation";
    }

    // saves cart
    @PostMapping("/shoppingCartConfirmation")
    public String shoppingCartConfirmationSave(HttpServletRequest request, Model model) {
        CartInfo cartInfo = CartUtils.getCartInSession(request);

        if (cartInfo.isEmpty()) {

            return "redirect:/shoppingCart";
        } else if (!cartInfo.isValidCustomer()) {

            return "redirect:/shoppingCartCustomer";
        }
        try {
            orderDAO.saveOrder(cartInfo);
        } catch (Exception e) {

            return "shoppingCartConfirmation";
        }

        CartUtils.removeCartInSession(request);
        CartUtils.storeLastOrderedCartInSession(request, cartInfo);

        return "redirect:/shoppingCartFinalize";
    }

    @GetMapping("/shoppingCartFinalize")
    public String shoppingCartFinalize(HttpServletRequest request, Model model) {

        CartInfo lastOrderedCart = CartUtils.getLastOrderedCartInSession(request);

        if (lastOrderedCart == null) {
            return "redirect:/shoppingCart";
        }
        model.addAttribute("lastOrderedCart", lastOrderedCart);
        return "shoppingCartFinalize";
    }

}