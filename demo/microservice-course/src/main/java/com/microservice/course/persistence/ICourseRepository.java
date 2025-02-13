package com.microservice.course.persistence;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.microservice.course.entities.Course;

@Repository
public interface ICourseRepository  extends CrudRepository<Course, Long> {

}
