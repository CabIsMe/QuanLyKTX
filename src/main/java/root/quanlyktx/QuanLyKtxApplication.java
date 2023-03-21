package root.quanlyktx;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import root.quanlyktx.service.HopDongKTXService;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableScheduling

//@SpringBootApplication
public class QuanLyKtxApplication {
    public static void main(String[] args) {

        SpringApplication.run(QuanLyKtxApplication.class, args);
    }

}
