package com.atguigu.gmall.order.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.atguigu.gmall.bean.UserAddress;
import com.atguigu.gmall.usermanage.service.UserAddressService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
public class OrderController {
    //调用service
    @Reference
    private UserAddressService userAddressService;
@RequestMapping("trade")
    public List<UserAddress> trade(HttpServletRequest request){
    String userId = request.getParameter("userId");
    List<UserAddress> userAddressList = userAddressService.getAddressList(userId);
    return userAddressList;

}
}
