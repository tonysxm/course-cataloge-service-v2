package sx.lit.coursecatalogeservicev2.service

import org.springframework.stereotype.Service
import sx.lit.course.coursecatalogeservicev2.models.CourseDto
import sx.lit.coursecatalogeservicev2.exception.InstructorNotValidException
import sx.lit.coursecatalogeservicev2.model.CourseEntity
import sx.lit.coursecatalogeservicev2.repository.CourseRepository

@Service
class CourseService(private val courseRepository: CourseRepository, val instructorService: InstructorService) {
    fun getAllCourses(): List<CourseDto> {
        return courseRepository.findAll()
            .map { CourseDto(id = it?.id, name = it.name, catagory = it.category)}
    }

    fun addCourse(courseDto: CourseDto): CourseDto {
        val instructor = instructorService.findByInstructorId(courseDto.instructorId!!)

        if(!instructor.isPresent) {
            throw InstructorNotValidException("No Instructor found with ID: ${courseDto.instructorId}")
        }

        val courseEntity = courseDto.let {
            CourseEntity(null, it.name!!, it.catagory!!, instructor.get())
        }

        return courseRepository.save(courseEntity).let {
            CourseDto(it.id, it.name, it.category, it.instructorEntity?.id)
        }
    }
}
