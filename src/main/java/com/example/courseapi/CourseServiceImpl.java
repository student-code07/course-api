
package com.example.courseapi;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(int id) {
        return courseRepository.findById(id).orElse(null);
    }

    @Override
    public Course addCourse(Course course) {
        course.setId(0);
        return courseRepository.save(course);
    }

    @Override
    public Course updateCourse(int id, Course updatedCourse) {
        Course existingCourse = courseRepository.findById(id).orElse(null);

        if (existingCourse == null) {
            return null;
        }

        existingCourse.setName(updatedCourse.getName());
        existingCourse.setInstructor(updatedCourse.getInstructor());

        return courseRepository.save(existingCourse);
    }

    @Override
    public boolean deleteCourse(int id) {
        if (!courseRepository.existsById(id)) {
            return false;
        }

        courseRepository.deleteById(id);
        return true;
    }
}