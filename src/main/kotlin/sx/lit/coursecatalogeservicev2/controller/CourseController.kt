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
    override fun addCourse(createCourseRequest: CreateCourseRequest): ResponseEntity<CreateCourseResponse> {
        return ResponseEntity(CreateCourseResponse(listOf(courseService.addCourse(CourseDto(name = createCourseRequest.name, catagory = createCourseRequest.category, instructorId = createCourseRequest.instructorId)))), HttpStatus.OK)
    }

    override fun deleteCourseById(courseId: UUID): ResponseEntity<Unit> {
        return super.deleteCourseById(courseId)
    }

    override fun getAllCourses(): ResponseEntity<GetAllCoursesResponse> {
        return ResponseEntity(GetAllCoursesResponse(courseService.getAllCourses()), HttpStatus.OK)
    }

    override fun getCourseById(courseId: UUID): ResponseEntity<GetCourseResponse> {
        return super.getCourseById(courseId)
    }

    override fun updateCourseById(courseId: UUID, updateCourseRequest: UpdateCourseRequest): ResponseEntity<Unit> {
        return super.updateCourseById(courseId, updateCourseRequest)
    }
}
