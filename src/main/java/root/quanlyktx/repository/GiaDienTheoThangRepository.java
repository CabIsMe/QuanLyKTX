package root.quanlyktx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import root.quanlyktx.entity.GiaDienTheoThang;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public interface GiaDienTheoThangRepository extends JpaRepository<GiaDienTheoThang, Integer> {
    GiaDienTheoThang findGiaDienTheoThangByNamAndThang(Integer nam, Integer thang);


    default List<Integer> getPreviousMonthAndYear(){
        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
        LocalDate currentDate = LocalDate.now(zoneId);
        int currentMonth = currentDate.getMonthValue();
        int currentYear = currentDate.getYear();
        YearMonth previousMonth = YearMonth.of(currentYear, currentMonth).minusMonths(1);
        Integer previousMonthValue = previousMonth.getMonthValue();
        Integer previousYearValue = previousMonth.getYear();
        List<Integer> list= new ArrayList<>();
        list.add(previousMonthValue);
        list.add(previousYearValue);
        return list;
    }
}
