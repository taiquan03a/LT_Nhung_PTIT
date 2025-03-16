package PTIT.IOT.Service.Impl;

import PTIT.IOT.Dto.ChartDto;
import PTIT.IOT.Dto.Count;
import PTIT.IOT.Dto.DataDto;
import PTIT.IOT.Model.Device;
import PTIT.IOT.Repository.DeviceRepository;
import PTIT.IOT.Service.DeviceService;
import PTIT.IOT.Service.DeviceSpecification;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {
    final private DeviceRepository deviceRepository;
    @Override
    public ResponseEntity<?> getDataChart() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//        List<Device> devices = deviceRepository.findFirst12();
//        List<DataDto> tem = new ArrayList<>();
//        List<DataDto> hum = new ArrayList<>();
//        List<DataDto> light = new ArrayList<>();
//        List<DataDto> dark = new ArrayList<>();
//        List<ChartDto> chart = new ArrayList<>();
//
//        for(Device device : devices) {
//            DataDto t = DataDto.builder()
//                    .x(String.valueOf(device.getTime().format(formatter)))
//                    .y(device.getTemperature())
//                    .build();
//            DataDto h = DataDto.builder()
//                    .x(String.valueOf(device.getTime().format(formatter)))
//                    .y(device.getHumidity())
//                    .build();
//            DataDto l = DataDto.builder()
//                    .x(String.valueOf(device.getTime().format(formatter)))
//                    .y(device.getLight())
//                    .build();
////            DataDto d = DataDto.builder()
////                    .x(String.valueOf(device.getTime().format(formatter)))
////                    .y(String.valueOf(Double.parseDouble(device.getLight()) - 10))
////                    .build();
//            tem.add(t);
//            hum.add(h);
//            light.add(l);
//            //dark.add(d);
//        }
//        Collections.reverse(tem);
//        Collections.reverse(hum);
//        Collections.reverse(light);
//        ChartDto te = ChartDto.builder()
//                .id("temperature")
//                .color("red")
//                .data(tem)
//                .build();
//        ChartDto hu =  ChartDto.builder()
//                .id("humidity")
//                .color("blue")
//                .data(hum)
//                .build();
//        ChartDto li = ChartDto.builder()
//                .id("light")
//                .color("yellow")
//                .data(light)
//                .build();
////        ChartDto da = ChartDto.builder()
////                .id("dark")
////                .color("black")
////                .data(dark)
////                .build();
//        chart.add(te);
//        chart.add(hu);
//        chart.add(li);
//        //chart.add(da);
        //return new ResponseEntity<>(chart, HttpStatus.OK);
        return null;
    }

    @Override
    public ResponseEntity<?> getValues() {
//        Device device = deviceRepository.findFirst();
//        System.out.println(device.getLight());
//        device.setLight(String.valueOf(4100*Double.valueOf(device.getLight())/100));
//        System.out.println(device.getLight());
//        return new ResponseEntity<>(device, HttpStatus.OK);
        return null;
    }

    @Override
    public ResponseEntity<?> devicePage(int page, int size, Sort sort,String searchBy,String searchValues) {
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Device> spec = null;
        if (searchBy != null && !searchBy.isEmpty() && searchValues != null && !searchValues.isEmpty()) {
            spec = Specification.where(DeviceSpecification.hasAttribute(searchBy, searchValues));
        }
        return new ResponseEntity<>(deviceRepository.findAll(spec,pageable), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> countRandom() {
        LocalDate specificDate = LocalDate.of(2024, 10, 23);
        LocalDateTime startOfDay = specificDate.atStartOfDay(); // 2024-10-23 00:00:00
        LocalDateTime endOfDay = specificDate.atTime(LocalTime.MAX); // 2024-10-23 23:59:59
        Count count = new Count();
        count.setCount(deviceRepository.countRandom(startOfDay, endOfDay));
        return new ResponseEntity<>(count, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getChartRandom() {
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
//        List<Device> devices = deviceRepository.findFirst12();
//        List<DataDto> ran = new ArrayList<>();
//        List<ChartDto> chart = new ArrayList<>();
//        for(Device device : devices) {
//            DataDto t = DataDto.builder()
//                    .x(String.valueOf(device.getTime().format(formatter)))
//                    .y(String.valueOf(device.getRandom()))
//                    .build();
//            ran.add(t);
//        }
//        Collections.reverse(ran);
//        ChartDto te = ChartDto.builder()
//                .id("random")
//                .color("red")
//                .data(ran)
//                .build();
//        chart.add(te);
//        return new ResponseEntity<>(chart, HttpStatus.OK);
        return null;
    }
}
