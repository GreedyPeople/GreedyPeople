package com.sparta.greeypeople.order.controller;

import com.sparta.greeypeople.auth.security.UserDetailsImpl;
import com.sparta.greeypeople.common.DataCommonResponse;
import com.sparta.greeypeople.common.StatusCommonResponse;
import com.sparta.greeypeople.order.dto.request.OrderRequestDto;
import com.sparta.greeypeople.order.dto.response.OrderResponseDto;
import com.sparta.greeypeople.order.service.OrderService;
import com.sparta.greeypeople.review.dto.response.ReviewResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private OrderService orderService;

    @PostMapping("/stores/{stordId}/order")
    public ResponseEntity<DataCommonResponse<OrderResponseDto>>createOrder(
        @PathVariable Long stordId,
        @RequestBody OrderRequestDto orderRequest,
        @AuthenticationPrincipal UserDetailsImpl userDetails){
        OrderResponseDto responseDto = orderService.createOrder(stordId, orderRequest, userDetails.getUser());
        DataCommonResponse<OrderResponseDto> response = new DataCommonResponse<>(201,"주문 작성 성공", responseDto);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/orders/{orderId}")
    public ResponseEntity<DataCommonResponse<OrderResponseDto>>getOrder(
        @PathVariable Long orderId
    ){
        OrderResponseDto order = orderService.getOrder(orderId);
        DataCommonResponse<OrderResponseDto> response = new DataCommonResponse<>(200, "주문 단건 조회 성공", order);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/orders")
    public ResponseEntity<DataCommonResponse<List<OrderResponseDto>>>getAllOrder(){
        List<OrderResponseDto> orders = orderService.getAllOrder();
        DataCommonResponse<List<OrderResponseDto>> response = new DataCommonResponse<>(200, "주문 전체 조회 성공", orders);
        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @PutMapping("/orders/{orderId}")
    public ResponseEntity<DataCommonResponse<OrderResponseDto>>updateOrder(
        @PathVariable Long orderId,
        @RequestBody OrderRequestDto orderRequestDto,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ){
        OrderResponseDto responseDto = orderService.updateOrdeer(orderId,orderRequestDto,userDetails.getUser());
        DataCommonResponse<OrderResponseDto> response = new DataCommonResponse<>(200, "리뷰 수정 성공", responseDto);

        return new ResponseEntity<>(response,HttpStatus.OK);
    }

    @DeleteMapping("/orders/{orderId}")
    public ResponseEntity<StatusCommonResponse>deleteOrder(
        @PathVariable Long orderId,
        @AuthenticationPrincipal UserDetailsImpl userDetails){
        orderService.deleteOrder(orderId, userDetails.getUser());
        StatusCommonResponse response = new StatusCommonResponse(200,"리뷰 삭제 성공");
        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}