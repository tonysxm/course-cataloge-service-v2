package sx.lit.coursecatalogeservicev2.service

import org.springframework.stereotype.Service
import sx.lit.course.coursecatalogeservicev2.models.CourseDto
import sx.lit.course.coursecatalogeservicev2.models.InstructorDto
import sx.lit.coursecatalogeservicev2.exception.CourseNotValidException
import sx.lit.coursecatalogeservicev2.exception.InstructorNotValidException
import sx.lit.coursecatalogeservicev2.model.CourseEntity
import sx.lit.coursecatalogeservicev2.repository.CourseRepository
import java.util.*

@Service
class CourseService(private val courseRepository: CourseRepository, val instructorService: InstructorService) {
    fun getAllCourses(): List<CourseDto> = courseRepository.findAll()
            .map { CourseDto(id = it?.id, name = it.name, catagory = it.category)}

    fun addCourse(courseDto: CourseDto): CourseDto = instructorService.findByInstructorId(courseDto.instructorId!!)
        .orElseThrow { InstructorNotValidException("No Instructor found with ID: ${courseDto.instructorId}") }
        .let { courseRepository.save(CourseEntity(id = null, name = courseDto.name!!, category = courseDto.catagory!!, instructorEntity = it)) }
        .let { courseDto.copy(id = it.id) }

    fun getCourseById(courseId: UUID): CourseDto = courseRepository.findById(courseId)
        .map { CourseDto(it.id, it.name, it.category, it.instructorEntity?.id!!) }
        .orElseThrow { CourseNotValidException("No Course found with id: $courseId") }

    fun delete(courseId: UUID) = courseRepository.deleteById(courseId)

    fun updateCourse(courseDto: CourseDto): Unit =
        instructorService.findByInstructorId(courseDto.instructorId!!)
            .orElseThrow { InstructorNotValidException("No Instructor found with ID: ${courseDto.instructorId}") }
            .let { val courseEntity = courseRepository.findById(courseDto.id!!)
                        .orElseThrow { CourseNotValidException("No Course found with id: ${courseDto.id}") }
                    courseRepository.save(courseEntity.copy(name = courseDto.name!!, category = courseDto.catagory!!, instructorEntity = it))
                }
}
