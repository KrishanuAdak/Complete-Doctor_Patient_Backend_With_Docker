package com.example.demo1.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo1.model.Payments;
@Repository
public interface PaymentsRepo extends JpaRepository<Payments,Integer>{
    @Query(value="select * from payments where razorpay_order_id=:razorpayOrderId",nativeQuery=true)
    public Payments findByRazorpayOrderId(String razorpayOrderId);

    public Payments findByIdempotencyKey(String idempotencyKey);
}
