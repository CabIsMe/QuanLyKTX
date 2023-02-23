package root.quanlyktx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import root.quanlyktx.dto.HopDongKTXDTO;
import root.quanlyktx.service.HopDongKTXService;

import java.util.List;

@RestController
@RequestMapping("/api/hopdong")
public class HopDongKTXController {
    @Autowired
    HopDongKTXService hopDongKTXService;
    @GetMapping("/")
    public List<HopDongKTXDTO> getAll(){ return hopDongKTXService.getAll();}
    @GetMapping("/{id}")
    public HopDongKTXDTO getById(@PathVariable("id") Integer id){
       return hopDongKTXService.getById(id);
    }
    @GetMapping("/phong/{idphongktx}")
    public List<HopDongKTXDTO> getByIdPhongKTX(@PathVariable("idphongktx") Integer idphongktx){
        return hopDongKTXService.getByPhongKTX(idphongktx);
    }
}
