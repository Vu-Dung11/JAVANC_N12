package com.example.backendTeam12.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.backendTeam12.dto.HomestaySummaryDTO;
import com.example.backendTeam12.model.Homestay;

public interface  HomestayRepository extends JpaRepository<Homestay, Long>{

    public List<Homestay> findByOwnerUserId(Long userId);

    public List<Homestay> findByNameContainingOrDescriptionContaining(String keyword, String keyword0);

    public List<Homestay> findByProvinceAndDistrictAndWard(String province, String district, String ward);

    @Query("SELECT h.homestayId FROM Homestay h WHERE h.name = :name")
    Long findIdByName(@Param("name") String name);
 
  @Query("SELECT new com.example.backendTeam12.dto.HomestaySummaryDTO(" +
       "h.name, CONCAT(h.ward, ', ', h.district, ', ', h.province), AVG(DISTINCT r.price), AVG(rev.rate)) " +
       "FROM Homestay h " +
       "JOIN h.rooms r " +
       "LEFT JOIN h.reviews rev " +
       "GROUP BY h.homestayId, h.name, h.ward, h.district, h.province")
    List<HomestaySummaryDTO> findHomestaysWithAvgPriceAndEmptyRoomType();
}
