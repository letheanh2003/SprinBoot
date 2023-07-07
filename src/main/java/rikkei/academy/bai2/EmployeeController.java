package rikkei.academy.bai2;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EmployeeController {
    List<Employee> employees = new ArrayList<>();

    @GetMapping("/employee")
    public ResponseEntity<?> getAll() {
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @PostMapping("/employee")
    public ResponseEntity<?> create(@RequestBody Employee employee) {
        if (findByEmail(employee.getEmail())) {
            return new ResponseEntity<>("Email already exists, please try again!", HttpStatus.BAD_REQUEST);
        }

        if (employees.size() == 0) {
            employee.setId(1);
        } else {
            employee.setId(employees.get(employees.size() - 1).getId() + 1);
        }

        employees.add(employee);
        return new ResponseEntity<>(employee, HttpStatus.CREATED);
    }

    @PutMapping("/employee")
    public ResponseEntity<?> update(@RequestBody Employee employee) {
        if (findByEmail(employee.getEmail())) {
            return new ResponseEntity<>("Email already exists, please try again!", HttpStatus.BAD_REQUEST);
        }
        for (Employee e : employees) {
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
        for (int i = 0; i < employees.size(); i++) {
            if (employees.get(i).getId() == id) {
                employees.remove(i);
                return new ResponseEntity<>("Delete Success", HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/employee/{id}")
    public ResponseEntity<?> findById(@PathVariable("id") int id) {
        for (Employee e : employees) {
            if (e.getId() == id) {
                return new ResponseEntity<>(e, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public boolean findByEmail(String email) {
        for (Employee e : employees) {
            if (e.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
}
