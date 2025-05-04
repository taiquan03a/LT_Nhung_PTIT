//package PTIT.IOT.Service.Impl;
//
//import PTIT.IOT.Dto.ChartDto;
//import PTIT.IOT.Dto.Count;
//import PTIT.IOT.Dto.DataDto;
//import PTIT.IOT.Model.Device;
//import PTIT.IOT.Repository.DeviceRepository;
//import PTIT.IOT.Service.DeviceService;
//import PTIT.IOT.Service.DeviceSpecification;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.domain.Sort;
//import org.springframework.data.jpa.domain.Specification;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.*;
//
//@Service
//@RequiredArgsConstructor
//public class DeviceServiceImpl implements DeviceService {
//    final private DeviceRepository deviceRepository;
//
//    @Override
//    public ResponseEntity<?> devicePage(int page, int size, Sort sort,String searchBy,String searchValues) {
//        Pageable pageable = PageRequest.of(page, size, sort);
//        Specification<Device> spec = null;
//        if (searchBy != null && !searchBy.isEmpty() && searchValues != null && !searchValues.isEmpty()) {
//            spec = Specification.where(DeviceSpecification.hasAttribute(searchBy, searchValues));
//        }
//        return new ResponseEntity<>(deviceRepository.findAll(spec,pageable), HttpStatus.OK);
//    }
//
//    @Override
//    public ResponseEntity<?> countRandom() {
//        LocalDate specificDate = LocalDate.of(2024, 10, 23);
//        LocalDateTime startOfDay = specificDate.atStartOfDay(); // 2024-10-23 00:00:00
//        LocalDateTime endOfDay = specificDate.atTime(LocalTime.MAX); // 2024-10-23 23:59:59
//        Count count = new Count();
//        count.setCount(deviceRepository.countRandom(startOfDay, endOfDay));
//        return new ResponseEntity<>(count, HttpStatus.OK);
//    }
//
//}
