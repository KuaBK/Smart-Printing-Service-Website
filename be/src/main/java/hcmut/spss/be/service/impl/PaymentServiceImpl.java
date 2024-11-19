package hcmut.spss.be.service.impl;

import hcmut.spss.be.dtos.request.BuyPrintingPageRequest;
import hcmut.spss.be.dtos.response.PaymentResponse;
import hcmut.spss.be.dtos.response.TransactionResponse;
import hcmut.spss.be.entity.discount.Discount;
import hcmut.spss.be.entity.payment.Payment;
import hcmut.spss.be.entity.payment.PaymentMethod;
import hcmut.spss.be.entity.user.User;
import hcmut.spss.be.repository.DiscountRepository;
import hcmut.spss.be.repository.PaymentRepository;
import hcmut.spss.be.repository.UserRepository;
import hcmut.spss.be.service.PaymentService;
import hcmut.spss.be.utils.AuthUtil;
import hcmut.spss.be.utils.VNPayUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentServiceImpl implements PaymentService {
    @Value("${payment.vnPay.tmnCode}")
    private String vnp_TmnCode;

    @Value("${payment.vnPay.secretKey}")
    private String vnp_HashSecret;

    @Value("${payment.vnPay.url}")
    private String vnp_PaymentUrl;

    @Value("${payment.vnPay.returnUrl}")
    private String vnp_ReturnUrl;

    @Autowired
    private AuthUtil authUtil;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscountRepository discountRepository;

    public PaymentResponse createPaymentURL(int numberPages, long codeId) {
        try {
            // calc amount
            String amount = String.valueOf(numberPages*500); // hardcode price
            String vnp_Version = "2.1.0";
            String vnp_Command = "pay";
            String vnp_OrderInfo = "Thanh toan don hang mua trang in";
            String orderType = "100000";
            String vnp_TxnRef = String.valueOf(System.currentTimeMillis()); // Mã đơn hàng unique
            String vnp_IpAddr = "127.0.0.1"; // IP của client
            String vnp_Locale = "vn";
            String vnp_CurrCode = "VND";
            String vnp_Amount = String.valueOf(Integer.parseInt(amount) * 100); // Số tiền tính bằng đồng (x100 để ra VNĐ)

            String vnp_BankCode = ""; // Để trống nếu không muốn chọn ngân hàng

            Map<String, String> vnp_Params = new HashMap<>();
            vnp_Params.put("vnp_Version", vnp_Version);
            vnp_Params.put("vnp_Command", vnp_Command);
            vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
            vnp_Params.put("vnp_Amount", vnp_Amount);
            vnp_Params.put("vnp_CurrCode", vnp_CurrCode);
            vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
            vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
            vnp_Params.put("vnp_OrderType", orderType);
            vnp_Params.put("vnp_Locale", vnp_Locale);
            vnp_Params.put("vnp_ReturnUrl", vnp_ReturnUrl+"/"+codeId);
            vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String vnpCreateDate = formatter.format(calendar.getTime());
            vnp_Params.put("vnp_CreateDate", vnpCreateDate);
            calendar.add(Calendar.MINUTE, 15);
            String vnp_ExpireDate = formatter.format(calendar.getTime());
            vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

            // Tạo chuỗi query string
            // Sắp xếp tham số theo thứ tự từ điển
            String sortedParams = vnp_Params.entrySet().stream()
                    .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty()) // Bỏ tham số null hoặc rỗng
                    .sorted(Map.Entry.comparingByKey()) // Sắp xếp theo key
                    .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8)) // Mã hóa giá trị
                    .collect(Collectors.joining("&")); // Ghép các tham số bằng '&'

            // Redirect user đến VNPay
            String secureHash = VNPayUtil.hmacSHA512(vnp_HashSecret, sortedParams);
            String paymentUrl = vnp_PaymentUrl + "?" + sortedParams + "&vnp_SecureHash=" + secureHash;
            return PaymentResponse.builder().status(200).message("Success").url(paymentUrl).build();

        } catch (Exception e) {
            return PaymentResponse.builder().status(500).message("Internal Server Error").build();
        }
    }


    @Override
    public PaymentResponse buyPrintingPage(BuyPrintingPageRequest request) {
        if(request.getDiscountCode().isEmpty()){
            return createPaymentURL(request.getPage(), 0);
        }
        Discount discount = discountRepository.findByDiscountCode(request.getDiscountCode()).orElseThrow(() -> new RuntimeException("Discount Code Not Found"));
        User student = authUtil.loggedInUser();
        // check used
        if(student.getDiscounts().contains(discount)){
            throw new RuntimeException("Discount Code Already Used");
        }
        // check expiry
        if (discount.getExpirationDate().isBefore(LocalDate.now())){
            throw new RuntimeException("Discount Code Expired");
        }
        int numberPages = request.getPage();
        numberPages -= discount.getPagesFree();
        student.getDiscounts().add(discount);
        userRepository.save(student);
        return  createPaymentURL(numberPages, discount.getDiscountId());
    }

    @Override
    public PaymentResponse handleResponse(long codeId, HttpServletRequest request) {
        try {
            // Lấy các tham số từ request callback
            Map<String, String> params = new HashMap<>();
            request.getParameterMap().forEach((key, value) -> params.put(key, value[0]));

            // Lấy và loại bỏ vnp_SecureHash để kiểm tra chữ ký
            String secureHash = params.remove("vnp_SecureHash");
            if (secureHash == null || secureHash.isEmpty()) {
                throw new RuntimeException("Thiếu chữ ký bảo mật!");
            }

            // Tạo chuỗi dữ liệu để so khớp chữ ký
            String hashData = VNPayUtil.hmacSHA512(vnp_HashSecret, VNPayUtil.generateQueryString(params));

            // Kiểm tra chữ ký
            if (!secureHash.equals(hashData)) {
                throw new RuntimeException("Chữ ký không hợp lệ!");
            }

            // Lấy mã phản hồi từ VNPay
            String responseCode = params.get("vnp_ResponseCode");
            if ("00".equals(responseCode)) {
                // Xử lý khi thanh toán thành công
                //thay doi so giay in cua sinh vien
                User student = authUtil.loggedInUser();
                String amount = params.get("vnp_Amount");
                int numberPages = Integer.parseInt(amount)/500/100;
                student.setNumOfPrintingPages(student.getNumOfPrintingPages()+numberPages);
                userRepository.save(student);

                Discount discount = discountRepository.findById(codeId).orElse(null);


                // save log
                Payment payment = Payment.builder()
                        .amountPaid(Double.parseDouble(amount)/100)
                        .paymentMethod(PaymentMethod.BANK)
                        .numberOfPagesBought(numberPages+(discount==null? 0:discount.getPagesFree()))
                        .student(student)
                        .discount(discount).build();
                paymentRepository.save(payment);

                return PaymentResponse.builder().status(200).message("Thanh toán thành công!").build();
            } else {
                // Xử lý khi thanh toán thất bại
                return PaymentResponse.builder().status(Integer.parseInt(responseCode)).message("Thanh toán thất bại!").build();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<TransactionResponse> getTransactionsOfCurrentUser() {
        List<Payment> payments = paymentRepository.findAllByStudent(authUtil.loggedInUser());
        return payments.stream().map(TransactionResponse::toTransactionResponse).toList();
    }

    @Override
    public List<TransactionResponse> getAllTransactions() {
        return paymentRepository.findAll().stream().map(TransactionResponse::toTransactionResponse).toList();
    }
}
