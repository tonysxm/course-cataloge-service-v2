package sx.lit.coursecatalogeservicev2.service

import org.springframework.stereotype.Service
import sx.lit.course.coursecatalogeservicev2.models.CourseDto
import sx.lit.course.coursecatalogeservicev2.models.InstructorDto
import sx.lit.coursecatalogeservicev2.exception.InstructorNotValidException
import sx.lit.coursecatalogeservicev2.helper.fromApi
import sx.lit.coursecatalogeservicev2.model.CourseEntity
import sx.lit.coursecatalogeservicev2.model.InstructorEntity
import sx.lit.coursecatalogeservicev2.repository.InstructorRepository
import java.util.*

@Service
class InstructorService(val instructorRepository: InstructorRepository) {

    fun addInstructor(instructorDTO: InstructorEntity) : InstructorDto = instructorDTO.let {
            fromApi(instructorRepository.save(InstructorEntity(null, it.name, it.courses)))
    }

    fun findAll() : List<InstructorDto> = instructorRepository.findAll().map { fromApi(it) }

    fun findByInstructorId(instructorId: Int): Optional<InstructorEntity> = instructorRepository.findById(instructorId)

    fun deleteById(instructorId: Int) = instructorRepository.deleteById(instructorId)

    fun updateInstructor(instructorDTO: InstructorDto): Unit = findByInstructorId(instructorDTO.id!!)
        .orElseThrow { InstructorNotValidException("No Instructor found with ID: ${instructorDTO.id}") }
        .let { instructorRepository.save(it.copy(name = instructorDTO.name!!))}
}
