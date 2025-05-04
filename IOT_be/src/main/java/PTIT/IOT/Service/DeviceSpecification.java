//package PTIT.IOT.Service;
//
//import PTIT.IOT.Model.Device;
//import jakarta.persistence.criteria.*;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//
//public class DeviceSpecification {
//    public static Specification<Device> hasAttribute(String attributeName, String attributeValue) {
//        return (root, query, criteriaBuilder) -> {
//            Path<Object> attribute = root.get(attributeName);
//            if (attribute.getJavaType() == String.class) {
//                return criteriaBuilder.like(attribute.as(String.class), "%" + attributeValue + "%");
//            } else if (attribute.getJavaType() == Long.class) {
//                return criteriaBuilder.equal(attribute.as(Long.class), Long.parseLong(attributeValue));
//            } else if (attribute.getJavaType() == LocalDateTime.class) {
//                if (attributeValue.length() == 10) { // Chỉ có ngày (yyyy-MM-dd)
//                    LocalDate date = LocalDate.parse(attributeValue, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
//                    return criteriaBuilder.equal(criteriaBuilder.function("DATE", LocalDate.class, attribute), date);
//                } else if (attributeValue.length() == 8) { // Chỉ có giờ (HH:mm:ss)
//                    LocalTime time = LocalTime.parse(attributeValue, DateTimeFormatter.ofPattern("HH:mm:ss"));
//                    return criteriaBuilder.equal(criteriaBuilder.function("TIME", LocalTime.class, attribute), time);
//                }else if (attributeValue.length() == 5) { // Chỉ có giờ và phút (HH:mm)
//                    LocalTime time = LocalTime.parse(attributeValue, DateTimeFormatter.ofPattern("HH:mm"));
//                    // Lấy phần HOUR và MINUTE từ LocalDateTime để so sánh
//                    Expression<Integer> hour = criteriaBuilder.function("HOUR", Integer.class, attribute);
//                    Expression<Integer> minute = criteriaBuilder.function("MINUTE", Integer.class, attribute);
//                    // So sánh cả giờ và phút, bỏ qua giây
//                    return criteriaBuilder.and(
//                            criteriaBuilder.equal(hour, time.getHour()),
//                            criteriaBuilder.equal(minute, time.getMinute())
//                    );
//                }else if (attributeValue.length() == 2) { // Chỉ có giờ và phút (HH:mm)
//                    int hour = Integer.parseInt(attributeValue);
//                    // Sử dụng hàm EXTRACT để lấy phần giờ của LocalDateTime
//                    return criteriaBuilder.equal(criteriaBuilder.function("HOUR", Integer.class, attribute), hour);
//                }
//                else { // Cả ngày và giờ (yyyy-MM-ddTHH:mm:ss)
//                    LocalDateTime dateTime = LocalDateTime.parse(attributeValue, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
//                    return criteriaBuilder.equal(attribute.as(LocalDateTime.class), dateTime);
//                }
//            }
//            return null;
//        };
//    }
//}
