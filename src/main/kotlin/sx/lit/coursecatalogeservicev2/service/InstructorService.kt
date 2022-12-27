package sx.lit.coursecatalogeservicev2.service

import org.springframework.stereotype.Service
import sx.lit.course.coursecatalogeservicev2.models.CourseDto
import sx.lit.course.coursecatalogeservicev2.models.InstructorDto
import sx.lit.coursecatalogeservicev2.exception.InstructorNotValidException
import sx.lit.coursecatalogeservicev2.model.CourseEntity
import sx.lit.coursecatalogeservicev2.model.InstructorEntity
import sx.lit.coursecatalogeservicev2.repository.InstructorRepository
import java.util.*

@Service
class InstructorService(val instructorRepository: InstructorRepository) {
    fun addInstructor(instructorDTO: InstructorEntity) : InstructorDto {
        val instructorEntity = instructorDTO.let {
            InstructorEntity(null, it.name, it.courses)
        }

        return instructorRepository.save(instructorEntity).let { instructor ->
            InstructorDto(instructor.id, instructor.name, instructor.courses.map { CourseDto(it.id, it.name, it.category, it.instructorEntity?.id) })
        }
    }

    fun findAll() : List<InstructorDto> = instructorRepository.findAll().map { instructor ->
            InstructorDto(instructor.id, instructor.name, instructor.courses.map { CourseDto(it.id, it.name, it.category, it.instructorEntity?.id) })
        }

    fun findByInstructorId(instructorId: Int): Optional<InstructorEntity> = instructorRepository.findById(instructorId)

    fun deleteById(instructorId: Int) = instructorRepository.deleteById(instructorId)

    fun updateInstructor(instructorDTO: InstructorDto) {
        val instructor = findByInstructorId(instructorDTO.id!!)

        if(!instructor.isPresent) {
            throw InstructorNotValidException("No Instructor found with ID: ${instructorDTO.id}")
        }

        val instructorEntity = instructor.get()
        instructorEntity.name = instructorDTO.name!!;

        instructorRepository.save(instructorEntity)
    }
}
