package web.example.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import web.example.demo.model.City;

public interface CityRepository extends JpaRepository<City, Integer> {
}
