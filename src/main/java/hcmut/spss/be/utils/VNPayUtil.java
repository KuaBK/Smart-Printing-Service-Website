package hcmut.spss.be.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

public class VNPayUtil {
    /**
     * Tạo chữ ký HMAC-SHA512
     *
     * @param key  Secret Key từ VNPay
     * @param data Chuỗi tham số đã nối
     * @return Chữ ký dạng hex
     **/
    public static String hmacSHA512(final String key, final String data) {
        try {
            if (key == null || data == null) {
                throw new NullPointerException("Key hoặc Data không được null");
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes(StandardCharsets.UTF_8);
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);

            // Chuyển kết quả hash sang dạng hex
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (Exception ex) {
            return ""; // Trả về chuỗi rỗng nếu có lỗi
        }
    }

    public static String generateQueryString(Map<String, String> params) {
        return params.entrySet().stream()
                .filter(entry -> entry.getValue() != null && !entry.getValue().isEmpty()) // Loại bỏ tham số null/rỗng
                .sorted(Map.Entry.comparingByKey()) // Sắp xếp tham số theo thứ tự từ điển
                .map(entry -> entry.getKey() + "=" + URLEncoder.encode(entry.getValue(), StandardCharsets.UTF_8)) // Mã hóa URL
                .collect(Collectors.joining("&")); // Ghép các tham số bằng "&"
    }

}