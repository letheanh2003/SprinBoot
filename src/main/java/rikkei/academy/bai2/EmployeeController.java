package rikkei.academy.bai2;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    List<Employee> employeeslist = new ArrayList<>();

    @GetMapping("/employee")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(employeeslist, HttpStatus.OK);
    }

    @PostMapping("/employee")
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        if (employeeslist.size() == 0) {
            employee.setId(1);
            employeeslist.add(employee);
            return new ResponseEntity<>(employee, HttpStatus.CREATED);
        }
        if (findByEmail(employee.getEmail())) {
            return new ResponseEntity<>("email existed ,please try again!!!!!!", HttpStatus.BAD_REQUEST);
        }
        employee.setId(employeeslist.get(employeeslist.size() - 1).getId() + 1);
        employeeslist.add(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @PutMapping("/employee")
    public ResponseEntity<?> upDate(@RequestBody Employee employee) {
        if (findByEmail(employee.getEmail())) {
            return new ResponseEntity<>("email existed ,please try again!!!!!!", HttpStatus.BAD_REQUEST);
        }
        for (Employee e : employeeslist) {
            if (e.getId() == employee.getId()) {
                e.setEmail(employee.getEmail() != null ? employee.getEmail() : e.getEmail());
                e.setName(employee.getName() != null ? employee.getName() : e.getName());
                e.setDepartment(employee.getDepartment() != null ? employee.getDepartment() : e.getDepartment());
                e.setRoles(employee.getRoles() != null ? employee.getRoles() : e.getRoles());
                return new ResponseEntity<>(e, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") int id) {
        for (int i = 0; i < employeeslist.size(); i++) {
            if (employeeslist.get(i).getId() == id) {
                employeeslist.remove(i);
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id) {
        for (Employee e : employeeslist) {
            if (e.getId() == id) ;
            return new ResponseEntity<>(e, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public boolean findByEmail(String email) {
        for (Employee e : employeeslist) {
            if (e.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}