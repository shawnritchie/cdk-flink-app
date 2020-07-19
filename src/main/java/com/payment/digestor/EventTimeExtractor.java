package com.payment.digestor;

import com.payment.digestor.dto.PaymentWrapper;
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks;
import org.apache.flink.streaming.api.watermark.Watermark;

public class EventTimeExtractor implements AssignerWithPeriodicWatermarks<PaymentWrapper> {

    @Override
    public long extractTimestamp(PaymentWrapper element, long previousElementTimestamp) {
        return System.currentTimeMillis();
    }

    @Override
    public Watermark getCurrentWatermark() {
        return new Watermark(System.currentTimeMillis() - 5000);
    }

}
