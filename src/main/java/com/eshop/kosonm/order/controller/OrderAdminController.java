package com.eshop.kosonm.order.controller;

import java.util.List;

import com.eshop.kosonm.order.OrderDao;
import com.eshop.kosonm.order.OrderDetailInfo;
import com.eshop.kosonm.order.OrderInfo;
import com.eshop.kosonm.pagination.PaginationResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Transactional
public class OrderAdminController {

   private OrderDao orderDao;

   @Autowired
   public OrderAdminController(OrderDao orderDao) {
      this.orderDao = orderDao;
   }

   @GetMapping("/admin/orderList")
   public String orderList(Model model, @RequestParam(value = "page", defaultValue = "1") String pageStr) {
      int page;
      page = Integer.parseInt(pageStr);
      final int MAX_RESULT = 5;
      final int MAX_NAVIGATION_PAGE = 10;

      PaginationResult<OrderInfo> paginationResult = orderDao.listOrderInfo(page, MAX_RESULT, MAX_NAVIGATION_PAGE);

      model.addAttribute("paginationResult", paginationResult);
      return "orderList";
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