package Undertaker.HospiBook.dto;

import Undertaker.HospiBook.model.enums.PaymentMethodEnum;
import Undertaker.HospiBook.model.enums.PaymentStatusEnum;

import java.time.LocalDateTime;

public class PaymentDTO {
    private PaymentMethodEnum method;
    private PaymentStatusEnum paymentStatusEnum;

    public PaymentDTO(PaymentMethodEnum method, PaymentStatusEnum paymentStatusEnum) {
        this.method = method;
        this.paymentStatusEnum = paymentStatusEnum;
    }

    public PaymentStatusEnum getPaymentStatusEnum() {
        return paymentStatusEnum;
    }

    public void setPaymentStatusEnum(PaymentStatusEnum paymentStatusEnum) {
        this.paymentStatusEnum = paymentStatusEnum;
    }

    public PaymentMethodEnum getMethod() {
        return method;
    }

    public void setMethod(PaymentMethodEnum method) {
        this.method = method;
    }
}
