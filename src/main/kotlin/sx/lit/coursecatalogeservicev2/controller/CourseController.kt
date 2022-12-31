package sx.lit.coursecatalogeservicev2.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController
import sx.lit.course.coursecatalogeservicev2.api.Course
import sx.lit.course.coursecatalogeservicev2.models.*
import sx.lit.coursecatalogeservicev2.service.CourseService
import java.util.*

@RestController
class CourseController(private val courseService: CourseService) : Course {
    override fun addCourse(courseDto: CourseDto): ResponseEntity<CourseDto> {
        return ResponseEntity(courseService.addCourse(CourseDto(name = courseDto.name, catagory = courseDto.catagory,
            instructorId = courseDto.instructorId)), HttpStatus.CREATED)
    }

    override fun deleteCourseById(courseId: UUID): ResponseEntity<Unit> {
        return ResponseEntity(courseService.delete(courseId), HttpStatus.NO_CONTENT)
    }

    override fun getAllCourses(): ResponseEntity<List<CourseDto>> {
        return ResponseEntity(courseService.getAllCourses(), HttpStatus.OK)
    }

    override fun getCourseById(courseId: UUID): ResponseEntity<CourseDto> {
        return ResponseEntity(courseService.getCourseById(courseId), HttpStatus.OK)
    }

    override fun updateCourseById(courseId: UUID, updateCourseRequest: UpdateCourseRequest): ResponseEntity<Unit> {
        return ResponseEntity(courseService
            .updateCourse(CourseDto(id = courseId,name = updateCourseRequest.name, catagory = updateCourseRequest.category,
                    instructorId = updateCourseRequest.instructorId)), HttpStatus.NO_CONTENT)
    }
}
