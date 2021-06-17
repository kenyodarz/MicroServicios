package co.com.personalsoft.productos.shared;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@RestController
public abstract class GenericRestController<E, ID extends Serializable> {

    private final GenericServiceAPI<E, ID> serviceAPI;

    public GenericRestController(GenericServiceAPI<E, ID> serviceAPI) {
        this.serviceAPI = serviceAPI;
    }

    // Validador de campos
    public ResponseEntity<?> validar(BindingResult result) {
        Map<String, Object> errores = new HashMap<>();
        result.getFieldErrors().forEach(err -> errores.put(err.getField(),
                " El campo " + err.getField() + " " + err.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errores);
    }

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body(serviceAPI.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable ID id) {
        var entity = serviceAPI.getOne(id);
        if (entity == null) {
            return ResponseEntity.notFound().build();
        } else return ResponseEntity.ok().body(entity);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable ID id) {
        var entity = serviceAPI.getOne(id);
        if (entity != null) {
            serviceAPI.delete(id);
        } else return ResponseEntity.notFound().build();
        return new ResponseEntity<>(entity, HttpStatus.ACCEPTED);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@Valid @RequestBody E entity, BindingResult result){
        if (result.hasErrors()) return validar(result);
        return ResponseEntity.status(HttpStatus.CREATED).body(serviceAPI.save(entity));
    }
}
